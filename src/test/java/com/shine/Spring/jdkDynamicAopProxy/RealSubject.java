package com.shine.spring.jdkDynamicAopProxy;

/**
 * @author 7le
 * @Description: 真实角色：实现了Subject的request()方法
 * @date 2017年4月11日
 */
public class RealSubject implements Subject {

    public void request() {
        System.out.println("From real subject.");
    }
}
