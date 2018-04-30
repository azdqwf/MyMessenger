package com.danila.diplom.client;

import com.danila.diplom.config.StageManager;
import com.danila.diplom.entity.Chat;
import com.danila.diplom.entity.User;
import com.danila.diplom.repository.ChatRepository;
import com.danila.diplom.repository.UserRepository;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;


public class ClientChatController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ChatRepository chatRepository;
    User me;
    User he;

    @FXML
    private Button select;
    @FXML
    private Button sendButton;
    @FXML
    private Button newChat;
    @FXML
    private ListView<Chat> chatsList;
    @FXML
    private TextArea messages;
    @FXML
    private TextField textField;

    @FXML
    public void sendButtonClicked() {
        messages.appendText(textField.getText() + "\n");
        textField.clear();
    }


    public void initManager(StageManager manager, String login, boolean isMe) throws IOException {
        if (isMe) me = userRepository.findById(login).get();
        if (!isMe) he = userRepository.findById(login).get();
        if (me.getChats() != null) chatsList.setItems(FXCollections.observableArrayList(me.getChats()));

        newChat.setOnAction(event -> manager.showNewChatScreen());
        select.setOnAction(event -> {
            Chat chat = chatsList.getSelectionModel().getSelectedItem();
            if (chat != null) {
                he = chat.getUser2();
                messages.setDisable(false);
                textField.setDisable(false);
                sendButton.setDisable(false);
                messages.clear();
                messages.appendText(chat.getMessages());

                new NetConnection(me, he);

            }
        });

    }


}
