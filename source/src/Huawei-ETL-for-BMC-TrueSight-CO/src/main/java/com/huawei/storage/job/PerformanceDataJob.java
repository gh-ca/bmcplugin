package com.huawei.storage.job;

import com.huawei.storage.common.extracdata.PerfStatHisFileInfo;
import com.huawei.storage.common.extracdata.PerfStatHisFileProxy;
import com.huawei.storage.common.extracdata.PerfStatHisObject;
import com.huawei.storage.common.sftp.SftpAccount;
import com.huawei.storage.common.sftp.SftpTransfer;
import com.huawei.storage.constants.ConnectionVO;
import com.huawei.storage.constants.DataType;
import com.huawei.storage.constants.PerfConstants;
import com.huawei.storage.exception.ETLException;
import com.huawei.storage.oceanstor.rest.operation.OceanStorOperation;
import com.huawei.storage.oceanstor.rest.operation.OperationResult;
import com.huawei.storage.utils.CommonUtils;
import org.apache.commons.io.FileDeleteStrategy;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;

/**
 * Huawei Technologies  all rights reserved
 * <p>
 * Created by m00373015 on 2016/8/19.
 */
public class PerformanceDataJob implements Callable<Map<String,Map<String,String>>> {
    private Logger logger = Logger.getLogger(PerformanceDataJob.class);
    private ConnectionVO connectionData;

    public PerformanceDataJob(ConnectionVO connectionData){

        this.connectionData = connectionData;
    }



    public Map<String, Map<String, String>> call() throws Exception {
        List<String> dataFiles = null;
        try {
            dataFiles = getPerformanceFileFromArray(connectionData);
            logger.debug("get data file from controller size is " + dataFiles.size());

            //get file from all controllers and merge them
            //merge controller data
            Map<Integer, List<PerfStatHisObject>> hisData = mergeData(dataFiles);
            //there are 15 records in data , only get the latest one
            List<PerfStatHisObject> latestData = getLatestData(hisData);
            //convert data to map<typeName_objID,<dataType,dataValue>>
            Map<String, Map<String, String>> latestMap = convertToMap(latestData);
            logger.debug("latest data size is :" + latestMap.size());
            return latestMap;
        }finally {
            deleteAllTempFile(dataFiles);
        }
    }

    private void deleteAllTempFile(List<String> dataFiles) {
        //delete the temp folder
        if(dataFiles==null||dataFiles.size()==0){
            return;
        }
        for(String fileName : dataFiles){
            if(fileName!=null){
                File f = new File(fileName);
                if(f.exists()&&f.isFile()){
                    File tempFolder = f.getParentFile();
                    if (null != tempFolder && tempFolder.isDirectory()) {
                        try {
                            FileDeleteStrategy.FORCE.delete(tempFolder);
                        } catch (IOException e) {
                            logger.warn("temp file" + tempFolder.getName() + "delete failed");
                        }
                    }
                }
            }
        }

    }

    private Map<String, Map<String, String>> convertToMap(List<PerfStatHisObject> latestData) {
        Map<String,Map<String,String>> latestMap = new HashMap<String, Map<String, String>>();
        for (PerfStatHisObject obj: latestData) {
            Map<String, String> perfData = new HashMap<String, String>();
            int[] data = obj.getData().get(0);
            List<Integer> dataTypes = obj.getDataTypes();
            for(int i=0;i<data.length;i++){
                if(DataType.valueOf(dataTypes.get(i))!=null){
                    perfData.put(DataType.valueOf(dataTypes.get(i)).name(),data[i]+"");
                }
            }
            latestMap.put(obj.getObjectType()+"_"+obj.getResId(),perfData);
        }
        return latestMap;
    }

    /**
     * there are 15 data sets of each object , only get the latest one
     * @param hisData
     * @return
     */
    @SuppressWarnings("unchecked")
    private List<PerfStatHisObject> getLatestData(Map<Integer, List<PerfStatHisObject>> hisData) {
        List<PerfStatHisObject> latestData = new ArrayList<PerfStatHisObject>();
        logger.debug("history data size is " + hisData.size());
        for(Map.Entry<Integer,List<PerfStatHisObject>> entry : hisData.entrySet()){
            List<PerfStatHisObject> perfs = entry.getValue();
            Map<String,List<PerfStatHisObject>> instanceData = new HashMap<String, List<PerfStatHisObject>>();
            for(PerfStatHisObject o : perfs){
                if(instanceData.get(o.getResId())==null){
                    List<PerfStatHisObject> l = new ArrayList<PerfStatHisObject>();
                    l.add(o);
                    instanceData.put(o.getResId(),l);
                }else{
                    instanceData.get(o.getResId()).add(o);
                }
            }
            for(Map.Entry<String,List<PerfStatHisObject>> e : instanceData.entrySet()){
                List<PerfStatHisObject> tempList = e.getValue();
                Collections.sort(tempList);
                logger.debug("history data performance list size is :" + e.getValue().size());
                latestData.add(tempList.get(0));
            }
        }
        logger.debug("latest data size is " + latestData.size());
        logger.debug("latest data is " + latestData);
        return latestData;
    }

