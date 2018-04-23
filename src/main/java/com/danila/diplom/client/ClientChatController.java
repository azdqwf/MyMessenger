package com.danila.diplom.client;

import com.danila.diplom.config.StageManager;
import com.danila.diplom.entity.Chat;
import com.danila.diplom.entity.User;
import com.danila.diplom.service.UserService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;


public class ClientChatController {
    @Autowired
    private UserService userService;
    @Autowired
    private StageManager stageManager;

    private User me;
    private BufferedReader reader;
    private PrintWriter writer;
    private NetConnection connection;

//    {
//           //this.connection = new NetConnection();
//    }

    @FXML
    private Button sendButton;
    @FXML
    private Button newChatButton;
    @FXML
    private ListView<User> chatsList;
    @FXML
    private TextArea messages;
    @FXML
    private TextField textField;

    @FXML
    public void setNewChatButtonClicked() {
    }

    @FXML
    public void sendButtonClicked() {
        chatsList.getItems().addAll(new User("Vasea", "", ""));
//       connection.getWriter().print("hi");
        messages.appendText(textField.getText() + "\n");
        textField.clear();
    }

    public void initChat(User user) {
        Chat chat = new Chat(me, user);
    }


}
