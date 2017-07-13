package com.spring;

import com.spring.jdkDynamicAopProxy.DynamicSubject;
import com.spring.jdkDynamicAopProxy.RealSubject;
import com.spring.jdkDynamicAopProxy.Subject;
import com.spring.jdkDynamicAopProxy.Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {


	//客户端：生成代理实例，并调用了request()方法
	@Test
	public void ClientJdkDynamicAop(){
       /* System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
        System.out.println(System.getProperties());*/

		//以下是一次性生成代理
		Subject subject=(Subject) new DynamicSubject().bind(new RealSubject());

		// 获取代理类的字节码
		Utils.generateProxyClassFile(subject);

		//这里可以通过运行结果证明subject是Proxy的一个实例，这个实例实现了Subject接口
		System.out.println(subject instanceof Proxy);

		//这里可以看出subject的Class类是$Proxy0,这个$Proxy0类继承了Proxy，实现了Subject接口
		System.out.println("subject的Class类是："+subject.getClass().toString());

		System.out.print("subject中的属性有：");

		Field[] field=subject.getClass().getDeclaredFields();
		for(Field f:field){
			System.out.print(f.getName()+", ");
		}

		System.out.print("\n"+"subject中的方法有：");

		Method[] method=subject.getClass().getDeclaredMethods();

		for(Method m:method){
			System.out.print(m.getName()+", ");
		}

		System.out.println("\n"+"subject的父类是："+subject.getClass().getSuperclass());

		System.out.print("\n"+"subject实现的接口是：");

		Class<?>[] interfaces=subject.getClass().getInterfaces();

		for(Class<?> i:interfaces){
			System.out.print(i.getName()+", ");
		}

		System.out.println("\n\n"+"运行结果为：");
		subject.request();
		subject.doSomething();
	}

	@Test
	public void contextLoads() {
	}

}
