package com.huawei.storage.constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelCapability {

    public static Map<String,List<String>> unSupportFunctionMap = new HashMap<String, List<String>>();

    public static Map<String, List<String>> StorageTypeMap = new HashMap<>();

    static {
        List<String> unSupportList2200 = new ArrayList<String>();
        unSupportList2200.add("filesystem");
        unSupportFunctionMap.put(BoxTypeConst.OCEAN_STOR_2200_V3,unSupportList2200);
//        unSupportFunctionMap.put(BoxTypeConst.OCEAN_STOR_2600_F_V3,unSupportList2200);
//        unSupportFunctionMap.put(BoxTypeConst.OCEAN_STOR_2600_V3,unSupportList2200);
        unSupportFunctionMap.put(BoxTypeConst.OCEAN_STOR_2800_V3,unSupportList2200);
        unSupportFunctionMap.put(BoxTypeConst.OCEAN_STOR_2800_V5,unSupportList2200);

        List<String> unSupportListDorado5000 = new ArrayList<String>();
        unSupportListDorado5000.add("filesystem");
        unSupportListDorado5000.add("fcoe_port");
        unSupportFunctionMap.put(BoxTypeConst.DORADO_3000_V_3, unSupportListDorado5000);
        unSupportFunctionMap.put(BoxTypeConst.DORADO_5000_V_3, unSupportListDorado5000);
        unSupportFunctionMap.put(BoxTypeConst.DORADO_6000_V_3, unSupportListDorado5000);
        unSupportFunctionMap.put(BoxTypeConst.DORADO_18000_V_3, unSupportListDorado5000);

        List<String> unSupportListDoradoNas = new ArrayList<String>();
        unSupportListDoradoNas.add("lun");
        unSupportListDoradoNas.add("host");
        unSupportListDoradoNas.add("mappedLunHost");
        unSupportFunctionMap.put(BoxTypeConst.DORADO_NAS, unSupportListDoradoNas);
        unSupportFunctionMap.put(BoxTypeConst.DORADO_NAS_Enhanced, unSupportListDoradoNas);

        List<String> V6 = new ArrayList<>();
        BoxTypeConst.boxMap.entrySet().stream()
                .filter(item -> Integer.valueOf(item.getKey()) >= 811 && Integer.valueOf(item.getKey()) <= 845)
                .forEach(m -> V6.add(m.getKey()));
        StorageTypeMap.put("V6", V6);

        List<String> pointReleases = new ArrayList<>();
        pointReleases.add("6.1.3");
        pointReleases.add("6.1.5");
        StorageTypeMap.put("POINTRELEASES", pointReleases);

        List<String> productVersions = new ArrayList<>();
        productVersions.add("V600R005");
        StorageTypeMap.put("PRODUCTVERSIONS", productVersions);
    }

}
