package com.huawei.storage.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Huawei Technologies  all rights reserved
 */
public enum PoolDiskType {

//    SSD(0),
//
//    SAS(1),
//
//    NL_SAS(2);


    FC(0),
    SAS(1),
    SATA(2),
    SSD(3),
    NL_SAS(4),
    SLC_SSD(5),
    MLC_SSD(6),
    FC_SED(7),
    SAS_SED(8),
    SATA_SED(9),
    SSD_SED(10),
    NL_SAS_SED(11),
    SLC_SSD_SED(12),
    MLC_SSD_SED(13),
    NVMe_SSD(14),
    NVMe_SSD_SED(16),
    SCM_SSD(17);



    private int type;

    PoolDiskType(int type){
        this.type = type;
    }

    public int getValue() {
        return type;
    }

    private static Map<Integer, PoolDiskType> c =
            new HashMap<Integer, PoolDiskType>(PoolDiskType.values().length);

    static {

        for (PoolDiskType value : PoolDiskType.values()) {
            c.put(value.getValue(), value);
        }

    }

    public static PoolDiskType valueOf(int type){
        return c.get(type);
    }

    @Override
    public String toString() {
        return "PoolDiskType{" +
                "type=" + type +
                '}';
    }
}
