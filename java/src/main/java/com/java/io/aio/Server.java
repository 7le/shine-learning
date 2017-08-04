package com.java.io.aio;

/**
 * 服务端
 */
public class Server {

    private static int DEFAULT_PORT = 5894;
    private static AsyncServerHandler serverHandle;
    public volatile static long clientCount = 0;
    protected static void start(){
        start(DEFAULT_PORT);
    }

    private static synchronized void start(int port){
        if(serverHandle!=null){
            return;
        }
        serverHandle = new AsyncServerHandler(port);
        new Thread(serverHandle,"Server").start();
    }

    public static void main(String[] args) {
        Server.start();
    }
}
