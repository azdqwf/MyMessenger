package com.danila.diplom.client;

import com.danila.diplom.config.StageManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class NewChatController {

    @FXML
    TextField textField;

    @FXML
    Button okButton;

    @FXML
    Label error;

    public void initManager(StageManager manager) throws IOException {
//        okButton.setOnAction((e) -> {
//            if (userRepository.findById(textField.getText()).isPresent())
//                manager.showChatScreen(textField.getText(), false);
//            else System.out.println("sda");
//        });
    }

}
