package com.huawei.storage.oceanstor.rest.connection;

import com.huawei.storage.oceanstor.rest.exception.RestException;
import org.apache.http.client.HttpClient;

public class HttpClientManager {

    private HttpClientFactory httpClientFactory = new HttpClientFactory();
    private HttpClient httpClient;


    public synchronized HttpClient getHttpClient(String keyPath, String keyPass, boolean strictCheckHostName) throws RestException {
        if (httpClient == null) {
            httpClient = httpClientFactory.createHttpClient(keyPath, keyPass,strictCheckHostName);
        }
        return httpClient;
    }

    public synchronized HttpClient getHttpClient() throws RestException {
        if (httpClient == null) {
            httpClient = httpClientFactory.createHttpClient();
        }
        return httpClient;
    }

    public void releaseHttpClient() {
        httpClientFactory.releaseHttpClient();
        httpClient = null;
    }


    public void setSocTimeout(int socTimeout) {
        httpClientFactory.setSocTimeout(socTimeout);
    }

    public HttpClient getHttpClient(String certificateFilePath, boolean strictCheckHostName) throws RestException {
        if (httpClient == null) {
            httpClient = httpClientFactory.createHttpClient(certificateFilePath,strictCheckHostName);
        }
        return httpClient;
    }
}
