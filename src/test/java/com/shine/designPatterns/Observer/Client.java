package com.shine.designPatterns.Observer;


/**
 * 入口
 * Created by 7le on 2017/6/7
 */
public class Client {

    public static void main(String[] args) {
        EventSource eventSource = new EventSource();

        eventSource.addListener(new MyListener() {
            @Override
            public void handleEvent(MyEvent event) {
                event.doEvent();
                if(event.getSource().equals("close")){
                    System.out.println("No1: close......");
                }

            }
        });

        eventSource.notifyListenerEvents(new MyEvent("start"));

        eventSource.onClose(new MyListener() {
            @Override
            public void handleEvent(MyEvent event) {
                event.doEvent();
                if(event.getSource().equals("close")){
                    System.out.println("No2: close......");
                }
            }
        });

        eventSource.doClose();
    }
}
