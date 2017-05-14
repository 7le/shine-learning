package com.shine.designPatterns.singleton;

/**
 * 双重检查锁定的 volatile保证顺序 懒汉单例
 * Created by 7le on 2017/5/14 0014.
 */
public class Singleton4 {

    private Singleton4(){}

    private static volatile Singleton4 single4 =null;

    public static Singleton4 getInstance(){
        if(single4==null){
            synchronized (Singleton4.class){
                if (single4 == null){
                    single4 = new Singleton4();
                }
            }
        }
        return single4;
    }

}
