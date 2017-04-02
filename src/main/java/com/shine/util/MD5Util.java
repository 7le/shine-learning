package com.shine.util;


import java.nio.charset.Charset;
import java.security.MessageDigest;

public class MD5Util {

    //密码加密
	public static String doImaoMd5(String userName, String passWord){
		String md5Pwd = MD5(userName + passWord +"加言");
//		System.out.println(md5Pwd);
		return md5Pwd;
	}

	// MD5  32位
	public static String MD5(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
			byte[] byteArray = inStr.getBytes("utf-8");

			byte[] md5Bytes = md5.digest(byteArray);

			StringBuffer hexValue = new StringBuffer();

			for (int i = 0; i < md5Bytes.length; i++) {
				int val = ((int) md5Bytes[i]) & 0xff;
				if (val < 16)
					hexValue.append("0");
				hexValue.append(Integer.toHexString(val));
			}

			return hexValue.toString();
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
	}

	public final static String MD52(String s) {
		char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};

		try {
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/*public static void main(String[] args) {

//		String a2 = "app_key=qflql2c2tdv519btm98fhl4c5yhh4uf8&batch_num=ces0012222a&content=【创力股份】尊敬的用户，本次验证码为12345\r\n" +
//				"6，请勿泄>露！&dest_id=15058306581&mission_num=test001&nonce_str=5iliulang&sms_type=verify_code&time_stamp=20160728145500&app_secret=66b5b9d2245acbf22012de18418aeac13312";
//		String bb = MD5(a2);
//		System.out.println(bb);
//		System.out.println(a2);
		String a = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
				"<xml>\n" +
				"\t<head>\n" +
				"\t\t<app_key>80ybyph1e7hfnrs24msiwcxidnj1d97k</app_key>\n" +
				"\t\t<time_stamp>20160729140702</time_stamp>\n" +
				"\t\t<nonce_str>aobenjy</nonce_str>\n" +
				"\t\t<sign>90af0abd08133be081db0de65204fef5</sign>\n" +
				"\t</head>\n" +
				"\t<body>\n" +
				"\t\t<dests>\n" +
				"\t\t\t<dest>\n" +
				"\t\t\t\t<mission_num>aobentest1841</mission_num>\n" +
				"\t\t\t\t<dest_id>13587682771</dest_id>\n" +
				"\t\t\t</dest>\n" +
				"\t\t</dests>\n" +
				"\t\t<batch_num>aobentest1841</batch_num>\n" +
				"\t\t<sms_type>verify_code</sms_type>\n" +
				"\t\t<content>【奥奔酒业】验证码：471031，您正在进行找回密码，如非本人操作请忽略。</content>\n" +
				"\t\t<app_secret>22fdf3f284289d59e67cdead78b070082714</app_secret>\n" +
				"\t</body>\n" +
				"</xml>";
		String url = "http://localhost:15666/api/v1/manySend";
		CloseableHttpClient httpclient = null;
		//进行HTTP回调
		try {
			httpclient = HttpClients.createDefault();
			//发送
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new StringEntity(a, Charset.forName("UTF-8")));

			//发送
			HttpResponse response = httpclient.execute(httpPost);
			String strResult = EntityUtils.toString(response.getEntity(), Charset.forName("utf-8"));
			System.out.println(strResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

}
