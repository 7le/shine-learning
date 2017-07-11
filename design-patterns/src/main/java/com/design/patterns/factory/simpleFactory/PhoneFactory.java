package com.design.patterns.factory.simpleFactory;

/**
 * 简单工厂模式
 * Created by 7le on 2017/4/28 0028.
 */
public class PhoneFactory  {

    public static Phone newPhone(String name){

        Phone phone;
        switch (name) {
            case "苹果":
                phone = new Iphone();
                break;
            case "三星":
                phone = new Samsung();
                break;
            default:
                phone = null;
                break;
        }
        return phone;
    }

    /**
     * 1.客户端可以免除创建一个工厂类的实例，并且直接通过工厂类的静态方法获得自己想要的产品，
     *  这样就降低了客户端与工厂类的耦合度，实现了对象的创建和使用分离。
     * 2.PhoneFactory 只通过调用产品实现类的方法去获得一个实例，并没有自己来实现它，
     *  也就是说现在这个类只有一个功能，那就是管理创建满足客户端的需求，符合单一职责原则。
     */
    public static void main(String[] args) {
        Phone phone=PhoneFactory.newPhone("苹果");
        System.out.println(phone.getName());
    }
}
