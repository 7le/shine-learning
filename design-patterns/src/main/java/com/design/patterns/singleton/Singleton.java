package com.design.patterns.singleton;

/**
 * 饿汉单例
 * Created by 7le on 2017/5/14 0014.
 */
public class Singleton {

    private static final Singleton single =new Singleton();
    private Singleton(){}

    public static Singleton getInstance(){
        return single;
    }
}