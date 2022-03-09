package com.huawei.storage.exception;

/**
 * Huawei Technologies  all rights reserved
 */
public class ETLException extends Exception{

    public ETLException(String s) {
        super(s);
    }

    public ETLException(String s, Exception e) {
        super(s,e);
    }
}
