package com.java.httpClient;

import com.google.common.base.Strings;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import javax.servlet.http.HttpServletRequest;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * HttpUtils
 *
 * @author 7le
 */
public class HttpUtils {

    private final static Logger log = LoggerFactory.getLogger(HttpUtils.class);
    private static final int MAX_TIMEOUT = 3000;
    private static final int SUCCESS = 200;
    private static PoolingHttpClientConnectionManager connMgr;
    private static RequestConfig requestConfig;

    static {
        // 设置连接池
        connMgr = new PoolingHttpClientConnectionManager();
        // 设置连接池大小
        connMgr.setMaxTotal(100);
        connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());
        requestConfig = RequestConfig.custom()
                // 设置连接超时
                .setConnectTimeout(MAX_TIMEOUT)
                // 设置读取超时
                .setSocketTimeout(MAX_TIMEOUT)
                // 设置从连接池获取连接实例的超时
                .setConnectionRequestTimeout(MAX_TIMEOUT).build();
    }

    private static CloseableHttpClient getHttpClient() throws NoSuchAlgorithmException {
        return HttpClientBuilder.create().setMaxConnTotal(200)
                .setMaxConnPerRoute(100)
                .setSSLHostnameVerifier((String host, final SSLSession session) -> true)
                .setSSLSocketFactory(new SSLConnectionSocketFactory(createSSLSocketFactory()))
                .build();
    }

    private static SSLContext createSSLSocketFactory() {
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }}, new SecureRandom());
            return sc;
        } catch (Exception e) {
            log.warn("Exception:{}", e);
        }
        return null;
    }

    /**
     * 不带参数的get请求
     */
    public static String get(String url) throws Exception {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        try (CloseableHttpClient httpClient = getHttpClient();
             CloseableHttpResponse response = httpClient.execute(httpGet)) {
            if (response.getStatusLine().getStatusCode() == SUCCESS) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        }
        return null;
    }

    /**
     * 带参数的get请求
     */
    public static String get(String url, Map<String, String> params) throws Exception {
        URIBuilder uriBuilder = new URIBuilder(url);
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                uriBuilder.setParameter(entry.getKey(), entry.getValue());
            }
        }
        return get(uriBuilder.build().toString());
    }

    /**
     * 带参数的post请求
     */
    public static String post(String url, Map<String, String> params) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        if (params != null) {
            List<NameValuePair> parameters = new ArrayList<>(0);
            parameters.addAll(params.entrySet().stream()
                    .map(entry -> new BasicNameValuePair(entry.getKey(), entry.getValue())).collect(Collectors.toList()));
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters);
            httpPost.setEntity(formEntity);
        }
        try (CloseableHttpClient httpClient = getHttpClient();
             CloseableHttpResponse response = httpClient.execute(httpPost)) {
            if (response.getStatusLine().getStatusCode() == SUCCESS) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        }
        return null;
    }

    /**
     * 不带参数的post请求
     */
    public static String post(String url) throws Exception {
        return post(url, null);
    }

    /**
     * 本机获取ip地址
     */
    public static String getIpAddress() {
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                if (netInterface.isLoopback() || netInterface.isVirtual() || !netInterface.isUp()) {
                    continue;
                } else {
                    Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        ip = addresses.nextElement();
                        if (ip != null && ip instanceof Inet4Address) {
                            return ip.getHostAddress();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取客户端的真实ip
     */
    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (log.isDebugEnabled()) {
            log.debug("x-forwarded-for = {}", ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            if (log.isDebugEnabled()) {
                log.debug("Proxy-Client-IP = {}", ip);
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            if (log.isDebugEnabled()) {
                log.debug("WL-Proxy-Client-IP = {}", ip);
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (log.isDebugEnabled()) {
                log.debug("RemoteAddr-IP = {}", ip);
            }
        }
        if (!Strings.isNullOrEmpty(ip)) {
            ip = ip.split(":")[0];
        }
        String[] ipArray = ip.split(",");
        // 过滤掉局域网ip
        for (String s : ipArray) {
            s = s.trim();
            if (!(s.startsWith("10.") || s.startsWith("192."))) {
                return s;
            }
        }
        if (!Strings.isNullOrEmpty(ip)) {
            ip = ip.split(",")[0];
        }
        return ip;
    }

    public static Map getParams(HttpServletRequest request) {
        Map map = new HashMap();
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            if (paramValues.length == 1) {
                String paramValue = paramValues[0];
                if (paramValue.length() != 0) {
                    map.put(paramName, paramValue);
                }
            }
        }
        return map;
    }
}
