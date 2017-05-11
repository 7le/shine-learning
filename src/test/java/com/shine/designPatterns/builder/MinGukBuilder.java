package com.shine.designPatterns.builder;

/**
 * 实现Builder的接口以构造和装配该产品的各个部件，
 * 定义并明确它所创建的表示，并提供一个检索产品的接口。
 * Created by 7le on 2017/5/11 0011.
 */
public class MinGukBuilder implements BabyBuilder{

    MinGuk minGuk;

    public MinGukBuilder() {
        minGuk = new MinGuk();
    }

    @Override
    public void buildSmile() {
        System.out.println("让民国笑~");
    }

    @Override
    public void buildMoe() {
        System.out.println("让民国卖萌~");
    }

    @Override
    public MinGuk BabyBuilder() {
        return minGuk;
    }
}
