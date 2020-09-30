package main.com.too.example.phase2.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

// 同步阻塞的
public class TimeServer {

    public static void main(String[] args) {
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("the time server is start in port: " + port);
            Socket socket = null;
            // 无限循环监听客户端连接
            while (true) {
                // 阻塞accept
                socket = serverSocket.accept();
                new Thread(new TimeServerHandler(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (serverSocket != null) {
                System.out.println("the time server close");
                try {
                    serverSocket.close();
                    serverSocket = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
