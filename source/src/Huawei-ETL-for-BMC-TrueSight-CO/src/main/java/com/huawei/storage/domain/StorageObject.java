package com.huawei.storage.domain;

import com.neptuny.cpit.etl.DataSetList;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Huawei Technologies  all rights reserved
 * <p>
 * Created by m00373015 on 2016/8/18.
 */
public class StorageObject {
    private static final Logger logger = Logger.getLogger(StorageObject.class);

    private String arrayId;
    private String arrayName;
    private String id;
    private String name;
    private int type;
    private List<Integer> perfDataType;
    private List<Counter>  counters;
    private Map<String,String> restData = new HashMap<String,String>();
    private Map<String,String> perfData = new HashMap<String,String>();
    private List<StorageObject> subObjectList;
    private String mappingName;
    private String typeName;
    private String bcoType;
    private String bcoParent;
    private Map<String, List<Map<String, String>>> metricData = new HashMap<String, List<Map<String, String>>>();
    private DataSetList dataSetList = new DataSetList();
    private String BCOParentID;
    private String BCOContainerID;
    private StorageObjectType storageObjectType;
    private StorageObject linkedObject;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getPerfDataType() {
        return perfDataType;
    }

    public void setPerfDataType(List<Integer> perfDataType) {
        this.perfDataType = perfDataType;
    }

    public List<Counter> getCounters() {
        return counters;
    }

    public void setCounters(List<Counter> counters) {
        this.counters = counters;
    }

    public Map<String, String> getRestData() {
        return restData;
    }

    public void setRestData(Map<String, String> restData) {
        this.restData = restData;
    }

    public Map<String, String> getPerfData() {
        return perfData;
    }

    public void setPerfData(Map<String, String> perfData) {
        this.perfData = perfData;
    }

    public String getMappingName() {
        return mappingName;
    }

    public void setMappingName(String mappingName) {
        this.mappingName = mappingName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Map<String, List<Map<String, String>>> getMetricData() {
        return metricData;
    }

    public void putMetricData(String counterName,List<Map<String,String>> metrics) {
        metricData.put(counterName,metrics);
    }

    public List<StorageObject> getSubObjectList() {
        return subObjectList;
    }

    public void setSubObjectList(List<StorageObject> subObjectList) {
        this.subObjectList = subObjectList;
    }

    public String getArrayId() {
        return arrayId;
    }

    public void setArrayId(String arrayId) {
        this.arrayId = arrayId;
    }

    public String getArrayName() {
        return arrayName;
    }

    public void setArrayName(String arrayName) {
        this.arrayName = arrayName;
    }

    public String getBcoType() {
        return bcoType;
    }

    public void setBcoType(String bcoType) {
        this.bcoType = bcoType;
    }

    public String getBCOParent() {
        return bcoParent;
    }

    public void setBCOParent(String bcoParent) {
        this.bcoParent = bcoParent;
    }

    public void setMetricData(Map<String, List<Map<String, String>>> metricData) {
        this.metricData = metricData;
    }

    public DataSetList getDataSetList() {
        return dataSetList;
    }

    public void setDataSetList(DataSetList dataSetList) {
        this.dataSetList = dataSetList;
    }

    public void setBCOParentID(String BCOParentID) {
        this.BCOParentID = BCOParentID;
    }

    public String getBCOParentID() {
        return BCOParentID;
    }

    public String getBCOContainerID() {
        return BCOContainerID;
    }

    public void setBCOContainerID(String BCOContainerID) {
        this.BCOContainerID = BCOContainerID;
    }

    public StorageObjectType getStorageObjectType() {
        return storageObjectType;
    }

    public void setStorageObjectType(StorageObjectType storageObjectType) {
        this.storageObjectType = storageObjectType;
    }

    public StorageObject getLinkedObject() {
        return linkedObject;
    }

    public void setLinkedObject(StorageObject linkedObject) {
        this.linkedObject = linkedObject;
    }
}
