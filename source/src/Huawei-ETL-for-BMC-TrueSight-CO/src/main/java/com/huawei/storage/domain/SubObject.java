package com.huawei.storage.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Huawei Technologies  all rights reserved
 */
@XStreamAlias("sub-object")
public class SubObject {

    @XStreamAlias("name")
    private String name;

    @XStreamAlias("link")
    private Link link;




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "SubObject{" +
                "name='" + name + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
