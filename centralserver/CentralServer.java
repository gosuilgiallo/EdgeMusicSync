package centralserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class CentralServer {
    private static final int PORT = 5000;
    private static final int BUFFER_SIZE = 4096;
    private static List<Socket> clients = new ArrayList<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Central server started, waiting for clients...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                clients.add(clientSocket);
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (InputStream clientIn = clientSocket.getInputStream();
             OutputStream clientOut = clientSocket.getOutputStream()) {

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = clientIn.read(buffer)) != -1) {
                synchronized (clients) {
                    for (Socket socket : clients) {
                        if (!socket.equals(clientSocket)) {
                            OutputStream out = socket.getOutputStream();
                            out.write(buffer, 0, bytesRead);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
