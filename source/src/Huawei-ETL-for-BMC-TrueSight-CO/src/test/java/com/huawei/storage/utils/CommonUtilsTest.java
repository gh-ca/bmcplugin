package com.huawei.storage.utils;

import com.huawei.storage.domain.ManagedObjects;
import com.huawei.storage.domain.StorageObjectType;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class CommonUtilsTest {


    private Logger log = Logger.getLogger(CommonUtilsTest.class);

    @Test
    public void testMakeBeanFromXML() throws Exception {
        InputStream ips = this.getClass().getResourceAsStream("/conf/MOList.xml");
        ManagedObjects mos = CommonUtils.makeBeanFromXML(ips, ManagedObjects.class);
        List<StorageObjectType> storageObjectTypeList = mos.getStorageObjectTypeList();
        Assert.assertEquals(12, storageObjectTypeList.size());
        log.debug("storage Type list is : " + storageObjectTypeList);

    }

}