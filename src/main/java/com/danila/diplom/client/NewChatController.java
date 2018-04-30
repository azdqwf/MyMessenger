package com.danila.diplom.client;

import com.danila.diplom.config.StageManager;
import com.danila.diplom.entity.User;
import com.danila.diplom.repository.UserRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class NewChatController {
    @Autowired
    UserRepository userRepository;

    @FXML
    TextField textField;

    @FXML
    Button okButton;

    @FXML
    Label error;

    public void initManager(StageManager manager) throws IOException {
        okButton.setOnAction((e) -> {
            if (userRepository.findById(textField.getText()).isPresent())
                manager.showChatScreen(textField.getText(), false);
            else System.out.println("sda");
        });
    }

}
