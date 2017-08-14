package com.java.references;

import java.lang.ref.SoftReference;

/**
 * 软引用
 */
public class Soft {

    /**
     * 如果一个对象只具有软引用，那就类似于可有可物的生活用品。
     * 如果内存空间足够，垃圾回收器就不会回收它，
     * 如果内存空间不足了，就会回收这些对象的内存。
     * 只要垃圾回收器没有回收它，该对象就可以被程序使用。
     */
    public static void main(String[] args) {
        Test obj = new Test();
        obj.setI(50);
        SoftReference<Object> sf = new SoftReference<Object>(obj);
        obj.setI(30);
        obj=null;
        System.gc(); //启动GC
        System.out.println(sf.get());
        Test aa=(Test) sf.get();
        //输出30  --java是传值的方式传引用，或者说传值的方式传地址。
        System.out.println(aa.getI());
    }
}
