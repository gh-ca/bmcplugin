package com.huawei.storage.oceanstor.rest.operation;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.huawei.storage.oceanstor.UserInfo;
import com.huawei.storage.oceanstor.rest.domain.Data;
import com.huawei.storage.oceanstor.rest.exception.RestException;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Huawei Technology  all rights reserverd
 * <p>
 * Created by m00373015 on 2016/8/9.
 */
public class DeviceManagerTestLogin {
    private transient final static Logger logger = Logger.getLogger(DeviceManagerTest.class);
    private DeviceManager deviceManager;

    @Before
    public void setUp() throws RestException {
        deviceManager = new DeviceManager(UserInfo.hostUrl, UserInfo.username, UserInfo.password);
    }

    @Test
    public void testLogin_correct_credential() throws Exception {
        deviceManager = new DeviceManager(UserInfo.hostUrl, UserInfo.username, UserInfo.password);
        deviceManager.login();
    }

    @Test(expected = Exception.class)
    public void testLogin_wrong_credential() throws Exception {
        deviceManager = new DeviceManager(UserInfo.hostUrl, UserInfo.username, UserInfo.password);
        deviceManager.login();

    }

    @Test
    public void testLogout() throws Exception {
        deviceManager.login();
        deviceManager.logout();
    }

    @Test
    public void testJsonParse() throws Exception {
        String path = this.getClass().getClassLoader().getResource(".").getFile()+"json.txt";
        System.out.println(path);
        String s = IOUtils.toString(new FileInputStream(new File(path)));
        System.out.println(s);
        JsonParser parser = new JsonParser();
        JsonObject restJSONObject = parser.parse(s).getAsJsonObject();
        JsonElement dataJSONObject = restJSONObject.get("data");
        Data data = new Data();
        List<Map<String,String>> dataList = new ArrayList<Map<String,String>>();
        //Return data can be single value or array
        if (dataJSONObject instanceof JsonArray) {
            System.out.println("it is a jsonArray ");
            JsonArray dataArray = (JsonArray) dataJSONObject;
                Gson gson = new Gson();
            for (JsonElement element : dataArray) {
                Map<String,String> map = gson.fromJson(element, new TypeToken<Map<String, String>>() {
                }.getType());
                dataList.add(map);
            }
        }
        Assert.assertTrue(dataList.size() > 0);
    }
}