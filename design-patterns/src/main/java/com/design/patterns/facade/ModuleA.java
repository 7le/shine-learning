package com.design.patterns.facade;

/**
 * A模块
 * Created by 7le on 2017/5/10 0010.
 */
public class ModuleA {
    /**
     * 提供给子系统外部使用的方法
     */
    public void a1(){
        System.out.println("调用ModuleA中的a1方法");
    };

    /**
     * 子系统内部模块之间相互调用时使用的方法
     */
    private void a2(){};
    private void a3(){};
}
