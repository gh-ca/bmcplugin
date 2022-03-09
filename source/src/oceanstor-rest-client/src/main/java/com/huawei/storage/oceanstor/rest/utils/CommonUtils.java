package com.huawei.storage.oceanstor.rest.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Huawei Technologies  all rights reserved
 */
public class CommonUtils {

	public static Map<String, String> upperOperationName(Map<String, String> dataMap) {
		Map<String, String> newDataMap = new HashMap<String, String>();
		if(dataMap!=null){
			for(Map.Entry<String, String> entry : dataMap.entrySet()){
				newDataMap.put(entry.getKey().toUpperCase(), entry.getValue());
			}
		}
		return newDataMap;
	}

}
