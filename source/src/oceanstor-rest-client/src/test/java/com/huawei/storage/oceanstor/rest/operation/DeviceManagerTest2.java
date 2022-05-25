package com.huawei.storage.oceanstor.rest.operation;

import com.huawei.storage.oceanstor.UserInfo;
import com.huawei.storage.oceanstor.rest.domain.ConnectionData;
import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * Huawei Technologies  all rights reserved
 * <p>
 * Created by m00373015 on 2016/10/20.
 */
public class DeviceManagerTest2 {
    private transient final static Logger logger = Logger.getLogger(DeviceManagerTest.class);
    private DeviceManager deviceManager;


    @Test
    public void testSSLLogin() throws Exception {
        ConnectionData data = new ConnectionData();
        data.setHostURL(UserInfo.hostUrl);
        //data.setCertificateFilePath("D:\\dev\\cert\\os_4002.cer");
        data.setUsername(UserInfo.username);
        data.setPassword(UserInfo.password);
        data.setStrictCheckHostName(false);
        deviceManager = new DeviceManager(data);
        deviceManager.login();

    }
}
