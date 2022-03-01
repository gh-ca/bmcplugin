package com.huawei.storage.utils;

import com.huawei.storage.constants.FileSystemType;
import com.huawei.storage.domain.StorageObject;
import com.huawei.storage.domain.Task;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Huawei Technologies  all rights reserved
 * <p>
 * Created by m00373015 on 2016/9/17.
 */
public class TaskHandlerTest {
    @Test
    public void testGetObjectUniqueName() throws Exception {
        TaskHandler handler = new TaskHandler();
        Task task = new Task();
        task.setTarget("StoragePool-[${ID}]-[${NAME}]");
        task.setResult("DS_NME");
        Map<String,List<StorageObject>> storageObjects = new HashMap<String,List<StorageObject>>();
        Map<String,String> flowContext = new HashMap<String, String>();
        StorageObject obj = new StorageObject();
        Map<String, String> restData = new HashMap<String, String>();
        restData.put("ID","01");
        restData.put("NAME","Cinder-HX");
        obj.setRestData(restData);
        handler.getObjectUniqueName(task,obj,flowContext,storageObjects);
        System.out.println(flowContext.get(task.getResult()));
    }

    @Test
    public void testEvalExpression_simple() throws Exception {
        TaskHandler handler = new TaskHandler();
        Task task = new Task();
        task.setTarget("${MEMORYSIZE},*,1024,*,1024");
        task.setResult("ST_CACHE");
        Map<String,List<StorageObject>> storageObjects = new HashMap<String,List<StorageObject>>();
        Map<String,String> flowContext = new HashMap<String, String>();
        flowContext.put("MEMORYSIZE","16384");
        StorageObject obj = new StorageObject();
        Map<String, String> restData = new HashMap<String, String>();
        obj.setRestData(restData);
        handler.evalExpression(task,obj,flowContext,storageObjects);
        System.out.println(flowContext.get(task.getResult()));
    }

    @Test
    public void testEvalExpression_complex() throws Exception {
        TaskHandler handler = new TaskHandler();
        Task task = new Task();
        task.setTarget("(${ReadBandWidth},+,${WriteBandWidth}),*,1024,*,1024,*,8");
        task.setResult("ST_SYSTEM_BANDWIDTH");
        Map<String,List<StorageObject>> storageObjects = new HashMap<String,List<StorageObject>>();
        Map<String,String> flowContext = new HashMap<String, String>();
        flowContext.put("ReadBandWidth","6");
        flowContext.put("WriteBandWidth","6");
        StorageObject obj = new StorageObject();
        Map<String, String> restData = new HashMap<String, String>();
        obj.setRestData(restData);
        handler.evalExpression(task,obj,flowContext,storageObjects);
        System.out.println(flowContext.get(task.getResult()));
    }

    @Test
    public void testEvalExpression_complex_complex() throws Exception {
        TaskHandler handler = new TaskHandler();
        Task task = new Task();
        task.setTarget("(${ReadBandWidth},+,${WriteBandWidth}),*,1024,*,(${ReadBandWidth},+,${WriteBandWidth}),*,1024,*,8");
        task.setResult("ST_SYSTEM_BANDWIDTH");
        Map<String,List<StorageObject>> storageObjects = new HashMap<String,List<StorageObject>>();
        Map<String,String> flowContext = new HashMap<String, String>();
        flowContext.put("ReadBandWidth","6");
        flowContext.put("WriteBandWidth","6");
        StorageObject obj = new StorageObject();
        Map<String, String> restData = new HashMap<String, String>();
        obj.setRestData(restData);
        handler.evalExpression(task,obj,flowContext,storageObjects);
        System.out.println(flowContext.get(task.getResult()));
    }

    @Test
    public void testEvalExpression_super_complex() throws Exception {
        TaskHandler handler = new TaskHandler();
        Task task = new Task();
        task.setTarget("(${ReadBandWidth},+,${WriteBandWidth}),*,1024,*,(${ReadBandWidth},+,${WriteBandWidth}),*,1024,*,8,*,(${ReadBandWidth},+,${WriteBandWidth})");
        task.setResult("ST_SYSTEM_BANDWIDTH");
        Map<String,List<StorageObject>> storageObjects = new HashMap<String,List<StorageObject>>();
        Map<String,String> flowContext = new HashMap<String, String>();
        flowContext.put("ReadBandWidth","6");
        flowContext.put("WriteBandWidth","6");
        StorageObject obj = new StorageObject();
        Map<String, String> restData = new HashMap<String, String>();
        obj.setRestData(restData);
        handler.evalExpression(task,obj,flowContext,storageObjects);
        System.out.println(flowContext.get(task.getResult()));
    }

    @Test
    public void testGetLunAndFileSystemType_Lun() throws Exception {
        TaskHandler handler = new TaskHandler();
        Task task = new Task();
        task.setTarget("TYPE,SUBTYPE");
        task.setResult("ST_VOLUME_TYPE");
        Map<String,List<StorageObject>> storageObjects = new HashMap<String,List<StorageObject>>();
        Map<String,String> flowContext = new HashMap<String, String>();
        flowContext.put("TYPE","11");
        flowContext.put("SUBTYPE","0");
        StorageObject obj = new StorageObject();
        Map<String, String> restData = new HashMap<String, String>();
        obj.setRestData(restData);
        handler.getLunAndFileSystemType(task,obj,flowContext,storageObjects);
        System.out.println(flowContext.get(task.getResult()));
    }

    @Test
    public void testGetLunAndFileSystemType_Filesystem() throws Exception {
        TaskHandler handler = new TaskHandler();
        Task task = new Task();
        task.setTarget("TYPE,SUBTYPE");
        task.setResult("ST_VOLUME_TYPE");
        Map<String,List<StorageObject>> storageObjects = new HashMap<String,List<StorageObject>>();
        Map<String,String> flowContext = new HashMap<String, String>();
        flowContext.put("TYPE","40");
        flowContext.put("SUBTYPE","0");
        StorageObject obj = new StorageObject();
        Map<String, String> restData = new HashMap<String, String>();
        obj.setRestData(restData);
        handler.getLunAndFileSystemType(task,obj,flowContext,storageObjects);
        System.out.println(flowContext.get(task.getResult()));
        Assert.assertEquals(FileSystemType.CommonFileSystem.name()
                ,flowContext.get(task.getResult()));
    }

    @Test
    public void testBigDecimalDivide() throws Exception {
        BigDecimal d1 = new BigDecimal("92233720368547758070");
        BigDecimal d2 = new BigDecimal("192233720368547758070");
        BigDecimal divide = d1.divide(d2,4,BigDecimal.ROUND_HALF_UP);
        System.out.println(divide.toPlainString());
    }
}