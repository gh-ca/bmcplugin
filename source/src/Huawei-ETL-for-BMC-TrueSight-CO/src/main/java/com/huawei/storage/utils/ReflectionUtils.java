package com.huawei.storage.utils;

import com.huawei.storage.constants.DataType;
import com.huawei.storage.constants.ObjectType;
import com.huawei.storage.domain.StorageObject;
import com.huawei.storage.domain.StorageObjectType;
import com.huawei.storage.domain.Task;
import com.huawei.storage.exception.ETLException;
import com.huawei.storage.oceanstor.rest.exception.RestException;
import com.huawei.storage.oceanstor.rest.operation.DeviceManager;
import com.huawei.storage.oceanstor.rest.operation.OceanStorOperation;
import com.huawei.storage.oceanstor.rest.operation.OperationResult;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Huawei Technologies  all rights reserved
 */
public class ReflectionUtils {

    private static Logger logger = Logger.getLogger(ReflectionUtils.class);
    private static Map<String,Object> handlerCache = new HashMap<String, Object>();
    private static Map<String,Method> methodCache = new HashMap<String, Method>();

    public static void invoke(Task t, StorageObject obj, Map<String, String> flowContext, Map<String, List<StorageObject>> storObjMap) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        String clazzName = t.getMethod();
        logger.info("start execute task method ,the object is " + obj.getTypeName()
                + " method is " + clazzName + " task target is " + t.getTarget());
        String className = clazzName.substring(0,clazzName.lastIndexOf("."));
        String methodName = clazzName.substring(clazzName.lastIndexOf(".")+1);
        Object handler = handlerCache.get(className);
        Method method = methodCache.get(methodName);
        if(handler==null||method==null){
            Class<?> clazz = Class.forName(className);
            handler = clazz.newInstance();
            handlerCache.put(className,handler);
            method = clazz.getDeclaredMethod(methodName, Task.class, StorageObject.class, Map.class ,Map.class);
            methodCache.put(methodName,method);
        }

        method.invoke(handler,t,obj,flowContext,storObjMap);
    }

    @SuppressWarnings("unchecked")
    public static List<StorageObject> invokeRestHandler(StorageObjectType type,DeviceManager deviceManager) throws ETLException {
        String methodfullName = type.getMethod();
        logger.info("start execute restHandler method ,the method is " + methodfullName);
        String className = methodfullName.substring(0,methodfullName.lastIndexOf("."));
        String methodName = methodfullName.substring(methodfullName.lastIndexOf(".")+1);
        List<StorageObject> storageObjects = new ArrayList<StorageObject>();
        try {
            Class<?> clazz = Class.forName(className);
            Object o = clazz.newInstance();
            Method method = clazz.getDeclaredMethod(methodName,String.class,String.class ,DeviceManager.class);
            storageObjects = (List<StorageObject>) method.invoke(o, type.getCountCommand(),type.getQueryCommand(),deviceManager);
            //get performance data from Rest, because some disk array can't get the performance file easily
            setPerformanceData(type,storageObjects,deviceManager);
        }catch (IllegalAccessException e) {
            logger.error("get data from rest failed " + e.getMessage() + e.getStackTrace() ,e );
            throw new ETLException("get Data from rest failed "+ e.getMessage() + e.getStackTrace(),e);
        } catch (InstantiationException e) {
            logger.error("get data from rest failed " + e.getMessage() + e.getStackTrace() ,e );
            throw new ETLException("get Data from rest failed "+ e.getMessage() + e.getStackTrace(),e);
        } catch (NoSuchMethodException e) {
            logger.error("get data from rest failed " + e.getMessage() + e.getStackTrace() ,e );
            throw new ETLException("get Data from rest failed "+ e.getMessage() + e.getStackTrace(),e);
        } catch (ClassNotFoundException e) {
            logger.error("get data from rest failed " + e.getMessage() + e.getStackTrace() ,e );
            throw new ETLException("get Data from rest failed "+ e.getMessage() + e.getStackTrace(),e);
        } catch (InvocationTargetException e) {
            logger.error("get data from rest failed " + e.getMessage() + e.getStackTrace() ,e );
            throw new ETLException("get Data from rest failed "+ e.getMessage() + e.getStackTrace(),e);
        } catch (RestException e) {
            logger.error("get data from rest failed " + e.getMessage() + e.getStackTrace() ,e );
            throw new ETLException("get Data from rest failed "+ e.getMessage() + e.getStackTrace(),e);
        }
        return storageObjects;
    }

    private static void setPerformanceData(StorageObjectType type, List<StorageObject> storageObjects, DeviceManager deviceManager) throws RestException {
        if(type.getPerformance()!=null){
            String[] performanceIndicats = type.getPerformance().split(",");
            StringBuilder sf = new StringBuilder();
            for(String perf : performanceIndicats){
                sf.append(DataType.valueOf(perf).getValue()).append(",");
            }
            String statisticIDList = sf.deleteCharAt(sf.length()-1).toString();
            OceanStorOperation operation = new OceanStorOperation();
            operation.setOperationName("get-current-performance-data");
            operation.putOperationData("CMO_STATISTIC_DATA_ID_LIST",statisticIDList);
            for(StorageObject object : storageObjects){
                object.setPerfData(getPerformanceDataFromRest(deviceManager,
                        performanceIndicats, operation, object));
            }
        }
    }

    private static Map<String,String> getPerformanceDataFromRest(DeviceManager deviceManager, String[] statisticNames, OceanStorOperation operation, StorageObject object) throws RestException {
        String uuid = object.getRestData().get("TYPE")+":"+object.getRestData().get("ID");
        operation.putOperationData("CMO_STATISTIC_UUID",uuid);
        OperationResult result = deviceManager.performAction(operation);
        Map<String,String> perfData = new HashMap<String, String>();
        if("0".equals(result.getErrorCode())){
            List<Map<String, String>> resultData = result.getResultData();
            if(resultData!=null&&resultData.size()>0){
                String statisticData = resultData.get(0).get("CMO_STATISTIC_DATA_LIST");
                if(statisticData!=null){
                    String[] statisticDatas = statisticData.split(",");
                    for (int i = 0; i < statisticNames.length ; i++) {
                        perfData.put(statisticNames[i],statisticDatas[i]);
                    }
                }
            }
        }
        return perfData;
    }
    }
