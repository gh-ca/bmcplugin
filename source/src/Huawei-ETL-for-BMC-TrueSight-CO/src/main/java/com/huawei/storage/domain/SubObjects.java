package com.huawei.storage.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 * Huawei Technologies  all rights reserved
 * <p>
 * Created by m00373015 on 2016/8/25.
 */
@XStreamAlias("sub-objects")
public class SubObjects {

    @XStreamImplicit
    private List<SubObject> subObjectList;

    public List<SubObject> getSubObjectList() {
        return subObjectList;
    }

    public void setSubObjectList(List<SubObject> subObjectList) {
        this.subObjectList = subObjectList;
    }

    @Override
    public String toString() {
        return "SubObjects{" +
                "subObjectList=" + subObjectList +
                '}';
    }
}
