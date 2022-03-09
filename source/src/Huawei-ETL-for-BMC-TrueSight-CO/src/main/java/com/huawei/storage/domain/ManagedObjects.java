package com.huawei.storage.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 * Huawei Technologies  all rights reserved
 */
@XStreamAlias("managed-objects")
public class ManagedObjects {
    @XStreamImplicit
    private List<StorageObjectType> storageObjectTypeList;

    public List<StorageObjectType> getStorageObjectTypeList() {
        return storageObjectTypeList;
    }

    public void setStorageObjectTypeList(List<StorageObjectType> storageObjectTypeList) {
        this.storageObjectTypeList = storageObjectTypeList;
    }
}
