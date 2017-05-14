package com.shine.designPatterns.singleton;

/**
 * 懒汉单例
 * Created by 7le on 2017/5/14 0014.
 */
public class Singleton1 {

    private Singleton1(){}

    private static Singleton1 single1 =null;

    public static Singleton1 getInstance(){
        if(single1==null){
            single1 = new Singleton1();
        }
        return single1;
    }
}
