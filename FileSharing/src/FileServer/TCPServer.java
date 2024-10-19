/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FileServer;

/**
 *
 * @author huynh
 */
import java.io.*;
import java.net.*;

public class TCPServer {
    public static void main(String[] args) {
        try {
            // Khởi động server trên cổng 6789
            ServerSocket welcomeSocket = new ServerSocket(6789);

            while (true) {
                // Chấp nhận kết nối từ client
                Socket connectionSocket = welcomeSocket.accept();
                
                // Tạo luồng đọc dữ liệu từ client
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                // Tạo luồng gửi dữ liệu cho client
                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

                // Nhận thông tin từ client (username và password)
                String username = inFromClient.readLine();
                String password = inFromClient.readLine();

                // Kiểm tra thông tin đăng nhập (ví dụ: username = "admin", password = "12345")
                if (username.equals("admin") && password.equals("12345")) {
                    outToClient.writeBytes("Login Successful\n");
                } else {
                    outToClient.writeBytes("Login Failed\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
