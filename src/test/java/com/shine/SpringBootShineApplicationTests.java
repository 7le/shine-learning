package com.shine;

import com.alibaba.fastjson.JSONArray;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedWriter;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.test.context.junit4.SpringRunner;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootShineApplicationTests {

	@Test
	public void xml() throws ParserConfigurationException, IOException, SAXException, TransformerException {
		//修改xml文件
//		File xmlFile = new File("C:/Users/admin/Desktop/article.xml");
//		File xmlFile = new File("C:/Users/admin/Desktop/article (1).xml");
//		File xmlFile = new File("C:/Users/admin/Desktop/article (2).xml");
//		File xmlFile = new File("C:/Users/admin/Desktop/article (3).xml");
		File xmlFile = new File("C:/Users/admin/Desktop/article (4).xml");
		System.out.println(xmlFile);

		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();

		DocumentBuilder builder = builderFactory.newDocumentBuilder();

		Document doc = builder.parse(xmlFile);

		doc.getDocumentElement().normalize();

		System.out.println("Root  element: " + doc.getDocumentElement().getNodeName());

		NodeList nList = doc.getElementsByTagName("ArticleDoc");

		for (int i = 0; i < nList.getLength(); i++) {

			Node node = nList.item(i);

			System.out.println("Node name: " + node.getNodeName());
			Element ele = (Element) node;
			if (node.getNodeType() == Element.ELEMENT_NODE) {
				System.out.println("---------------------修改前--------------------------------------------");
				System.out.println("Source: " +  ele.getElementsByTagName("Source").item(0).getTextContent());
				System.out.println("ChannelId: " +  ele.getElementsByTagName("ChannelId").item(0).getTextContent());
				System.out.println("WebsiteId: " +  ele.getElementsByTagName("WebsiteId").item(0).getTextContent());
				System.out.println("InputAdminId: " +  ele.getElementsByTagName("InputAdminId").item(0).getTextContent());
				System.out.println("AuditAdminId: " +  ele.getElementsByTagName("AuditAdminId").item(0).getTextContent());

				ele.getElementsByTagName("ChannelId").item(0).setTextContent("3231");


				ele.getElementsByTagName("WebsiteId").item(0).setTextContent("1399");
				ele.getElementsByTagName("InputAdminId").item(0).setTextContent("39");
				ele.getElementsByTagName("AuditAdminId").item(0).setTextContent("");

				System.out.println("---------------------修改后--------------------------------------------");
				System.out.println("ChannelId: " +  ele.getElementsByTagName("ChannelId").item(0).getTextContent());
				System.out.println("WebsiteId: " +  ele.getElementsByTagName("WebsiteId").item(0).getTextContent());
				System.out.println("InputAdminId: " +  ele.getElementsByTagName("InputAdminId").item(0).getTextContent());
				System.out.println("AuditAdminId: " +  ele.getElementsByTagName("AuditAdminId").item(0).getTextContent());

			}
		}
		System.out.println("执行数量： "+nList.getLength());
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File("C:/Users/admin/Desktop/xml/投资入门/article5.xml"));
		transformer.transform(source, result);
	}

	@Test
	public void contextLoads() throws IOException {

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

	private static final String MEET99_PREFIX = "http://www.meet99.com/maps/loadchild/lvyou/";
	private static final String MEET99_SCENIC = "http://www.meet99.com/lvyou-";
	public static String[] MEET99_LOCATION = new String[]{"beijing","shanghai","zhejiang","hainan","guizhou","yunnan","xizhan","xinjiang","HongKong"};

	@Test
	public void testSleep(){
		long startTime = System.currentTimeMillis();
		List<String> urlList = new ArrayList<String>();
		//CrawlConstant.MEET99_LOCATION.length
		for(int i = 0; i < 2; i++){
			try {
				String crawlUrl = MEET99_PREFIX + MEET99_LOCATION[i];
				System.out.println("开始睡觉啦");
				long start=System.currentTimeMillis();
				//Thread.sleep(20000);
				long end=System.currentTimeMillis();
				System.out.println("睡醒啦 我睡了"+(end-start)+"ms");
				String content = httpClient(crawlUrl);
				System.out.println("content"+content);
				JSONArray jsonArray = JSONArray.parseArray(content);
				for (int j = 0; j < jsonArray.size(); j++){
					String text = "";
					try {
						text = jsonArray.getJSONObject(j).getString("text");
						String name = jsonArray.getJSONObject(j).getString("id");
						String url = MEET99_SCENIC + name +".html";
						urlList.add(url);
					}catch (Exception e){
						//LOG.error("相约99 获取id出错 "+ text,e);
						continue;
					}
				}

			}catch (Exception e){
				//LOG.error("相约99解析地理位置url 出错",e);
				continue;
			}
		}

		for(int j = 0; j < urlList.size(); j++){
			System.out.println(urlList.get(j));
		}
		long endTime = System.currentTimeMillis();
		System.out.println("花费时间："+(endTime-startTime));
	}

	public String httpClient(String url){
		String content = "";
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();

			HttpGet get = new HttpGet(url);
			get.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			get.setHeader("Accept-Encoding","gzip, deflate, sdch, br");
			get.setHeader("Accept-Language","zh-CN,zh;q=0.8");
			get.setHeader("Cache-Control","max-age=0");
			//get.setHeader("Connection","keep-alive");
			//get.setHeader("Cookie","bid=6NMeskj_Y50; ps=y; gr_user_id=939b8817-eed7-40f1-b20b-c2ab9c0e0ccd; ll=\"118172\"; _ga=GA1.2.697115885.1490608794; ct=y; ue=\"454940608@qq.com\"; dbcl2=\"159597346:GOMpGo/pXOU\"; ck=kgVE; _pk_ref.100001.4cf6=%5B%22%22%2C%22%22%2C1490945961%2C%22https%3A%2F%2Fwww.douban.com%2Faccounts%2Flogin%3Fredir%3Dhttps%253A%252F%252Fmovie.douban.com%252Fsubject%252F26966583%252F%22%5D; _vwo_uuid_v2=21318EB7C8019D8BA300792B3FA13E95|4be75f50789643b511d1a121a35fa523; push_noty_num=0; push_doumail_num=0; __utma=30149280.697115885.1490608794.1490940816.1490945961.19; __utmb=30149280.0.10.1490945961; __utmc=30149280; __utmz=30149280.1490945961.19.13.utmcsr=douban.com|utmccn=(referral)|utmcmd=referral|utmcct=/accounts/login; __utmv=30149280.15959; __utma=223695111.388155953.1490608794.1490945961.1490946352.16; __utmb=223695111.0.10.1490946352; __utmc=223695111; __utmz=223695111.1490946352.16.10.utmcsr=baidu|utmccn=(organic)|utmcmd=organic|utmctr=%E8%B1%86%E7%93%A3; _pk_id.100001.4cf6=2f15505cc1522b0b.1490608223.15.1490946478.1490942090.; _pk_ses.100001.4cf6=*; ap=1");
			//get.setHeader("Cookie","bid=26-Zep4i5Tw; ps=y; ll="108231"; _pk_ref.100001.4cf6=%5B%22%22%2C%22%22%2C1490947748%2C%22https%3A%2F%2Fwww.google.com.sg%2F%22%5D; __utma=30149280.671038541.1490947748.1490947748.1490947748.1; __utmb=30149280.0.10.1490947748; __utmc=30149280; __utmz=30149280.1490947748.1.1.utmcsr=google|utmccn=(organic)|utmcmd=organic|utmctr=(not%20provided); __utma=223695111.1718243204.1490947748.1490947748.1490947748.1; __utmb=223695111.0.10.1490947748; __utmc=223695111; __utmz=223695111.1490947748.1.1.utmcsr=google|utmccn=(organic)|utmcmd=organic|utmctr=(not%20provided); _pk_id.100001.4cf6=bcb82520fb1de8cd.1490947748.1.1490947893.1490947748.; _pk_ses.100001.4cf6=*; _vwo_uuid_v2=56B99E34FA2E9DDB12DED0E43B9B760E|b9052bff922e3c0921af4809375d5583; ap=1");

			//get.setHeader("Host","movie.douban.com");
			//get.setHeader("Referer","https://movie.douban.com/");
			get.setHeader("Upgrade-Insecure-Requests","1");
			get.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36");

			String proxyIp = "180.76.154.5";
			int proxyPort = 8888;
			HttpHost host = new HttpHost(proxyIp,proxyPort);
			RequestConfig config = RequestConfig.custom().setProxy(host).setConnectTimeout(3000).setConnectionRequestTimeout(3000).build();
			get.setConfig(config);


			CloseableHttpResponse response = httpClient.execute(get);
			HttpEntity entity = response.getEntity();
			content = EntityUtils.toString(entity);
//            Document document = Jsoup.parse(content);
//            System.out.println(document);
			response.close();
			httpClient.close();
		}catch (Exception e){
			e.printStackTrace();
		}

		return content;
	}
}
