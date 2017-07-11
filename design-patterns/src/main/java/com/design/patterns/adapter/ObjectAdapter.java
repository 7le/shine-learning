package com.design.patterns.adapter;

/**
 * 对象适配器
 * Created by 7le on 017/6/14
 */
public class ObjectAdapter implements Target{

    private Adaptee adaptee;

    // 可以通过构造函数传入具体需要适配的被适配类对象
    public ObjectAdapter (Adaptee adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void method() {
        this.adaptee.specialMethod();
    }
}
