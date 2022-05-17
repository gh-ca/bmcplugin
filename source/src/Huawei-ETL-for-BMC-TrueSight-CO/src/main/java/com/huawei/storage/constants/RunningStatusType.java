/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2022-2022. All rights reserved.
 */
package com.huawei.storage.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lianq
 * @since 2022-05-17
 */
public enum RunningStatusType {

    Online(27),

    Offline(28),

    Invalid(35),

    Initializing(53),

    Deleting(106);

    private int type;

    RunningStatusType(int type){
        this.type = type;
    }

    public int getValue() {
        return type;
    }

    private static Map<Integer, RunningStatusType> c =
            new HashMap<Integer, RunningStatusType>(RunningStatusType.values().length);

    static {

        for (RunningStatusType value : RunningStatusType.values()) {
            c.put(value.getValue(), value);
        }

    }

    public static RunningStatusType valueOf(int type){
        return c.get(type);
    }

    @Override
    public String toString() {
        return "RunningStatusType{" +
                "type=" + type +
                '}';
    }
}
