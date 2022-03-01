package com.huawei.storage.job;

import com.huawei.storage.constants.ConnectionVO;
import com.huawei.storage.domain.Link;
import com.huawei.storage.domain.StorageObject;
import com.huawei.storage.domain.SubObject;
import com.huawei.storage.oceanstor.rest.operation.OceanStorOperation;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Huawei Technologies  all rights reserved
 * <p>
 * Created by m00373015 on 2016/8/22.
 */

public class RestJobTest {
    RestJob restJob;
    private Logger log = Logger.getLogger(RestJobTest.class);

    @Before
    public void setUp() throws Exception {
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("log4j.properties"));
        PropertyConfigurator.configure(properties);
        ConnectionVO connectionVO = new ConnectionVO();
        connectionVO.setUsername("admin");
        connectionVO.setPassword("Admin@storage");
//        connectionVO.setPassword("Admin@storage2");
        connectionVO.setScope("0");
        connectionVO.setIpControllerA("10.158.196.210");
        connectionVO.setHostIP("10.158.196.210");
        connectionVO.setRestPort("40000");
//        connectionVO.setIpControllerA("10.169.219.91");
//        connectionVO.setHostIP("10.169.219.91");
//        connectionVO.setRestPort("34088");
        restJob = new RestJob(connectionVO);
    }

    @Test
    public void testGetAllObjects() throws Exception {
        List<StorageObject> storageObjects = restJob.call();
        System.out.println("===== rest return size is :"+storageObjects.size());
        List<String> list = new ArrayList<String>();
        for (StorageObject s: storageObjects) {
            list.add(s.getType()+"_"+s.getId()+"_"+s.getName());
            System.out.println("object is "+ s.getTypeName()+"_" +
                    (s.getName()==null?s.getId():s.getName())
            +" Performance Data is :" + s.getPerfData());
        }
        //log.debug(list);
    }

    @Test
    public void testEvalExpression() throws Exception {
        Class<RestJob> clazz = (Class<RestJob>) Class.forName("com.huawei.storage.job.RestJob");
        Constructor<RestJob> restJobConstructor = clazz.getDeclaredConstructor(ConnectionVO.class);
        RestJob restJob = restJobConstructor.newInstance(new ConnectionVO());
        Method method = clazz.getDeclaredMethod("evalExpression", String.class, StorageObject.class);
        method.setAccessible(true);
        String exp = "filter=PARENTID::${ID},ALLOCATETYPE:${TYPE}";
        StorageObject obj = new StorageObject();
        Map<String, String> restData = new HashMap<String, String>();
        restData.put("ID","2");
        restData.put("TYPE","3");
        restData.put("ALLOCATETYPE","4");
        //ASSOCIATEOBJTYPE=${TYPE}
        obj.setRestData(restData);
        String value = (String) method.invoke(restJob, exp, obj);
        log.debug(value);
        exp = "ASSOCIATEOBJTYPE=${TYPE}";
        log.debug(method.invoke(restJob,exp,obj));
    }

    @Test
    public void testPopulateLinkData() throws Exception {
        Class<RestJob> clazz = (Class<RestJob>) Class.forName("com.huawei.storage.job.RestJob");
        Constructor<RestJob> restJobConstructor = clazz.getDeclaredConstructor(ConnectionVO.class);
        RestJob restJob = restJobConstructor.newInstance(new ConnectionVO());
        Method method = clazz.getDeclaredMethod("populateLinkData", SubObject.class,
                StorageObject.class, OceanStorOperation.class);
        method.setAccessible(true);
        Link link = new Link("batch","filter=OWNINGCONTROLLER::${ID}");
        SubObject sub = new SubObject();
        sub.setName("eth_port");
        sub.setLink(link);
        StorageObject obj = new StorageObject();
        Map<String, String> restData = new HashMap<String, String>();
        restData.put("ID","2");
        obj.setRestData(restData);
        OceanStorOperation operation = new OceanStorOperation();
        method.invoke(restJob,sub,obj,operation);
        log.debug(operation);
        //==============
        //ASSOCIATEOBJTYPE=${TYPE}#ASSOCIATEOBJID=${ID}
        link = new Link("associate","ASSOCIATEOBJTYPE=${TYPE}#ASSOCIATEOBJID=${ID}");
        sub.setName("lun");
        sub.setLink(link);
        restData.put("TYPE","21");
        restData.put("ID","1");
        OceanStorOperation op2 = new OceanStorOperation();
        method.invoke(restJob,sub,obj,op2);
        log.debug(op2);
    }


}