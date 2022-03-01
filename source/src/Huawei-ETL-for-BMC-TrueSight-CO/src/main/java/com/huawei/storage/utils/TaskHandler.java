package com.huawei.storage.utils;

import com.huawei.storage.constants.*;
import com.huawei.storage.domain.StorageObject;
import com.huawei.storage.domain.Task;
import com.huawei.storage.domain.Tier;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.math.BigDecimal.ZERO;

/**
 * Huawei Technologies  all rights reserved
 * <p>
 * Created by m00373015 on 2016/8/16.
 */
public class TaskHandler {


    private Logger logger = Logger.getLogger(TaskHandler.class);
   /* //###
    private ExecutorService executorService;
    private com.neptuny.cpit.logger.Logger bcoLogger ;
    //###
    public TaskHandler(){
        String loggerKey = "service-test-log";
        String loglevel = "ALL";
        String loggerFileName;

        loggerFileName = com.neptuny.cpit.logger.Logger.loadLoggerAppenderForInstance(loggerKey, "scheduler", loglevel, Boolean.TRUE.booleanValue());
        File loggerFile = new File(loggerFileName);
        this.bcoLogger = com.neptuny.cpit.logger.Logger.getLogger(loggerKey);
        this.bcoLogger.enableDiagnosticLogCaching();
    }
    */

    public void getFromRestContext(Task task, StorageObject obj, Map<String, String> flowContext, Map<String, List<StorageObject>> storObjMap) {
        String[] targets = task.getTarget().split(",");
        String[] results = task.getResult().split(",");
        logger.debug("object is :" + obj.getTypeName() + "_" + obj.getName() + "targets is " + task.getTarget());
        logger.debug("flowContxt is " + flowContext);
        for (int i = 0; i < targets.length; i++) {
            logger.debug("get from rest is : " + obj.getRestData().get((targets[i])));
            String contextValue = obj.getRestData().get((targets[i]));
            flowContext.put(results[i], contextValue);
        }
    }

    public void calculatePercentage(Task task, StorageObject obj, Map<String, String> flowContext, Map<String, List<StorageObject>> storObjMap) {
        String[] targets = task.getTarget().split(",");
        logger.debug("object is :" + obj.getTypeName() + "_" + obj.getName() + "targets is " + task.getTarget());
        logger.debug("flow context is " + flowContext);

        if(flowContext.get(targets[0])==null||flowContext.get(targets[1])==null){
            flowContext.put(task.getResult(),"0");
            return;
        }
        double dividend = (Double.parseDouble(flowContext.get(targets[0])) > 0) ? Double.parseDouble(flowContext.get(targets[0])) : 0;
        if (flowContext.get(targets[1]) != null) {
            double divisor = Double.parseDouble(flowContext.get(targets[1]));
            double result = ((dividend / divisor) <= 1) ? (dividend / divisor) : 1;
            logger.debug("result is " + result);
            flowContext.put(task.getResult(), String.format("%.2f", result));
        } else {
            flowContext.put(task.getResult(), String.format("%.2f", dividend / 100));
        }
    }


    public void getFromPerformanceData(Task task, StorageObject obj, Map<String, String> flowContext, Map<String, List<StorageObject>> storObjMap) {
        String[] targets = task.getTarget().split(",");
        String[] results = task.getResult().split(",");
        logger.debug("object is :" + obj.getTypeName() + "_" + obj.getName() + "targets is " + targets
                + "flowContxt is " + flowContext);
        for (int i = 0; i < targets.length; i++) {
            if (obj.getPerfData() != null && obj.getPerfData().get((targets[i])) != null) {
                flowContext.put(results[i], obj.getPerfData().get((targets[i])));
            } else {
                flowContext.put(results[i], "0");
            }
            logger.debug("the context is :" + flowContext);
        }
    }


