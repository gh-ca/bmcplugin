package com.huawei.storage.utils;

import com.huawei.storage.domain.ManagedObjects;
import com.huawei.storage.domain.StorageObjectType;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * Huawei Technologies  all rights reserved
 * <p>
 * Created by m00373015 on 2016/8/25.
 */
public class CommonUtilsTest {


    private Logger log = Logger.getLogger(CommonUtilsTest.class);

    @Test
    public void testMakeBeanFromXML() throws Exception {
        InputStream ips = this.getClass().getResourceAsStream("/conf/MOList.xml");
        ManagedObjects mos = CommonUtils.makeBeanFromXML(ips, ManagedObjects.class);
        List<StorageObjectType> storageObjectTypeList = mos.getStorageObjectTypeList();
        log.debug("storage Type list is : " + storageObjectTypeList);
    }

}