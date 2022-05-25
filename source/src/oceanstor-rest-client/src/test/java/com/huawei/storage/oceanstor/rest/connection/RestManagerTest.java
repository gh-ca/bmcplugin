package com.huawei.storage.oceanstor.rest.connection;

import com.google.gson.JsonObject;
import com.huawei.storage.oceanstor.UserInfo;
import com.huawei.storage.oceanstor.rest.domain.RestResponse;
import org.junit.Assert;
import org.junit.Test;

/**
 * Huawei Technologies  all rights reserved
 * <p>
 * Created on 2016/11/17.
 */
public class RestManagerTest {

    @Test
    public void testLogin() throws Exception {
        RestManager manager = new RestManager();
        manager.setHostURL(UserInfo.hostUrl);
        JsonObject requestBodybody = new JsonObject();
        requestBodybody.addProperty("username", UserInfo.username);
        requestBodybody.addProperty("password", UserInfo.password);
        requestBodybody.addProperty("scope", UserInfo.scope);
        System.out.println(requestBodybody.toString());
        RestResponse restResponse = manager.doLogin(UserInfo.loginRelativeURL, requestBodybody.toString());
        Assert.assertEquals("2102351QLH9WK5800028",restResponse.getData().getDataList().get(0).get("deviceid"));
        //RestResponse resp = manager.doRequest(HttpMethodEnum.POST, UserInfo.loginRelativeUri, UserInfo.Controller_performance_body);
        //System.out.println(resp);
    }
}