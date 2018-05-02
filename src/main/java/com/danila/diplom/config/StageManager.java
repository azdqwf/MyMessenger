package com.danila.diplom.config;


import com.danila.diplom.client.AuthorizationController;
import com.danila.diplom.client.ClientChatController;
import com.danila.diplom.client.NewChatController;
import com.danila.diplom.client.RegistrationController;
import com.danila.diplom.entity.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Manages switching Scenes on the Primary Stage
 */
public class StageManager {

    private final Stage primaryStage;
    private Scene scene;

    public StageManager(Stage stage) {
        this.primaryStage = stage;
    }


    public void showLoginScreen() {

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/authorization.fxml")
            );
            Parent root = loader.load();
            AuthorizationController controller = loader.getController();
            controller.initManager(this);
            show(root, "Login");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void showNewChatScreen(User me) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/newChat.fxml")
            );
            Parent root = loader.load();
            NewChatController controller = loader.getController();
            controller.initManager(this, me);
            show(root, "NewChat");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public void showRegistrationScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/registration.fxml")
            );
            Parent root = loader.load();
            RegistrationController controller = loader.getController();
            controller.initManager(this);
            show(root, "Registration");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    ;

    public void showChatScreen(User me, User he, boolean isMe) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/clientChat.fxml")
            );
            Parent root = loader.load();
            ClientChatController controller = loader.getController();
            controller.initManager(this, me, he, isMe);
            show(root, "MyMessenger, " + me.getLogin());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void show(final Parent rootnode, String title) {
        scene = primaryStage.getScene();

        if (scene == null) {
            scene = new Scene(rootnode);
        }
        scene.setRoot(rootnode);

        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();

        try {
            primaryStage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}