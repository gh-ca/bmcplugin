package com.huawei.storage.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Huawei Technologies  all rights reserved
 * <p>
 * Created by m00373015 on 2016/8/25.
 */
public enum FileSystemType {

    CommonFileSystem(0),

    WORMFileSystem(1);

    private int type;

    FileSystemType(int type){
        this.type = type;
    }

    public int getValue() {
        return type;
    }

    private static Map<Integer, FileSystemType> c =
            new HashMap<Integer, FileSystemType>(FileSystemType.values().length);

    static {

        for (FileSystemType value : FileSystemType.values()) {
            c.put(value.getValue(), value);
        }

    }

    public static FileSystemType valueOf(int type){
        return c.get(type);
    }

    @Override
    public String toString() {
        return "FileSystemType{" +
                "type=" + type +
                '}';
    }
}
