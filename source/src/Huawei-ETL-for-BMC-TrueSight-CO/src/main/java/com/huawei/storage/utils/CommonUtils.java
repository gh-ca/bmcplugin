package com.huawei.storage.utils;

import com.huawei.storage.constants.ConnectionVO;
import com.huawei.storage.domain.*;
import com.huawei.storage.oceanstor.rest.operation.OceanStorOperation;
import com.huawei.storage.oceanstor.rest.operation.OperationExecutor;
import com.huawei.storage.oceanstor.rest.operation.OperationResult;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Huawei Technologies  all rights reserved
 */
public class CommonUtils {
    private final Logger logger = Logger.getLogger(CommonUtils.class);

    private CommonUtils(){};


    public static void upperMapKey(Map<String,String> map){
        Map<String,String> tempMap = new HashMap<String, String>();
        for(Map.Entry<String,String> entry : map.entrySet()){
            String key = entry.getKey();
            String value = entry.getValue();
            tempMap.put(key.toUpperCase(),value);
        }
        map.putAll(tempMap);
    }

    @SuppressWarnings("unchecked")
    public static <T> T makeBeanFromXML(InputStream ips, Class<T> clazz) {
        XStream xStream = new XStream(new DomDriver("utf-8"));
        XStream.setupDefaultSecurity(xStream);
        xStream.allowTypes(new Class[]{Counter.class, Counters.class, Link.class, ManagedObjects.class,Metric.class,
                Metrics.class,StorageObjectType.class,SubObject.class,SubObjects.class,Task.class,Tasks.class});
        xStream.processAnnotations(clazz);
        return (T)xStream.fromXML(ips);
    }

    public static OperationResult executeRestRequest(ConnectionVO vo, OceanStorOperation operation){
        OperationExecutor executor = new OperationExecutor();
        executor.setDMConnection(vo.getUsername(),vo.getPassword(),
                "https://"+vo.getHostIP()+":"+vo.getRestPort()+"/deviceManager/rest",
                vo.getScope());
        return executor.execute(operation);
    }

    public static String getCurrentTimeStamp() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sf.format(new Date());
    }
}
