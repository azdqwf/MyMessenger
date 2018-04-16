package client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AuthorizationController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField passwordField;


    public TextField getNameField() {
        return nameField;
    }

    public TextField getPasswordField() {
        return passwordField;
    }


    @FXML
    private Button okButton;

    public void initManager(AuthorizationManager manager) throws IOException {
        okButton.setOnAction((e) -> {
            if (AuthorizationManager.isValid(nameField.getText(), passwordField.getText())) {
                manager.showMainView();
            } else {
                System.out.println("not ok");
            }
        });

    }
}
