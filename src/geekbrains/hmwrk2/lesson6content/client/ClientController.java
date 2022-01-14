package geekbrains.hmwrk2.lesson6content.client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.function.Consumer;


public class ClientController {

    @FXML private TextArea textArea;
    @FXML private TextField textField;
    @FXML private Button sendButton;
    @FXML public ListView userList;

    private Network network;
    private ClientChat application;

    public void sendMessage() {

        String message = textField.getText().trim();

        if (message.isEmpty()) {
            textField.clear();
            return;
        }
//        По какой-то причине конструкция в if не работает, когда sender числится как String - ругается IDE
        String sender = null;
        if (!userList.getSelectionModel().isEmpty()){
//        Из-за IDE вынужденно добавил (String) дабы разрешить ошибку
            sender = (String) userList.getSelectionModel().getSelectedItem();
        }

        appendMessageToChat(sender ,message);

        try {
            message = sender != null ? String.join(": ", sender, message) : message;
            network.sendMessage(message);
        } catch (IOException e) {
            application.showErrorDialog("Ошибка передачи данных сети");
        }
    }

    private void appendMessageToChat(String sender,String message) {
        textArea.appendText(DateFormat.getDateTimeInstance().format(new Date()));
        textArea.appendText(System.lineSeparator());

        if (sender != null){
            textArea.appendText(sender + ":");
            textArea.appendText(System.lineSeparator());
        }

        textArea.appendText(message);
        textArea.appendText(System.lineSeparator());
        textField.setFocusTraversable(true);
        textArea.appendText(System.lineSeparator());
        textField.clear();
    }

    public void setApplication(ClientChat application) {
        this.application = application;
    }

    public void setNetwork(Network network) {
        this.network = network;

        network.waitMessages(new Consumer<String>() {
            @Override
            public void accept(String message) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        appendMessageToChat("Server", message);
                    }
                });
            }
        });
    }
}