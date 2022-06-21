package com.huawei.storage.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("pre-query")
public class PreQuery {

    @XStreamAlias("count-command")
    String countCommand;

    @XStreamAlias("query-command")
    String queryCommand;

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

    @Override
    public String toString() {
        return "PreCommand{" +
                "countCommand='" + countCommand + '\'' +
                ", queryCommand='" + queryCommand + '\'' +
                '}';
    }
}
