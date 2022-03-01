package com.huawei.storage.oceanstor.rest.connection;

import com.huawei.storage.oceanstor.rest.constants.OperationError;
import com.huawei.storage.oceanstor.rest.exception.RestException;
import org.apache.http.client.HttpClient;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.log4j.Logger;

import java.io.*;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Huawei Technology  all rights reserverd
 * <p>
 * Created by m00373015 on 2016/8/4.
 */
public class HttpClientFactory {
    private Logger log = Logger.getLogger(HttpClientFactory.class);


    private static final int SOCKET_TIMEOUT = 30 * 1000; // default data read timeout
    private CloseableHttpClient httpClientInstance = null;
    private int socketTimeOut = SOCKET_TIMEOUT; // data read timeout




    public void setSocTimeout(int socTimeout)
    {
        this.socketTimeOut = socTimeout;
    }


    public HttpClient createHttpClient() throws RestException{
        SSLContext sslcontext = null;
        try
        {
            sslcontext = SSLContext.getInstance("TLS");
            sslcontext.init(null, new TrustManager[]
                    {manager}, null);
        }
        catch (NoSuchAlgorithmException e)
        {
            log.error("when create https connection,encrypt algorithm does not exist"
                    +e.getMessage(), e);
            throw OperationError.HTTP.HTTP_SSL_NO_SUCH_ALGORITHM_ERROR.newException("TLS",e.getMessage());
        }
        catch (KeyManagementException e)
        {
            log.error("when create https connection,key management exception happened"
                    +e.getMessage(),e);
            throw OperationError.HTTP.HTTP_SSL_KEY_MANAGEMENT_ERROR.newException(e.getMessage());
        }

        SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(
                sslcontext,NoopHostnameVerifier.INSTANCE);

        PoolingHttpClientConnectionManager manager = getPoolConnectionManager(sslSocketFactory);

        CloseableHttpClient httpclient = HttpClients.custom()
                .setConnectionManager(manager)
                .build();

        httpClientInstance = httpclient;
        return httpclient;
    }




