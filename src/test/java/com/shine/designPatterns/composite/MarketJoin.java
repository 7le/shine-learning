package com.shine.designPatterns.composite;

/**
 * 最底层的加盟店  （Leaf）
 * Created by 7le on 2017/6/16
 */
public class MarketJoin extends Market{

    public MarketJoin(String s) {
        this.name = s;
    }

    @Override
    public void add(Market m) {
        System.out.println(name + "不能添加加盟店");
    }

    @Override
    public void remove(Market m) {
        System.out.println(name + "不能移除加盟店");
    }

    @Override
    public void payByCard() {
        System.out.println(name + "消费,积分已累加入该会员卡");
    }
}
