package com.huawei.storage.common.extracdata;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.huawei.storage.common.extracdata.util.GFCommon;
import com.huawei.storage.common.extracdata.util.TarUtils;



/**
 * 解析性能文件
 *
 * @author l90005176
 * @version V100R100C00
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class PerfStatHisFileProxy {
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(PerfStatHisFileProxy.class); 
	
	private static final int INFO_HEAD_LENGTH = 337;

    private static final int CHECK_CODE_LENGTH = 32;

    private static final int VERSION_LENGTH = 4;

    private static final int DEVICE_SN_LENGTH = 256;

    private static final int DEVICE_NAME_LENGTH = 41;

    private static final int DATA_LENGTH_LENGTH = 4;

    private static final String DATA_TLV_MAP_STRING = "\"Map\":{";

    private static final String DATA_TLV_OBJ_SPLIT = "]},";

    private static final long ONE_DAY_TIME_MILLIS = 86400000;
    
    
    public static PerfStatHisFileInfo queryPerfStatHisFileInfoByCompress(String fileName) throws Exception{
		String filename = decompress(fileName);
		return queryPerfStatHisFileInfo(filename);
	}

    /**
     * <解析性能文件>
     *
     * @param fileName 文件文件
     * @return PerfStatHisFileInfo
     * @throws ReportException <异常>
     */
    public static PerfStatHisFileInfo queryPerfStatHisFileInfo(String fileName)
            throws Exception {
        PerfStatHisFileInfo perfStatHisFileInfo = new PerfStatHisFileInfo();
        File file = new File(fileName);
        perfStatHisFileInfo.setFileName(file.getAbsolutePath());
        perfStatHisFileInfo.setSize(file.length());
        RandomAccessFile raf = null;
        try {
            raf = open(fileName);

            // 一次读完头的固定部分337个字节
            byte[] headerBuf = new byte[INFO_HEAD_LENGTH];
            raf.readFully(headerBuf);

            // 解析文件基本信息
            int dataLength = parseFileBaseInfo(perfStatHisFileInfo, headerBuf);

            // 是否继续解析
            boolean continueRead = Boolean.TRUE;

            // 降低深度和复杂度，拆分方法
            while (continueRead && raf.getFilePointer() < raf.length()) {
                continueRead = queryPerfStatHisFileInfo(perfStatHisFileInfo, raf, dataLength);
            }
            return perfStatHisFileInfo;
        } catch (IOException e) {
            logger.error("read data error", e);
            throw new Exception("HIS_FILE_PARSE_ERROR", e);
        } catch (Exception e) {
            logger.error("performance history file error :" + e.getMessage(), e);
            throw new Exception("HIS_FILE_PARSE_ERROR", e);
        } finally {
            close(raf);
        }
    }

    /****************************************
     * Private Function
     ************************************/

    private static RandomAccessFile open(String fileName) throws FileNotFoundException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(fileName, "rw");
        return randomAccessFile;
    }

    private static void close(RandomAccessFile randomAccessFile) {
        if (randomAccessFile != null) {
            try {
                randomAccessFile.close();
            } catch (IOException e) {
                logger.error("close performance history file error", e);
            }
        }
    }

    /**
     * 解析文件头
     *
     * @param perfStatHisFileInfo
     * @param raf
     * @param dataLength
     * @throws IOException
     * @throws JSONException
     * @see [类、类#方法、类#成员]
     */
    private static String perfStatHisFileTlvHeaderParser(RandomAccessFile raf)
            throws IOException, JSONException {
        int skipLen = raf.skipBytes(4); //跳过TLV中的T的4个字节，该数据为无用数据

        if (skipLen != 4) {
            logger.info("parse file fail,skip=4,skipLen=" + skipLen);
            return null;
        }

        byte[] tlvLenBuf = new byte[4];
        raf.readFully(tlvLenBuf);

        int tlvLength = GFCommon.bytes2int(tlvLenBuf, false);
        if (tlvLength <= 8) {
            if (logger.isDebugEnabled()) {
                logger.debug("parse file fail, zero byte left.fileLen:" + raf.length()
                        + " current tlv length:" + tlvLength + " current file pointer:"
                        + raf.getFilePointer());
            }
            return null;
        }

        // 如果产品返回的tlvlength读取的直接超过了 32M 直接返回
        if (tlvLength > 1024 * 1024  * 32) {
            logger.error("The file is exceed file lenth：" + tlvLength);
            return null;
        }

        String json = null;
        try {
            byte[] jsonByteBuf = new byte[tlvLength - 8];
            raf.readFully(jsonByteBuf);
            json = new String(jsonByteBuf, "UTF-8").trim();
        } catch (Exception e) {
            logger.error("Failed to process file.", e);
        }

        return json;
    }

    /**
     * 降低深度和复杂度，拆分方法
     *
     * @param perfStatHisFileInfo
     * @param raf
     * @param dataLength
     * @throws ReportException
     * @see [类、类#方法、类#成员]
     */
    private static void perfStatHisFileTvlBodyParser(PerfStatHisFileInfo perfStatHisFileInfo, RandomAccessFile raf,
                                                     JSONObject jsonObject, List<String> objTypes, int dataLength) throws IOException, JSONException {
        long dataStartTime = jsonObject.getLong("StartTime") * 1000;
        long dataEndTime = jsonObject.getLong("EndTime") * 1000;
        int period = jsonObject.getInt("Archive") * 1000;
        JSONObject objectMap = jsonObject.getJSONObject("Map");

        // 从文件中读出dataType和OBJTYPE
        Map<Integer, List<String>> objTypeToNamesTmp = new HashMap<Integer, List<String>>();
        Map<Integer, List<String>> objTypeToIdsTmp = new HashMap<Integer, List<String>>();
        Map<Integer, List<Integer>> objTypeToDataTypesTmp = new HashMap<Integer, List<Integer>>();
        List<Integer> objectTypesTmp = new LinkedList<Integer>();

        int periodDataLength = 0;
        for (String objectTypeString : objTypes) {
            Integer objectType = 0;
            try {
                objectType = Integer.parseInt(objectTypeString);
            } catch (Exception e) {
                logger.error("conversion failed.", e);
            }

            JSONObject objectTypeMap = objectMap.getJSONObject(objectTypeString);
            List<String> resIds = getResIds(objectTypeMap);
            List<String> names = getNames(objectTypeMap);
            List<Integer> dataTypes = getDataTypes(objectTypeMap);
            objTypeToIdsTmp.put(objectType, resIds);
            objTypeToNamesTmp.put(objectType, names);
            objTypeToDataTypesTmp.put(objectType, dataTypes);
            objectTypesTmp.add(objectType);
            periodDataLength += resIds.size() * dataTypes.size() * dataLength;

        }

        mergeObjectTypeToMapping(perfStatHisFileInfo.getObjectTypeToIdsMapping(),
                objTypeToIdsTmp);
        mergeObjectTypeToMapping(perfStatHisFileInfo.getObjectTypeToNamesMapping(),
                objTypeToNamesTmp);
        mergeObjectTypeToDataTypesMapping(perfStatHisFileInfo.getObjectTypeToDataTypeMapping(),
                objTypeToDataTypesTmp);
        mergeObjectTypes(perfStatHisFileInfo.getObjectTypes(), objectTypesTmp);
        byte[] periodDataBuf = new byte[periodDataLength];
        int offset = 0;
        long currPeriodDataTime = dataStartTime;
        while (currPeriodDataTime < dataEndTime) {
            System.out.println("read data beg:" + raf.getFilePointer());
            raf.readFully(periodDataBuf);
            System.out.println( GFCommon.bytes2int(periodDataBuf, false));
            System.out.println("read data end :" + raf.getFilePointer());
            offset = 0;

            for (Integer objType : objectTypesTmp) {
                Map<Integer, List<PerfStatHisObject>> dataMap = perfStatHisFileInfo
                        .getObjectTypeToDatasMapping();
                List<PerfStatHisObject> dataList = dataMap.get(objType);
                if (dataList == null) {
                    dataList = new ArrayList<PerfStatHisObject>();
                    dataMap.put(objType, dataList);
                }
                List<String> resIds = objTypeToIdsTmp.get(objType);
                List<Integer> dataTypeEnum = objTypeToDataTypesTmp.get(objType);
                for (String resId : resIds) {
                    PerfStatHisObject perfStatHisObject = new PerfStatHisObject();
                    perfStatHisObject.setPeriod(period);
                    perfStatHisObject.setObjectType(objType);
                    perfStatHisObject.setDataTypes(dataTypeEnum);
                    perfStatHisObject.setResId(resId);
                    perfStatHisObject.setStartTime(currPeriodDataTime);
                    // 从文件获取数据
                    getDataSource(offset, periodDataBuf, dataLength, perfStatHisObject);
                    dataList.add(perfStatHisObject);

                    offset += dataLength * dataTypeEnum.size();
                }
            }
            currPeriodDataTime = currPeriodDataTime + period;
        }
        System.out.println("》》》》》  5  《《《《《");
        System.out.println("》》》》》  4  《《《《《");
        System.out.println("》》》》》  3  《《《《《");
        System.out.println("》》》》》  2  《《《《《");
        System.out.println("》》》》》  1  《《《《《");
    }

    /**
     * <获取文件中的指标ID>
     *
     * @param objectTypeMap JSON数据
     * @return List<Integer> 指标ID
     * @throws JSONException JSONException
     */
    private static List<Integer> getDataTypes(JSONObject objectTypeMap) throws JSONException {
        JSONArray dataTypesJSONArray = objectTypeMap.getJSONArray("DataTypes");
        List<Integer> dataTypes = new LinkedList<Integer>();
        for (int j = 0; j < dataTypesJSONArray.length(); j++) {
            dataTypes.add(dataTypesJSONArray.getInt(j));
        }
        return dataTypes;
    }

    /**
     * 降低深度和复杂度，拆分方法
     *
     * @param perfStatHisFileInfo
     * @param raf
     * @param dataLength
     * @return 是否继续解析
     * @throws ReportException
     * @see [类、类#方法、类#成员]
     */
    //@SuppressWarnings("unchecked")
    private static boolean queryPerfStatHisFileInfo(PerfStatHisFileInfo perfStatHisFileInfo,
                                                    RandomAccessFile raf, int dataLength) throws Exception {
        long minStartTime = perfStatHisFileInfo.getStartTime();
        long maxEndTime = perfStatHisFileInfo.getEndTime();

        try {
            System.out.println("config beg:" + raf.getFilePointer());
            String jsonString = perfStatHisFileTlvHeaderParser(raf);
            System.out.println("config end:" + raf.getFilePointer());
            System.out.println(jsonString);
            if (null == jsonString) {
                logger.debug("read file fail. JSON STRING IS NULL,CompressedFileName:"
                        + perfStatHisFileInfo.getCompressedFileName());
                return false;
            }
            int mapStart = jsonString.indexOf(DATA_TLV_MAP_STRING) + DATA_TLV_MAP_STRING.length();
            String[] stringObjTypes = jsonString.substring(mapStart).split(DATA_TLV_OBJ_SPLIT);

            List<String> objTypes = new ArrayList<String>();
            addDatatypes(stringObjTypes, objTypes);

            JSONObject jsonObject = new JSONObject(jsonString);
            String startTime = jsonObject.getString("StartTime").trim();
            long dataStartTime = Long.parseLong(startTime)* 1000;
            String endTime = jsonObject.getString("EndTime").trim();
            long dataEndTime = Long.parseLong(endTime) * 1000;
            int controllerId = jsonObject.getInt("CtrlID");
            int period = jsonObject.getInt("Archive") * 1000;

            long[] tlvInfo = {dataStartTime, dataEndTime, controllerId};
            perfStatHisFileInfo.getTlvInfos().add(tlvInfo);
            perfStatHisFileInfo.setPeriod(period);

            minStartTime = getMinStartTime(minStartTime, dataStartTime);
            maxEndTime = getMaxEndTime(maxEndTime, dataEndTime);
            if (maxEndTime - minStartTime > ONE_DAY_TIME_MILLIS) {
                logger.warn("File time too looooooooooooooong, file name: "
                        + perfStatHisFileInfo.getFileName() + ", start time: " + minStartTime
                        + ", end time: " + maxEndTime);
                return Boolean.FALSE;
            }
            perfStatHisFileTvlBodyParser(perfStatHisFileInfo,
                    raf,
                    jsonObject,
                    objTypes,
                    dataLength);
            perfStatHisFileInfo.setStartTime(minStartTime);

            // 最后一个有数据的时间点是endTime前一个周期
            perfStatHisFileInfo.setEndTime(maxEndTime - period);
        } catch (IOException e) {
            logger.error("read file fail.", e);
            throw new Exception("HIS_FILE_PARSE_ERROR", e);
        } catch (JSONException e) {
            logger.error("performance history file json parse error.", e);
            throw new Exception("HIS_FILE_PARSE_ERROR", e);
        } catch (Exception e) {
            logger.error("performance history file json parse error.", e);
            throw new Exception("HIS_FILE_PARSE_ERROR", e);
        }
        return true;
    }

    private static void addDatatypes(String[] stringObjTypes, List<String> objTypes) {
        for (int i = 0; i < stringObjTypes.length; i++) {
            int index = stringObjTypes[i].indexOf(":{");
            /**没有数据*/
            if (-1 == index) {
                if (logger.isDebugEnabled()) {
                    logger.debug("no data. objType:" + stringObjTypes[i]);
                }
                continue;
            }
            String objType = stringObjTypes[i].substring(0, index);
            objType = objType.replaceAll("\"", "").trim();
            objTypes.add(objType);
        }
    }

    private static long getMaxEndTime(long maxEndTime, long dataEndTime) {
        return Math.max(maxEndTime, dataEndTime);
    }

    private static long getMinStartTime(long minStartTime, long dataStartTime) {
        if (minStartTime == 0) {
            return dataStartTime;
        }
        return Math.min(minStartTime, dataStartTime);
    }

    /**
     * 解析文件基本信息
     *
     * @param perfStatHisFileInfo 文件对象
     * @param headerBuf           临时缓存
     * @return
     * @throws UnsupportedEncodingException
     * @see [类、类#方法、类#成员]
     */
    private static int parseFileBaseInfo(PerfStatHisFileInfo perfStatHisFileInfo, byte[] headerBuf)
            throws UnsupportedEncodingException {
        //校验文件的有效性 32
        byte[] checkCodeBuf = new byte[CHECK_CODE_LENGTH];
        System.arraycopy(headerBuf, 0, checkCodeBuf, 0, checkCodeBuf.length);
        perfStatHisFileInfo.setCheckCode(new String(checkCodeBuf));

        //消息包版本   4字节
        byte[] versionBuf = new byte[VERSION_LENGTH];
        System.arraycopy(headerBuf,
                32,
                versionBuf,
                0,
                versionBuf.length);
        perfStatHisFileInfo.setVersion(GFCommon.bytes2int(versionBuf, false));

        //设备标识 256字节
        byte[] deviceSnBuf = new byte[DEVICE_SN_LENGTH];
        System.arraycopy(headerBuf,
                36,
                deviceSnBuf,
                0,
                deviceSnBuf.length);
        perfStatHisFileInfo.setDeviceSn(new String(deviceSnBuf).trim());

        //设备名 41字节
        byte[] deviceNameBuf = new byte[DEVICE_NAME_LENGTH];
        System.arraycopy(headerBuf,
                3 * 100 - 8,
                deviceNameBuf,
                0,
                deviceNameBuf.length);
        perfStatHisFileInfo.setDeviceName(new String(deviceNameBuf, "UTF-8").trim());

        //data length 数据长度    4字节 默认值=4
        byte[] dataLengthBuf = new byte[DATA_LENGTH_LENGTH];
        System.arraycopy(headerBuf,
                333,
                dataLengthBuf,
                0,
                dataLengthBuf.length);
        int dataLength = GFCommon.bytes2int(dataLengthBuf, false);
        perfStatHisFileInfo.setDataLength(dataLength);
        return dataLength;
    }

    private static List<String> getResIds(JSONObject objectTypeMap) throws JSONException {
        JSONArray jsonArray;
        try {
            jsonArray = objectTypeMap.getJSONArray("IDs");
        } catch (JSONException e) {
            jsonArray = objectTypeMap.getJSONArray("Names");
        }
        List<String> names = new LinkedList<String>();
        for (int i = 0; i < jsonArray.length(); i++) {
            names.add(jsonArray.getString(i));
        }
        return names;
    }

    private static List<String> getNames(JSONObject objectTypeMap) throws JSONException {
        JSONArray jsonArray = objectTypeMap.getJSONArray("Names");
        List<String> names = new LinkedList<String>();
        for (int i = 0; i < jsonArray.length(); i++) {
            names.add(jsonArray.getString(i));
        }
        return names;
    }

    /**
     * <功能详细描述>
     *
     * @param offset            起始位置
     * @param periodDataBuf     数据buffer
     * @param dataLength        单个数据长度
     * @param perfStatHisObject 数据长度
     * @return void [返回类型说明]
     * @throws IOException
     * @throws JSONException [参数说明]
     * @see [类、类#方法、类#成员]
     */
    private static void getDataSource(int offset, byte[] periodDataBuf, int dataLength,
                                      PerfStatHisObject perfStatHisObject) throws IOException, JSONException {
        List<Integer> dataTypes = perfStatHisObject.getDataTypes();
        int[] values = new int[dataTypes.size()];
        int valueindex = 0, curindex = 0;
//        System.out.println();
//        System.out.println("data 》》》 object id:"+perfStatHisObject.getObjectType()+",resid:"+perfStatHisObject.getResId());
        for (Integer d : dataTypes) {
            if (d != null) {
                byte[] valueBuf = new byte[dataLength];
                System.arraycopy(periodDataBuf,
                        offset + curindex * dataLength,
                        valueBuf,
                        0,
                        valueBuf.length);
                values[valueindex] = GFCommon.bytes2int(valueBuf, false);
//                System.out.print("【key:"+d+"="+values[valueindex]+"】");
                valueindex++;
            }
            curindex++;
        }
        perfStatHisObject.getData().add(values);
    }

    private static Map<Integer, List<Integer>> mergeObjectTypeToDataTypesMapping(
            Map<Integer, List<Integer>> destMapping, Map<Integer, List<Integer>> srcMapping) {
        Iterator<Map.Entry<Integer, List<Integer>>> it = srcMapping.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, List<Integer>> entry = it.next();
            Integer objectType = entry.getKey();
            if (destMapping.keySet().contains(objectType)) {
                List<Integer> destDataTypes = destMapping.get(objectType);
                List<Integer> srcDataTypes = srcMapping.get(objectType);
                addDataType2destMap(destMapping, objectType, destDataTypes, srcDataTypes);
            } else {
                destMapping.put(objectType, srcMapping.get(objectType));
            }
        }
        return destMapping;
    }

    private static void addDataType2destMap(Map<Integer, List<Integer>> destMapping,
                                            Integer objectType, List<Integer> destDataTypes, List<Integer> srcDataTypes) {
        for (Integer srcDataType : srcDataTypes) {
            if (!destDataTypes.contains(srcDataType)) {
                destMapping.get(objectType).add(srcDataType);
            }
        }
    }

    private static Map<Integer, List<String>> mergeObjectTypeToMapping(
            Map<Integer, List<String>> destMapping, Map<Integer, List<String>> srcMapping) {
        Iterator<Map.Entry<Integer, List<String>>> it = srcMapping.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, List<String>> entry = it.next();
            Integer objectType = entry.getKey();
            if (destMapping.keySet().contains(objectType)) {
                List<String> destNames = destMapping.get(objectType);
                List<String> srcNames = srcMapping.get(objectType);

                addName2Map(destMapping, objectType, destNames, srcNames);
            } else {
                destMapping.put(objectType, srcMapping.get(objectType));
            }
        }
        return destMapping;
    }

    private static void addName2Map(Map<Integer, List<String>> destMapping, Integer objectType,
                                    List<String> destNames, List<String> srcNames) {
        for (String srcName : srcNames) {
            if (!destNames.contains(srcName)) {
                destMapping.get(objectType).add(srcName);
            }
        }
    }

    private static List<Integer> mergeObjectTypes(List<Integer> destObjectTypes,
                                                  List<Integer> srcObjectTypes) {
        for (Integer objectType : srcObjectTypes) {
            if (!destObjectTypes.contains(objectType)) {
                destObjectTypes.add(objectType);
            }
        }
        return destObjectTypes;
    }
    
    
    
    private static String decompress(String fileName) throws Exception {
        if (fileName.endsWith("tgz") || fileName.endsWith("bz2")) {
            File file = new File(fileName);
            String folder = fileName.substring(0, fileName.lastIndexOf(File.separator));
            return TarUtils.extract(file, folder);
        } else {
            return fileName;
        }
    }
}