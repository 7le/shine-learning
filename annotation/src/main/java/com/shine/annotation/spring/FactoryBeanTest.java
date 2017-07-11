package com.shine.annotation.spring;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cglib.core.SpringNamingPolicy;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class FactoryBeanTest<T> implements InitializingBean, FactoryBean<T> {

    private String innerClassName;

    public void setInnerClassName(String innerClassName) {
        this.innerClassName = innerClassName;
    }

    public T getObject() throws Exception {
        Class innerClass = Class.forName(innerClassName);
        //这里用jdk代理还有问题，先都用cglib代理
        /*Class classes[]=innerClass.getInterfaces();
        if(classes.length>0){
            for(Class bean:classes){
                if(bean.isInterface()){
                    return (T) InterfaceProxy.newInstance(bean);
                }
            }
        }*/
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(innerClass);
        enhancer.setNamingPolicy(SpringNamingPolicy.INSTANCE);
        enhancer.setCallback(new MethodInterceptorImpl());
        return (T) enhancer.create();

    }

    public Class<?> getObjectType() {
        try {
            if(innerClassName!=null)
            return Class.forName(innerClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isSingleton() {
        return true;
    }

    public void afterPropertiesSet() throws Exception {

    }
}

//jdk
class InterfaceProxy implements InvocationHandler {

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
       // System.out.println("ObjectProxy execute:" + method.getName());
        try {
            return method.invoke(proxy, args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T newInstance(Class<T> innerInterface) {
        ClassLoader classLoader = innerInterface.getClassLoader();
        Class[] interfaces = new Class[]{innerInterface};
        InterfaceProxy proxy = new InterfaceProxy();
        return (T) Proxy.newProxyInstance(classLoader, interfaces, proxy);
    }
}

//cglib
class MethodInterceptorImpl implements MethodInterceptor {

    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("MethodInterceptorImpl \r\n class:" +o.getClass().getName()+"\r\n methodName:"+ method.getName());
        return methodProxy.invokeSuper(o, objects);
    }
}


