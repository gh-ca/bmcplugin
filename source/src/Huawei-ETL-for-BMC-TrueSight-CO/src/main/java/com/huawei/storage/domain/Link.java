package com.huawei.storage.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;

/**
 * Huawei Technologies  all rights reserved
 */
@XStreamAlias("link")
@XStreamConverter(value=ToAttributedValueConverter.class, strings={"expression"})
public class Link {

    @XStreamAlias("type")
    private String type;






    private String expression;


    public Link(String type,String expression) {
        this.type = type;
        this.expression = expression;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }







    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Link{" +
                "type='" + type + '\'' +
                ", expression='" + expression + '\'' +
                '}';
    }
}
