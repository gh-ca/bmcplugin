package com.huawei.storage.oceanstor.rest.connection;

import com.huawei.storage.oceanstor.rest.constants.HttpMethodEnum;
import com.huawei.storage.oceanstor.rest.constants.OperationConstants;
import com.huawei.storage.oceanstor.rest.constants.OperationError;
import com.huawei.storage.oceanstor.rest.domain.RestResponse;
import com.huawei.storage.oceanstor.rest.exception.RestException;
import org.apache.log4j.Logger;

/**
 * Huawei Technologies  all rights reserved
 */
public class RestManager {
	private static final Logger logger = Logger.getLogger(RestManager.class);

	private String hostURL;
	private RestRequestHandler restRequestHandler = new RestRequestHandler();

    public RestManager(){}

    /**
     * Instantiates a new Rest manager.
     *
     * when user provide ssl key file , use this constructor
	 * @param keyPath the key path
     * @param keyPass the key pass
	 * @param strictCheckHostName
	 */
    public RestManager(String keyPath, String keyPass, boolean strictCheckHostName){

        restRequestHandler = new RestRequestHandler(keyPath,keyPass,strictCheckHostName);
    }


	public RestManager(String certificateFilePath, boolean strictCheckHostName){

		restRequestHandler = new RestRequestHandler(certificateFilePath,strictCheckHostName);
	}

	/**
	 * Do Http get post delete put operation 
	 * make the response body string into a JSONObject
	 * @param method
	 * @param restRelativeUri
	 * @param requestBody
	 * @return
	 * @throws RestException 
	 */
	public RestResponse doRequest(HttpMethodEnum method, String restRelativeUri, String requestBody) throws RestException {
		RestResponse restResponse = null;
		String url = hostURL  + restRelativeUri;
		logger.info("start rest request ,the request url is " + url
                +" request body is " +requestBody);

		switch (method) {
		case GET:
			restResponse = restRequestHandler.doGet(url,requestBody);
			break;
		case DELETE:
			restResponse = restRequestHandler.doDelete(url,requestBody);
			break;
		case PUT:
			restResponse = restRequestHandler.doPut(url,requestBody);
			break;
		case POST:
			restResponse = restRequestHandler.doPost(url,requestBody);
			break;
		default:
			throw OperationError.HTTP.HTTP_UNSUPPORTED_HTTP_METHOD.newException(method.name());
		}
		logger.info("after rest request response is " +restResponse);
		return restResponse;
	}

	


	/**
	 * Do login http post to login the device
	 * get ibase Token
	 * 
	 * @param loginRelativeURL
	 * @param requestBody
	 * @return
	 * @throws RestException
	 */
	public RestResponse doLogin(String loginRelativeURL, String requestBody) throws RestException {
		String url = this.hostURL  + loginRelativeURL;
		logger.debug("login url is : " + url);
		RestResponse restResponse = restRequestHandler.doPost(url, requestBody);
		
		if(restResponse.getError().getCode().equals("0")){
			String iBaseToken = restResponse.getData().getDataList().get(0).get("iBaseToken");
	        String deviceId = restResponse.getData().getDataList().get(0).get("deviceid");
	        logger.debug("get login operation ibaseToken : " + iBaseToken);
	        logger.debug("get login operation deviceId : "+ deviceId);
	        restRequestHandler.setToken(iBaseToken);
	        this.hostURL = hostURL + "/"+ deviceId;
		}else{
            logger.error("login error happened : " + restResponse);
            throw new RestException(restResponse.getError().getCode(),
                    restResponse.getError().getDescription(),restResponse.getError().getSuggestion());
        }
        return restResponse;
	}



	/**
	 * do http delete to logout the device
	 * @throws RestException 
	 */
	public void doLogout() throws RestException {
		logger.debug("the rest logout url is " + hostURL+"/sessions");
		restRequestHandler.doDelete(
                hostURL+ OperationConstants.RestRequestConstant.REST_LOGOUT_URI
                ,null);
		restRequestHandler.releaseConnection();
	}


    /**
     * Sets socket timeout.
     * when some operation need more time,use this override default 30s socket idle timeout
     * @param socketTimeout the socket timeout
     */
    public void setSocketTimeout(int socketTimeout) {
        this.restRequestHandler.setSocketTimeout(socketTimeout);
    }

    /**
     * Sets connect timeout.
     * when the network environment is not very good,use this override default 5s connect timeout
     * @param connectTimeout the connect timeout
     */
    public void setConnectTimeout(int connectTimeout) {
        this.restRequestHandler.setConnectTimeout(connectTimeout);
    }


    public void setHostURL(String hostURL) {
        this.hostURL = hostURL;
    }
}
