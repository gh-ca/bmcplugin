package com.huawei.storage;

import com.huawei.storage.constants.ConnectionVO;
import com.huawei.storage.etl.HWStorageExtractor;
import com.neptuny.cpit.etl.DataSetList;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.Executors;

/**
 * Huawei Technologies  all rights reserved
 * <p>
 * Created by m00373015 on 2016/8/30.
 */

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
        connVo.setHostIP("10.143.133.201");
        connVo.setIpControllerA("10.143.133.201:8088");
        connVo.setIpControllerB("10.143.133.201:8088");
        connVo.setUsername("admin");
        connVo.setPassword("Pbu4@123");
        connVo.setSftpPort("22");
        connVo.setRestPort("8088");
        connVo.setScope("0");
        conn.set(extractor,connVo);
        executor.set(extractor, Executors.newFixedThreadPool(10));
        DataSetList list = (DataSetList) method.invoke(extractor);
        log.debug(list);
    }

}