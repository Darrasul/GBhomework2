package geekbrains.hmwrk2.lesson6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ConsoleServer {

    static final int PORT = 8189;



    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server online");
            System.out.println("Server is waiting for clients");

            Socket clientSocket = serverSocket.accept();
            System.out.println("Client online");
            DataInputStream input = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());

            messageRead(input);
            System.out.println("You can chat now");
            messageSend(output);

        } catch (IOException e) {
            System.err.println("Server cant get port number " + PORT);
            e.printStackTrace();
        }

    }

    private static void messageSend(DataOutputStream output) throws IOException {
        Scanner typeMessage = new Scanner(System.in);
        while (true){
            String serverMessage = typeMessage.next();
            if (serverMessage.equals("/end")){
                break;
            }
            output.writeUTF(serverMessage);
            System.out.println("Server: " + "'" + serverMessage + "'");
        }
    }

    private static void messageRead(DataInputStream input) {
        Thread serverReadThread = new Thread(() -> {
            try {
                listenToTheClient(input);
            } catch (IOException e) {
                System.err.println("Something went terribly wrong with client");
                e.printStackTrace();
            }
        });
        serverReadThread.setDaemon(true);
        serverReadThread.start();
    }

    private static void listenToTheClient(DataInputStream input) throws IOException {
        while (true){
            String userMessage = input.readUTF();
            if (userMessage.equals("/end")){
                break;
            }
            System.out.println("User: " + "'" + userMessage + "'");
        }
    }
}
