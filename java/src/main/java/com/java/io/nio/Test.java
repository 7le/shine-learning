package com.java.io.nio;

import java.util.Scanner;

/**
 * 入口
 */
public class Test {

    public static void main(String[] args) throws Exception {
        Server.start();
        Thread.sleep(100);
        Client.start();
        while(Client.sendMsg(new Scanner(System.in).nextLine()));
    }
}
