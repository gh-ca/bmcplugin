package com.huawei.storage.oceanstor.rest.connection;

import com.huawei.storage.oceanstor.rest.constants.HttpMethodEnum;
import com.huawei.storage.oceanstor.rest.domain.RestResponse;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Huawei Technologies  all rights reserved
 * <p>
 * Created on 2016/11/17.
 */
public class RestManagerTest {

    @Test
    public void testLogin() throws Exception {
        RestManager manager = new RestManager();
        manager.setHostURL("https://10.143.133.201:8088/deviceManager/rest");
        RestResponse restResponse = manager.doLogin("/xxxx/sessions",
                "{\"username\":\"admin\",\"password\":\"Pbu4@123\",\"scope\":\"0\"}");
        //System.out.println(restResponse);
        String body = "{\"207:0A\":{\"method\":\"GET\",\"url\":\"https://10.143.133.201:8088/deviceManager/rest/2102350BVB10F2000019/performace_statistic/cur_statistic_data?CMO_STATISTIC_UUID=207:0A&CMO_STATISTIC_DATA_ID_LIST=21,511,22,69\"},\"207:0B\":{\"method\":\"GET\",\"url\":\"https://10.143.133.201:8088/deviceManager/rest/2102350BVB10F2000019/performace_statistic/cur_statistic_data?CMO_STATISTIC_UUID=207:0B&CMO_STATISTIC_DATA_ID_LIST=21,511,22,69\"}}";
        RestResponse resp = manager.doRequest(HttpMethodEnum.POST, "/server/compositeinfo", body);
        System.out.println(resp);
    }
}