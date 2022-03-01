package com.huawei.storage.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 * Huawei Technologies  all rights reserved
 * <p>
 * Created by m00373015 on 2016/8/17.
 */
@XStreamAlias("counters")
public class Counters {

    @XStreamAlias("counter")
    @XStreamImplicit
    private List<Counter> counterList;

    public List<Counter> getCounterList() {
        return counterList;
    }

    public void setCounterList(List<Counter> counterList) {
        this.counterList = counterList;
    }


    @Override
    public String toString() {
        return "Counters{" +
                "counterList=" + counterList +
                '}';
    }
}
