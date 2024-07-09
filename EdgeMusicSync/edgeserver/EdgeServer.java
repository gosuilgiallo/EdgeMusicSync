package edgeserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class EdgeServer {
    private static final int PORT = 5000;
    private static final int BUFFER_SIZE = 4096;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Edge server started, waiting for clients...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (Socket centralSocket = new Socket("central-server-service", PORT);
             InputStream clientIn = clientSocket.getInputStream();
             OutputStream centralOut = centralSocket.getOutputStream();
             InputStream centralIn = centralSocket.getInputStream();
             OutputStream clientOut = clientSocket.getOutputStream()) {

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;

            // Invia l'audio ricevuto dal client al server centrale
            while ((bytesRead = clientIn.read(buffer)) != -1) {
                centralOut.write(buffer, 0, bytesRead);
            }

            // Riceve l'audio mixato dal server centrale e lo inoltra al client
            while ((bytesRead = centralIn.read(buffer)) != -1) {
                clientOut.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
