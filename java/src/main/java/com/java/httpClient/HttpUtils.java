package com.java.httpClient;

import com.google.common.base.Strings;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.*;

/**
 * HttpUtils
 *
 * @author 7le
 */
public class HttpUtils {

    private final static Logger LOG = LoggerFactory.getLogger(HttpUtils.class);

    private static PoolingHttpClientConnectionManager connMgr;
    private static RequestConfig requestConfig;
    private static final int MAX_TIMEOUT = 7000;
    private static final int SUCCESS = 200;

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

    private static CloseableHttpClient getHttpClient() {
        return HttpClientBuilder.create().setMaxConnTotal(200)
                .setMaxConnPerRoute(100)
                .build();
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
            for (String key : params.keySet()) {
                uriBuilder.setParameter(key, params.get(key));
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
            for (String key : params.keySet()) {
                parameters.add(new BasicNameValuePair(key, params.get(key)));
            }
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
    public static String post(String url) throws Exception{
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
        if (LOG.isDebugEnabled()) {
            LOG.debug("x-forwarded-for = {}", ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            if (LOG.isDebugEnabled()) {
                LOG.debug("Proxy-Client-IP = {}", ip);
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            if (LOG.isDebugEnabled()) {
                LOG.debug("WL-Proxy-Client-IP = {}", ip);
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (LOG.isDebugEnabled()) {
                LOG.debug("RemoteAddr-IP = {}", ip);
            }
        }
        if (!Strings.isNullOrEmpty(ip)) {
            ip = ip.split(":")[0];
        }
        String[] ipArray = ip.split(",");
        for (String s : ipArray) {// 过滤掉局域网ip
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
