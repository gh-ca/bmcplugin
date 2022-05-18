package com.huawei.storage.constants;

import java.util.HashMap;
import java.util.Map;

public enum DiskType {

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



    private int typeNum;
    DiskType(int typeNum){
        this.typeNum = typeNum;
    }


    public int getValue() {
        return typeNum;
    }

    private static Map<Integer, DiskType> c =
            new HashMap<Integer, DiskType>(DiskType.values().length);

    static {
        for (DiskType value : DiskType.values()) {
            c.put(value.getValue(), value);
        }

    }

    public static DiskType valueOf(int type){
        return c.get(type);
    }
}
