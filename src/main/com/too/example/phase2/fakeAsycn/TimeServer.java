package main.com.too.example.phase2.fakeAsycn;

import main.com.too.example.phase2.bio.TimeServerHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

// 伪异步
// 避免为每个请求都创建独立线程， 造成资源耗尽
// 底层仍是同步阻塞模型
public class TimeServer {

    public static void main(String[] args) {
        int port = 8090;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        ServerSocket serverSocket = null;
        BufferedReader bufferedReader = null;
        PrintWriter writer = null;

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("The time server is in port: " + port);
            Socket socket = null;
            TimeServerHandlerExcutePool executePool = new TimeServerHandlerExcutePool(50,10000);
            // 无限循环监听客户端连接
            while (true) {
                socket = serverSocket.accept();
                executePool.execute(new TimeServerHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                serverSocket = null;
            }
        }

    }

}
