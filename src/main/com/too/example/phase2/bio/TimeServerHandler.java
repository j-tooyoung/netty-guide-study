package main.com.too.example.phase2.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

public class TimeServerHandler implements Runnable {

    private Socket socket;

    public TimeServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader bufferedReader = null;
        PrintWriter writer = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            writer = new PrintWriter(this.socket.getOutputStream(), true);
            String currentTime = null;
            String content = null;
            while (true) {
                content = bufferedReader.readLine();
                if (content == null) {
                    break;
                }
                System.out.println("the time server receive order: " + content);
                currentTime = "QUERY TIME ORDER".equalsIgnoreCase(content) ? new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
                writer.println(currentTime);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            // 关闭输入和输出流和socket套接字
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (writer != null) {
                writer.close();
                writer = null;
            }
            if (this.socket != null) {
                try {
                    this.socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                this.socket = null;
            }
        }
    }
}
