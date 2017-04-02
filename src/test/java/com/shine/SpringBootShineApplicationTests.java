package com.shine;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootShineApplicationTests {

	@Test
	public void contextLoads() throws IOException, ServletException {

		String str="m.cngold.org/htmeee/lm_.htm_edfef/";
		Pattern pattern=Pattern.compile("([\\s\\S]*?)\\.htm(l)?([\\s\\S]*?)");
		Matcher matcher = pattern.matcher(str);
		boolean rs = matcher.matches();
		System.out.println(rs);

		/*ServletRequest request=null;
		ServletResponse response=null;
		FilterChain chain=null;
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		//对路由为html和htm后面非法的拦截跳转404
		Pattern pattern=Pattern.compile("([\\s\\S]*?)htm(l)?([\\s\\S]*?)");
		Matcher matcher = pattern.matcher(httpRequest.getRequestURL());
		boolean rs = matcher.matches();
		if(rs==false){
			pattern=Pattern.compile("([\\s\\S]*?)\\w/");
			matcher = pattern.matcher(httpRequest.getRequestURL());
			boolean rs1 = matcher.matches();
			if(rs1==false){
				String str[]=httpRequest.getRequestURL().toString().split("/");
				String url="";
				for (int i = 0; i < str.length; i++) {
					url=url+str[i]+"/";
				}
				httpResponse.setStatus(301);
				httpResponse.addHeader("Location", url);
				System.out.println("rs1  "+url);
			}else {
				chain.doFilter(httpRequest, httpResponse);
			}
		}else{
			pattern=Pattern.compile("([\\s\\S]*?)htm(l)?\\?([\\s\\S]*?)");
			matcher = pattern.matcher(httpRequest.getRequestURL()+"?");
			boolean rs2 = matcher.matches();
			if(rs2==false){
				httpResponse.setStatus(301);
				httpResponse.addHeader("Location", "http://m.cngold.org/404.html");
			}else{
				//让目标资源执行
				chain.doFilter(httpRequest, httpResponse);
			}
		}
*/
		//验证是否为邮箱地址
	/*	String str1="ceponline@yahoo.com.cn";

		Pattern pattern1 =Pattern.compile("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+",Pattern.CASE_INSENSITIVE);

		Matcher matcher1 = pattern1.matcher(str1);

		System.out.println(matcher1.matches());*/
	}

	@Test
	public void test(){
		String a="";
		StringBuilder stringBuilder=new StringBuilder(a.length()*100000);
		long start=System.currentTimeMillis();
		for (int i = 0; i <100000 ; i++) {
			//a=a.concat("a");  4300ms
			//a=a+"a";          1500ms
			stringBuilder.append("a");  //4ms
		}
		a=stringBuilder.toString();
		long end=System.currentTimeMillis();
		System.out.println(a);
		System.out.println(end-start+"ms");
	}

}
