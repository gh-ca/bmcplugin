package com.huawei.storage.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Huawei Technologies  all rights reserved
 */
public enum StorageArrayType {
    OceanStor_S2200T("43"),

    OceanStor_S2600T("30"),

    OceanStor_S5500T("18"),

    OceanStor_S5600T("19"),

    OceanStor_S6800T("20"),

    OceanStor_S5800T("26"),

    OceanStor_18800F("56"),

    OceanStor_18500("57"),

    OceanStor_18800("58"),

    OceanStor_6800V3("61"),

    OceanStor_6900V3("62"),

    OceanStor_5600V3("63"),

    OceanStor_5800V3("64"),

    OceanStor_5500V3("68"),

    OceanStor_2600V3("69"),

    OceanStor_5300V3("70"),

    OceanStor_2800V3("71"),

    OceanStor_18500V3("72"),

    OceanStor_18800V3("73"),

    OceanStor_2200V3("74"),

    OceanStor_5100V3("79"),

    OCEANSTOR_2600_V3_FV("82"),

    OceanStor_2600FV3("84"),

    OceanStor_5500FV3("85"),

    OceanStor_5600FV3("86"),

    OceanStor_5800FV3("87"),

    OceanStor_6800FV3("88"),

    OceanStor_18500FV3("89"),

    OceanStor_18800FV3("90"),

    OceanStor_2800V5("92"),

    OceanStor_5300V5("93"),

    OceanStor_5300FV5("94"),

    OceanStor_5500V5("95"),

    OceanStor_5500FV5("96"),

    OceanStor_5600V5("97"),

    OceanStor_5600FV5("98"),

    OceanStor_5800V5("99"),

    OceanStor_5800FV5("100"),

    OceanStor_6800V5("101"),

    OceanStor_6800FV5("102"),

    OceanStor_18500V5("103"),

    OceanStor_18500FV5("104"),

    OceanStor_18800V5("105"),

    OceanStor_18800FV5("106"),

    OceanStor_5500V5Elite("107"),

    OceanStor_2100V3("108"),

    OceanStor_5110V5("116"),

    OceanStor_5110FV5("117"),

    OceanStor_5210V5("118"),

    OceanStor_5210FV5("119"),

    OceanStor_5310V5("120"),

    OceanStor_5310FV5("121"),

    OceanStor_5510V5("122"),

    OceanStor_5510FV5("123"),

    OceanStor_5610V5("124"),

    OceanStor_5610FV5("125"),

    OceanStor_6810V5("128"),

    OceanStor_6810FV5("129"),

    OceanStor_15810V5("130"),

    OceanStor_15810FV5("131"),

    OceanStor_18810V5("132"),

    OceanStor_18810FV5("133"),

    OceanStor_5210V5_Enhanced("134"),

    OceanStor_5810FV5_Enhanced("135"),

    OceanStor_5810V5("126"),

    OceanStor_5810FV5("127"),

    Dorado3000V3("810"),

    Dorado5000V3("805"),

    Dorado6000V3("806"),

    Dorado18000V3("807"),

    DORADO_5000_V6_SAS("811"),

    DORADO_5000_V6_NVME("812"),

    DORADO_6000_V6_SAS("813"),

    DORADO_6000_V6_NVME("814"),

    DORADO_8000_V6_SAS("815"),

    DORADO_8000_V6_NVME("816"),

    DORADO_18000_V6_SAS("817"),

    DORADO_18000_V6_NVME("818"),

    DORADO_3000_V6_SAS("819"),

    DORADO_5000_V6_IP_SAS("821"),

    DORADO_6000_V6_IP_SAS("822"),

    DORADO_8000_V6_IP_SAS("823"),

    DORADO_18000_V6_IP_SAS("824"),

    DORADO_5300_V6("825"),

    DORADO_5500_V6("826"),

    DORADO_5600_V6("827"),

    DORADO_5800_V6("828"),

    DORADO_6800_V6("829"),

    DORADO_18500_V6("830"),

    DORADO_18800_V6("831"),

    DORADO_18800K_V6("832"),

    OCEANSTOR_5310("833"),

    OCEANSTOR_5510("834"),

    OCEANSTOR_5610("835"),

    OCEANSTOR_6810("836"),

    OCEANSTOR_18510("837"),

    OCEANSTOR_18810("838"),

    OCEANSTOR_DORADO_5600K("839"),

    OCEANSTOR_18500K("840");

    private String type;

    StorageArrayType(String type){
        this.type = type;
    }

    public String getValue() {
        return type;
    }

    private static Map<String, StorageArrayType> c =
            new HashMap<String, StorageArrayType>(StorageArrayType.values().length);

    static {

        for (StorageArrayType value : StorageArrayType.values()) {
            c.put(value.getValue(), value);
        }

    }

    public static StorageArrayType getTypeName(String type){
        return c.get(type);
    }

    @Override
    public String toString() {
        return "StorageArrayType{" +
                "type=" + type +
                '}';
    }
}
