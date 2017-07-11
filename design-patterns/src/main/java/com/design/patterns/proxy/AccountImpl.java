package com.design.patterns.proxy;

/**
 * 接口实现类(包含业务逻辑)
 * Created by 7le on 2017/5/4 0004.
 */
public class AccountImpl implements Account{

    @Override
    public void queryAccount() {
        System.out.println("查询...");
    }

    @Override
    public void updateAccount() {
        System.out.println("更新...");
    }
}
