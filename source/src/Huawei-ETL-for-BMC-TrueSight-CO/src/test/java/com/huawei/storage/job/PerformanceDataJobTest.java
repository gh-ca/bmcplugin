package com.huawei.storage.job;

import com.huawei.storage.UserInfo;
import com.huawei.storage.constants.ConnectionVO;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.Properties;

public class PerformanceDataJobTest {
    PerformanceDataJob job;
    private Logger log = Logger.getLogger(PerformanceDataJobTest.class);

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
        job = new PerformanceDataJob(connVo);
    }

    @Test
    public void testGetALLData() throws Exception {
        Map<String, Map<String, String>> data = job.call();
        log.debug(data);
        log.debug("return file data size is : " + data.size());
        log.debug(data.keySet());
        Assert.assertTrue(data.size() > 0);
    }
}