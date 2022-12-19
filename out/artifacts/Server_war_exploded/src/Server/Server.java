package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        int port = 8080;
        try (ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Server on port: " + port);
            try (Socket socket = serverSocket.accept()) {
                System.out.println("Accept");
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                Thread.sleep(10);

                String text = "";
                while (reader.ready()) {
                    text += reader.readLine();
                }
                System.out.println(text);

                /*String s = reader.readLine();
                while (s != null) {
                    text += s + "\n";
                    s = reader.readLine();
                }
                System.out.println(text);*/

                String toClientText = text.toUpperCase();

                PrintWriter writer = new PrintWriter(outputStream, true);
                writer.println(toClientText);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