    public void getAllFreePortCount(Task task, StorageObject obj, Map<String, String> flowContext, Map<String, List<StorageObject>> storObjMap) {
        //get eth port , get sas port , get fc port ,get fc_oe port
        int freePortNum = 0;
        List<StorageObject> ports = new ArrayList<StorageObject>();
        String[] portTypes = task.getTarget().split(",");
        for (String port : portTypes) {
            if (storObjMap.get(port) != null) {
                ports.addAll(storObjMap.get(port));
            }
        }
        logger.debug("object is :" + obj.getTypeName() + "_" + obj.getName());
        logger.debug("sub port count is " + ports.size() + "sub port list :" + ports);
        for (StorageObject s : ports) {
            //11 link down
            if ("11".equals(s.getRestData().get("RUNNINGSTATUS"))) {
                logger.debug("link down port is " + s.getName());
                freePortNum++;
            }
        }
        flowContext.put(task.getResult(), freePortNum + "");
    }

    public void convertSectorToBytes(Task task, StorageObject obj, Map<String, String> flowContext, Map<String, List<StorageObject>> storObjMap) {
        String target = task.getTarget();
        logger.debug("object is :" + obj.getTypeName() + "_" + obj.getName() + " targets is " + task.getTarget());
        logger.debug("target get from context is " + flowContext.get(target));
        String value = flowContext.get(target);
        BigDecimal result = new BigDecimal(0);
        if (null != value) {
            BigDecimal dividend = new BigDecimal(value);
            result = dividend.divide(new BigDecimal(2));
        }
        logger.debug("convert to bytes : from " + value + " ,to " + result);
        flowContext.put(task.getResult(), result.toPlainString());
    }


    public void getDifference(Task task, StorageObject obj, Map<String, String> flowContext, Map<String, List<StorageObject>> storObjMap) {
        String[] targets = task.getTarget().split(",");
        logger.debug("object is :" + obj.getTypeName() + "_" + obj.getName() + "targets is " + task.getTarget());
        logger.debug("difference parameter is " + flowContext);
        BigDecimal subtractor = new BigDecimal(flowContext.get(targets[0]));
        BigDecimal subtrahend = new BigDecimal(flowContext.get(targets[1]));
        BigDecimal result = (subtractor.subtract(subtrahend).compareTo(ZERO) < 0) ? ZERO : subtractor.subtract(subtrahend);

        flowContext.put(task.getResult(), result.toPlainString());
    }

    public void getLunAndFileSystemCount(Task task, StorageObject obj, Map<String, String> flowContext, Map<String, List<StorageObject>> storObjMap) {
        String[] objTypes = task.getTarget().split(",");
        int count = 0;
        for (String objTypeName : objTypes) {
            List<StorageObject> storageObjects = storObjMap.get(objTypeName);
            if (storageObjects != null) {
                for (StorageObject o : storageObjects) {
                    if (o.getRestData().get("PARENTID").equals(obj.getId())) {
                        count++;
                    }
                }

            }
        }
        flowContext.put(task.getResult(), count + "");
    }

    public void convertUnit(Task task, StorageObject obj, Map<String, String> flowContext, Map<String, List<StorageObject>> storObjMap) {

    }


    public void addValues(Task task, StorageObject obj, Map<String, String> flowContext, Map<String, List<StorageObject>> storObjMap) {
        long result = 0;
        for (String var : task.getTarget().split(",")) {
            logger.debug("var key is :" + var);
            String value = flowContext.get(var);
            logger.debug("the add value is " + value);
            if (null != value){
                try{
                    result += Long.valueOf(flowContext.get(var));
                }catch (NumberFormatException e){
                    logger.warn(var + "get from rest is not a number,the value:" + value);
                }
            }

        }
        flowContext.put(task.getResult(), result + "");
    }


