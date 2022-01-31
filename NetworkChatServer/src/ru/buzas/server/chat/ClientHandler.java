package ru.buzas.server.chat;

import ru.buzas.clientserver.Command;
import ru.buzas.clientserver.CommandType;
import ru.buzas.clientserver.commands.AuthCommandData;
import ru.buzas.clientserver.commands.PrivateMessageCommandData;
import ru.buzas.clientserver.commands.PublicMessageCommandData;

import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ClientHandler {

    private MyServer server;
    private final Socket clientSocket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private String username;
    private boolean interruptRun = false;
    private boolean isInterrupted = false;
    private boolean isAuthOK = false;

    public ClientHandler(MyServer myServer, Socket clientSocket) {
        this.server = myServer;
        this.clientSocket = clientSocket;
    }

    public void handle() throws IOException {
        outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        inputStream = new ObjectInputStream(clientSocket.getInputStream());

        new Thread(() -> {
            try {
                authenticate();
                readMessages();
            } catch (IOException e) {
                System.err.println("Failed to process client message");
                e.printStackTrace();
            } finally {
                try {
                    closeConnection();
                } catch (IOException e) {
                    System.err.println("Failed to close connection");
                    e.printStackTrace();
                }
            }
        }).start();


    }

    private void authenticate() throws IOException {
        while (true) {
            Timer interruptTimer = new Timer();
            TimerTask interruptTask = new TimerTask() {
                @Override
                public void run() {
                    try {
                        if (!isAuthOK && !interruptRun){
                            System.out.println("InterruptTimer start work at " + new Date());
                            interruptRun = true;
                            Thread.sleep(120000);
                            if (!isInterrupted && !isAuthOK) {
                                isInterrupted = true;
                                System.out.println("Client disconnected at" + new Date());
                            } else {
                                cancel();
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };

            interruptTimer.schedule(interruptTask, 1000);
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
                    sendCommand(Command.errorCommand("Incorrect login or password"));
                } else if (server.isUsernameBusy(userName)) {
                    sendCommand(Command.errorCommand("User already online"));
                } else if (isInterrupted) {
                    sendCommand(Command.errorCommand("Connection time expired(2 min)"));
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
            System.err.println("Failed to read command class");
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
                }
            }
        }
    }

    private void processMessage(String message) throws IOException {
        System.out.println("clientMessageCommand: " + message);
        this.server.broadcastMessage(message, this);
    }

    private void closeConnection() throws IOException {
        isAuthOK = false;
        clientSocket.close();
        server.unsubscribe(this);
        server.getAuthService().setOnlineAccess(username, false);
    }

    public String getUsername() {
        return username;
    }
}
