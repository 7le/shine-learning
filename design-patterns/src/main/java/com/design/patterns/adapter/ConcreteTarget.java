package com.design.patterns.adapter;

/**
 * 具体目标类
 * Created by 7le on 017/6/14
 */
public class ConcreteTarget implements Target{
    @Override
    public void method() {
        System.out.println("标准接口……");
    }
}
