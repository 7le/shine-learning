package com.design.patterns.factory.abstractFactory;

/**
 * 抽象工厂
 * Created by 7le on 2017/5/1 0001.
 */
public abstract class PhoneFactory {

    public abstract Iphone createIphone();

    public abstract Samsung createSamsung();
}
