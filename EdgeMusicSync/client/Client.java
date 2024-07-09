package client;

import javax.sound.sampled.*;
import java.io.*;
import java.net.Socket;

public class Client {
    private static final String SERVER_ADDRESS = "edge-server-service";
    private static final int SERVER_PORT = 5000;
    private static final int BUFFER_SIZE = 4096;

    public static void main(String[] args) {
        new Thread(Client::sendAudioFromMicrophone).start();
        new Thread(Client::receiveAudio).start();
    }

    private static void sendAudioFromMicrophone() {
        AudioFormat format = new AudioFormat(44100, 16, 2, true, true);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
        try (TargetDataLine microphone = (TargetDataLine) AudioSystem.getLine(info)) {
            microphone.open(format);
            microphone.start();

            try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                 OutputStream os = socket.getOutputStream()) {

                byte[] buffer = new byte[BUFFER_SIZE];
                int bytesRead;
                while ((bytesRead = microphone.read(buffer, 0, buffer.length)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
            }
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void receiveAudio() {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             InputStream is = socket.getInputStream()) {

            AudioFormat format = new AudioFormat(44100, 16, 2, true, true);
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                line.write(buffer, 0, bytesRead);
            }
            line.drain();
            line.close();
        } catch (IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
