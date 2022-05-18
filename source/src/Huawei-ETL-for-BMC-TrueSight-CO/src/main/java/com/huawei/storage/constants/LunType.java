package com.huawei.storage.constants;

import java.util.HashMap;
import java.util.Map;

public enum LunType {

    CommonLUN(0),

    VVolLUN(1),

    PELUN(2),

    In_BandLUN(5);

    private int type;

    LunType(int type){
        this.type = type;
    }

    public int getValue() {
        return type;
    }

    private static Map<Integer, LunType> c =
            new HashMap<Integer, LunType>(LunType.values().length);

    static {

        for (LunType value : LunType.values()) {
            c.put(value.getValue(), value);
        }

    }

    public static LunType valueOf(int type){
        return c.get(type);
    }

    @Override
    public String toString() {
        return "LunType{" +
                "type=" + type +
                '}';
    }
}
