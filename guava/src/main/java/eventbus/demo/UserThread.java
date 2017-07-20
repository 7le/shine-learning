package eventbus.demo;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 综合实例
 */
public class UserThread extends Thread {

    private Socket connection;
    private EventBus channel;
    private BufferedReader in;
    private PrintWriter out;

    public UserThread(Socket connection, EventBus channel) {
        this.connection = connection;
        this.channel = channel;

        try {
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            out = new PrintWriter(connection.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Subscribe
    public void receiveMessage(String message) {
        if (out != null){
            out.println(message);
            System.out.println("receiveMessage: "+message);
        }
    }

    @Override
    public void run() {
        String input;
        try {
            while ((input=in.readLine())!=null){
                channel.post(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        channel.unregister(this);
        try {
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        in = null;
        out = null;
    }
}
