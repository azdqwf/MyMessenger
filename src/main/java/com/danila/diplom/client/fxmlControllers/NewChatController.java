package com.danila.diplom.client.fxmlControllers;

import com.danila.diplom.client.NetConnection;
import com.danila.diplom.client.config.StageManager;
import com.danila.diplom.entity.Chat;
import com.danila.diplom.entity.User;
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
    Button cancelButton;

    @FXML
    Label error;

    public void initManager(StageManager manager, User me) throws IOException {
        okButton.setOnAction((e) -> {
            Chat chat = NetConnection.getInstance().newChat(me, textField.getText());
            if (chat != null) {
                User he = new User();
                he.setLogin(textField.getText());
                me.getChats().add(chat);
                manager.showChatScreen(me, he, true);
            }
            else {
            error.setText("Failed to create: wrong user or duplicate chat");
            textField.setStyle("-fx-border-color:red;");}

        });
        cancelButton.setOnAction(event -> manager.showChatScreen(me, null, true));
    }

}
