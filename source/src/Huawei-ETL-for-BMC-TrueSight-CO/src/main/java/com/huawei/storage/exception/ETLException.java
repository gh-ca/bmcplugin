package com.huawei.storage.exception;

/**
 * Huawei Technologies  all rights reserved
 * <p>
 * Created by m00373015 on 2016/8/19.
 */
public class ETLException extends Exception{

    public ETLException(String s) {
        super(s);
    }

    public ETLException(String s, Exception e) {
        super(s,e);
    }
}
