package com.danila.diplom.client;

import com.danila.diplom.repository.UserRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
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


//    public TextField getNameField() {
//        return nameField;
//    }
//
//    public TextField getPasswordField() {
//        return passwordField;
//    }


    public void initManager(AuthorizationManager manager) throws IOException {
        okButton.setOnAction((e) -> {
            if (AuthorizationManager.isValid(userRepository, nameField.getText(), passwordField.getText())) {
                manager.showMainView();
            } else {
                System.out.println("not ok");
            }
        });

    }
}
