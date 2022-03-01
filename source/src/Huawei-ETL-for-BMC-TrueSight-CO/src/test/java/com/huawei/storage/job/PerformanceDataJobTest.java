package com.huawei.storage.job;

import com.huawei.storage.constants.ConnectionVO;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.Properties;

import static org.junit.Assert.*;

/**
 * Huawei Technologies  all rights reserved
 * <p>
 * Created by m00373015 on 2016/8/22.
 */
public class PerformanceDataJobTest {
    PerformanceDataJob job;
    private Logger log = Logger.getLogger(PerformanceDataJobTest.class);

    @Before
    public void setUp() throws Exception {
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("log4j.properties"));
        PropertyConfigurator.configure(properties);
        ConnectionVO connectionVO = new ConnectionVO();
        connectionVO.setUsername("admin");
        connectionVO.setPassword("Admin@storage");
        connectionVO.setScope("0");
        connectionVO.setIpControllerA("10.158.196.210:40000");
        connectionVO.setIpControllerB("10.158.196.210:40001");
        connectionVO.setRestPort("40000");
        connectionVO.setSftpPort("31000");
        connectionVO.setHostIP("10.158.196.210");
        job = new PerformanceDataJob(connectionVO);
    }

    @Test
    public void testGetALLData() throws Exception {
        Map<String, Map<String, String>> data = job.call();
        log.debug(data);
        log.debug("return file data size is : " + data.size());
        log.debug(data.keySet());

    }
}