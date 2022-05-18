package com.huawei.storage.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;


@XStreamAlias("metric")
public class Metric {

    @XStreamAsAttribute
    @XStreamAlias("dataSet")
    private String dataSet;

    @XStreamAsAttribute
    @XStreamAlias("column")
    private String column;

    @XStreamAsAttribute
    @XStreamAlias("value")
    private String value;

    @XStreamAlias("tasks")
    private Tasks tasks;

    @XStreamAlias("name")
    private String name;

    public String getDataSet() {
        return dataSet;
    }

    public void setDataSet(String dataSet) {
        this.dataSet = dataSet;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public Tasks getTasks() {
        return tasks;
    }

    public void setTasks(Tasks tasks) {
        this.tasks = tasks;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
