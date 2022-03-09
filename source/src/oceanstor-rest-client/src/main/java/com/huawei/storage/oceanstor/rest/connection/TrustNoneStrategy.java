package com.huawei.storage.oceanstor.rest.connection;

import org.apache.http.conn.ssl.TrustStrategy;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Huawei Technologies  all rights reserved
 */
public class TrustNoneStrategy implements TrustStrategy{
    @Override
    public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        return false;
    }
}
