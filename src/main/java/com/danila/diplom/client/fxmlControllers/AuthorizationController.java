package com.danila.diplom.client.fxmlControllers;

import com.danila.diplom.client.NetConnection;
import com.danila.diplom.client.config.StageManager;
import com.danila.diplom.entity.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AuthorizationController {

    public User isValid(String login, String password) {
        User user = new User(login, password, "");
        return NetConnection.getInstance().authenticate(user);

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
            register.setOnAction((e) -> manager.showRegistrationScreen());
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
