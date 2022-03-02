package com.huawei.storage.utils;

import com.huawei.storage.constants.ObjectType;
import com.huawei.storage.domain.StorageObject;
import com.huawei.storage.domain.StorageObjectType;
import com.huawei.storage.exception.ETLException;
import com.huawei.storage.oceanstor.rest.exception.RestException;
import com.huawei.storage.oceanstor.rest.operation.DeviceManager;
import com.huawei.storage.oceanstor.rest.operation.OceanStorOperation;
import com.huawei.storage.oceanstor.rest.operation.OperationResult;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Huawei Technologies  all rights reserved
 * <p>
 * Created on 2016/11/9.
 */
public class RestPageUtils {
    public static final int DEFAULT_PAGE_SIZE = 100;
    private static final Logger logger = Logger.getLogger(RestPageUtils.class);


    public static List<StorageObject> pageGetAll(DeviceManager deviceManager, String countOperationName, String queryOperationName, Map<String, String> operationData) throws ETLException {
        List<StorageObject> dataObjects = new ArrayList<StorageObject>();
        if(countOperationName==null){
            return getDataFromRest(queryOperationName,deviceManager,operationData);
        }
        List<Map<String, String>> countResult = executeRestRequest(countOperationName, deviceManager, operationData);
        if(countResult.size()==0){
            dataObjects.addAll(getDataFromRest(queryOperationName,deviceManager,null));
            return dataObjects;
        }

        String count = executeRestRequest(countOperationName, deviceManager, operationData).get(0).get("COUNT");
        try {
            int totalCount = Integer.parseInt(count);
            int pages = totalCount % DEFAULT_PAGE_SIZE == 0 ? totalCount / DEFAULT_PAGE_SIZE : (totalCount / DEFAULT_PAGE_SIZE) + 1;
            for (int i = 0; i < pages; i++) {
                int start = i * DEFAULT_PAGE_SIZE;
                int limit = start + DEFAULT_PAGE_SIZE;
                operationData.put("range", "[" + start + "-" + limit + "]");
                dataObjects.addAll(getDataFromRest(queryOperationName, deviceManager, operationData));
            }
        } catch (NumberFormatException e) {
            logger.warn("the count field get from rest is not a number");
        }
        return dataObjects;
    }

    public static List<StorageObject> getDataFromRest(String operationName, DeviceManager deviceManager, Map<String, String> operationData) throws ETLException {
        List<Map<String, String>> storageRestObjects = executeRestRequest(operationName, deviceManager, operationData);
        List<StorageObject> dataObjects = new ArrayList<StorageObject>();
        for (Map<String, String> data : storageRestObjects) {
            StorageObject obj = new StorageObject();
            obj.setId(data.get("ID"));
            obj.setRestData(data);
            dataObjects.add(obj);
        }
        return dataObjects;
    }

    private static List<Map<String, String>> executeRestRequest(String operationName, DeviceManager deviceManager, Map<String, String> operationData) throws ETLException {
        List<Map<String, String>> resultData = new ArrayList<Map<String, String>>();
        OceanStorOperation operation = new OceanStorOperation();
        operation.setOperationName(operationName);
        if (operationData != null) {
            operation.getOperationData().putAll(operationData);
        }
        OperationResult result = null;
        try {
            result = deviceManager.performAction(operation);
            if("0".equals(result.getErrorCode())){
                resultData = result.getResultData();
            }else{
                if(result.getErrorDescription().contains("The operation is not supported")){
                    logger.warn("this device dose not support this operation");
                }else{
                    logger.error("rest request error happened " + result.getErrorDescription());
                    throw new ETLException("rest request error happened "
                            + result.getErrorDescription());
                }
            }
        } catch (RestException e) {
            logger.error("get data from rest failed " + e.getErrorDescription() ,e );
            throw new ETLException("get Data from rest failed "+e.getErrorDescription(),e);
        }
        return resultData;
    }


    public static StorageObject setStorageObjectRestData(StorageObjectType type, StorageObject object) {
        Map<String,String> data = object.getRestData();
        CommonUtils.upperMapKey(data);
        object.setCounters(type.getCounters().getCounterList());
        object.setMappingName(type.getMappingName());
        object.setStorageObjectType(type);
        object.setId(data.get("ID"));
        object.setType(Integer.parseInt(data.get("TYPE")));
        //v6 unsupport NFSHARE/CIFSHARE NAME
        object.setName(data.get("NAME") == null ? data.get("NAME"): data.get("SHAREPATH"));
        object.setTypeName(ObjectType.valueOf(object.getType()).name());
        object.setRestData(data);

        logger.debug("ID: "+object.getId()+",TYPE: "+object.getType()
                +",NAME: "+object.getName() +",TYPE-NAME: " + object.getTypeName());

        return object;
    }
}
