package com.shine;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootShineApplicationTests {

	@Test
	public void contextLoads() {
		String uri="/ag/xw123214.html?";
		Pattern pattern=Pattern.compile("([\\s\\S]*?)htm(l)?\\?([\\s\\S]*?)");
		Matcher matcher = pattern.matcher(uri);
		// 字符串是否与正则表达式相匹配
		boolean rs = matcher.matches();
		System.out.println(rs);

		//验证是否为邮箱地址
		String str1="ceponline@yahoo.com.cn";

		Pattern pattern1 =Pattern.compile("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+",Pattern.CASE_INSENSITIVE);

		Matcher matcher1 = pattern1.matcher(str1);

		System.out.println(matcher1.matches());
	}

}
