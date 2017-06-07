package com.shine.designPatterns.Observer;

import java.util.EventListener;

/**
 * 事件监听器
 * Created by 7le on 2017/6/7
 */
public interface MyListener extends EventListener {

    //事件处理
    void handleEvent(MyEvent event);
}
