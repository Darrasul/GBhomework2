package ru.buzas.client.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.buzas.client.ClientChat;
import ru.buzas.client.dialogs.Dialogs;
import ru.buzas.client.model.Network;
import ru.buzas.client.model.ReadCommandListener;
import ru.buzas.client.service.ChatHistory;
import ru.buzas.clientserver.Command;
import ru.buzas.clientserver.CommandType;
import ru.buzas.clientserver.commands.ClientMessageCommandData;
import ru.buzas.clientserver.commands.UpdateUserListCommandData;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Optional;


public class ClientController {
    @FXML
    private TextArea textArea;
    @FXML
    private TextField textField;
    @FXML
    private Button sendButton;
    @FXML
    public ListView userList;

    private ClientChat application;
    private ChatHistory chatHistoryService;
    private static final int LAST_HISTORY_ROWS_NUMBER = 100;

    public void createChatHistory() {
        this.chatHistoryService = new ChatHistory(Network.getInstance().getCurrentUserName());
        chatHistoryService.init();
    }

    public void sendMessage() {

        String message = textField.getText().trim();

        if (message.isEmpty()) {
            textField.clear();
            return;
        }
        String sender = null;
        if (!userList.getSelectionModel().isEmpty()) {
            sender = (String) userList.getSelectionModel().getSelectedItem();
        }

        try {
            if (sender != null){
                Network.getInstance().sendPrivateMessage(sender, message);
            } else {
                Network.getInstance().sendMessage(message);
            }

        } catch (IOException e) {
            application.showErrorDialog("Ошибка передачи данных сети");
        }

        appendMessageToChat("Я", message);
    }

    private void appendMessageToChat(String sender, String message) {
        String currentText = textArea.getText();

        textArea.appendText(DateFormat.getDateTimeInstance().format(new Date()));
        textArea.appendText(System.lineSeparator());

        if (sender != null) {
            textArea.appendText(sender + ":");
            textArea.appendText(System.lineSeparator());
        }

        textArea.appendText(message);
        textArea.appendText(System.lineSeparator());
        textArea.appendText(System.lineSeparator());
        textField.setFocusTraversable(true);
        textField.clear();

        String newMessage = textArea.getText(currentText.length(), textArea.getLength());
        chatHistoryService.appendText(newMessage);
    }

    public void setApplication(ClientChat application) {
        this.application = application;
    }

    public void initializeMessageHandler() {
        Network.getInstance().addReadMessageListener(new ReadCommandListener() {
            @Override
            public void processReceivedMessage(Command command) {
                if (chatHistoryService == null) {
                    createChatHistory();
                    loadChatHistory();
                }
                if (command.getType() == CommandType.CLIENT_MESSAGE){
                    ClientMessageCommandData data = (ClientMessageCommandData) command.getData();
                    appendMessageToChat(data.getSender(), data.getMessage());
                } else if (command.getType() == CommandType.UPDATE_USER_LIST){
                   UpdateUserListCommandData data = (UpdateUserListCommandData) command.getData();
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            userList.setItems(FXCollections.observableList(data.getUsers()));
                        }
                    });
                }
            }
        });
    }

    public void closeChat(ActionEvent actionEvent) {
        chatHistoryService.close();
        ClientChat.INSTANCE.getChatStage().close();
    }

    public void updateUsername(ActionEvent actionEvent) {
        TextInputDialog editDialog = new TextInputDialog();
        editDialog.setTitle("Изменение имени пользователя");
        editDialog.setHeaderText("Введите новое имя");
        editDialog.setContentText("Новое имя: ");

        Optional<String> result = editDialog.showAndWait();
        if (result.isPresent()){
            try {
                Network.getInstance().changeUsername(result.get());
                chatHistoryService.close();
                chatHistoryService.renameInit(result.get());
            } catch (IOException e) {
                e.printStackTrace();
                Dialogs.NetworkError.SEND_MESSAGE.show();
            }
        }
    }

    private void loadChatHistory() {
        String rows = chatHistoryService.loadLastRows(LAST_HISTORY_ROWS_NUMBER);
        textArea.clear();
        textArea.setText(rows);
    }

    public void About(ActionEvent actionEvent) {
        Dialogs.AboutDialog.INFO.show();
    }
}