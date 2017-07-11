package com.shine.designPatterns.singleton;

/**
 * 双重检查锁定的 懒汉单例
 * Created by 7le on 2017/5/14 0014.
 */
public class Singleton3 {

    private Singleton3(){}

    private static Singleton3 single3 =null;

    public static Singleton3 getInstance(){
        if(single3==null){
            synchronized (Singleton3.class){
                if (single3 == null){
                    single3 = new Singleton3();
                }
            }
        }
        return single3;
    }

}
