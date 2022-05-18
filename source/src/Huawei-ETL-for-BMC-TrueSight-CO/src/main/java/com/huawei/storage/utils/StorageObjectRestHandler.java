package com.huawei.storage.utils;

import com.huawei.storage.domain.StorageObject;
import com.huawei.storage.exception.ETLException;
import com.huawei.storage.oceanstor.rest.operation.DeviceManager;

import java.util.HashMap;
import java.util.List;

public class StorageObjectRestHandler {

    public List<StorageObject> findStorageObjectsFromRest(String countCommand,String queryCommand, DeviceManager deviceManager) throws ETLException {

        return  RestPageUtils.pageGetAll(deviceManager, countCommand, queryCommand
                    , new HashMap<String, String>());

    }
}
