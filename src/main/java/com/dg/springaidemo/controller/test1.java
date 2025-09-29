package com.dg.springaidemo.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class test1 {

    public static void main(String[] args) {
        // 基于java实现的网络通信的代码
        // 我是收货员
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            while(true){
                Socket accept = serverSocket.accept();
                // 异步处理（等待到达我的送餐地点）
                new ServerThread(accept).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
class ServerThread extends Thread{

    private Socket socket;
    public ServerThread(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
            String clientMsg;
            while((clientMsg = in.readLine()) != null){
                System.out.println("收到客户端信息：" + clientMsg);
                out.println("外卖我已收到！");
                if("bye".equalsIgnoreCase(clientMsg)){
                    out.print("拜拜");
                    break;
                }
            }
            in.close();
            out.close();
            socket.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