    public void getHostLunsCapacity(Task task, StorageObject obj, Map<String, String> flowContext, Map<String, List<StorageObject>> storObjMap) {
        List<StorageObject> subObjectList = obj.getSubObjectList();
        long capacityCount = 0L;
        if (null != subObjectList && subObjectList.size() > 0) {
            for (StorageObject subObj : subObjectList) {
                if (ObjectType.Lun.getValue() == subObj.getType()) {
                    String capacity = subObj.getRestData().get(task.getTarget());
                    capacityCount += Long.valueOf(capacity);
                }
            }
            flowContext.put(task.getResult(), capacityCount + "");
        }
    }

    public void getLunRaidLevel(Task task, StorageObject obj, Map<String, String> flowContext, Map<String, List<StorageObject>> storObjMap) {
        String parentID = obj.getRestData().get(task.getTarget());
        String raidLevel = "";
        List<StorageObject> storageObjects = storObjMap.get(ObjectType.StoragePool.name());
        for (StorageObject o : storageObjects) {
            if (parentID.equals(o.getId())) {

                for (int i = 0; i < 3; i++) {
                    if (o.getRestData().get("TIER" + i + "RAIDLV") != null
                            &&!o.getRestData().get("TIER" + i + "RAIDLV").equals("0")) {
                        raidLevel = raidLevel + "TIER" + i + "RAIDLV:" +
                                RaidLevelType.valueOf(
                                        Integer.parseInt(o.getRestData().get("TIER" + i + "RAIDLV"))
                                ).name() + " ";
                    }
                }
                raidLevel.substring(0, raidLevel.length() - 1);
                flowContext.put(task.getResult(), raidLevel);
                break;
            }
        }
    }

    public void getObjectUniqueName(Task task, StorageObject obj, Map<String, String> flowContext, Map<String, List<StorageObject>> storObjMap) {
        String target = task.getTarget();
        int start;
        int end;
        String exp;
        while ((start = target.indexOf("${")) > -1) {
            end = target.indexOf("}");
            exp = target.substring(start + 2, end);
            if(obj.getRestData().get(exp)!=null){
                target = target.replace("${" + exp + "}", obj.getRestData().get(exp));
            }else{
                target = target.replace("${" + exp + "}", "");
            }

        }
        flowContext.put(task.getResult(), target);
    }

    public void getContainerDSName(Task task, StorageObject obj, Map<String, String> flowContext, Map<String, List<StorageObject>> storObjMap) {
        String[] nameAndPre = task.getTarget().split(",");
        if ("System".equals(nameAndPre[0])) {
            StorageObject system = storObjMap.get("System").get(0);
            flowContext.put(task.getResult(), nameAndPre[1] + system.getId());
        }
    }

    public void getContainerName(Task task, StorageObject obj, Map<String, String> flowContext, Map<String, List<StorageObject>> storObjMap) {
        String[] nameAndPre = task.getTarget().split(",");
        if ("System".equals(nameAndPre[0])) {
            StorageObject system = storObjMap.get("System").get(0);
            flowContext.put(task.getResult(), nameAndPre[1] + system.getId());
        }
    }


    public void getBCOParentDSName(Task task, StorageObject obj, Map<String, String> flowContext, Map<String, List<StorageObject>> storObjMap) {
        if (task.getTarget().equals("System")) {
            flowContext.put(task.getResult(),
                    "Huawei_OceanStor+" + storObjMap.get("System").get(0).getId());
        } else {
            List<StorageObject> storageObjects = storObjMap.get(task.getTarget());
            for (StorageObject o : storageObjects) {
                String parentid = obj.getRestData().get("PARENTID");
                if (null != parentid) {
                    if (parentid.equals(o.getId())) {
                        flowContext.put(task.getResult(),
                                o.getTypeName() + "-[" + o.getId() + "]-" +
                                        "[" + o.getName() + "]"
                        );
                        break;
                    }
                }
            }
        }

    }


    public void getCurrentTimeStamp(Task task, StorageObject obj, Map<String, String> flowContext, Map<String, List<StorageObject>> storObjMap) {
        flowContext.put(task.getResult(), CommonUtils.getCurrentTimeStamp());
    }


