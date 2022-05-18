package com.huawei.storage.exception;

public class ETLException extends Exception{

    public ETLException(String s) {
        super(s);
    }

    public ETLException(String s, Exception e) {
        super(s,e);
    }
}
