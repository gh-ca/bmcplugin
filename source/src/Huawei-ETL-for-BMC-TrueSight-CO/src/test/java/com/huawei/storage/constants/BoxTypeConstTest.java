package com.huawei.storage.constants;

import org.junit.Assert;
import org.junit.Test;

public class BoxTypeConstTest {

    @Test
    public void testGetType() throws Exception {
        Assert.assertEquals("OceanStor_5300 V3",BoxTypeConst.boxMap.get("70"));
        Assert.assertEquals("OceanStor_5500 V3",BoxTypeConst.boxMap.get("68"));

    }
}