    public void getDataFromRest(Task task, StorageObject obj, Map<String, String> flowContext, Map<String, List<StorageObject>> storObjMap) {
        flowContext.put(task.getResult(), obj.getRestData().get(task.getTarget()));
    }

    public void getDataFromPerformance(Task task, StorageObject obj, Map<String, String> flowContext, Map<String, List<StorageObject>> storObjMap) {
        if (obj.getPerfData() != null && task.getTarget() != null
                && obj.getPerfData().get(task.getTarget()) != null) {
            flowContext.put(task.getResult(), obj.getPerfData().get(task.getTarget()));
        } else {
            flowContext.put(task.getResult(), "0");
        }
    }

    public void convertNumToEnum(Task task, StorageObject obj, Map<String, String> flowContext, Map<String, List<StorageObject>> storObjMap) {
        String diskType = flowContext.get(task.getTarget());
        int diskTypeNum = Integer.parseInt(diskType);
        logger.debug("the disk type is " + diskType);
        logger.debug("the disk type is " + DiskType.valueOf(diskTypeNum));
        flowContext.put(task.getResult(), DiskType.valueOf(diskTypeNum).name());
    }


    public void PercentageToNum(Task task, StorageObject obj, Map<String, String> flowContext, Map<String, List<StorageObject>> storObjMap) {
        String percentage = flowContext.get(task.getTarget());
        float f = Float.parseFloat(percentage);
        flowContext.put(task.getResult(), f / 100 + "");
    }

    public void computeDiskCapacity(Task task, StorageObject obj, Map<String, String> flowContext, Map<String, List<StorageObject>> storObjMap) {
        String[] parameters = task.getTarget().split(",");
        long multiplicand = Long.parseLong(flowContext.get(parameters[0]));
        long multiplicator = Long.parseLong(flowContext.get(parameters[1]));
        flowContext.put(task.getResult(), multiplicand * multiplicator + "");
    }

    public void convertMsToSec(Task task, StorageObject obj, Map<String, String> flowContext, Map<String, List<StorageObject>> storObjMap) {
        long ms = Long.parseLong(flowContext.get(task.getTarget()));
        flowContext.put(task.getResult(), ms / 1000 + "");
    }

    public void caculateIOrate(Task task, StorageObject obj, Map<String, String> flowContext, Map<String, List<StorageObject>> storObjMap) {
        String[] parameters = task.getTarget().split(",");
        long multiplicand = Long.parseLong(flowContext.get(parameters[0]));
        long multiplicator = Long.parseLong(flowContext.get(parameters[1]));
        flowContext.put(task.getResult(), multiplicand * multiplicator + "");
    }


    public void getPoolDiskType(Task task, StorageObject obj, Map<String, String> flowContext, Map<String, List<StorageObject>> storObjMap) {
        String[] tierCAPACITY = new String[3];
        tierCAPACITY[0] = obj.getRestData().get("TIER0CAPACITY");
        tierCAPACITY[1] = obj.getRestData().get("TIER1CAPACITY");
        tierCAPACITY[2] = obj.getRestData().get("TIER2CAPACITY");

        String poolDiskType = "";
        for (int tier = 0; tier < tierCAPACITY.length; tier++) {
            if (null != tierCAPACITY[tier]){
                if (!"0".equals(tierCAPACITY[tier])) {
                    poolDiskType = poolDiskType + PoolDiskType.valueOf(tier).name() + " ";
                }
            }
        }
        poolDiskType.substring(0, poolDiskType.length() - 1);
        flowContext.put(task.getResult(), poolDiskType);
    }

    public void convertBoolToNumber(Task task, StorageObject obj, Map<String, String> flowContext, Map<String, List<StorageObject>> storObjMap) {
        String target = task.getTarget();
        logger.debug("object is :" + obj.getTypeName() + "_" + obj.getName() + "targets is " + task.getTarget());
        logger.debug("target get from context is " + flowContext.get(target));
        String result = "true".equals(flowContext.get(target)) ? "1" : "0";
        flowContext.put(task.getResult(), result);
    }

