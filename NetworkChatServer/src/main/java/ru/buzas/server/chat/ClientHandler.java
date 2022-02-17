package ru.buzas.server.chat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.buzas.clientserver.Command;
import ru.buzas.clientserver.CommandType;
import ru.buzas.clientserver.commands.AuthCommandData;
import ru.buzas.clientserver.commands.PrivateMessageCommandData;
import ru.buzas.clientserver.commands.PublicMessageCommandData;
import ru.buzas.clientserver.commands.UpdateUsernameCommandData;

import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ClientHandler {

    private MyServer server;
    private final Socket clientSocket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private String username;
    private boolean interruptRun = false;
    private boolean isInterrupted = false;
    private boolean isAuthOK = false;
    ExecutorService handlingPool = Executors.newCachedThreadPool();
    private final static Logger LOGGER = LogManager.getLogger(ClientHandler.class);

    public ClientHandler(MyServer myServer, Socket clientSocket) {
        this.server = myServer;
        this.clientSocket = clientSocket;
    }

    public void handle() throws IOException {
        outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        inputStream = new ObjectInputStream(clientSocket.getInputStream());
//        Тут кажется CachedThreadPool
        handlingPool.execute(() -> {
            try {
                ScheduledExecutorService interruptionTimer = Executors.newSingleThreadScheduledExecutor();
                if (!interruptRun){
//                    System.out.println("Interruption timer is on");
                    LOGGER.info("Interruption timer is on");
                    interruptionTimer.schedule(() -> {
                        if (!isAuthOK){
                            isInterrupted = true;
//                            System.out.println("Client disconnected at" + new Date());
                            LOGGER.info("Client disconnected at {}", new Date());
                        }
                    }, 2, TimeUnit.MINUTES);
                }
                authenticate();
                readMessages();
            } catch (IOException e) {
//                System.err.println("Failed to process client message");
                LOGGER.error("Failed to process client message", new IOException("IOException"));
                e.printStackTrace();
            } finally {
                try {
                    closeConnection();
                } catch (IOException e) {
//                    System.err.println("Failed to close connection");
                    LOGGER.error("Failed to close connection", new IOException("IOException"));
                    e.printStackTrace();
                }
            }
        });


    }

    private void authenticate() throws IOException {
        while (true) {
            Command command = readCommand();

            if (command == null) {
                continue;
            }

            if (command.getType() == CommandType.AUTH) {
                AuthCommandData data = (AuthCommandData) command.getData();
                String login = data.getLogin();
                String password = data.getPassword();
                String userName = server.getAuthService().getUserNameByLoginAndPassword(login, password);

                if (userName == null) {
                    sendCommand(Command.errorCommand("Неверные имя или пароль"));
                } else if (server.isUsernameBusy(userName)) {
                    sendCommand(Command.errorCommand("Пользователь уже в сети"));
                } else if (isInterrupted) {
                    sendCommand(Command.errorCommand("Время на подключение исчерпано(2 мин)"));
                    return;
                } else {
                    isAuthOK = true;
                    this.username = userName;
                    sendCommand(Command.authOKCommand(userName));
                    server.subscribe(this);
                    server.getAuthService().setOnlineAccess(userName, true);

                    return;
                }
            }


        }
    }

    public void sendCommand(Command command) throws IOException {
        outputStream.writeObject(command);
    }

    private Command readCommand() throws IOException {
        Command command = null;
        try {
            command = (Command) inputStream.readObject();
        } catch (ClassNotFoundException e) {
//            System.err.println("Failed to read command class");
            LOGGER.error("Failed to read command class", new ClassNotFoundException("ClassNotFoundException"));
            e.printStackTrace();
        }
        return command;
    }

    private void readMessages() throws IOException {
        while (true) {
            Command command = readCommand();

            if (command == null) {
                continue;
            }

            switch (command.getType()) {
                case END: {
                    return;
                }
                case PRIVATE_MESSAGE: {
                    PrivateMessageCommandData privateData = (PrivateMessageCommandData) command.getData();
                    String receiver = privateData.getReceiver();
                    String privateMessage = privateData.getMessage();
                    server.sendPrivateMessage(this, receiver, privateMessage);
                    break;
                }
                case PUBLIC_MESSAGE: {
                    PublicMessageCommandData publicData = (PublicMessageCommandData) command.getData();
                    processMessage(publicData.getMessage());
                    break;
                }
                case UPDATE_USERNAME: {
                    UpdateUsernameCommandData data = (UpdateUsernameCommandData) command.getData();
                    String newUsername = data.getUsername();
                    LOGGER.info("Username change from {} to {}", username, newUsername);
                    server.getAuthService().updateUsername(username, newUsername);
                    username = newUsername;
                    server.notifyUsersAboutUserList();
                    break;
                }
            }
        }
    }

    private void processMessage(String message) throws IOException {
        LOGGER.info("clientMessageCommand: {}", message);
        this.server.broadcastMessage(message, this);
    }

    private void closeConnection() throws IOException {
        isAuthOK = false;
        handlingPool.shutdownNow();
        clientSocket.close();
        server.unsubscribe(this);
        server.getAuthService().setOnlineAccess(username, false);
    }

    public String getUsername() {
        return username;
    }
}
