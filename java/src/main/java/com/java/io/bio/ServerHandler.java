package com.java.io.bio;

import com.java.io.Calculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 客户端消息处理线程
 */
public class ServerHandler implements Runnable{

    private Socket socket;
    public ServerHandler(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        try{
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(),true);
            String expression;
            String result;
            while(true){
                //通过BufferedReader读取一行
                //如果已经读到输入流尾部，返回null,退出循环
                //如果得到非空值，就尝试计算结果并返回
                if((expression = in.readLine())==null) break;
                System.out.println("服务器收到消息：" + expression);
                try{
                    result = Calculator.cal(expression).toString();
                }catch(Exception e){
                    result = "计算错误：" + e.getMessage();
                }
                out.println(result);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            //一些必要的清理工作
            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                in = null;
            }
            if(out != null){
                out.close();
                out = null;
            }
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                socket = null;
            }
        }
    }
}
