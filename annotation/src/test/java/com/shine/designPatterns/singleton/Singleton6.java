package com.shine.designPatterns.singleton;

import java.util.HashMap;
import java.util.Map;

/**
 * 登记式单例(可忽略)
 * Created by 7le on 2017/5/14 0014.
 */
//类似Spring里面的方法，将类名注册，下次从里面直接获取。
public class Singleton6 {
    private static Map<String,Singleton6> map = new HashMap<String,Singleton6>();
    static{
        Singleton6 singleton6 = new Singleton6();
        map.put(singleton6.getClass().getName(), singleton6);
    }
    //保护的默认构造子
    protected Singleton6(){}
    //静态工厂方法,返还此类惟一的实例
    public static Singleton6 getInstance(String name) {
        if(name == null) {
            name = Singleton6.class.getName();
            System.out.println("name == null"+"--->name="+name);
        }
        if(map.get(name) == null) {
            try {
                map.put(name, (Singleton6) Class.forName(name).newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return map.get(name);
    }
    //一个示意性的商业方法
    public String about() {
        return "Hello, I am RegSingleton.";
    }
    public static void main(String[] args) {
        Singleton6 singleton6 = Singleton6.getInstance(null);
        System.out.println(singleton6.about());
    }
}
