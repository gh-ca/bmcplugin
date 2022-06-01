package com.huawei.storage.utils;

import com.huawei.storage.UserInfo;
import com.huawei.storage.constants.ConnectionVO;
import com.huawei.storage.domain.StorageObject;
import com.huawei.storage.domain.StorageObjectType;
import com.huawei.storage.oceanstor.rest.operation.DeviceManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Properties;

public class StorageObjectHandlerTest {
    DeviceManager deviceManager ;
    ConnectionVO connectionVO = new ConnectionVO();
    private Logger log = Logger.getLogger(StorageObjectHandlerTest.class);

    @Before
    public void setUp() throws Exception {
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("log4j.properties"));
        PropertyConfigurator.configure(properties);
        connectionVO.setUsername(UserInfo.username);
        connectionVO.setPassword(UserInfo.password);
        connectionVO.setScope(UserInfo.scope);
        connectionVO.setRestPort(UserInfo.port);
        connectionVO.setHostIP(UserInfo.hostIp);
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
        Assert.assertTrue(mappedLunHost.size() > 0);
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