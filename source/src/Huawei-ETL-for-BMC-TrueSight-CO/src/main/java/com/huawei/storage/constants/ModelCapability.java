package com.huawei.storage.constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Huawei Technologies  all rights reserved
 */
public class ModelCapability {

    public static Map<String,List<String>> unSupportFunctionMap = new HashMap<String, List<String>>();


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

    }


}
