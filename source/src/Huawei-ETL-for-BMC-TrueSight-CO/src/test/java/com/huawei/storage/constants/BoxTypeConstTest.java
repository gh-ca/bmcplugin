package com.huawei.storage.constants;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Huawei Technologies  all rights reserved
 * <p>
 * Created by m00373015 on 2016/9/28.
 */
public class BoxTypeConstTest {

    @Test
    public void testGetType() throws Exception {
        System.out.println(BoxTypeConst.boxMap.get("70"));
        System.out.println(BoxTypeConst.boxMap.get("68"));

    }
}