    public void getObjectRunningStatus(Task task, StorageObject obj, Map<String, String> flowContext, Map<String, List<StorageObject>> storObjMap) {
        String target = task.getTarget();
        logger.debug("object is :" + obj.getTypeName() + "_" + obj.getName() + "targets is " + task.getTarget());
        logger.debug("target get from context is " + flowContext.get(target));
        String result = RunningStatusType.valueOf(Integer.parseInt(flowContext.get(target))).name();
        flowContext.put(task.getResult(), result);
    }

    public void getLunAndFileSystemType(Task task, StorageObject obj, Map<String, String> flowContext, Map<String, List<StorageObject>> storObjMap) {
        String[] targets = task.getTarget().split(",");
        String result = "";
        logger.debug("object is :" + obj.getTypeName() + "_" + obj.getName() + "targets is " + task.getTarget());
        logger.debug("target get from context is " + flowContext);
        if(flowContext.get(targets[0])==null||flowContext.get(targets[1])==null){
            flowContext.put(task.getResult(), result);
            return;
        }
        if (String.valueOf(ObjectType.Lun.getValue()).equals(flowContext.get(targets[0]))) {
            result = LunType.valueOf(Integer.parseInt(flowContext.get(targets[1]))).name();
        } else if (String.valueOf(ObjectType.FileSystem.getValue()).equals(flowContext.get(targets[0]))) {
            result = FileSystemType.valueOf(Integer.parseInt(flowContext.get(targets[1]))).name();
        }
        flowContext.put(task.getResult(), result);
    }

    public void computeCapacity(Task task, StorageObject obj, Map<String, String> flowContext, Map<String, List<StorageObject>> storObjMap) {
        String[] parameters = task.getTarget().split(",");
        if (obj.getRestData().get(parameters[0]) != null &&
                obj.getRestData().get(parameters[1]) != null) {
            BigDecimal multiplicand = new BigDecimal(obj.getRestData().get(parameters[0]));
            BigDecimal multiplicator = new BigDecimal(obj.getRestData().get(parameters[1]));
            flowContext.put(task.getResult(), multiplicand.multiply(multiplicator).toPlainString());
        } else {
            flowContext.put(task.getResult(), "0");
        }

    }


    public void getAllPortCount(Task task, StorageObject obj, Map<String, String> flowContext, Map<String, List<StorageObject>> storObjMap) {
        //get eth port , get sas port , get fc port ,get fc_oe port
        List<StorageObject> ports = new ArrayList<StorageObject>();
        String[] portTypes = task.getTarget().split(",");
        for (String port : portTypes) {
            if (storObjMap.get(port) != null) {
                ports.addAll(storObjMap.get(port));
            }
        }
        logger.debug("object is :" + obj.getTypeName() + "_" + obj.getName());
        logger.debug("sub port count is " + ports.size() + "sub port list :" + ports);
        flowContext.put(task.getResult(), ports.size() + "");
    }

    public void evalExpression(Task task, StorageObject obj, Map<String, String> flowContext, Map<String, List<StorageObject>> storObjMap) {
        StringBuilder sf = new StringBuilder();
        String result = "0";
        if (task.getTarget() != null) {
            String exp = task.getTarget();
            if (exp.contains("(")) {
                while (exp.indexOf("(") >= 0) {
                    int start = exp.indexOf("(");
                    int end = exp.indexOf(")");
                    String expression = exp.substring(start + 1, end);
                    if (start != 0) {
                        sf.append(exp.substring(0, start));
                    }
                    sf.append(evalExpression(expression, flowContext));
                    if (!exp.substring(end + 1).contains("(")) {
                        sf.append(exp.substring(end + 1));
                    }
                    exp = exp.substring(end + 1);
                }
            } else {
                sf.append(exp);
            }
            result = evalExpression(sf.toString(), flowContext);
            flowContext.put(task.getResult(), result);
        }
    }

