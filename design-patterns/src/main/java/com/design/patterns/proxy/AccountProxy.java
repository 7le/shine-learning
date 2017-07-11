package com.design.patterns.proxy;

/**
 * 代理类（增强AccountImpl的功能）静态代理
 * Created by 7le on 2017/5/4 0004.
 */
public class AccountProxy implements Account {

    private AccountImpl accountImpl;

    /**
     * 重写默认构造函数
     * 静态代理。
     * 真实角色必须是事先已经存在的，并将其作为代理对象的内部属性
     */

    @Override
    public void queryAccount() {
        System.out.println("业务处理之前...");
        //延迟加载
        //在多线程环境下，这里返回一个虚假类，类似于 Future 模式
        if (accountImpl == null) {
            accountImpl = new AccountImpl();
        }
        accountImpl.queryAccount();
        System.out.println("业务处理之后...");
    }

    @Override
    public void updateAccount() {
        System.out.println("业务处理之前...");
        if (accountImpl == null) {
            accountImpl = new AccountImpl();
        }
        accountImpl.updateAccount();
        System.out.println("业务处理之后...");
    }

    public static void main(String[] args) {

        AccountProxy accountProxy = new AccountProxy(); //使用代理

        accountProxy.updateAccount(); //在真正使用时才创建真实对象
        accountProxy.queryAccount();
        //多个接口代理,就引入了动态代理,可以看spring的DynamicAopProxy
    }

}
