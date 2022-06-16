package com.huawei.storage.oceanstor.rest.utils;

import com.google.gson.JsonObject;
import com.huawei.storage.oceanstor.rest.constants.OperationError;
import com.huawei.storage.oceanstor.rest.domain.RestResponse;
import com.huawei.storage.oceanstor.rest.exception.RestException;
import com.huawei.storage.oceanstor.rest.operation.OperationResult;
import com.huawei.storage.oceanstor.rest.operation.RestInterfaceConfs;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.Map.Entry;

public class ComposeUtils {
    private static final Logger log = Logger.getLogger(ComposeUtils.class);


    public static String composeRelativeUri(String operationName, Map<String, String> requestData) throws RestException {
        requestData = CommonUtils.upperOperationName(requestData);
        String restRelativePath = RestInterfaceConfs.getRestRelativePath(operationName);
        if (restRelativePath.contains("?")) {
            restRelativePath = makeQueryUri(requestData, restRelativePath);
        } else {
            restRelativePath = makeActionUri(requestData, restRelativePath);
        }


        return restRelativePath;
    }

    private static String makeActionUri(Map<String, String> requestData, String restRelativePath) throws RestException {
        String[] paths = restRelativePath.split(",");
        StringBuffer sf = new StringBuffer();
        for (String path : paths) {
            if (path.startsWith("${")) {
                //TODO check and make sure every variable can get from request
                //if require data not contained in requestDataMap throw exception
                String varKey = (path.substring(2, path.length() - 1)).toUpperCase();
                if (requestData.get(varKey) == null) {
                    log.info("mandatory paramter " + varKey + "does not provided");
                    throw OperationError.REQUEST.MISSING_REQUIRED_PARAM.newException(varKey);
                }
                path = requestData.get(varKey);
            }
            sf.append("/").append(path);
        }
        restRelativePath = sf.toString();
        return restRelativePath;
    }

    private static String makeQueryUri(Map<String, String> requestData, String restRelativePath) {
        //filesystem?${filter=${filter},range=${range}}
        //snapshot,associate?${ASSOCIATEOBJTYPE=${ASSOCIATEOBJTYPE}}
        log.debug("start make query uri relative path is " + restRelativePath );
        StringBuffer sf = new StringBuffer();
        makePrePart(restRelativePath, sf);
        makePostPart(requestData, restRelativePath, sf);
        //if there is no additional query parameter,remove question mark
        if (sf.toString().endsWith("?")) {
            sf.deleteCharAt(sf.length() - 1);
        }
        log.debug("after make the query uri is " + sf.toString());
        return sf.toString();
    }

    private static void makePostPart(Map<String, String> requestData, String restRelativePath, StringBuffer sf) {
        log.debug("start make query uri post part");
        ////filesystem?${filter=${filter},range=${range}}
        String expression = restRelativePath.substring(restRelativePath.indexOf("?${") + 3,
                restRelativePath.length() - 1);
        String[] exps = expression.split(",");
        for (String exp : exps) {
            String[] equation = exp.split("=");
            String key = equation[0];
            String value = equation[1];
            if ("filter".equals(key)) {
                dealFilterCondition(requestData, sf, value);
                continue;
            }

            if (value.startsWith("${")) {
                value = getExpressionPayLoad(value);
                //if request part not contain , just drop it
                if(requestData.get(value.toUpperCase())!=null){
                    sf.append(key).append("=")
                            .append(requestData.get(value.toUpperCase()))
                                    .append("&");
                }
            } else {
                sf.append(key).append("=")
                        .append(value).append("&");
            }
        }
        //if there is no more query condition,remove the last add mark
        if(sf.toString().endsWith("&")){
            sf.deleteCharAt(sf.length()-1);
        }
        log.debug("after post part deal , the query uri is : " + sf.toString());
    }

    private static String getExpressionPayLoad(String value) {
        // get string inside ${}  part
        return value.substring(2, value.length() - 1);
    }

    private static void makePrePart(String restRelativePath, StringBuffer sf) {
        log.debug("start make query uri start part " + restRelativePath);
        String queryPre = restRelativePath.substring(0, restRelativePath.indexOf("?") + 1);
        if (queryPre.contains(",")) {
            String[] queryPres = queryPre.split(",");
            for (String pre : queryPres) {
                sf.append("/").append(pre);
            }
        } else {
            sf.append("/").append(queryPre);
        }
        log.debug("after make query uri start part , the query uri is " + sf.toString());
    }

