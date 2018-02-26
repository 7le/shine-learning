package com.java.references;

import java.util.LinkedList;

/**
 * 强引用
 */
public class Strong {

    /**
     * Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
     * 堆内存溢出。不断的在堆上分配一个 1M 大小的 byte[]对象，并且将该引用加入到 list 中，循环10240次，
     * 需要占用 10G 的堆内存，从而导致 heap space OutOfMemory.
     *
     */
    public static void main(String[] args) {
        LinkedList<byte[]> list = new LinkedList<>();
        for (int i = 0; i < 1024 * 10; i++) {
            list.add(new byte[1024 * 1024]);
        }
    }
}
