package com.huawei.storage.oceanstor.rest.domain;

/**
 * Created by m00373015 on 2016/8/4.
 */
public class Error {
    private String code;
    private String description;
    private String suggestion;

    public Error(String errorCode, String errorDesc, String errorSuggestion) {
        this.code = errorCode;
        this.description = errorDesc;
        this.suggestion = errorSuggestion;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    @Override
    public String toString() {
        return "Error{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", suggestion='" + suggestion + '\'' +
                '}';
    }
}
