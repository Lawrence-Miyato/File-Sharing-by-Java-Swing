/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FileClient;

/**
 *
 * @author admin
 */
import java.io.*;
import java.net.Socket;

public class FileClient {
    public static void sendFile(String hostname, int port, File file) {
        try (Socket socket = new Socket(hostname, port);
             OutputStream output = socket.getOutputStream();
             FileInputStream input = new FileInputStream(file)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
            System.out.println("File sent.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

