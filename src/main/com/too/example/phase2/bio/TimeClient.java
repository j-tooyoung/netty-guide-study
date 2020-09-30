package main.com.too.example.phase2.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

//
public class TimeClient {

    public static void main(String[] args) {
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        // 便于关闭
        Socket socket = null;
        // 客户端通过PrintWriter向服务端发送Query TIME ORDER指令
        // 通过BufferReader读取响应并打印输出
        BufferedReader bufferedReader = null;
        PrintWriter writer = null;
        try {
            socket = new Socket("127.0.0.1", port);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println("QUERY TIME ORDER");
            System.out.println("Send order 2 server succeed.");
            String response = bufferedReader.readLine();
            System.out.println("Now is : " + response);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                socket = null;
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (writer != null) {
                try {
                    writer.close( );
                } catch (Exception e) {
                    e.printStackTrace();
                }
                writer = null;
            }
        }

        // 放在try里没法关闭socket
//        try {
//            Socket socket = null;
//        } catch (IOException E) {
//
//        } finally {
//            socke
//        }
    }

}
