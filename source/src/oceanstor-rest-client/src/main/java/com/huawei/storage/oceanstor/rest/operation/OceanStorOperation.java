package com.huawei.storage.oceanstor.rest.operation;


import com.huawei.storage.oceanstor.rest.constants.OperationNamesEnum;

import java.util.HashMap;
import java.util.Map;

public class OceanStorOperation {
	public String operationName;
	public Map<String, String>  operationData = new HashMap<String, String>();
	public Map<String, String>  queryData = new HashMap<String, String>();
	public Map<String, String>  filterData = new HashMap<String, String>();
	
	public Map<String, String> getOperationData() {
		return operationData;
	}


    public void putOperationData(String key , String value) {
		this.operationData.put(key, value);
	}
	
	public Map<String, String> getQueryData() {
		return queryData;
	}


    public void putQueryData(String key , String value) {
		this.queryData.put(key, value);
	}
	

    public Map<String, String> getFilterData() {
		return filterData;
	}


    public void putFilterData(String key , String value) {
		this.filterData.put(key, value);
	}
	
	
	
	public String getOperationName() {
		return operationName;
	}
	
	public void setOperationName(String operationName){
		this.operationName  = operationName;
	}
	
	public void setOperationName(OperationNamesEnum operationName) {
		this.operationName = operationName.getValue();
	}
	
	
	@Override
	public String toString() {
		return "the operation is : operationName : " + operationName 
				+ " operationData is " + operationData; 
	}
	
}
