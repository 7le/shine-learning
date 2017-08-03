package com.java.io.nio;

import java.util.Scanner;

/**
 * NIO客户端
 */
public class Client {

    private static String DEFAULT_HOST = "127.0.0.1";
    private static int DEFAULT_PORT = 5894;
    private static ClientHandle clientHandle;

    protected static void start(){
        start(DEFAULT_HOST,DEFAULT_PORT);
    }

    private static synchronized void start(String ip,int port){
        if(clientHandle!=null){
            clientHandle.stop();
        }
        clientHandle = new ClientHandle(ip,port);
        new Thread(clientHandle,"Server").start();
    }

    //向服务器发送消息
    public static boolean sendMsg(String msg) throws Exception{
        if(msg.equals("end")){
            return false;
        }
        clientHandle.sendMsg(msg);
        return true;
    }

    public static void main(String[] args) throws Exception {
        start();
        while(Client.sendMsg(new Scanner(System.in).nextLine()));
    }
}
