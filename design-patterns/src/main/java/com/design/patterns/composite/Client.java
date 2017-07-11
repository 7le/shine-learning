package com.design.patterns.composite;

/**
 * 入口
 * Created by 7le on 2017/6/16
 */
public class Client {

    /**
     * 什么时候使用?  引用大话模式里的话:
     *
     * 当发现需求中是体现部分与整体层次结构时，
     * 以及你希望用户可以忽略组合对象与单个对象的不同，
     * 统一地使用组合结构中的所有对象时，就应该考虑组合模式了。
     * @param args
     */

    public static void main(String[] args) {
        MarketBranch market=new MarketBranch("总店");

        MarketBranch marketBranch = new MarketBranch("杭州分店");
        MarketBranch marketBranch1 = new MarketBranch("温州分店");

        MarketJoin marketJoin = new MarketJoin("西湖分店");


        marketBranch.add(marketJoin);
        market.add(marketBranch1);
        market.add(marketBranch);

        //在总店消费
        market.payByCard();
    }
}
