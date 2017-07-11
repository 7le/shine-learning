package com.shine.designPatterns.factory.abstractFactory;

/**
 * 具体工厂
 * Created by 7le on 2017/5/1 0001.
 */
public class Factory2016 extends PhoneFactory {
    @Override
    public Iphone createIphone() {
        return new IphoneI7();
    }

    @Override
    public Samsung createSamsung() {
        return new SamsungS6();
    }
}
