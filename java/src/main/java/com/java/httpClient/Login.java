package com.java.httpClient;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import static java.lang.System.out;

/**
 * 模拟简单登录
 */
public class Login {
    public static void main(String[] args){
        //创建默认客户端
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();

        //创建Post请求实例
        HttpPost httpPost = new HttpPost("http://localhost:2010/back/ajaxJsonLogin.do");

        //创建参数列表
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("admin.loginName", "liuweibin"));
        nvps.add(new BasicNameValuePair("admin.passwordMd5", "000000"));

        //向对方服务器发送Post请求
        try {
            //将参数进行封装，提交到服务器端
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF8"));
            CloseableHttpResponse httpResponse = closeableHttpClient.execute(httpPost);

            //如果模拟登录成功
            if(httpResponse.getStatusLine().getStatusCode() == 200) {
                Header[] headers = httpResponse.getAllHeaders();
                for (Header header : headers) {
                    out.println(header.getName() + ": " + header.getValue());
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpPost.abort();      //释放资源
        }
    }
}
