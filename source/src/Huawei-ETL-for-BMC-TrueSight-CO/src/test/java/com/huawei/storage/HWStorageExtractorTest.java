package com.huawei.storage;

import com.huawei.storage.constants.ConnectionVO;
import com.huawei.storage.etl.HWStorageExtractor;
import com.neptuny.cpit.etl.DataSetList;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.Executors;


public class HWStorageExtractorTest {

    private Logger log = Logger.getLogger(HWStorageExtractorTest.class);

    @Test
    public void testExtractData() throws Exception {
        Class<HWStorageExtractor> clazz = (Class<HWStorageExtractor>) Class.forName("com.huawei.storage.etl.HWStorageExtractor");

        HWStorageExtractor extractor = clazz.newInstance();
        Field conn = clazz.getDeclaredField("conn");
        Method method = clazz.getDeclaredMethod("extract");
        Field executor = clazz.getDeclaredField("executorService");
        conn.setAccessible(true);
        executor.setAccessible(true);
        ConnectionVO connVo = new ConnectionVO();
        connVo.setHostIP(UserInfo.hostIp);
        connVo.setIpControllerA(UserInfo.hostIp + ":" + UserInfo.port);
        connVo.setIpControllerB(UserInfo.hostIp + ":" + UserInfo.port);
        connVo.setUsername(UserInfo.username);
        connVo.setPassword(UserInfo.password);
        connVo.setSftpPort(UserInfo.sftpPort);
        connVo.setRestPort(UserInfo.port);
        connVo.setScope(UserInfo.scope);
        conn.set(extractor,connVo);
        executor.set(extractor, Executors.newFixedThreadPool(10));
        DataSetList list = (DataSetList) method.invoke(extractor);
        Assert.assertTrue(list.size() > 0);
        log.debug(list);
    }

}