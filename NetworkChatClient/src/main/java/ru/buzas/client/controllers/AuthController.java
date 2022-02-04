package ru.buzas.client.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ru.buzas.client.ClientChat;
import ru.buzas.client.dialogs.Dialogs;
import ru.buzas.client.model.Network;
import ru.buzas.client.model.ReadCommandListener;
import ru.buzas.clientserver.Command;
import ru.buzas.clientserver.CommandType;
import ru.buzas.clientserver.commands.AuthOKCommandData;
import ru.buzas.clientserver.commands.ErrorCommandData;

import java.io.IOException;

public class AuthController {
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button authButton;

    private ReadCommandListener readCommandListener;

    @FXML
    public void executeAuth(ActionEvent actionEvent) {
        String login = loginField.getText();
        String password = passwordField.getText();

        if (login == null || login.isBlank() || password == null || password.isBlank()) {
            Dialogs.AuthError.EMPTY_CREDENTIALS.show();
            return;
        }

        if (!connectToServer()) {
            Dialogs.NetworkError.SERVER_CONNECT.show();
        }

        try {
            Network.getInstance().sendAuthMessage(login, password);
        } catch (IOException e) {
            Dialogs.NetworkError.SEND_MESSAGE.show();
            e.printStackTrace();
        }
    }

    private boolean connectToServer() {
        Network network = Network.getInstance();
        return network.isConnected() || network.connect();
    }

    public void initializeMessageHandler() {
        readCommandListener = getNetwork().addReadMessageListener(new ReadCommandListener() {
            @Override
            public void processReceivedMessage(Command command) {
                if (command.getType() == CommandType.AUTH_OK) {
                    AuthOKCommandData data = (AuthOKCommandData) command.getData();
                    String userName = data.getUserName();
                    Network.getInstance().setCurrentUserName(userName);
                    Platform.runLater(() -> {
                        ClientChat.INSTANCE.switchToMainChatWindow(userName);
                    });
                } else if (command.getType() == CommandType.ERROR) {
                    ErrorCommandData data = (ErrorCommandData) command.getData();
                    Platform.runLater(() -> {
                        Dialogs.AuthError.INVALID_CREDENTIALS.show(data.getErrorMessage());
                    });
                }
            }
        });
    }

    public void close(){
        getNetwork().removeMessageListener(readCommandListener);
    }

    private Network getNetwork() {
        return Network.getInstance();
    }
}
