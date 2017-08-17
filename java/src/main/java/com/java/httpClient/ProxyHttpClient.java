package com.java.httpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.annotation.Resource;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.entity.DeflateDecompressingEntity;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ProxyHttpClient {

    private final static Logger LOG = LoggerFactory.getLogger(ProxyHttpClient.class);

    enum ContentEncoding {
        GZIP, // zip压缩内容
        DEFLATE, // deflate压缩
        UNZIP; // 非压缩内容
    }

    /**
     * 代理访问,随机遍历代理服务器
     * @param url
     * @return
     * @throws Exception
     */
    public static RequestResult httpProxyRequest(List<ProxyServer> proxyList, String url) {
        RequestResult result = null;
        while (!proxyList.isEmpty()) {
            int i = (int) (Math.random() * proxyList.size());
            ProxyServer proxyServer = proxyList.get(i);
            try {
                result = httpClientRequest(proxyServer, url);
            } catch (Exception e) {
                LOG.error("httpclient is error!", e.getMessage());
            }
            if (result != null && result.getStream() != null && result.getStream().length > 0) {
                return result;
            }
            proxyList.remove(i);
        }
        // 显示结果
        return result;
    }

    private static RequestResult httpClientRequest(ProxyServer proxyServer, String url)
            throws Exception {

        DefaultHttpClient httpClient = null;
        try {
            int index1 = url.indexOf("//") + 2;
            int index2 = url.lastIndexOf("/");
            String host = "";
            String postfix = "";
            if (index2 > index1) {
                host = url.substring(url.indexOf("//") + 2, url.lastIndexOf("/"));
                postfix = url.substring(url.lastIndexOf("/"), url.length());
            } else {
                host = url;
            }
            httpClient = new DefaultHttpClient();
            httpClient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
            httpClient.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 5000);

            httpClient.getCredentialsProvider().setCredentials(
                    new AuthScope(proxyServer.getIp(), proxyServer.getPort()),
                    new UsernamePasswordCredentials("", "")); // 128.8.126.11
            // 3124
            HttpHost targetHost = new HttpHost(host);

            HttpHost proxy = new HttpHost(proxyServer.getIp(),proxyServer.getPort()); // 这两句话去掉就是直接访问不用代理
            httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
                    proxy);
            HttpGet httpGet = new HttpGet(postfix);
            addCommonHeader(httpGet);
            HttpResponse response = httpClient.execute(targetHost, httpGet);
            RequestResult result = reponse(url, response, "utf-8");
            httpGet.abort();
            return result;
        } catch (Exception e) {
            LOG.error("httpclient is error!", e.getMessage());
        } finally {
            if (httpClient != null) {
                httpClient.getConnectionManager().shutdown();
            }
        }
        return null;
    }

    public static RequestResult httpRequest(String url) {
        return httpRequest(url, "utf-8");
    }

    public static RequestResult httpsRequest(String url) {
        return httpsRequest(url, "utf-8");
    }

    public static RequestResult httpRequest(String url, String charset) {
        DefaultHttpClient httpClient = null;
        try {
            httpClient = new DefaultHttpClient();
            httpClient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);
            httpClient.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 30000);

            HttpGet httpGet = new HttpGet(url);
            addCommonHeader(httpGet);
            HttpResponse response = httpClient.execute(httpGet);
            RequestResult result = reponse(url, response, charset);
            httpGet.abort();
            return result;
        } catch (Exception e) {
            LOG.error("httpclient is error!", e.getMessage());
        } finally {
            if (httpClient != null) {
                httpClient.getConnectionManager().shutdown();
            }
        }
        return null;
    }

    /*
     * 进行https的get请求
     */
    public static RequestResult httpsRequest(String url,String charset){
        DefaultHttpClient httpClient = null;
        HttpGet httpGet = null;
        try{
            httpClient = new SSLClient();
            httpClient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);
            httpClient.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 30000);
            httpGet = new HttpGet(url);
            addCommonHeader(httpGet);
            HttpResponse response = httpClient.execute(httpGet);
            RequestResult result = reponse(url, response, charset);
            httpGet.abort();
            return result;
        }catch(Exception e){
            LOG.error("httpclient is error!", e.getMessage());
        }  finally {
            if (httpClient != null) {
                httpClient.getConnectionManager().shutdown();
            }
        }
        return null;
    }

    public RequestResult jsouphttpRequest(String url) throws Exception {
        RequestResult result = new RequestResult();
        String content = Jsoup.parse(new URL(url), 1000 * 10).html();
        if ("".equals(content)) {
            content = null;
        }
        result.setContent(content);
        return result;
    }

    //用于进行Https请求的HttpClient
    public static class SSLClient extends DefaultHttpClient {
        public SSLClient() throws Exception{
            super();
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }
                @Override
                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx,SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            ClientConnectionManager ccm = this.getConnectionManager();
            SchemeRegistry sr = ccm.getSchemeRegistry();
            sr.register(new Scheme("https", 443, ssf));
        }
    }

    private static RequestResult reponse(String url, HttpResponse response, String charset) throws Exception {
        RequestResult result = new RequestResult();
        result.setReturnCode(response.getStatusLine().getStatusCode());
        if (response.getStatusLine().getStatusCode() == 200) {// 成功返回
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String contentEncoding = entity.getContentEncoding() != null && entity.getContentEncoding().getValue() != null ? entity.getContentEncoding().getValue() : "";
                String contentType = entity.getContentType() == null ? null : entity.getContentType().getValue();
                String content = null;
                result.setContentType(contentType);

                if (contentEncoding.equalsIgnoreCase(ContentEncoding.GZIP.name())) {
                    entity = new GzipDecompressingEntity(response.getEntity());
                } else if (contentEncoding.equalsIgnoreCase(ContentEncoding.DEFLATE.name())) {
                    entity = new DeflateDecompressingEntity(response.getEntity());
                }

                if(result.isBinaryStream()){
                    result.setStream(readStream(entity.getContent()));
                }else{
                    content = EntityUtils.toString(entity, charset);
                    Document doc = Jsoup.parse(content);
                    Elements eles = doc.select("meta[http-equiv=Content-Type]");
                    if(eles!=null&&!eles.isEmpty()){
                        Element e = eles.get(0);
                        String v = e.attr("content");
                        String set;
                        if(v.indexOf("charset")>-1){
                            set = v.substring(v.indexOf("=")+1,v.length());
                        }else{
                            Elements eles0 = doc.select("meta[charset]");
                            Element e0 = eles0.get(0);
                            set = e0.attr("charset");
                        }
                        if(!charset.toLowerCase().equals(set.toLowerCase())){
                            content = httpRequest(url,set).getContent();
                        }
                    }else{
                        if(LOG.isDebugEnabled())
                            LOG.debug("not has char set url:" + url);
                    }
                }

                try {
                    EntityUtils.consume(response.getEntity());
                } catch (Exception e) {
                    LOG.error("httpclient is error!", e.getMessage());
                }
                result.setContent(content);
                return result;
            }
        } else {
            LOG.warn("visit url:{}, return code:{}", url, response.getStatusLine().getStatusCode());
        }
        return result;
    }

    public static byte[] readStream(InputStream stream) throws IOException {
        ByteArrayOutputStream outsStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[10 * 1024];
        int len = 0;
        while ((len = stream.read(buffer)) != -1) {
            outsStream.write(buffer, 0, len);
        }
        outsStream.close();
        stream.close();
        return outsStream.toByteArray();
    }

    private static void addCommonHeader(HttpRequest httpRequest) {
        httpRequest.addHeader("User-Agent","Mozilla/5.0 (Windows NT 5.1; rv:8.0.1) Gecko/20100101 Firefox/8.0.1");
        httpRequest.addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        httpRequest.addHeader("Accept-Language", "zh-cn,zh;q=0.5");
        httpRequest.addHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
        httpRequest.addHeader("Cache-Control", "max-age=0");
        httpRequest.addHeader("Accept-Encoding", "gzip, deflate");
    }

    public static RequestResult httpRequestToVote(String url, String charset,String signature,String verification) {
        DefaultHttpClient httpClient = null;
        try {
            httpClient = new DefaultHttpClient();
            httpClient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);
            httpClient.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 30000);

            HttpGet httpGet = new HttpGet(url);
            addCommonHeader(httpGet);
            addVoteHeader(httpGet,signature,verification);
            HttpResponse response = httpClient.execute(httpGet);
            RequestResult result = reponse(url, response, charset);
            httpGet.abort();
            return result;
        } catch (Exception e) {
            LOG.error("httpclient is error!", e.getMessage());
        } finally {
            if (httpClient != null) {
                httpClient.getConnectionManager().shutdown();
            }
        }
        return null;
    }

    private static void addVoteHeader(HttpRequest httpRequest,String signature,String verification) {
        httpRequest.addHeader("Api-Signature",signature);
        httpRequest.addHeader("Api-Verification",verification);
        httpRequest.addHeader("Api-Timestamp", "1");
    }
}
