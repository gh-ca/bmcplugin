package com.huawei.storage.oceanstor.rest.connection;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.huawei.storage.oceanstor.rest.constants.OperationError;
import com.huawei.storage.oceanstor.rest.domain.Data;
import com.huawei.storage.oceanstor.rest.domain.Error;
import com.huawei.storage.oceanstor.rest.domain.RestResponse;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ResponseHandler;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Huawei Technology  all rights reserved
 * <p>
 * Created by m00373015 on 2016/8/4.
 */
class RestResponseHandler implements ResponseHandler<RestResponse>{
    private static final Logger log = Logger.getLogger(RestResponseHandler.class);


    public RestResponse handleResponse(HttpResponse response) throws IOException {
        RestResponse restResponse;
        if((restResponse = validateResponse(response))!=null){
            return restResponse;
        }
        HttpEntity entity = response.getEntity();
        String encoding = entity.getContentEncoding() != null ? entity.getContentEncoding().getValue() : "utf-8";
        String restResponseString;
        try {
            restResponseString = IOUtils.toString(entity.getContent(), encoding);
        }catch (IOException e){
            log.error("read http body IO Exception happened :" + e.getMessage(),e);
            return makeErrorResponse(OperationError.HTTP.HTTP_RESPONSE_BODY_READ_ERROR,e);
        }

        return makeRestResponse(restResponseString);
    }


    private RestResponse makeRestResponse(String restResponseString) {
        log.debug("Enter makeRestResponse method : parameter is " + restResponseString );
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonObject restJSONObject = parser.parse(restResponseString).getAsJsonObject();
        JsonElement dataJSONElement = restJSONObject.get("data");
        if(dataJSONElement == null) {
            log.info("the return data part is missing...");
        }
        Data data = new Data();
        List<Map<String,String>> dataList = new ArrayList<Map<String,String>>();
        //Return data can be single value or array
        if (null!=dataJSONElement&&dataJSONElement instanceof JsonArray) {
            JsonArray dataArray = (JsonArray) dataJSONElement;
            for (JsonElement element : dataArray) {
                Map<String,String> map = gson.fromJson(element, new TypeToken<Map<String, String>>() {
                }.getType());
                dataList.add(map);
            }
        }else if(dataJSONElement!=null){
            Map<String,String> map = gson.fromJson(dataJSONElement.getAsJsonObject(), new TypeToken<Map<String, String>>() {
            }.getType());
            dataList.add(map);
        }
        data.setDataList(dataList);
        log.debug(String.format("after parse data part is :%s", dataList));
        //get error part
        Error error = new Error(OperationError.INTERNAL.REST_RESPONSE_ERROR_CODE_MISSING.getErrorCode(),
                OperationError.INTERNAL.REST_RESPONSE_ERROR_CODE_MISSING.getErrorDesc(),
                OperationError.INTERNAL.REST_RESPONSE_ERROR_CODE_MISSING.getErrorSuggestion());
        JsonElement errorJSONObject = restJSONObject.get("error");
        if(errorJSONObject!=null){
            errorJSONObject = errorJSONObject.getAsJsonObject();
            error = gson.fromJson(errorJSONObject, Error.class);
        }else{
            log.info("the return error part is missing...");
        }
        log.debug("after parse error part is : " + error);
        return new RestResponse(data,error);
    }


    private RestResponse validateResponse(HttpResponse response){
        if(response==null){
            log.error("http response is null");
            return makeErrorResponse(OperationError.HTTP.HTTP_RESPONSE_NULL_ERROR);
        }
        if(response.getStatusLine()==null){
            log.error("http response status line is null");
            return makeErrorResponse(OperationError.HTTP.HTTP_RESPONSE_NULL_STATUS_LINE);
        }

        if(response.getStatusLine().getStatusCode()!=HttpStatus.SC_OK){
            log.error("http response statusLine code is not 200 , the code is " + response.getStatusLine().getStatusCode());
            return makeErrorResponse(OperationError.HTTP.HTTP_RESPONSE_ERROR_STATUS_CODE,
                    response.getStatusLine().getStatusCode()+"");
        }

        if(response.getEntity()==null || !response.getEntity().isStreaming()){
            log.error("http response body is error");
            return makeErrorResponse(OperationError.HTTP.HTTP_RESPONSE_BODY_ERROR);
        }

        return  null;
    }



    private RestResponse makeErrorResponse(OperationError.HTTP error,String... desc){
        return makeErrorResponse(error,null,desc);
    }

    private RestResponse makeErrorResponse(OperationError.HTTP error ,Exception e,String... desc){
        Data data = new Data();
        data.setDataList(new ArrayList<Map<String, String>>());
        return new RestResponse(data,
                new Error(error.getErrorCode(),
                        error.getErrorDesc(desc) +","+ (e!=null?e.getMessage():""),
                        error.getErrorSuggestion()));
    }
}