    private static void dealFilterCondition(Map<String, String> requestData, StringBuffer sf, String value) {
        //remove ${}
        //Double " :: "
        //find-filesystem-by-name=GET;filesystem?${filter=${NAME::${name}}
        StringBuffer buffer = new StringBuffer();
        String[] filterItems = value.substring(2, value.length() - 1).split(",");
        if (value.contains("::")) {
            for (String f : filterItems) {
                String[] condition = f.split("::");
                buffer.append(condition[0]).append("::")
                        .append(requestData.get(condition[0])).append(",");
            }

            //single " : "
            //find-CIFS-share-by-name=GET;CIFSHARE?${filter=${NAME:${name},range=[0-100]}
        } else if (value.matches("[\\s\\S]+:[\\s\\S]+")) {
            for (String f : filterItems) {
                String[] equation = f.split(":");
                if (equation[1].startsWith("${")) {
                    String keyInRequest = equation[1].substring(2, equation[1].length() - 1);
                    buffer.append(equation[0]).append(":")
                            .append(requestData.get(keyInRequest.toUpperCase()))
                            .append(",");
                } else {
                    buffer.append(equation[0]).append(":").append(equation[1]).append(",");
                }

            }
        }else{
            if(value.startsWith("${")){
                String requestKey = value.substring(2, value.length() - 1).toUpperCase();
                if(requestData.get(requestKey)!=null){
                    buffer.append(requestData.get(requestKey)).append(",");
                }
            }
        }

        String post = buffer.toString();
        if(post!=null&&post.length()>0&&!post.contains("null")){
            if (post.endsWith(",")) {
                buffer.deleteCharAt(buffer.length() - 1).append("&");
            }
            sf.append("filter=").append(buffer.toString());
        }
    }

    public static String composeJsonFromMap(Map<String, String> operationData) throws RestException {
        JsonObject jsonObject = new JsonObject();
        for (Entry<String, String> entry : operationData.entrySet()) {
            String key = entry.getKey().toString().
                    toUpperCase().replace("-", "").replace("_", "");
            String value = entry.getValue();
            //TODO convert the map string value to int or long value here
            jsonObject.addProperty(key, value);
        }
        return jsonObject.toString();
    }

    public static OperationResult composeResultFromRest(RestResponse restResult) {
        OperationResult operationResult = new OperationResult();
        if (restResult != null && restResult.getError() != null && restResult.getData() != null) {
            operationResult.setErrorCode(restResult.getError().getCode());
            operationResult.setErrorDescription(restResult.getError().getDescription());
            operationResult.setErrorSuggestion(restResult.getError().getSuggestion());
            operationResult.setResultData(restResult.getData().getDataList());
        }
        return operationResult;
    }

	
	/*public static OperationResult composeResultFromJson(JSONObject responseJSON, OperationResult operationResult) throws RestException {
        OperationResult composeResult = composeResultFromJson(responseJSON);
        operationResult.setErrorCode(composeResult.getErrorCode());
        operationResult.setErrorDescription(composeResult.getErrorDescription());
        operationResult.setErrorSuggestion(composeResult.getErrorSuggestion());
        operationResult.setResultData(composeResult.getResultData());
		return operationResult;
	}
	
	
	public static Map<String, String> ComposeMapFromJson(JSONObject jsonObject){
		TypeToken<Map<String, String>> typeToken = new MapTypeToken();
        Type type = typeToken.getType();
        Gson gson = new Gson();
        Map<String, String> dataMap = gson.fromJson(jsonObject.toString(), type);
        return dataMap;
	}

	public static OperationResult composeResultFromJson(JSONObject responseJSON) throws RestException {
		List<Map<String,String>> resultDataList = new ArrayList<Map<String,String>>();
		OperationResult operationResult = new OperationResult();
		try {
			JSONObject errJsonObject = responseJSON.getJSONObject("error");
			String errorCode = errJsonObject.getString("code");
			String errorDesc = errJsonObject.getString("description");
			String errorSuggest = "";
			if(errJsonObject.has("suggestion")){
				errorSuggest = errJsonObject.getString("suggestion");
			}
			if(errorCode.equals("0") || errorCode.isEmpty()){
				if (responseJSON.has("data")&& null != responseJSON.get("data"))
                {
                    Object data = responseJSON.get("data");
                    if(data instanceof JSONArray){
                    	JSONArray array = (JSONArray)data;
                    	for(int i=0;i<array.length();i++){
                    		resultDataList.add(ComposeMapFromJson(array.getJSONObject(i)));
                    	}
                    }else{
                    	resultDataList.add(ComposeMapFromJson((JSONObject)data));
                    }
                }
			}else{
				throw new RestException(errorCode, errorDesc, errorSuggest);
			}
			
			operationResult.setResultData(resultDataList);
			operationResult.setErrorCode(errorCode+"");
			operationResult.setErrorDescription(errorDesc);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return operationResult;
	}*/

}
