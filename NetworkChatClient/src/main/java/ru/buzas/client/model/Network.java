package ru.buzas.client.model;

import ru.buzas.clientserver.Command;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Network {
    public static final String SERVER_HOST = "localhost";
    public static final int SERVER_PORT = 8189;

    private int port;
    private String host;
    private Socket socket;
    private ObjectInputStream socketInput;
    private ObjectOutputStream socketOutput;

    private final List<ReadCommandListener> listeners = new CopyOnWriteArrayList<>();
    private static Network INSTANCE;
    private boolean connected;
    private Thread readMessageProcess;

    public static Network getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new Network();
        }
        return INSTANCE;
    }

    private Network(int port, String host) {
        this.port = port;
        this.host = host;
    }

    private Network() {
        this(SERVER_PORT, SERVER_HOST);
    }

    public boolean connect() {
        try {
            socket = new Socket(this.host, this.port);
            socketInput = new ObjectInputStream(socket.getInputStream());
            socketOutput = new ObjectOutputStream(socket.getOutputStream());
            readMessageProcess = startReadMessageProcess();

            connected = true;
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Не удалось установить соединение");

            return false;
        }
    }

    public void sendMessage(String message) throws IOException {
        System.out.println("Public message: " + message);
        sendCommand(Command.publicMessageCommand(message));
    }

    public void sendCommand(Command command) throws IOException {
        try {
            socketOutput.writeObject(command);
        } catch (IOException e) {
            System.err.println("Не удалось отправить команду на сервер");
            throw e;
        }
    }

    public void changeUsername(String newUsername) throws IOException {
        sendCommand(Command.updateUsernameCommand(newUsername));
    }

    public void sendPrivateMessage(String receiver, String message) throws IOException {
        sendCommand(Command.privateMessageCommand(receiver, message));
    }

    public void sendAuthMessage(String login, String password) throws IOException {
        sendCommand(Command.authCommand(login, password));
    }

    public Thread startReadMessageProcess() {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    if (Thread.currentThread().isInterrupted()) {
                        return;
                    }
                    Command command = readCommand();
                    if (command.getType() == null){
                        continue;
                    }
                    for (ReadCommandListener messageListener : listeners){
                        messageListener.processReceivedMessage(command);
                    }

                } catch (IOException e) {
                    System.err.println("Не удалось прочитать сообщение от сервера");
                    e.printStackTrace();
                    close();
                    break;
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
        return thread;
    }

    private Command readCommand() throws IOException {
        Command command = null;

        try {
            command = (Command) socketInput.readObject();
        } catch (ClassNotFoundException e) {
            System.err.println("Failed to read command class");
            e.printStackTrace();
        }

        return command;
    }

    public ReadCommandListener addReadMessageListener(ReadCommandListener listener){
        listeners.add(listener);
        return listener;
    }

    public void removeMessageListener(ReadCommandListener listener){
        listeners.remove(listener);
    }

    public void close() {
        try {
            connected = false;
            readMessageProcess.interrupt();
            this.socket.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public boolean isConnected() {
        return connected;
    }

}
