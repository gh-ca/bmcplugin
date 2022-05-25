package com.huawei.storage.oceanstor.rest.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Huawei Technology  all rights reserverd
 * <p>
 * Created by m00373015 on 2016/8/5.
 */
public class ComposeUtilsTest {

    @Test
    public void test_composeJsonFromMap() throws Exception {
        Map<String,String> map = new HashMap<String, String>();
        map.put("name","storagePool1");
        map.put("sectorSize","102400");
        map.put("parentId","2");
        String s = ComposeUtils.composeJsonFromMap(map);
        Assert.assertEquals("{\"NAME\":\"storagePool1\",\"SECTORSIZE\":\"102400\",\"PARENTID\":\"2\"}", s);
        System.out.println(s);

    }
}