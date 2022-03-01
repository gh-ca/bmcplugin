package com.huawei.storage.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Huawei Technologies  all rights reserved
 * <p>
 * Created by m00373015 on 2016/8/25.
 */
public enum  ObjectType {

    System(201),

    Controller(207),

    DiskDomain(266),

    StoragePool(216),

    Disk(10),

    Lun(11),

    FileSystem(40),

    RemoteReplication(263),

    SmartPartition(268),

    SmartQos(230),

    LunCopy(219),

    SnapShot(27),

    FCPort(212),

    ETHPort(213),

    SASPort(214),

    FCoEPort(252),

    Host(21);

    private int type;

    ObjectType(int type){
        this.type = type;
    }

    public int getValue() {
        return type;
    }

    private static Map<Integer, ObjectType> c =
            new HashMap<Integer, ObjectType>(ObjectType.values().length);

    static {

        for (ObjectType value : ObjectType.values()) {
            c.put(value.getValue(), value);
        }

    }

    public static ObjectType valueOf(int type){
        return c.get(type);
    }

    @Override
    public String toString() {
        return "ObjectType{" +
                "type=" + type +
                '}';
    }

}
