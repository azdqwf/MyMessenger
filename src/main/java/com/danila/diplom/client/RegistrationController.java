package com.danila.diplom.client;

import com.danila.diplom.config.StageManager;
import com.danila.diplom.entity.User;
import com.danila.diplom.repository.UserRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RegistrationController {
    @Autowired
    UserRepository userRepository;
    @FXML
    Label error;
    @FXML
    TextField login;
    @FXML
    PasswordField pass;
    @FXML
    TextField email;
    @FXML
    Button ok;


    public void initManager(StageManager manager) throws IOException {
        //  userRepository = Main.springContext.getBean(UserRepository.class);
        ok.setOnAction((e) -> {
            if(login.getText().length() >= 4)

            { if
                    (!userRepository.findById(login.getText()).isPresent()) {
                userRepository.save(new User(login.getText(), pass.getText(), email.getText()));
                manager.showLoginScreen();
            } else
                error.setText("This login already exists");
                login.setStyle("-fx-border-color:red;");
            }
            else
                error.setText("Login must have at least 4 letters");
            login.setStyle("-fx-border-color:red;");

        });
    }
}
