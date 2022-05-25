package com.huawei.storage.constants;

import org.junit.Assert;
import org.junit.Test;

/**
 * Huawei Technologies  all rights reserved
 * <p>
 * Created by m00373015 on 2016/9/28.
 */
public class BoxTypeConstTest {

    @Test
    public void testGetType() throws Exception {
        Assert.assertEquals("OceanStor_5300 V3",BoxTypeConst.boxMap.get("70"));
        Assert.assertEquals("OceanStor_5500 V3",BoxTypeConst.boxMap.get("68"));

    }
}