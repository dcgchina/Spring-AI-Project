package com.dg.springaidemo.controller;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

import static java.io.FileDescriptor.out;

public class test2 {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1",8080);
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(),true);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String serverClient;
            while((serverClient = stdIn.readLine()) != null){
                System.out.println("用户输入：" + serverClient);
                printWriter.print(serverClient);

                String response = bufferedReader.readLine();
                System.out.println("服务器回复：" + response);

                if("拜拜".equalsIgnoreCase(response)){
                    break;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
