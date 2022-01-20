package ru.buzas.client.dialogs;

import javafx.scene.control.Alert;
import ru.buzas.client.ClientChat;

public class Dialogs {

    public enum AuthError {
        EMPTY_CREDENTIALS("Логин и пароль указаны неверно"),
        INVALID_CREDENTIALS("Логин и пароль заданы некорректно");

        private static final String TITLE = "Ошибка аутентификации";
        private static final String TYPE = TITLE;
        private final String message;

        AuthError(String message){
            this.message = message;
        }

        public void show() {
            showDialog(Alert.AlertType.ERROR, TITLE, TYPE, message);
        }

        public void show(String errorMessage){
            showDialog(Alert.AlertType.ERROR, TITLE, TYPE, errorMessage);
        }

    }

    public enum NetworkError {
        SEND_MESSAGE("Не удалось отправить сообщение"),
        SERVER_CONNECT("Не удалось установить соединение с сервером");

        private static final String TITLE = "Сетевая ошибка";
        private static final String TYPE = "Ошибка передачи данных по сети";
        private final String message;

        NetworkError(String message){
            this.message = message;
        }

        public void show() {
            showDialog(Alert.AlertType.ERROR, TITLE, TYPE, message);
        }

    }

    private static void showDialog(Alert.AlertType dialogType, String title, String type, String errorMessage){
        Alert alert = new Alert(dialogType);
        alert.initOwner(ClientChat.INSTANCE.getChatStage());
        alert.setTitle(title);
        alert.setHeaderText(type);
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }
}
