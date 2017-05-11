package com.shine.designPatterns.builder;

/**
 * 为创建一个产品对象的各个部件指定抽象接口。
 * Created by 7le on 2017/5/11 0011.
 */
public interface BabyBuilder {

    void buildSmile();

    void buildMoe();

    MinGuk BabyBuilder();
}
