package FileServer;

import FileServer.Server;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author nguye
 */
public class FileServer implements Runnable {

    private final DataInputStream input;
    private final DataOutputStream output;

    public FileServer(Socket socket) throws IOException {
        this.input = new DataInputStream(socket.getInputStream());
        this.output = new DataOutputStream(socket.getOutputStream());
    }

    public void sendData(String message, byte[] fileData, String type, String fileName) {
        try {
            // Gửi loại dữ liệu
            output.writeUTF(type);

            if (type.equals("TEXT")) {
                // Gửi tin nhắn
                output.writeUTF(message);
            } else if (type.equals("FILE")) {
                // Gửi thông tin file
                output.writeUTF(fileName);
                output.writeLong(fileData.length);
                output.write(fileData);
            }
        } catch (IOException e) {
            System.err.println("Error sending data: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Đọc loại dữ liệu
                String type = input.readUTF(); // "TEXT" hoặc "FILE"

                if (type.equals("TEXT")) {
                    // Đọc tin nhắn
                    String message = input.readUTF();
                    System.out.println("Received message: " + message);
                    Server.broadcast(message, null, "TEXT", null); // Gửi tới các client khác
                } else if (type.equals("FILE")) {
                    // Đọc file
                    String fileName = input.readUTF();
                    long fileSize = input.readLong();
                    byte[] fileData = new byte[(int) fileSize];
                    input.readFully(fileData);

                    System.out.println("Received file: " + fileName);
                    // Gửi file tới các client khác
                    Server.broadcast(null, fileData, "FILE", fileName);
                }
            }
        } catch (IOException e) {
            System.err.println("Connection error: " + e.getMessage());
        }
    }
}
