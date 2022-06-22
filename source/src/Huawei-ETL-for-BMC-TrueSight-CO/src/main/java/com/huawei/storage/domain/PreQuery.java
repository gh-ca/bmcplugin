package com.huawei.storage.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("pre-query")
public class PreQuery {

    @XStreamAlias("count-command")
    String countCommand;

    @XStreamAlias("query-command")
    String queryCommand;

    @XStreamAlias("pre-param")
    PreParam preParam;

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

    public PreParam getPreParam() {
        return preParam;
    }

    public void setPreParam(PreParam preParam) {
        this.preParam = preParam;
    }

    @Override
    public String toString() {
        return "PreQuery{" +
                "countCommand='" + countCommand + '\'' +
                ", queryCommand='" + queryCommand + '\'' +
                ", preParam=" + preParam +
                '}';
    }
}
