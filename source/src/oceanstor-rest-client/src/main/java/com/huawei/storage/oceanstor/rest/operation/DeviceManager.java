package com.huawei.storage.oceanstor.rest.operation;

import java.util.Map;

import com.google.gson.JsonObject;
import com.huawei.storage.oceanstor.rest.connection.RestManager;
import com.huawei.storage.oceanstor.rest.constants.HttpMethodEnum;
import com.huawei.storage.oceanstor.rest.constants.OperationConstants;
import com.huawei.storage.oceanstor.rest.constants.OperationError;
import com.huawei.storage.oceanstor.rest.domain.ConnectionData;
import com.huawei.storage.oceanstor.rest.domain.RestResponse;
import com.huawei.storage.oceanstor.rest.exception.RestException;
import com.huawei.storage.oceanstor.rest.utils.ComposeUtils;
import org.apache.log4j.Logger;



/**
 * The main Entrance for operate the rest
 * @author m00373015
 *
 */
public class DeviceManager {
	private static final Logger log = Logger.getLogger(DeviceManager.class); 
	
	private RestManager restManager = new RestManager();
	private ConnectionData connectionData = new ConnectionData();
    private boolean isLogin = false;


    /**
     * Instantiates a new Device manager.
     * use connection data to contain all connection info.
     * @param connectionData the connection data
     */
    public DeviceManager(ConnectionData connectionData) {
		this.connectionData = connectionData;
		if(connectionData.getKeyStoreFile()!=null&&connectionData.getKeyStoreFilePass()!=null){
			restManager = new RestManager(connectionData.getKeyStoreFile(),
					connectionData.getKeyStoreFilePass(),connectionData.isStrictCheckHostName());
		}
        if(connectionData.getCertificateFilePath()!=null&&
                connectionData.getCertificateFilePath().length()>0){
            restManager = new RestManager(connectionData.getCertificateFilePath(),
                    connectionData.isStrictCheckHostName());
        }
		restManager.setHostURL(connectionData.getHostURL());
	}

    /**
     * Instantiates a new Device manager.
     *
     * @param hostURL  the host url
     * @param username the username
     * @param password the password
     * @param scope    the scope 0 local user 1 ldap user
     */
    public DeviceManager(String hostURL,String username,String password,String scope) {
		connectionData.setHostURL(hostURL);
		connectionData.setUsername(username);
		connectionData.setPassword(password);
		connectionData.setScope(scope);
        restManager.setHostURL(hostURL);
	}

    /**
     * Instantiates a new Device manager.
     *
     * @param hostURL  the host url
     * @param username the username
     * @param password the password
     */
    public DeviceManager(String hostURL,String username,String password) {
		connectionData.setHostURL(hostURL);
		connectionData.setUsername(username);
		connectionData.setPassword(password);
        restManager.setHostURL(hostURL);
	}
	

	/**
	 * Login into the device , get the login ibaseToken and cookie 
	 * and put them into the following request header
	 * @throws RestException
	 */
	public void login() throws RestException {
		log.info("start login... login data is :" + connectionData);
		JsonObject requestBody = new JsonObject();
		requestBody.addProperty("username", connectionData.getUsername());
		requestBody.addProperty("password", connectionData.getPassword());
		requestBody.addProperty("scope", connectionData.getScope());

		log.debug("request body is " +"{'username':"+connectionData.getUsername()+",'scope:'"
				+connectionData.getScope()+"}");
		restManager.doLogin(OperationConstants.RestRequestConstant.REST_LOGIN_URI,
                requestBody.toString());
        isLogin = true;
		log.info("login success.");
	}

	/**
	 * Logout the device
	 */
	public void logout() {
		if(null!=restManager){
			try {
				log.info("start logout..");
				restManager.doLogout();
			} catch (RestException e) {
				log.warn("exception happened when logout:" + e);
			}
		}
        isLogin = false;
        log.info("logout success..");
	}


    /**
     * Perform action to restful webservice.
     *
     * @param operation the operation contains name and data
     * @return the operation result
     * @throws RestException the rest exception
     */
    public OperationResult performAction(OceanStorOperation operation) throws RestException {
		log.info("start perform action ," + operation);
		checkOperation(operation);
        if(!isLogin){
            throw OperationError.REQUEST.PERFORM_ACTION_NOT_LOGIN_ERROR.newException("");
        }
        OperationResult operationResult = doRestRequest(operation.getOperationName(), operation.getOperationData());
        log.info("after process , the operation result is " + operationResult);
        return  operationResult;
    }
	
	/**
	 * check if the request data is valid
	 * @param operationData
	 * @throws RestException
	 */
	private void checkOperation(OceanStorOperation operationData) throws RestException {
		log.debug("start check operation name");
        if(!RestInterfaceConfs.isValidOperation(operationData.getOperationName())){
			throw OperationError.REQUEST.REQUEST_OPERATION_NAME_INVALID.newException(operationData.getOperationName());
		}
		//TODO check mandatory field here, check filed valid here
		
	}

	/**
	 * do real Http request 
	 * @param operationName
	 * @param operationData
	 * @return the operation Result
	 */
	private OperationResult doRestRequest(String operationName, Map<String, String> operationData) throws RestException {
		/*
		 * 1.Get operationMethod,operationURL from configuration by operationName
		 * 1.Set operationURL ,Set requestJSONBody
		 * 2.Get result data errorCode errorDesc
		 */
		log.info("start do operation request,operation " + operationName + "," + operationData);
        String restMethod = RestInterfaceConfs.getRestMethod(operationName);
        String restRelativeUri = ComposeUtils.composeRelativeUri(operationName, operationData);
        String requestBody = ComposeUtils.composeJsonFromMap(operationData);
        log.info("after compose url,the request is " + restMethod + "," + restRelativeUri + "," + requestBody);
        //do Http request
        RestResponse restResult = restManager.doRequest(HttpMethodEnum.valueOf(restMethod),
                restRelativeUri, requestBody);
        //make JsonObject to a return result Object
        return ComposeUtils.composeResultFromRest(restResult);
    }

    public void setSocketTimeout(int socketTimeout) {
        this.restManager.setSocketTimeout(socketTimeout);
    }

    public void setConnectTimeout(int connectTimeout) {
        this.restManager.setConnectTimeout(connectTimeout);
    }
}
