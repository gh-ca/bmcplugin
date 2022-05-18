package com.huawei.storage.oceanstor.rest.connection;

import com.huawei.storage.oceanstor.rest.constants.OperationError;
import com.huawei.storage.oceanstor.rest.domain.RestResponse;
import com.huawei.storage.oceanstor.rest.exception.RestException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.log4j.Logger;

import java.io.IOException;

public class RestRequestHandler {
    private static final Logger logger = Logger.getLogger(RestRequestHandler.class);
    private String certificateFilePath;
    private String keyPath;
    private String keyPass;
	private boolean strictCheckHostName;
	private String iBaseToken;
    private static final int SOCKET_TIMEOUT = 30 * 1000; // default data read timeout
    private static final int CONNECT_TIMEOUT = 10 * 1000; // default connection timeout
	private HttpClientManager clientManager = new HttpClientManager();
    private int socketTimeout = SOCKET_TIMEOUT;
    private int connectTimeout = CONNECT_TIMEOUT;

    public RestRequestHandler(){}


    public RestRequestHandler(String keyPath, String keyPass, boolean strictCheckHostName) {
        this.keyPath = keyPath;
        this.keyPass = keyPass;
		this.strictCheckHostName = strictCheckHostName;
	}

    public RestRequestHandler(String certificateFilePath, boolean strictCheckHostName) {
        this.certificateFilePath = certificateFilePath;
        this.strictCheckHostName = strictCheckHostName;
    }


    RestResponse doPost(String url, String requestBody) throws RestException {
		HttpPost method = new HttpPost(url);
		setRequestHeader(method);
		setRequestBody(requestBody, method);
		return doRequest(method);
	}


	RestResponse doPut(String url, String requestBody) throws RestException {
		HttpPut method = new HttpPut(url);
		setRequestHeader(method);
		setRequestBody(requestBody, method);
		return doRequest(method);
	}


	private void setRequestBody(String requestBody, HttpEntityEnclosingRequestBase method) throws RestException {
		StringEntity bodyStringEntity = new StringEntity(requestBody, "utf-8");
		bodyStringEntity.setContentType("application/json");
		method.setEntity(bodyStringEntity);
	}

	RestResponse doDelete(String url, String requestBody) throws RestException {
		HttpDelete method = new HttpDelete(url);
		setRequestHeader(method);
		return doRequest(method);
	}


	RestResponse doGet(String url, String requestBody) throws RestException {
		HttpGet method = new HttpGet(url);
		setRequestHeader(method);
		return doRequest(method);
	}
	
	private void setRequestHeader(HttpRequestBase request){
		request.setHeader("Content-Type", "application/json");
		request.setHeader("Accept-Language","en");
		if(null != iBaseToken){
			request.setHeader("iBaseToken", iBaseToken);
		}
	}


    private void setRequestTimeouts(HttpRequestBase method){
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(socketTimeout)
                .setConnectTimeout(connectTimeout)
                .setConnectionRequestTimeout(connectTimeout)
                .build();
        method.setConfig(requestConfig);
    }
	
	
	private RestResponse doRequest(HttpRequestBase method) throws RestException {
        RestResponse restResponse;
		try {
            setRequestTimeouts(method);
            if(keyPath!=null&&keyPass!=null){
                restResponse = clientManager.getHttpClient(keyPath,keyPass,strictCheckHostName)
                        .execute(method,new RestResponseHandler());
            }else if(certificateFilePath!=null&&certificateFilePath.length()>0){
                restResponse = clientManager.getHttpClient(certificateFilePath,strictCheckHostName)
                        .execute(method,new RestResponseHandler());
            }
            else{
                restResponse = clientManager.getHttpClient()
                        .execute(method, new RestResponseHandler());
            }
		} catch (IOException e) {
			logger.error("The http request failed because of an IOException: " +
					e.getMessage(),e);
			throw OperationError.HTTP.HTTP_IO_EXCEPTION_ERROR.newException(method.getMethod(),method.getURI().getHost()+":"+
					method.getURI().getPort()+method.getURI().getPath(),e+":"+e.getMessage());
		} catch (RestException e){
            throw e;
        }catch (Exception e) {
			logger.error("The http request failed because of an Exception: " +
                    e.getMessage(),e);
			throw OperationError.HTTP.HTTP_EXCEPTION_ERROR.newException(method.getMethod(),method.getURI().getHost()+":"+
					method.getURI().getPort()+method.getURI().getPath(),e+":"+e.getMessage());
		}
		return restResponse;
	}


	void setToken(String iBaseToken) {
		this.iBaseToken = iBaseToken;
	}




    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    void releaseConnection() {
		if(clientManager!=null){
				clientManager.releaseHttpClient();
		}
	}

}
