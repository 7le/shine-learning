package com.shine.designPatterns.factory.abstractFactory;

/**
 * 简单工厂通过反射改进抽象工厂及其子工厂
 * Created by 7le on 2017/5/1 0001.
 */
public class ReflexFactory {

    public static Iphone createIphone(String phone) throws Exception {
        return (Iphone) Class.forName(phone).newInstance();
    }

    public static Samsung createSamsung(String phone) throws Exception {
        return (Samsung) Class.forName(phone).newInstance();
    }

    public static void main(String[] args) throws Exception {

        Samsung phone = ReflexFactory.createSamsung("com.shine.designPatterns.factory.abstractFactory.SamsungS7");
        phone.getName();
    }
}
