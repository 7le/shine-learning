package com.shine.designPatterns.decorator;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * 具体装饰类
 * 它是抽象装饰类的子类，负责向构件添加新的职责。
 * 每一个具体装饰类都定义了一些新的行为，它可以调用在抽象装饰类中定义的方法，
 * 并可以增加新的方法用以扩充对象的行为。
 * Created by 7le on 2017/5/2 0002.
 */
public class ConcreteDecorator extends Decorator{


    public ConcreteDecorator(Phone phone) {
        super(phone);
    }

    @Override
    protected void call(){
      super.call();
      bye();
    }

    //附加的装饰性功能
    private void bye(){
        System.out.println("bye......");
    }

    /**
     * 适用场景：在不影响其他对象的情况下，以动态、透明的方式给单个对象添加新功能、新职责场景下适合适用装饰模式，
     *           除此之外，当遇到不能采用继承方式对系统进行扩展或者采用继承不利于系统扩展的情况下也适合使用装饰者模式。
     * 总结：  装饰模式降低了系统的耦合度，可以动态增加或删除对象的职责，
     *        并使得需要装饰的具体构件类和具体装饰类可以独立变化，以便增加新的具体构件类和具体装饰类。
     *        对于扩展一个对象的功能，装饰模式比继承更加灵活性，不会导致类的个数急剧增加。
     * @param args
     */
    public static void main(String[] args) throws FileNotFoundException {
        ConcreteDecorator decorator=new ConcreteDecorator(new Iphone());
        decorator.call();

        //InputStream就是装饰者模式中的超类（Component） ==>Phone
        //ByteArrayInputStream，FileInputStream相当于被装饰者（ConcreteComponent）==>Iphone   这些类都提供了最基本的字节读取功能。
        //而另外一个和这两个类是同一级的类FilterInputStream即是装饰者（Decorator）==>Decorator
        //BufferedInputStream，DataInputStream这些都是被装饰者装饰后形成的成品。==>ConcreteDecorator
        BufferedInputStream bufferedInputStream=new BufferedInputStream(new FileInputStream("Decorator"));
    }
}