    public void getArrayModelFromProductModel(Task task, StorageObject obj, Map<String, String> flowContext, Map<String, List<StorageObject>> storObjMap) {
        String target = task.getTarget();
        logger.debug("object is :" + obj.getTypeName() + "_" + obj.getName() + "targets is " + task.getTarget());
        logger.debug("target get from context is " + flowContext.get(target));
        String result = BoxTypeConst.boxMap.get(flowContext.get(target));
        flowContext.put(task.getResult(), result);
    }

    public void getBoxTypeFromProductModel(Task task, StorageObject obj, Map<String, String> flowContext, Map<String, List<StorageObject>> storObjMap) {
        String target = task.getTarget();
        logger.debug("object is :" + obj.getTypeName() + "_" + obj.getName() + "targets is " + task.getTarget());
        logger.debug("getBoxTypeFromProductModel target get from context is " + flowContext.get(target));
        String result = BoxTypeConst.boxMap.get(flowContext.get(target));
        flowContext.put(task.getResult(), result);
    }

    public void getLinkedLUNUniqueName(Task task, StorageObject obj, Map<String, String> flowContext, Map<String, List<StorageObject>> storObjMap) {
        flowContext.put(task.getResult(), "LUN-[" + obj.getRestData().get("ID")
                + "]-[" + obj.getRestData().get("NAME") + "]");
    }

    public void getLinkedHostUniqueName(Task task, StorageObject obj, Map<String, String> flowContext, Map<String, List<StorageObject>> storObjMap) {
        String fqdn = "Host-[" + obj.getLinkedObject().getId() + "]-[" + obj.getLinkedObject().getName() + "]";
        //###
        //bcoLogger.info("#### fqdn value get from context is " + fqdn);
        if (obj.getRestData().get("WWN") != null) {
            fqdn = fqdn + " " + obj.getRestData().get("WWN");
        }
        flowContext.put(task.getResult(), fqdn);
    }

    public void getLinkedLUNCapacity(Task task, StorageObject obj, Map<String, String> flowContext, Map<String, List<StorageObject>> storObjMap) {
        String lunCapacity = obj.getRestData().get("CAPACITY");
        if (lunCapacity != null) {
            BigDecimal capacity = new BigDecimal(lunCapacity);
            flowContext.put(task.getResult(),
                    (capacity.multiply(new BigDecimal("512")).toPlainString()));
        }
    }

    private String evalExpression(String exprsssion, Map<String, String> flowContext) {
        List<String> expressList = new ArrayList<String>();
        String[] elements = exprsssion.split(",");
        for (String e : elements) {
            if (e.startsWith("${")) {
                expressList.add(flowContext.get(e.substring(2, e.length() - 1)));
            } else {
                expressList.add(e);
            }
        }
        return calculateResult(expressList);
    }

    private String calculateResult(List<String> express) {
        if(express==null||express.size()==0||express.get(0)==null){
            return "0";
        }
        BigDecimal result = new BigDecimal(express.get(0));
        for (int i = 1; i < express.size(); i++) {
            if (express.get(i).equals("+")) {
                result = result.add(new BigDecimal(express.get(i + 1)));
            }
            if (express.get(i).equals("-")) {
                result = result.subtract(new BigDecimal(express.get(i + 1)));
            }
            if (express.get(i).equals("*")) {
                result = result.multiply(new BigDecimal(express.get(i + 1)));
            }
            if (express.get(i).equals("/")) {
                if (!express.get(i + 1).equals("0")) {
                    result = result.divide(new BigDecimal(express.get(i + 1)), 4,
                            RoundingMode.HALF_UP);
                }
            }
        }
        return result.toPlainString();
    }

    public void judgeHostViewCapacity(Task task, StorageObject obj, Map<String, String> flowContext, Map<String, List<StorageObject>> storObjMap) {
        if ("false".equals(flowContext.get(task.getTarget()))) {
            flowContext.put(task.getResult(), "0");
        }
    }