    public HttpClient createHttpClient(String keyPath, String keyPass, boolean strictCheckHostName) throws RestException{
        SSLContext sslcontext = null;
        FileInputStream fis = null;
        KeyStore keyStore = null;
        try {
            fis = new FileInputStream(keyPath);
            keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(fis,keyPass.toCharArray());

        } catch (FileNotFoundException e) {
            log.error("can not find the key file " + keyPath +" "+ e.getMessage(),e);
            throw OperationError.HTTP.HTTP_SSL_KEY_FILE_DOES_NOT_EXIST.newException(keyPath,e.getMessage());
        } catch (KeyStoreException e) {
            log.error("when create https connection,key store exception happened," +
                    "key type may not correct "+ e.getMessage(),e);
            throw OperationError.HTTP.HTTP_SSL_KEY_STORE_ERROR.newException(keyPath,e.getMessage());
        } catch (CertificateException e) {
            log.error("when create https connection,key file format not correct" +
                    e.getMessage(),e);
            throw OperationError.HTTP.HTTP_SSL_KEY_CERTIFICATE_ERROR.newException(keyPath,keyPass,e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            log.error("when create https connection,the algorithm used to check " +
                    "the integrity of the keystore cannot be found "+e.getMessage(),e);
            throw OperationError.HTTP.HTTP_SSL_KEY_NO_SUCH_ALGORITHM_ERROR.
                    newException(keyPath,e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fis!=null){
                try {
                    fis.close();
                } catch (IOException e) {
                    log.warn("key file input stream close failed");
                }
            }
        }

        if(keyStore==null){
            throw OperationError.HTTP.HTTP_SSL_KEY_LOAD_ERROR.newException(keyPath);
        }

        CloseableHttpClient httpclient = getHttpSSLCertificationClient(keyStore,strictCheckHostName);

        return  httpclient;
    }

    private CloseableHttpClient getHttpSSLCertificationClient(KeyStore ks, boolean strictCheckHostName) throws RestException {
        SSLContext sslcontext;
        try {
            sslcontext = SSLContexts.custom()
                    .loadTrustMaterial(ks,
                            new TrustNoneStrategy())
                    .build();
        } catch (NoSuchAlgorithmException e) {
            log.error("when create https connection,the algorithm used to check " +
                    "the integrity of the keystore cannot be found "+e.getMessage(),e);
            throw OperationError.HTTP.HTTP_SSL_KEY_NO_SUCH_ALGORITHM_ERROR.
                    newException("",e.getMessage());
        } catch (KeyManagementException e) {
            log.error("when create https connection,key management exception happened "
                    +e.getMessage(),e);
            throw OperationError.HTTP.HTTP_SSL_KEY_MANAGEMENT_ERROR.newException(e.getMessage());
        } catch (KeyStoreException e) {
            log.error("when create https connection,key store exception happened," +
                    "key type may not correct "+ e.getMessage(),e);
            throw OperationError.HTTP.HTTP_SSL_KEY_STORE_ERROR.newException("",e.getMessage());
        }


        HostnameVerifier verifier = null;
        if(strictCheckHostName){
            verifier = SSLConnectionSocketFactory.getDefaultHostnameVerifier();
        }else{
            verifier = NoopHostnameVerifier.INSTANCE;
        }

        SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(
                sslcontext,
                new String[] { "TLSv1.1","TLSv1.2" },
                null,
                verifier);


        PoolingHttpClientConnectionManager manager = getPoolConnectionManager(sslSocketFactory);


        CloseableHttpClient httpclient = HttpClients.custom()
                .setConnectionManager(manager)
                .setSSLSocketFactory(sslSocketFactory)
                .build();

        httpClientInstance = httpclient;
        return httpclient;
    }

    private PoolingHttpClientConnectionManager getPoolConnectionManager(SSLConnectionSocketFactory sslSocketFactory) {
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", sslSocketFactory).build();

        PoolingHttpClientConnectionManager manager =
                new PoolingHttpClientConnectionManager(socketFactoryRegistry);

        SocketConfig socketConfig = SocketConfig.custom()
                .setTcpNoDelay(true)
                .setSoTimeout(socketTimeOut)
                .build();

        manager.setDefaultSocketConfig(socketConfig);

        return manager;
    }


    public void releaseHttpClient() {
        if(httpClientInstance!=null){
            try {
                httpClientInstance.close();
            } catch (IOException e) {
                log.warn("release httpclient IO error " + e.getMessage(),e);
            }
        }
        httpClientInstance = null;
    }


    private static X509TrustManager manager = new X509TrustManager()
    {
        public X509Certificate[] getAcceptedIssuers()
        {
            return new java.security.cert.X509Certificate[0];
        }

        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException
        {
            return;
        }

        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException
        {
            return;
        }
    };


    public HttpClient createHttpClient(String certificateFilePath, boolean strictCheckHostName) throws RestException {
        CloseableHttpClient httpClient = null;
        BufferedInputStream bis = null;
        FileInputStream fis = null;
        try {
            KeyStore keystore = loadJVMKeyStore();
            fis = new FileInputStream(certificateFilePath);
            bis = new BufferedInputStream(fis);
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            while(bis.available() > 0){
                Certificate certificate = cf.generateCertificate(bis);
                keystore.setCertificateEntry("oceanStor",certificate);
            }
            httpClient = getHttpSSLCertificationClient(keystore, strictCheckHostName);
        } catch (FileNotFoundException e) {
            log.error("can not find the key file " + certificateFilePath +" "+ e.getMessage(),e);
            throw OperationError.HTTP.HTTP_SSL_KEY_FILE_DOES_NOT_EXIST.newException(certificateFilePath,e.getMessage());
        } catch (CertificateException e) {
            log.error("get x.509 certificate instance error");
            throw OperationError.HTTP.HTTP_GET_X509_CERTIFICATE_INSTANCE_ERROR.newException(certificateFilePath,e.getMessage());
        } catch (IOException e) {
            log.error("certification file read error");
            throw OperationError.HTTP.HTTP_SSL_KEY_READ_ERROR.newException(certificateFilePath,e.getMessage());
        } catch (KeyStoreException e) {
            log.error("when create https connection,key store exception happened," +
                    "key type may not correct "+ e.getMessage(),e);
            throw OperationError.HTTP.HTTP_SSL_KEY_STORE_ERROR.newException("",e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            log.error("when create https connection,the algorithm used to check " +
                    "the integrity of the certificate cannot be found "+e.getMessage(),e);
            throw OperationError.HTTP.HTTP_SSL_CERTIFICATE_NO_SUCH_ALGORITHM_ERROR.
                    newException(certificateFilePath,e.getMessage());
        } finally {
            if(bis!=null){
                try {
                    bis.close();
                } catch (IOException e) {
                    log.warn("certificate file input stream failed");
                }
            }
            if(fis!=null){
                try {
                    fis.close();
                } catch (IOException e) {
                    log.warn("certificate file input stream failed");
                }
            }
        }

        if(httpClient==null){
            log.error("when create https connection exception happened," +
                    "certificate file  may not correct");
            throw OperationError.HTTP.HTTP_SSL_CERTIFICATE_LOAD_ERROR.newException(certificateFilePath,"");
        }

        return httpClient;
    }

    private KeyStore loadJVMKeyStore() throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
        final char sep = File.separatorChar;
        InputStream ips = this.getClass().getResourceAsStream(sep + "config" + sep + "cacerts");
        KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
        keystore.load(null);
        return keystore;
    }
}
