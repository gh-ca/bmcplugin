package com.huawei.storage.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * Huawei Technologies  all rights reserved
 */

@XStreamAlias("counter")
public class Counter {

    @XStreamAlias("name")
    private String name;

    @XStreamAsAttribute
    @XStreamAlias("mapping")
    private String mapping;

    @XStreamAsAttribute
    @XStreamAlias("link")
    private String link;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMapping() {
        return mapping;
    }

    public void setMapping(String mapping) {
        this.mapping = mapping;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "Counter{" +
                "name='" + name + '\'' +
                ", mapping='" + mapping + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
