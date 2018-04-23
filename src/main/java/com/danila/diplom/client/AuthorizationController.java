package com.danila.diplom.client;

import com.danila.diplom.Main;
import com.danila.diplom.config.StageManager;
import com.danila.diplom.repository.UserRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    private TextField passwordField;
    @FXML
    private Button okButton;



    public void initManager(StageManager manager) throws IOException {
      //  userRepository = Main.springContext.getBean(UserRepository.class);
        okButton.setOnAction((e) -> {
            if (AuthorizationManager.isValid(userRepository, nameField.getText(), passwordField.getText())) {
                manager.switchScene("/clientChat.fxml", "Chat");
            } else {
                System.out.println("ok");
            }
        });

    }
}
