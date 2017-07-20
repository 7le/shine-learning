package eventbus.demo;

import com.google.common.eventbus.EventBus;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class EventBusChat {

    //用telnet命令登录：telnet 127.0.0.1 4444,可以多实例发送接受
    public static void main(String[] args) {
        EventBus channel = new EventBus();
        ServerSocket socket;

        try {
            socket = new ServerSocket(4444);
            while(true){
                Socket connection=socket.accept();
                UserThread userThread=new UserThread(connection,channel);
                channel.register(userThread);
                userThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
