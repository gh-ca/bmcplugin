package com.huawei.storage.job;

import com.huawei.storage.constants.BoxTypeConst;
import com.huawei.storage.constants.ConnectionVO;
import com.huawei.storage.constants.ModelCapability;
import com.huawei.storage.domain.ManagedObjects;
import com.huawei.storage.domain.StorageObject;
import com.huawei.storage.domain.StorageObjectType;
import com.huawei.storage.exception.ETLException;
import com.huawei.storage.oceanstor.rest.exception.RestException;
import com.huawei.storage.oceanstor.rest.operation.DeviceManager;
import com.huawei.storage.oceanstor.rest.operation.OceanStorOperation;
import com.huawei.storage.oceanstor.rest.operation.OperationResult;
import com.huawei.storage.utils.CommonUtils;
import com.huawei.storage.utils.ReflectionUtils;
import com.huawei.storage.utils.RestPageUtils;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Huawei Technologies  all rights reserved
 */
public class RestJob implements Callable<List<StorageObject>> {

    private ConnectionVO connectionData;
    private Logger log = Logger.getLogger(RestJob.class);


    public RestJob(ConnectionVO connectionData) {
        this.connectionData = connectionData;
    }

    /**
     * get all storageObject instance info from rest
     * @return  all object instance
     * @throws Exception
     */
    public List<StorageObject> call() throws Exception {
        List<StorageObjectType> storageObjectTypes = getAllStorageObjectType();
        List<StorageObject> storageObjects = getAllStorageObjectInstance(storageObjectTypes);
        return storageObjects;
    }

    private List<StorageObjectType> getAllStorageObjectType() {
        InputStream ips = this.getClass().getResourceAsStream("/conf/MOList.xml");
        ManagedObjects mos = CommonUtils.makeBeanFromXML(ips, ManagedObjects.class);
        List<StorageObjectType> storageObjectTypeList = mos.getStorageObjectTypeList();
        return storageObjectTypeList;
    }


    private List<StorageObject> getAllStorageObjectInstance(List<StorageObjectType> storageObjectTypes) throws ETLException {
        List<StorageObject> storageObjects = null;
        try{
            DeviceManager deviceManager = getDeviceManager();
            deviceManager.login();
            storageObjects = getAllStorageObjectInstanceFromRest(deviceManager,storageObjectTypes);
            deviceManager.logout();
        }catch (RestException e) {
            throw new ETLException("error occurred when get storage object data from rest " + e.getErrorDescription(),e);
        }
        return storageObjects;
    }



    private List<StorageObject> getAllStorageObjectInstanceFromRest(DeviceManager deviceManager, List<StorageObjectType> storageObjectTypes) throws ETLException, RestException {
        List<StorageObject> storageObjects = new ArrayList<StorageObject>();
        List<String> unsupportedList = getTargetDiskArrayUnsupportList(deviceManager);
        for (StorageObjectType s : storageObjectTypes) {
            if(!unsupportedList.contains(s.getName())) {
                //get all object instance from rest
                List<StorageObject> objects= findStorageObjectsFromRest(s, deviceManager);
                storageObjects.addAll(objects);
            }
        }
        return storageObjects;
    }

    private List<StorageObject> findStorageObjectsFromRest(StorageObjectType type, DeviceManager deviceManager)
            throws ETLException {
        List<StorageObject> storageObjects = ReflectionUtils.invokeRestHandler(type, deviceManager);
        for(StorageObject dataObject : storageObjects){
            RestPageUtils.setStorageObjectRestData(type,dataObject);
        }
        return storageObjects;
    }





    private DeviceManager getDeviceManager() {
        String hostURL = getHostURL();
        DeviceManager deviceManager = new DeviceManager(
                hostURL,
                connectionData.getUsername(),
                connectionData.getPassword(),connectionData.getScope());
        return deviceManager;
    }

    private String getHostURL() {
        return
                "https://"+connectionData.getHostIP()+":"+
                        connectionData.getRestPort()+"/deviceManager/rest";
    }


    /**
     * some storage array model does not support "filesystem" "fcoe port" remove these from type list to
     * prevent rest request fail
     * @param deviceManager  deviceManager instance to execute rest operation
     * @return  the unsupported function list
     * @throws RestException
     */
    private List<String> getTargetDiskArrayUnsupportList(DeviceManager deviceManager) throws RestException {
        String model = getStorageArrayModel(deviceManager);
        List<String> list = new ArrayList<String>();
        if(model!=null){
            List<String> unSupportList = ModelCapability.unSupportFunctionMap.get(model);
            if(unSupportList!=null){
                list.addAll(unSupportList);
            }
        }
        return list;
    }

    /**
     * get the array model from rest
     * @param deviceManager
     * @return  arraymode list 5300V3
     * @throws RestException
     */
    private String getStorageArrayModel(DeviceManager deviceManager) throws RestException {
        String modelVersion = null;
        OceanStorOperation operation = new OceanStorOperation();
        operation.setOperationName("find-all-system");
        OperationResult result = deviceManager.performAction(operation);
        List<Map<String, String>> resultData = result.getResultData();
        if(resultData!=null){
            String productmode = resultData.get(0).get("PRODUCTMODE");
            //String productversion = resultData.get(0).get("PRODUCTVERSION");
            //modelVersion = StorageArrayType.getTypeName(productmode).name();
            modelVersion = BoxTypeConst.boxMap.get(productmode);
        }
        return modelVersion;
    }
}
