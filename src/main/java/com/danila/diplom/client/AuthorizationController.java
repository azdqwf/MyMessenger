package com.danila.diplom.client;

import com.danila.diplom.Main;
import com.danila.diplom.config.StageManager;
import com.danila.diplom.entity.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

import java.io.IOException;

public class AuthorizationController {

    public User isValid(String login, String password) {
        User user = new User(login, password, "");
        return Main.connection.authenticate(user);

    }

    @FXML
    private TextField nameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button okButton;
    @FXML
    Button register;


    public void initManager(StageManager manager) throws IOException {
            register.setOnAction((e) -> {
                manager.showRegistrationScreen();
            });
            okButton.setOnAction((e) -> {
                User me = isValid(nameField.getText(), passwordField.getText());
                if (me != null) {
                    manager.showChatScreen(me, null, true);
                } else {
                    System.out.println("fail");

                }
        });

    }
}
