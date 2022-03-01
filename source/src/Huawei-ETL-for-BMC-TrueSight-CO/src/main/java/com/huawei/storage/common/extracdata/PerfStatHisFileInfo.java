package com.huawei.storage.common.extracdata;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 历史性能文件信息，从V1R10代码中移植
 *
 * @author l90005176
 * @version V100R001C00
 * @date 2012-9-3
 * @see [相关类/方法]
 */
public class PerfStatHisFileInfo {
    //校验码
    private String checkCode;

    private int version;

    private String deviceSn;

    private String deviceName;

    private int dataLength;

    private long startTime = 0;

    private long endTime = 0;

    private long graphicStartTime = 0;

    private long graphicEndTime = 0;

    private int period;

    private String fileName;

    private long size;

    private String compressedFileName;

    private long compressedSize;

    //老版本数据文件使用，换新版本数据文件后不再使用此属性
    private long offset;

    /*
     * long[0]:Start Time
     * long[1]:End Time
     * long[2]:Controller Id
     */
    private List<long[]> tlvInfos = new LinkedList<long[]>();

    private List<Integer> objectTypes = new LinkedList<Integer>();

    private Map<Integer, List<String>> objectTypeToNamesMapping = new HashMap<Integer, List<String>>();

    private Map<Integer, List<String>> objectTypeToIdsMapping = new HashMap<Integer, List<String>>();

    private Map<Integer, List<PerfStatHisObject>> objectTypeToDatasMapping =
            new LinkedHashMap<Integer, List<PerfStatHisObject>>();

    private Map<Integer, List<Integer>> objectTypeToDataTypeMapping;

    /**
     * <默认构造函数>
     */
    public PerfStatHisFileInfo() {
        objectTypeToDataTypeMapping = new HashMap<Integer, List<Integer>>();
    }

    /**
     * 返回 objectTypeToDatasMapping
     *
     * @return objectTypeToDatasMapping
     */
    public Map<Integer, List<PerfStatHisObject>> getObjectTypeToDatasMapping() {
        return objectTypeToDatasMapping;
    }

    /**
     * 对objectTypeToDatasMapping进行赋值
     *
     * @param objectTypeToDatasMapping objectTypeToDatasMapping
     */
    public void setObjectTypeToDatasMapping(
            Map<Integer, List<PerfStatHisObject>> objectTypeToDatasMapping) {
        this.objectTypeToDatasMapping = objectTypeToDatasMapping;
    }

    public Map<Integer, List<String>> getObjectTypeToIdsMapping() {
        return objectTypeToIdsMapping;
    }

    public void setObjectTypeToIdsMapping(Map<Integer, List<String>> objectTypeToIdsMapping) {
        this.objectTypeToIdsMapping = objectTypeToIdsMapping;
    }

    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getDeviceSn() {
        return deviceSn;
    }

    public void setDeviceSn(String deviceSN) {
        this.deviceSn = deviceSN;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public int getDataLength() {
        return dataLength;
    }

    public void setDataLength(int dataLength) {
        this.dataLength = dataLength;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getGraphicStartTime() {
        return graphicStartTime;
    }

    public void setGraphicStartTime(long graphicStartTime) {
        this.graphicStartTime = graphicStartTime;
    }

    public long getGraphicEndTime() {
        return graphicEndTime;
    }

    public void setGraphicEndTime(long graphicEndTime) {
        this.graphicEndTime = graphicEndTime;
    }

    /**
     * 返回文件周期间隔
     *
     * @return 单位ms 毫秒
     */
    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getCompressedFileName() {
        return compressedFileName;
    }

    public void setCompressedFileName(String compressedFileName) {
        this.compressedFileName = compressedFileName;
    }

    public long getCompressedSize() {
        return compressedSize;
    }

    public void setCompressedSize(long compressedSize) {
        this.compressedSize = compressedSize;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public long getOffset() {
        return offset;
    }

    public List<long[]> getTlvInfos() {
        return tlvInfos;
    }

    public void setTlvInfos(List<long[]> tlvInfos) {
        this.tlvInfos = tlvInfos;
    }

    public List<Integer> getObjectTypes() {
        return objectTypes;
    }

    public void setObjectTypes(List<Integer> objectTypes) {
        this.objectTypes = objectTypes;
    }

    public Map<Integer, List<String>> getObjectTypeToNamesMapping() {
        return objectTypeToNamesMapping;
    }

    public void setObjectTypeToNamesMapping(Map<Integer, List<String>> objectTypeToNamesMapping) {
        this.objectTypeToNamesMapping = objectTypeToNamesMapping;
    }

    public Map<Integer, List<Integer>> getObjectTypeToDataTypeMapping() {
        return objectTypeToDataTypeMapping;
    }

    public void setObjectTypeToDataTypeMapping(
            Map<Integer, List<Integer>> objectTypeToDataTypeMapping) {
        this.objectTypeToDataTypeMapping = objectTypeToDataTypeMapping;
    }

    /**
     * clear all inner list or map
     */
    public void clearAll() {
        this.objectTypes.clear();
        this.objectTypeToDataTypeMapping.clear();
        this.objectTypeToIdsMapping.clear();
        this.objectTypeToNamesMapping.clear();
        this.objectTypeToDatasMapping.clear();
    }
}
