package com.shine.designPatterns.factory.abstractFactory;

/**
 * 产品族
 * Created by 7le on 2017/5/1 0001.
 */
public class SamsungS7 extends Samsung{
    @Override
    public void getName() {
        System.out.println("我是： S7");
    }
}
