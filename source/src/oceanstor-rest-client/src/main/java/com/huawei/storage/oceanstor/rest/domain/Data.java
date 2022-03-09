package com.huawei.storage.oceanstor.rest.domain;

import java.util.List;
import java.util.Map;

/**
 * Huawei Technologies  all rights reserved
 */
public class Data {

    private List<Map<String,String>> dataList;

    public List<Map<String, String>> getDataList() {
        return dataList;
    }

    public void setDataList(List<Map<String, String>> dataList) {
        this.dataList = dataList;
    }

    @Override
    public String toString() {
        return "Data: " + dataList;
    }
}
