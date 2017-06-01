package com.shine.spring.jdkDynamicAopProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author 7le
 * @Description: 实现InvocationHandler
 * @date 2017年4月11日
 */
public class DynamicSubject implements InvocationHandler {

    //这是动态代理的好处，被封装的对象是Object类型，接受任意类型的对象
    private Object obj;

    public DynamicSubject() {
    }

    public DynamicSubject(Object obj) {
        this.obj=obj;
    }

    public Object bind(Object obj) {
        this.obj = obj;
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(),obj.getClass().getInterfaces(),this);

    }

    //这个方法不是我们显示的去调用

    /**
     * @param proxy 需要代理的真实类的对象
     * @param method 待调用的真实对象的某个方法”
     * @param args 调用该方法时需要的参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before calling " + method);

        method.invoke(obj, args);

        System.out.println("after calling " + method);

        return null;
    }
}
