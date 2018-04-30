package com.danila.diplom.client;

import com.danila.diplom.config.StageManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

import java.io.IOException;

public class AuthorizationController {


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
            if (AuthorizationManager.isValid(nameField.getText(), passwordField.getText()) != null) {
                manager.showChatScreen(nameField.getText(), true);
            } else {
                System.out.println("fail");

            }
        });

    }
}
