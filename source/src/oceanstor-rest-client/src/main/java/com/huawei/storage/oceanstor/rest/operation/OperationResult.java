package com.huawei.storage.oceanstor.rest.operation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Huawei Technologies  all rights reserved
 */
public class OperationResult {
	private String errorCode = "0";
	private String errorDescription;
	private String errorSuggestion;
	private List<Map<String, String>> resultData = new ArrayList<Map<String,String>>();
	
	
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorDescription() {
		return errorDescription;
	}
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	
	public String getErrorSuggestion() {
		return errorSuggestion;
	}
	public void setErrorSuggestion(String errorSuggest) {
		this.errorSuggestion = errorSuggest;
	}
	public List<Map<String, String>> getResultData() {
		return resultData;
	}
	public void setResultData(List<Map<String, String>> resultData) {
		this.resultData = resultData;
	}
	
	@Override
	public String toString() {
		return "Rest OperationResult is :" 
				+"[ErrorCode is : "+errorCode 
				+",ErrorDescription is " + errorDescription 
				+",ErrorSuggestion is "+ errorSuggestion
				+",Result data size is " + resultData.size()
				+",Result data is " + resultData+"]";
	}


	public void addEmptyResult() {
		List<Map<String,String>> list = new ArrayList<Map<String, String>>();
		list.add(new HashMap<String, String>());
		setResultData(list);
	}
}
