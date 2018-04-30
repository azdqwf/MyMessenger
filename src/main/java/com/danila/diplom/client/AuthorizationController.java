package com.danila.diplom.client;

import com.danila.diplom.Main;
import com.danila.diplom.config.StageManager;
import com.danila.diplom.repository.UserRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthorizationController {
    @Autowired
    private UserRepository userRepository;

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
            if (AuthorizationManager.isValid(nameField.getText(), passwordField.getText())) {
                manager.showChatScreen(nameField.getText(), true);
            } else {
                System.out.println("ok");
            }
        });

    }
}
