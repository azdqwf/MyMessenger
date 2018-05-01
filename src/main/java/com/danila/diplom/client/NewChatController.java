package com.danila.diplom.client;

import com.danila.diplom.Main;
import com.danila.diplom.config.StageManager;
import com.danila.diplom.entity.Chat;
import com.danila.diplom.entity.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

public class NewChatController {

    @FXML
    TextField textField;

    @FXML
    Button okButton;


    public void initManager(StageManager manager, User me) throws IOException {
        okButton.setOnAction((e) -> {
            if (Main.connection.newChat(me.getLogin(), textField.getText())) {
                User he = new User();
                he.setLogin(textField.getText());
                me.getChats().add(new Chat(me.getLogin(), he.getLogin()));
                manager.showChatScreen(me, he, true);
            } else System.out.println("fail");
        });
    }

}
