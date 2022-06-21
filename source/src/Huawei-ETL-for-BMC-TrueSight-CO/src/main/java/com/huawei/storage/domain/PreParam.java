package com.huawei.storage.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("pre-param")
public class PreParam {

    @XStreamAlias("target")
    String target;

    @XStreamAlias("source")
    String source;

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "PreParam{" +
                "target='" + target + '\'' +
                ", source='" + source + '\'' +
                '}';
    }
}
