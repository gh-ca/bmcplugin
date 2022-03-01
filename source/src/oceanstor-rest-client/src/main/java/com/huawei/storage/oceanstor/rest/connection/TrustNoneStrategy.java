package com.huawei.storage.oceanstor.rest.connection;

import org.apache.http.conn.ssl.TrustStrategy;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Huawei Technologies  all rights reserved
 * <p>
 * Created by m00373015 on 2016/10/19.
 */
public class TrustNoneStrategy implements TrustStrategy{
    @Override
    public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        return false;
    }
}
