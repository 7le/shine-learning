package com.design.patterns.adapter;

/**
 * 类适配器
 * Created by 7le on 017/6/14
 */
public class ClassAdapter extends Adaptee implements Target{

    @Override
    public void method() {
        super.specialMethod();
    }
}
