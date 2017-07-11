package com.design.patterns.factory.abstractFactory;

/**
 * 具体工厂
 * Created by 7le on 2017/5/1 0001.
 */
public class Factory2017 extends PhoneFactory {
    @Override
    public Iphone createIphone() {

        return new IphoneI7Plus();
    }

    @Override
    public Samsung createSamsung() {

        return new SamsungS7();
    }

    public static void main(String[] args) throws Exception {

        PhoneFactory f = new Factory2017();
        Iphone i=f.createIphone();
        i.getName();
    }
}
