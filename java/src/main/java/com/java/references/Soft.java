package com.java.references;

import java.lang.ref.SoftReference;
import java.util.LinkedList;

/**
 * 软引用
 */
public class Soft {

    /**
     * 如果一个对象只具有软引用，那就类似于可有可物的生活用品。
     * 如果内存空间足够，垃圾回收器就不会回收它，
     * 如果内存空间不足了，就会回收这些对象的内存。
     * 只要垃圾回收器没有回收它，该对象就可以被程序使用。
     *
     * 在同样需要10G内存的场景下 而软引用SoftReference引用的对象，如果仅仅只被软引用，
     * 而没有被强引用的话，在内存空间不足时，GC 就会回收该对象占用的内存，所以不会内存溢出
     */
    public static void main(String[] args) {
        long beginTime = System.nanoTime();
        LinkedList<SoftReference<byte[]>> list = new LinkedList<>();
        for (int i = 0; i < 1024 * 10; i++) {
            list.add(new SoftReference<>(new byte[1024 * 1024]));
        }
        long endTime = System.nanoTime();
        System.out.println(list.get(0).get().length);
        System.out.println("time: " + (endTime - beginTime));
    }
}
