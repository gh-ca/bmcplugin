package com.huawei.storage.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * Huawei Technologies  all rights reserved
 */
@XStreamAlias("task")
public class Task {
    @XStreamAsAttribute
    private String id;
    @XStreamAsAttribute
    private String method;

    @XStreamAlias("target")
    private String target;
    @XStreamAlias("result")
    private String result;

    @XStreamAlias("input")
    private String input;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
