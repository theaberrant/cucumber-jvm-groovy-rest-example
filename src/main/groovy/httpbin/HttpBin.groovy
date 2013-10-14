package httpbin

import groovy.util.logging.Slf4j
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.RESTClient
import org.apache.http.conn.scheme.Scheme
import org.apache.http.conn.ssl.SSLSocketFactory

import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import java.security.SecureRandom
import java.security.cert.X509Certificate

import static groovyx.net.http.ContentType.URLENC

/**
 * Helper classes for testing calls using0.
 * httpbin.org
 */
@Slf4j
class HttpBin {
    public static String httpBinURL = 'https://httpbin.org/'
    static RESTClient rc

    static {
        rc = new RESTClient(httpBinURL)
        rc.handler.failure = rc.handler.success

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
        rc.delete(path: 'delete') as HttpResponseDecorator
    }

    static HttpResponseDecorator post(HashMap data) {
        rc.post(body: data, path: 'post', requestContentType: URLENC) as HttpResponseDecorator
    }

    static HttpResponseDecorator put(HashMap data) {
        rc.put(body: data, path: 'put', requestContentType: URLENC) as HttpResponseDecorator
    }

    static HttpResponseDecorator get(HashMap data = null) {
        rc.get(query: data, path: 'get') as HttpResponseDecorator
    }

    static def reset() {
        // Reset client - currently only cookies, but add headers here as well if used
        rc.client.cookieStore.clear()
    }
}
