package com.huawei.storage.oceanstor.rest.connection;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.huawei.storage.oceanstor.rest.domain.RestResponse;
import org.apache.commons.io.IOUtils;
import org.apache.http.*;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHttpResponse;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;


import java.io.File;
import java.io.FileInputStream;

import static org.junit.Assert.*;

/**
 * Created by m00373015 on 2016/8/4.
 */
public class RestResponseHandlerTest {
    private static final Logger log = Logger.getLogger(RestResponseHandlerTest.class);

    private StatusLine successStatusLine;
    private RestResponseHandler restResponseHandler;


    @Before
    public void setUp() throws Exception {
        successStatusLine = new StatusLine() {
            public ProtocolVersion getProtocolVersion() {
                return HttpVersion.HTTP_1_1;
            }

            public int getStatusCode() {
                return 200;
            }

            public String getReasonPhrase() {
                return null;
            }
        };

        restResponseHandler = new RestResponseHandler();
    }

    @Test
    public void test_rest_response_data_single() throws Exception {
        HttpResponse response = new BasicHttpResponse(successStatusLine);
        InputStreamEntity entity =
                new InputStreamEntity(new FileInputStream(new File(
                        this.getClass().getClassLoader().getResource("responseSingleData").getFile())));
        response.setEntity(entity);
        RestResponseHandler handler = new RestResponseHandler();
        RestResponse restResponse = handler.handleResponse(response);
        System.out.println(restResponse.getData().getDataList());
        System.out.println(restResponse.getError());
        assertEquals(1,restResponse.getData().getDataList().size());
        assertEquals("0",restResponse.getError().getCode());
    }

    @Test
    public void test_rest_response_data_array() throws Exception {
        HttpResponse response = new BasicHttpResponse(successStatusLine);
        InputStreamEntity entity =
                new InputStreamEntity(new FileInputStream(new File(
                        this.getClass().getClassLoader().getResource("responseArrayData").getFile())));
        response.setEntity(entity);
        RestResponse restResponse = restResponseHandler.handleResponse(response);
        log.debug("response data is :" + restResponse.getData().getDataList());
        log.debug("response error is :" + restResponse.getError());
        assertEquals(2,restResponse.getData().getDataList().size());
        assertEquals("0",restResponse.getError().getCode());

    }
}