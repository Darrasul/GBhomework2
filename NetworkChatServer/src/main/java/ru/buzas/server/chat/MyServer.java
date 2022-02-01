package ru.buzas.server.chat;

import ru.buzas.clientserver.Command;
import ru.buzas.server.chat.auth.AuthInterface;
import ru.buzas.server.chat.auth.AuthService;
import ru.buzas.server.chat.auth.DataBaseAuthService;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MyServer {

    private final List<ClientHandler> clients = new ArrayList<>();
    private AuthInterface authService;

    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Server has been started");
            authService = createAuthService();
            authService.start();

            while (true){
                waitForClientConnectionAndProcess(serverSocket);
            }

            
        } catch (IOException e){
            System.err.println("Failed to bind port " + port);
        } finally {
            if (authService != null){
                authService.stop();
            }
        }
    }

    private AuthInterface createAuthService() {
//        return new AuthService();
        return new DataBaseAuthService();
    }

    private void waitForClientConnectionAndProcess(ServerSocket serverSocket) throws IOException {
        System.out.println("Waiting for new connections");
        Socket clientSocket = serverSocket.accept();

        System.out.println("Client has been connected");
        ClientHandler clientHandler = new ClientHandler(this ,clientSocket);

        clientHandler.handle();
    }

    public synchronized void broadcastMessage(String message, ClientHandler sender) throws IOException {
        for (ClientHandler client: clients){
            if (client != sender){
                String login = client.getLogin();
                recordMessage(message, sender.getUsername(), login);
                client.sendCommand(Command.clientMessageCommand(sender.getUsername(), message));
            }
        }
    }

    public synchronized void sendPrivateMessage(ClientHandler sender, String recipient, String privateMessage) throws IOException {
        for (ClientHandler client: clients){
            if (client != sender && client.getUsername().equals(recipient)){
                String login = client.getLogin();
                recordMessage(privateMessage, sender.getUsername(), login);
                client.sendCommand(Command.clientMessageCommand(sender.getUsername(), privateMessage));
            }
        }
    }

    private void recordMessage(String message, String sender, String login) {
        String historyLogsPath = "NetworkChatServer/src/main/java/ru/buzas/server/chat/messages/history_"+ login + ".txt";
        try (PrintWriter writer = new PrintWriter(new FileWriter(historyLogsPath, true))) {
            Date date = new Date();
//            writer.printf("%s(%s): %s", sender, date, message);
            writer.write(sender + "(" + date + "): " + message + "\n");
        } catch (IOException e) {
            System.err.println("Unable to record message");
            e.printStackTrace();
        }
    }

    public synchronized void viewRecords(ClientHandler client, String login) {
        String historyLogsPath = "NetworkChatServer/src/main/java/ru/buzas/server/chat/messages/history_"+ login + ".txt";
        try (Scanner scanner = new Scanner(new FileReader(historyLogsPath))) {
            for (int i = 100; i >= 0; i--) {
                if (scanner.hasNext()){
                    client.sendCommand(Command.clientMessageCommand("Архив", scanner.nextLine()));
                } else {
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Unable to read records");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Failed to connect to server while view records");
            e.printStackTrace();
        }
    }

    public synchronized boolean isUsernameBusy(String username){
        for (ClientHandler client: clients){
            if (client.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    public void notifyUsersAboutUserList() throws IOException {
        List<String> userListOnline = new ArrayList<>();

        for (ClientHandler client : clients) {
            userListOnline.add(client.getUsername());
        }

        for (ClientHandler client : clients) {
            client.sendCommand(Command.updateUserListCommand(userListOnline));
        }
    }

    public synchronized void subscribe(ClientHandler clientHandler) throws IOException {
        this.clients.add(clientHandler);
        notifyUsersAboutUserList();
    }

    public synchronized void unsubscribe(ClientHandler clientHandler) throws IOException {
        this.clients.remove(clientHandler);
        notifyUsersAboutUserList();
    }

    public AuthInterface getAuthService() {
        return authService;
    }
}
