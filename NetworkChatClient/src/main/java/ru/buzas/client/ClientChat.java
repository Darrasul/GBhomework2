package ru.buzas.client;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ru.buzas.client.controllers.AuthController;
import ru.buzas.client.controllers.ClientController;
import ru.buzas.client.model.Network;

import java.io.IOException;

public class ClientChat extends Application {

    public static final String SERVER_HOST = "localhost";
    public static final int SERVER_PORT = 8189;

    public static ClientChat INSTANCE;

    private FXMLLoader chatWindowLoader;
    private FXMLLoader authLoader;
    private Stage primaryStage;
    private Stage authStage;

    @Override
    public void start(Stage stage) throws IOException {
        this.primaryStage = stage;

        initViews();
        getChatStage().show();
        getAuthStage().show();


        getAuthController().initializeMessageHandler();
    }

    @Override
    public void init() throws Exception {
        INSTANCE = this;
    }

    public void showErrorDialog(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error!");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void switchToMainChatWindow(String userName) {
        getChatStage().setTitle(userName);
        getChatController().initializeMessageHandler();
        getAuthController().close();
        getAuthStage().close();
    }

    public void initViews() throws IOException {
        initChatWindow();
        initAuthWindow();
    }

    private void initAuthWindow() throws IOException {
        authLoader = new FXMLLoader();
        authLoader.setLocation(getClass().getResource("authDialog.fxml"));

        Parent authDialogPanel = authLoader.load();
        authStage = new Stage();
        authStage.initOwner(primaryStage);
        authStage.initModality(Modality.WINDOW_MODAL);
        authStage.setScene(new Scene(authDialogPanel));
    }

    private void initChatWindow() throws IOException {
        chatWindowLoader = new FXMLLoader();
        chatWindowLoader.setLocation(getClass().getResource("Chat-template.fxml"));

        Parent root = chatWindowLoader.load();
        this.primaryStage.setScene(new Scene(root));
    }


    public static void main(String[] args) {
        Application.launch();
    }

    public AuthController getAuthController() {
        return authLoader.getController();
    }

    public ClientController getChatController() {
        return chatWindowLoader.getController();
    }

    public Stage getAuthStage() {
        return authStage;
    }

    public Stage getChatStage() {
        return this.primaryStage;
    }
}