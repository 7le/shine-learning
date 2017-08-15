package com.java.lambda;

/**
 * Lambda - 启动线程
 * Created by 7le on 2017/8/15 .
 */
public class LambdaRunnable {

    private static int a=5;
    private static int b=5;

    public static void main(String[] args) {

        //正常启动线程
        Thread thread =new Thread(new Runnable() {
            @Override
            public void run() {
                a++;
                System.out.println(a);
            }
        });

        //Lambda
        Thread lamdba=new Thread(() ->{
            b++;
            System.out.println(b);
        });

        thread.start();
        lamdba.start();
        System.out.println("Done!");
    }

}
