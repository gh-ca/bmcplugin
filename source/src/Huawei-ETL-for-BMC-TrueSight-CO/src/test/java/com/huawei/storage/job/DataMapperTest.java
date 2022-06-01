package com.huawei.storage.job;

import com.huawei.storage.UserInfo;
import com.huawei.storage.constants.ConnectionVO;
import com.huawei.storage.domain.StorageObject;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class DataMapperTest {
    PerformanceDataJob perfJob;
    private Logger log = Logger.getLogger(PerformanceDataJobTest.class);
    private RestJob restJob;

    @Before
    public void setUp() throws Exception {
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("log4j.properties"));
        PropertyConfigurator.configure(properties);
        ConnectionVO connVo = new ConnectionVO();
        connVo.setHostIP(UserInfo.hostIp);
        connVo.setIpControllerA(UserInfo.hostIp + ":" + UserInfo.port);
        connVo.setIpControllerB(UserInfo.hostIp + ":" + UserInfo.port);
        connVo.setUsername(UserInfo.username);
        connVo.setPassword(UserInfo.password);
        connVo.setSftpPort(UserInfo.sftpPort);
        connVo.setRestPort(UserInfo.port);
        connVo.setScope(UserInfo.scope);
        perfJob = new PerformanceDataJob(connVo);
        restJob = new RestJob(connVo);
    }


    @Test
    public void testDataCompare() throws Exception {
        Map<String, Map<String, String>> perfData = perfJob.call();
        List<StorageObject> restData = restJob.call();
        List<String> missList = new ArrayList<String>();
        List<String> matchList = new ArrayList<String>();
        List<StorageObject> lunList = new ArrayList<StorageObject>();
        log.debug(perfData);
        log.debug(restData);
        for (StorageObject s : restData) {
            if (perfData.get(s.getType() + "_" + s.getId())==null) {
                missList.add(s.getTypeName()+"_"+s.getType()+"_"+s.getId()+"_"+s.getName());
            }else{
                matchList.add(s.getTypeName()+"_"+s.getType()+"_"+s.getId()+"_"+s.getName());
                if(11 == s.getType()){
                    lunList.add(s);
                }
            }
        }
        log.debug("performance data size is :" + perfData.size());
        log.debug("storage objects size is :" + restData.size());
        log.debug(missList);
        log.debug(matchList);
        log.debug("lun : " + lunList.size());
        Assert.assertTrue(lunList.size() > 0);
    }

    @Test
    public void testDataMapping() throws Exception {
        Map<String, Map<String, String>> perfData = perfJob.call();
        List<StorageObject> restData = restJob.call();
        log.debug(perfData);
        log.debug(restData);
        Assert.assertTrue(perfData.size() > 0);
        Assert.assertTrue(restData.size() > 0);
      /*  DataMapper mapper = new DataMapper();
        List<StorageObject> storageObjects = mapper.mappingAllData(restData, perfData);
        for (StorageObject s : storageObjects) {
            log.debug(s.getTypeName()+"_"+s.getName()+" metric data is :" + s.getMetricData());
        }
        log.debug("the storage object list size is : " + storageObjects.size());*/
    }


    @Test
    public void testDataMappingVerify() throws Exception {
        //Map<String, Map<String, String>> perfData = perfJob.call();
        List<StorageObject> restData = restJob.call();

       /* DataMapper mapper = new DataMapper();
        List<StorageObject> storageObjects = mapper.mappingAllData(restData, perfData);*/
        for (StorageObject s : restData) {
            if(s.getRestData().get("ID").equals("258")){
                log.debug("--------------------------");
                log.debug(s.getTypeName()+"_"+s.getName()+" rest data is :" + s.getRestData());
                log.debug("--------------------------");
            }
        }
        Assert.assertTrue(restData.size() > 0);
    }

    @Test
    public void testBigDecimal() throws Exception {
        BigDecimal a = new BigDecimal("92233720368547758072");
        BigDecimal b = new BigDecimal("2");
        BigDecimal result = a.subtract(b);
        log.debug(result.toPlainString());
        log.debug(a.divide(b).toPlainString());
        Assert.assertEquals(new BigDecimal("92233720368547758070"),result);
    }

    @Test
    public void testSplit() throws Exception {
        String exp = "OWNINGCONTROLLER::${ID}";
        int dataIndex = exp.indexOf("${");
        String substring = exp.substring(dataIndex + 2, exp.length() - 1);
        Assert.assertEquals("ID", substring);
        System.out.println(substring);
    }
}