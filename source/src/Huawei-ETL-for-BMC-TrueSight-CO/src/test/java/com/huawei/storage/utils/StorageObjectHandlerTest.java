package com.huawei.storage.utils;

import com.huawei.storage.constants.ConnectionVO;
import com.huawei.storage.domain.StorageObject;
import com.huawei.storage.domain.StorageObjectType;
import com.huawei.storage.oceanstor.rest.operation.DeviceManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Properties;

/**
 * Huawei Technologies  all rights reserved
 * <p>
 * Created on 2016/11/8.
 */
public class StorageObjectHandlerTest {
    DeviceManager deviceManager ;
    ConnectionVO connectionVO = new ConnectionVO();
    private Logger log = Logger.getLogger(StorageObjectHandlerTest.class);

    @Before
    public void setUp() throws Exception {
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("log4j.properties"));
        PropertyConfigurator.configure(properties);
        connectionVO.setUsername("admin");
        connectionVO.setPassword("Admin@storage");
//        connectionVO.setPassword("Admin@storage2");
        connectionVO.setScope("0");
        connectionVO.setRestPort("40000");
        connectionVO.setHostIP("10.158.196.210");
//        connectionVO.setRestPort("34088");
//        connectionVO.setHostIP("10.169.219.91");
        deviceManager = getDeviceManager();
        deviceManager.login();
    }

    @Test
    public void getMappedLunHost() throws Exception {
        LunHostRestHandler handler = new LunHostRestHandler();
        StorageObjectType type = new StorageObjectType();
        List<StorageObject> mappedLunHost = handler.getMappedLunHost("","",deviceManager);
        log.debug("mappedLunHost size is " + mappedLunHost.size());
        log.debug("================================================================");
        for (StorageObject storageObj : mappedLunHost ) {
            log.debug("mappedLun data is " + storageObj.getRestData()
                    +"linked host data is : " +  storageObj.getLinkedObject().getRestData());
        }
        log.debug("================================================================");
    }


    private DeviceManager getDeviceManager() {
        String hostURL = getHostURL();
        DeviceManager deviceManager = new DeviceManager(
                hostURL,
                connectionVO.getUsername(),
                connectionVO.getPassword(),connectionVO.getScope());
        return deviceManager;
    }

    private String getHostURL() {
        return
                "https://"+connectionVO.getHostIP()+":"+
                        connectionVO.getRestPort()+"/deviceManager/rest";
    }

    @After
    public void teardown() throws Exception {
        deviceManager.logout();
    }
}