    /**
     * merge all data from all controllers
     * @param filepaths
     * @return
     * @throws Exception
     */
    private Map<Integer,List<PerfStatHisObject>> mergeData(List<String> filepaths) throws Exception {
        Map<Integer, List<PerfStatHisObject>> resultMap = new HashMap<Integer, List<PerfStatHisObject>>();
        for (String path : filepaths) {
            PerfStatHisFileInfo perfStatHisFileInfo =
                    PerfStatHisFileProxy.queryPerfStatHisFileInfoByCompress(path);
            Map<Integer, List<PerfStatHisObject>> objectTypeToDatasMapping =
                    perfStatHisFileInfo.getObjectTypeToDatasMapping();
            //merge entry
            for (Map.Entry<Integer, List<PerfStatHisObject>> entry : objectTypeToDatasMapping.entrySet()) {
                if (resultMap.get(entry.getKey()) != null) {
                    resultMap.get(entry.getKey()).addAll(entry.getValue());
                } else {
                    resultMap.put(entry.getKey(), entry.getValue());
                }
            }
        }
        logger.debug("resultMap  data size is :" + resultMap.size());
        return resultMap;
    }

    private List<String> getPerformanceFileFromArray(ConnectionVO connectionData) throws ETLException {

        String arrayUserName = connectionData.getUsername();
        String arrayUserPass = connectionData.getPassword();
        int arrayPort = Integer.parseInt(connectionData.getSftpPort());
        String deviceSN = getDeviceSN(connectionData);
        logger.debug("the array device SN is " +deviceSN);


        List<String> hostControllerIP = new ArrayList<String>();
        if(connectionData.getIpControllerA()!=null&&connectionData.getIpControllerB()!=null){
            hostControllerIP.add(connectionData.getIpControllerA());
            hostControllerIP.add(connectionData.getIpControllerB());
        }else{
            hostControllerIP = getHostControllerIP(connectionData);
        }

        List<String> fileList = new ArrayList<String>();
        //get file from all controller
        for(String arrayHost : hostControllerIP){
            String[] array = arrayHost.split(":");
            logger.debug("host array is " + array[0] + "," +array[1]);
            arrayHost = array[0];
            arrayPort = Integer.parseInt(array[1]);
            SftpAccount account = new SftpAccount(arrayHost, arrayUserName, arrayUserPass,
                    arrayPort);
            SftpTransfer sftp = new SftpTransfer();
            String dataFileNew = sftp.getNewDataFile(arrayHost,
                    PerfConstants.DATA_PERF_FILE_FOLDER, account);
            logger.debug("the latest file is " + dataFileNew);
            //get all file from sftp
            try {
                String fileTempPath = PerfConstants.TEMP_PERF_FILE_FOLDER +"_" + dataFileNew;
                createTempFolder(fileTempPath);
                sftp.transferFileFromDevice(deviceSN,
                        arrayHost + PerfConstants.SERVER_SPLITER +
                                PerfConstants.DATA_PERF_FILE_FOLDER + dataFileNew, fileTempPath,
                        account, arrayPort, true);
                fileList.add(fileTempPath+File.separator+dataFileNew);
            } catch (Exception e) {
                logger.error("download data file failed!" + e.getMessage(),e);
                throw new ETLException("download data from array failed " + e.getMessage(),e);
            }
        }

        return  fileList;

    }

    private List<String> getHostControllerIP(ConnectionVO vo) {
        List<String> addrList = new ArrayList<String>();
        OceanStorOperation operation = new OceanStorOperation();
        operation.setOperationName("find-all-eth_port");

        OperationResult result = CommonUtils.executeRestRequest(vo, operation);
        for(Map<String,String> m : result.getResultData()){
            //get all management ip, selectType==2
            //TODO think about ipv6 and check ip valid
            if("2".equals(m.get("SELECTTYPE"))){
                addrList.add(m.get("IPV4ADDR")+":"+vo.getSftpPort());
            }
        }
        if(addrList.size()==0){
            addrList.add(vo.getHostIP()+":"+vo.getSftpPort());
        }
        return  addrList;
    }

    private String getDeviceSN(ConnectionVO vo) {
        String arraySN = null;
        OceanStorOperation operation = new OceanStorOperation();
        operation.setOperationName("find-all-system");
        OperationResult result = CommonUtils.executeRestRequest(vo, operation);
        if("0".equals(result.getErrorCode())){
            arraySN = result.getResultData().get(0).get("ID");
        }
        return arraySN;
    }

    private void createTempFolder(String fileTempPath) throws ETLException {
        File file = new File(fileTempPath);
        if (!file.exists()) {
            if (!file.mkdir()) {
                logger.error("mkdir error");
                throw new ETLException("Can't create temp download folder");
            }
        }
    }
}