    public void getAllChildrenCapacity(Task task, StorageObject obj, Map<String, String> flowContext, Map<String, List<StorageObject>> storObjMap) {
        String[] objTypes = task.getTarget().split(",");
        long childrenCapacity = 0;
        for (String objTypeName : objTypes) {
            List<StorageObject> storageObjects = storObjMap.get(objTypeName);
            if (storageObjects != null) {
                for (StorageObject o : storageObjects) {
                    if (o.getRestData().get("PARENTID").equals(obj.getId())) {
                        childrenCapacity += Long.valueOf(o.getRestData().get("CAPACITY"));
                    }
                }

            }
        }
        childrenCapacity = childrenCapacity > Long.valueOf(obj.getRestData().get("USERTOTALCAPACITY")) ?
                childrenCapacity : Long.valueOf(obj.getRestData().get("USERTOTALCAPACITY"));
        flowContext.put(task.getResult(), childrenCapacity + "");
    }

    public void isOverSubscription(Task task, StorageObject obj, Map<String, String> flowContext, Map<String, List<StorageObject>> storObjMap) {
        String[] params = task.getTarget().split(",");
        String isOverSubscription = Long.valueOf(flowContext.get(params[0])) -
                Long.valueOf(flowContext.get(params[1])) > 0 ? "yes" : "no";
        flowContext.put(task.getResult(), isOverSubscription);
    }

    public void calculatePhysicalSize(Task task, StorageObject obj, Map<String, String> flowContext, Map<String, List<StorageObject>> storObjMap) {
        long totalConfigSize = 0;
        for (int i = 0; i < 3; i++) {
            String raidLV = obj.getRestData().get("TIER" + i + "RAIDLV");
            String diskNum = obj.getRestData().get("TIER" + i + "RAIDDISKNUM");
            String tierCapacity = obj.getRestData().get("TIER" + i + "CAPACITY");
            if (raidLV != null && diskNum != null && tierCapacity != null) {
                Tier tier = new Tier(raidLV, diskNum, tierCapacity);
                totalConfigSize += calculateTierSize(tier.getRaidLV(),
                        tier.getDiskNum(), tier.getCapacity());
            }
        }
        flowContext.put(task.getResult(), totalConfigSize * 512 + "");
    }

    private long calculateTierSize(int tierRAIDLV, int tierRAIDDISKNUM, long tierCAPACITY) {
        long tierSize = 0;
       /* 1：RAID10
        2：RAID5
        3：RAID0
        4：RAID1
        5：RAID6
        6：RAID50
        7：RAID3*/
        switch (tierRAIDLV) {
            case ConfigConst.RAID10:
                tierSize = tierCAPACITY * 2;
                break;
            case ConfigConst.RAID5:
                tierSize = tierCAPACITY * tierRAIDDISKNUM / (tierRAIDDISKNUM - 1);
                break;
            case ConfigConst.RAID0:
                tierSize = tierCAPACITY;
                break;
            case ConfigConst.RAID1:
                tierSize = tierCAPACITY * 2;
                break;
            case ConfigConst.RAID6:
                tierSize = tierCAPACITY * tierRAIDDISKNUM / (tierRAIDDISKNUM - 2);
                break;
            case ConfigConst.RAID50:
                tierSize = tierCAPACITY * tierRAIDDISKNUM / (tierRAIDDISKNUM - 1) * 2;
                break;
            case ConfigConst.RAID3:
                tierSize = tierCAPACITY * tierRAIDDISKNUM / (tierRAIDDISKNUM - 1);
                break;
            case ConfigConst.EC1:
                tierSize = tierCAPACITY * tierRAIDDISKNUM / (tierRAIDDISKNUM - 1);
                break;
            case ConfigConst.EC2:
                tierSize = tierCAPACITY * tierRAIDDISKNUM / (tierRAIDDISKNUM - 2);
                break;
            case ConfigConst.EC3:
                tierSize = tierCAPACITY * tierRAIDDISKNUM / (tierRAIDDISKNUM - 3);
                break;
            default:
                tierSize = 0;
        }
        return tierSize;
    }

