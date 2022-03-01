package com.huawei.storage.oceanstor.rest.domain;

/**
 * Created by m00373015 on 2016/8/4.
 */
public class RestResponse {
    private Data data;
    private Error error;

    public RestResponse(Data data, Error error) {
        this.data = data;
        this.error = error;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }


    @Override
    public String toString() {
        return "RestResponse:[Data:"+data
                +",Error:" + error;
    }
}
