package com.shine.designPatterns.builder;

/**
 * 构造一个使用Builder接口的对象，指导构建过程。
 * Created by 7le on 2017/5/11 0011.
 */
public class BabyDirector {

    public Baby constructBaby(BabyBuilder bb) {

        bb.buildSmile();

        bb.buildMoe();

        return bb.BabyBuilder();
    }

    /**
     * 建造者模式和装饰模式很像，
     * 建造者模式：对象内部构建间的建造顺序通常是稳定的
     * 装饰模式：顺序是可变的，职能不固定，可以随意添加或者减免
     */
    public static void main(String[] args) {
        BabyDirector pd = new BabyDirector();
        Baby baby = pd.constructBaby(new MinGukBuilder());
    }
}
