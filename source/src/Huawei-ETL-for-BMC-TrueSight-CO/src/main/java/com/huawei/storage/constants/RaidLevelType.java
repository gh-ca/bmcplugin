package com.huawei.storage.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Huawei Technologies  all rights reserved
 */
public enum RaidLevelType {

    raid10(1),

    raid5(2),

    raid0(3),

    raid1(4),

    raid6(5),

    raid50(6),

    raid3(7),

    RAID_TP(11);

    private int type;

    RaidLevelType(int type){
        this.type = type;
    }

    public int getValue() {
        return type;
    }

    private static Map<Integer, RaidLevelType> c =
            new HashMap<Integer, RaidLevelType>(RaidLevelType.values().length);

    static {

        for (RaidLevelType value : RaidLevelType.values()) {
            c.put(value.getValue(), value);
        }

    }

    public static RaidLevelType valueOf(int type){
        return c.get(type);
    }

    @Override
    public String toString() {
        return "RaidLevelType{" +
                "type=" + type +
                '}';
    }
}
