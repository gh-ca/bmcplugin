package com.huawei.storage.oceanstor.rest.operation;

import com.huawei.storage.oceanstor.rest.domain.ConnectionData;
import com.huawei.storage.oceanstor.rest.exception.RestException;
import com.huawei.storage.oceanstor.rest.utils.CommonUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OperationExecutor {
	private static final Logger log = Logger.getLogger(OperationExecutor.class);
	
	private DeviceManager deviceManager;
	private ConnectionData connectionData = new ConnectionData();

	

	public OperationExecutor() {
		
	}

	public void setDMConnection(String dmUsername, String dmPassword, String dmUrl, String dmUserScope) {
		this.connectionData.setUsername(dmUsername);
		this.connectionData.setPassword(dmPassword);
		this.connectionData.setHostURL(dmUrl);
		this.connectionData.setScope(dmUserScope);
		
	}

	

	public OperationResult execute(OceanStorOperation operation) {
		log.info("Operation executor start process.. operation data is " + operation);
		log.debug("operation conntionData is " + connectionData);
		deviceManager = new DeviceManager(this.connectionData);
		OperationResult operationResult = new OperationResult();
		try {
			deviceManager.login();
            //workaround for get-CIFS-by-name
            if(operation.getOperationName().contains("fuzzy-single")){
                operationResult = fuzzyFindReturnOne(operation,deviceManager);
            }else{
			    operationResult = deviceManager.performAction(operation);
            }
		} catch (RestException e) {
			log.error("execution exception happened : " + e.getMessage() ,e);
			operationResult.setErrorCode(e.getErrorCode());
			operationResult.setErrorDescription(e.getErrorDescription());
			operationResult.setErrorSuggestion(e.getErrorSuggestion());
            operationResult.addEmptyResult();
		}finally {
			deviceManager.logout();
		}
		log.info("operation complete, operation resulst is "+operationResult);
		return operationResult;
	}

    //==========================================================
    //for rest fuzzy query but only precise one need(for CIFS !!!)
    //todo one day you should remove this
    //================================================================
    private OperationResult fuzzyFindReturnOne(OceanStorOperation operation, DeviceManager deviceManager) throws RestException {
        log.debug("start deal fuzzy find and return one. opertaion ["+operation+"]");
        OceanStorOperation countOperation = new OceanStorOperation();
        String operationName = operation.getOperationName();
        String target = CommonUtils.upperOperationName(operation.getOperationData()).get("NAME");
        countOperation.setOperationName(operationName.substring(0,
                operationName.indexOf("by"))+"count");
        countOperation.putOperationData("filter","NAME:"+target);
        OperationResult result = deviceManager.performAction(countOperation);
        if(!"0".equals(result.getErrorCode())||result.getResultData()==null||
                result.getResultData().get(0)==null||result.getResultData().get(0).get("COUNT")==null){
            throw new RestException(result.getErrorCode(),result.getErrorDescription(),result.getErrorSuggestion());
        }
        int count = Integer.parseInt(result.getResultData().get(0).get("COUNT"));
        OperationResult opResult = new OperationResult();
        log.debug("find all fuzzy result count is " + count);
        //page find and search
        if (count > 100) {
            int pages = count / 100;
            if (count % 100 != 0) {
                pages += 1;
            }
            for (int i = 0; i < pages; i++) {
                int start = i * 100;
                int end = start + 99;
                if (end > count) {
                    end = count;
                }
                log.debug("start get the range " + start +"-"  + end);
                operation.putOperationData("range", "[" + start + "-" + end + "]");
                opResult = deviceManager.performAction(operation);
                log.debug("start find target in result " + target);
                if (findTarget(opResult, target)) {
                    log.debug("find result " + opResult + "in the range " +
                            "[" + start +"---"+ end + "]");
                    break;
                }
            }
        } else {
            operation.putOperationData("range", "[0-100]");
            opResult = deviceManager.performAction(operation);
            log.debug("get from rest result is " + opResult);
            log.debug("start find target in result " + target);
            findTarget(opResult, target);
        }

        return opResult;
    }

    private boolean findTarget(OperationResult operationResult,String target){
        List<Map<String, String>> resultData =
                operationResult.getResultData();
        boolean isfound = false;
        for (Map<String,String> result: resultData) {
            if(result.get("NAME").equals(target)){
                log.debug("find target " + target);
                List<Map<String,String>> l = new ArrayList<Map<String, String>>();
                l.add(result);
                operationResult.setResultData(l);
                isfound = true;
                break;
            }
        }
        if(!isfound){
            operationResult.addEmptyResult();
        }
        return  isfound;
    }

}
