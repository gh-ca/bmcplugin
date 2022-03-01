package com.huawei.storage.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 * Huawei Technologies  all rights reserved
 * <p>
 * Created by m00373015 on 2016/8/17.
 */

@XStreamAlias("storage-object")
public class StorageObjectType {

    @XStreamAsAttribute()
    @XStreamAlias("name")
    String name;

    @XStreamAsAttribute()
    @XStreamAlias("mapping")
    String mappingName;


    @XStreamAlias("counters")
    Counters counters;

    @XStreamAlias("method")
    String method;

    @XStreamAlias("count-command")
    String countCommand;

    @XStreamAlias("query-command")
    String queryCommand;


    @XStreamAlias("performance")
    String performance;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMappingName() {
        return mappingName;
    }

    public void setMappingName(String mappingName) {
        this.mappingName = mappingName;
    }

    public Counters getCounters() {
        return counters;
    }

    public void setCounters(Counters counters) {
        this.counters = counters;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getCountCommand() {
        return countCommand;
    }

    public void setCountCommand(String countCommand) {
        this.countCommand = countCommand;
    }

    public String getQueryCommand() {
        return queryCommand;
    }

    public void setQueryCommand(String queryCommand) {
        this.queryCommand = queryCommand;
    }

    public String getPerformance() {
        return performance;
    }

    public void setPerformance(String performance) {
        this.performance = performance;
    }

    @Override
    public String toString() {
        return "StorageObjectType{" +
                "name='" + name + '\'' +
                ", mappingName='" + mappingName + '\'' +
                ", counters=" + counters +
                ", method='" + method + '\'' +
                ", countCommand='" + countCommand + '\'' +
                ", queryCommand='" + queryCommand + '\'' +
                '}';
    }
}
