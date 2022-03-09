package com.huawei.storage.oceanstor.rest.domain;

/**
 * Huawei Technologies  all rights reserved
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
