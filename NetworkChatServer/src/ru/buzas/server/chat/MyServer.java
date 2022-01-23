package ru.buzas.server.chat;

import ru.buzas.server.chat.auth.AuthService;
import ru.buzas.server.chat.auth.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MyServer {

    private final List<ClientHandler> clients = new ArrayList<>();
    private AuthService authService;

    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Server has been started");
            authService = new AuthService();

            while (true){
                waitForClientConnectionAndProcess(serverSocket);
            }

            
        } catch (IOException e){
            System.err.println("Failed to bind port " + port);
        }
    }

    private void waitForClientConnectionAndProcess(ServerSocket serverSocket) throws IOException {
        System.out.println("Waiting for new connections");
        Socket clientSocket = serverSocket.accept();

        System.out.println("Client has been connected");
        ClientHandler clientHandler = new ClientHandler(this ,clientSocket);

        clientHandler.handle();
    }

    public void broadcastMessage(String message, ClientHandler sender) throws IOException {
        for (ClientHandler client: clients){
            if (client != sender && !message.startsWith("/w")){
                client.sendMessage(message);
            } else if (message.startsWith("/w " + client.getUserName())){
                client.sendMessage(message);
            }
        }
    }

    public void subscribe(ClientHandler clientHandler){
        this.clients.add(clientHandler);
    }

    public void unsubscribe(ClientHandler clientHandler){
        this.clients.remove(clientHandler);
    }

    public AuthService getAuthService() {
        return authService;
    }
}