    public void getPerfDataFromController(Task task, StorageObject obj, Map<String, String> flowContext, Map<String, List<StorageObject>> storObjMap) {
        String[] perfNames = task.getTarget().split(",");
        String[] results = task.getResult().split(",");
        List<StorageObject> controllers = storObjMap.get(ObjectType.Controller.name());
        for (int i = 0; i < perfNames.length; i++) {
            long perfValue = 0;
            for (StorageObject controller : controllers) {
                if(controller.getPerfData()!=null&&controller.getPerfData().get(perfNames[i])!=null)
                    try{
                        perfValue += Long.parseLong(controller.getPerfData().get(perfNames[i]));
                    }catch (NumberFormatException e){
                        logger.warn("the controller performance data get from data file is not a number");
                    }
            }
            flowContext.put(results[i], perfValue + "");
        }
    }

    public void getRestDataFromController(Task task, StorageObject obj, Map<String, String> flowContext, Map<String, List<StorageObject>> storObjMap) {
        String[] perfNames = task.getTarget().split(",");
        List<StorageObject> controllers = storObjMap.get(ObjectType.Controller.name());
        for (String perfName : perfNames) {
            long perfValue = 0;
            for (StorageObject controller : controllers) {
                perfValue += Long.parseLong(controller.getRestData().get(perfName));
            }
            flowContext.put(perfName, perfValue + "");
        }
    }

    public void getMAXBandWidth(Task task, StorageObject obj, Map<String, String> flowContext, Map<String, List<StorageObject>> storObjMap) {
        int maxspeed = 0;
        String portSpeed = null;
        List<StorageObject> fcports = storObjMap.get(ObjectType.FCPort.name());
        List<StorageObject> fcoeports = storObjMap.get(ObjectType.FCoEPort.name());
        List<StorageObject> ethPorts = storObjMap.get(ObjectType.ETHPort.name());
        List<StorageObject> ports = new ArrayList<StorageObject>();
        if (fcports != null) {
            ports.addAll(fcports);
        }
        if(fcoeports != null){
            ports.addAll(fcoeports);
        }
        if(ethPorts != null){
                ports.addAll(ethPorts);
        }

        for (StorageObject port : ports) {
                String healthStatus = port.getRestData().get("HEALTHSTATUS");
                String runningStatus = port.getRestData().get("RUNNINGSTATUS");
                if (ConfigConst.HEALTHSTATUS_NORMAL.equals(healthStatus) &&
                        ConfigConst.RUNNINGSTATUS_NORMAL.equals(runningStatus)) {
                    portSpeed = port.getRestData().get("MAXSPEED");
                    if (portSpeed == null) {
                        portSpeed = port.getRestData().get("maxSpeed");
                    }
                    if (portSpeed != null) {
                        maxspeed += Integer.parseInt(portSpeed);
                    }
                }
        }
        flowContext.put(task.getResult(), maxspeed + "");
    }

    public void getLunTierPolicy(Task task, StorageObject obj, Map<String, String> flowContext, Map<String, List<StorageObject>> storObjMap) {
        int tierPolicy = 0 ;
        if(flowContext.get(task.getTarget())!=null){
            tierPolicy = Integer.parseInt(flowContext.get(task.getTarget()));
        }
        String result = "";
        switch (tierPolicy){
            case 0 :
               result= "no migration";
               break;
            case 1 :
                result= "automatic migration";
                break;
            case 2 :
                result= "migration to a higher performance tier";
                break;
            case 3 :
                result= "migration to a lower performance tier";
                break;
            default:
                result = "no migration";
        }
        flowContext.put(task.getResult(),result);
    }
}