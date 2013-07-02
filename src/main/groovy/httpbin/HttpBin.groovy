package httpbin

import groovy.util.logging.Slf4j
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient
import org.apache.http.conn.scheme.Scheme
import org.apache.http.conn.ssl.SSLSocketFactory

import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import java.security.SecureRandom
import java.security.cert.X509Certificate

/**
 * Helper classes for testing calls using httpbin.org
 */
@Slf4j
class HttpBin {
    public static String httpBinURL = 'https://httpbin.org/'
    static RESTClient rc

    static {
        rc = new RESTClient(httpBinURL)

        //=== SSL UNSECURE CERTIFICATE ===
        def sslContext = SSLContext.getInstance("SSL")
        sslContext.init null, [([
                checkClientTrusted: { X509Certificate[] certs, String authType -> },
                checkServerTrusted: { X509Certificate[] certs, String authType -> },
                getAcceptedIssuers: {}
        ] as X509TrustManager)] as TrustManager[], new SecureRandom()
        def sf = new SSLSocketFactory(sslContext, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
        def httpsScheme = new Scheme("https", sf, 443)
        rc.client.connectionManager.schemeRegistry.register(httpsScheme)
    }

    static HttpResponseDecorator delete() {
        HttpResponseDecorator response = null
        try {
            response = rc.delete(path: 'delete') as HttpResponseDecorator
        } catch (HttpResponseException ex) {
            response = ex.response
        }

        return response
    }
}
