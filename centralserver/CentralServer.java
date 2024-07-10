package centralserver;

import java.io.*;
import java.net.*;

public class CentralServer {
    private static final int BUFFER_SIZE = 256;

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("Usage: java CentralServer <port1> <port2>");
            System.exit(1);
        }

        int port1 = Integer.parseInt(args[0]);
        int port2 = Integer.parseInt(args[1]);

        ServerSocket serverSocket1 = new ServerSocket(port1);
        ServerSocket serverSocket2 = new ServerSocket(port2);

        System.out.println("Central Server started, waiting for edge servers...");

        Socket edgeSocket1 = serverSocket1.accept();
        Socket edgeSocket2 = serverSocket2.accept();
        InputStream edgeInputStream1 = edgeSocket1.getInputStream();
        InputStream edgeInputStream2 = edgeSocket2.getInputStream();

        // the mix of the sound is calculating the average
        byte[] buffer1 = new byte[BUFFER_SIZE];
        byte[] buffer2 = new byte[BUFFER_SIZE];
        byte[] mixedBuffer = new byte[BUFFER_SIZE];

        while (true) {
            int bytesRead1 = edgeInputStream1.read(buffer1);
            int bytesRead2 = edgeInputStream2.read(buffer2);

            if (bytesRead1 == -1 || bytesRead2 == -1) break;

            for (int i = 0; i < BUFFER_SIZE; i++) {
                mixedBuffer[i] = (byte) ((buffer1[i] + buffer2[i]) / 2);
            }

            // send the audio to edge servers
            for (Socket socket : new Socket[] {edgeSocket1, edgeSocket2}) {
                OutputStream outputStream = socket.getOutputStream();
                outputStream.write(mixedBuffer, 0, BUFFER_SIZE);
            }
        }

        edgeSocket1.close();
        edgeSocket2.close();
    }
}
