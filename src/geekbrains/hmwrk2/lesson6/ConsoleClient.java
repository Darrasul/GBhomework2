package geekbrains.hmwrk2.lesson6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ConsoleClient {

    public static final String SERVER_HOST = "localhost";
    public static final int SERVER_PORT = 8189;

    public static void main(String[] args) {
        try (Socket clientSocket = new Socket(SERVER_HOST, SERVER_PORT)) {
            DataInputStream input = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());

            messageRead(input);
            System.out.println("You can chat now");
            messageSend(output);

        } catch (IOException e) {
            System.err.println("Connection failed!");
            e.printStackTrace();
        }

    }

    private static void messageRead(DataInputStream input) {
        Thread clientReadThread = new Thread(() -> {
            try {
                listenToTheServer(input);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        clientReadThread.setDaemon(true);
        clientReadThread.start();
    }

    private static void listenToTheServer(DataInputStream input) throws IOException {
        while (true){
            String serverMessage = input.readUTF();
            System.out.println("Server: " + "'" + serverMessage + "'");
        }
    }

    private static void messageSend(DataOutputStream output) throws IOException {
        Scanner clientType = new Scanner(System.in);
        while (true){
            String clientMessage = clientType.next();
            if (clientMessage.equals("/end")){
                break;

            }
            output.writeUTF(clientMessage);
            System.out.println("Client: " + "'" + clientMessage + "'");
        }
    }
}
