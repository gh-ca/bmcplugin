package com.huawei.storage.job;

import com.huawei.storage.domain.*;
import com.huawei.storage.utils.CommonUtils;
import com.huawei.storage.utils.ReflectionUtils;
import com.neptuny.cpit.etl.DBConf;
import com.neptuny.cpit.etl.DataSet;
import com.neptuny.cpit.etl.DataSetList;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataMapper {


    private Logger logger = Logger.getLogger(DataMapper.class);
    private DBConf dbConf;
    private com.neptuny.cpit.logger.Logger bcoLogger;

    public DataMapper(DBConf dbConf, com.neptuny.cpit.logger.Logger bcoLogger){

        this.dbConf = dbConf;
        this.bcoLogger = bcoLogger;
    }


    public DataSetList mappingAllData(List<StorageObject> storageObjects, Map<String, Map<String, String>> perfData) throws Exception {
        //set performance data to all object instance
        addPerformanceData(storageObjects, perfData);
        //mapping metric item to performance data
        return mappingMetricData(storageObjects);
    }

    public DataSetList mappingAllData(List<StorageObject> storageObjects) throws Exception {
        //mapping metric item to performance data
        return mappingMetricData(storageObjects);
    }


    private DataSetList mappingMetricData(List<StorageObject> storageObjects) throws Exception {
        long startTime = System.currentTimeMillis();
        Map<String, List<StorageObject>> storObjMap = makeQuickSearchMap(storageObjects);
        Map<String,DataSet> dataSetMap = new HashMap<String, DataSet>();
        for (StorageObject obj : storageObjects) {
            if (obj.getCounters() != null) {
                for (Counter c : obj.getCounters()) {
                    Map<String, String> metricData = dealMapping(obj, storObjMap, c, startTime);
                    createDataSet(c.getName(),metricData,dataSetMap);
                }
            }
        }
        return createDataSetList(dataSetMap);

    }

    private DataSetList createDataSetList(Map<String, DataSet> dataSetMap) {
        //put STOGLB the fist place,because load need time
        DataSetList list = new DataSetList();
        list.add(dataSetMap.get("STOGLB"));
        dataSetMap.remove("STOGLB");
        list.add(dataSetMap.get("OBJREL"));
        dataSetMap.remove("OBJREL");
        for(Map.Entry<String,DataSet> dataSetEntry : dataSetMap.entrySet()){
            list.add(dataSetEntry.getValue());
        }
        StringBuilder sf = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            DataSet dataSet = list.get(i);
            sf.append("[name: "+dataSet.getName()+",row: "+dataSet.size()+"]");
            if(i<list.size()-1){
                sf.append(",");
            }
        }
        bcoLogger.info("after extract, dataList size is " + list.size()
                +" data is :"+ sf.toString());
        return  list;
    }

    private void createDataSet(String datasetName,Map<String,String> metricData, Map<String, DataSet> dataMap) throws Exception {
        DataSet dataSet = null;
        if(dataMap.containsKey(datasetName)){
            dataSet = dataMap.get(datasetName);
        }else{
            dataSet = new DataSet(datasetName);
            dbConf.getDefChecker().initializeColumns(dataSet);
            dataMap.put(dataSet.getName(),dataSet);
        }
        try {
            dataSet.addRow(createRow(metricData, dataSet));
        } catch (Exception e) {
            logger.error("when create data-set, exception happened" + e.getMessage(),e);
            throw e;
        }
    }

    private String[] createRow(Map<String, String> metricData, DataSet dataSet) {
        String[] row = dataSet.newRow();
        for (Map.Entry<String,String> entry: metricData.entrySet()) {
            dataSet.fillRow(entry.getKey(), entry.getValue(), row);
        }
        return row;
    }

    private Map<String, String> dealMapping(StorageObject obj, Map<String, List<StorageObject>> storObjMap, Counter c, long startTime) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        logger.debug("start dealing mapping all metric item , storage object is :"
                + obj.getTypeName()+"_"+obj.getType()+"_"+obj.getName());
        InputStream ips = getMetricMappingStream(c.getName().toLowerCase(),
                c.getMapping());
        Metrics metrics = CommonUtils.makeBeanFromXML(ips, Metrics.class);
        Map<String, String> metricMap = new HashMap<String, String>();
        for (Metric m : metrics.getMetrics()) {
            //logger.debug("start deal metric item , dataSet: "+ m.getDataSet()+"_column:" + m.getColumn());
            if(m.getTasks()==null&&m.getValue()!=null){
                metricMap.put(m.getColumn(),m.getValue());
                continue;
            }
            List<Task> tasks = m.getTasks().getTaskList();
            Map<String, String> flowContext = new HashMap<String, String>();

            for (int i = 0; i < tasks.size(); i++) {
                //logger.debug("start execute the flow task ,task target is " + tasks.get(i).getTarget() + "");
                Task task = tasks.get(i);
                ReflectionUtils.invoke(task, obj, flowContext, storObjMap);
                if (i == tasks.size() - 1) {
                    metricMap.put(m.getColumn(), flowContext.get(task.getResult()));
                }
                //logger.debug("task execute complete,");
            }
        }
        String timestamp = CommonUtils.getCurrentTimeStamp();
        metricMap.put("TS", timestamp);
        metricMap.put("DURATION", "300");
        return metricMap;
    }

    private Map<String,List<StorageObject>> makeQuickSearchMap(List<StorageObject> storageObjects) {
        Map<String,List<StorageObject>> map = new HashMap<String,List<StorageObject>>();
        for(StorageObject s : storageObjects){
            if(map.get(s.getTypeName())!=null){
                map.get(s.getTypeName()).add(s);
            }else{
                List<StorageObject> list = new ArrayList<StorageObject>();
                list.add(s);
                map.put(s.getTypeName(),list);
            }
        }
        return map;
    }


    private InputStream getMetricMappingStream(String name, String mappingName) {
        logger.info("start get mapping from xml, the xml file is " + "/conf/metric/" + name + "/" + mappingName + ".xml");
        return this.getClass().getResourceAsStream("/conf/metric/" + name + "/" + mappingName + ".xml");
    }


    private void addPerformanceData(List<StorageObject> storageObjects, Map<String, Map<String, String>> perfData) throws Exception {
        //add data to storageObject
        for (StorageObject obj : storageObjects) {
            Map<String, String> data = perfData.get(obj.getType() + "_" + obj.getId());
            if (data != null) {
                obj.setPerfData(data);
            }
        }
        perfData = null;
    }
}
