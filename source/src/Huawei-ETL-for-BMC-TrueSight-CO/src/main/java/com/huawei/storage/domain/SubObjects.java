package com.huawei.storage.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

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
