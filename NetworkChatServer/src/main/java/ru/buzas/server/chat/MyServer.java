package ru.buzas.server.chat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.buzas.clientserver.Command;
import ru.buzas.server.chat.auth.AuthInterface;
import ru.buzas.server.chat.auth.DataBaseAuthService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MyServer {

    private static final Logger LOGGER = LogManager.getLogger(MyServer.class);
    private final List<ClientHandler> clients = new ArrayList<>();
    private AuthInterface authService;

    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)){
            LOGGER.info("Server has been started");
            authService = createAuthService();
            authService.start();

            while (true){
                waitForClientConnectionAndProcess(serverSocket);
            }

            
        } catch (IOException e){
            LOGGER.error("Failed to bind port {}", port, new IOException("IOException"));
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
        LOGGER.info("Waiting for new connections");
        Socket clientSocket = serverSocket.accept();

        LOGGER.info("Client has been connected");
        ClientHandler clientHandler = new ClientHandler(this ,clientSocket);

        clientHandler.handle();
    }

    public synchronized void broadcastMessage(String message, ClientHandler sender) throws IOException {
        for (ClientHandler client: clients){
            if (client != sender){
                client.sendCommand(Command.clientMessageCommand(sender.getUsername(), message));
            }
        }
    }

    public synchronized void sendPrivateMessage(ClientHandler sender, String recipient, String privateMessage) throws IOException {
        for (ClientHandler client: clients){
            if (client != sender && client.getUsername().equals(recipient)){
                client.sendCommand(Command.clientMessageCommand(sender.getUsername(), privateMessage));
            }
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
