package com.danila.diplom.client;

import com.danila.diplom.Main;
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
    private String chatId;
    boolean isFirst;
    boolean isEmpty;

    public void initManager(StageManager manager, User me, User he, boolean isMe) throws IOException {
        if (isMe) this.me = me;
        if (!isMe) {
            this.me = me;
            this.he = he;
        }

        if (me.getChats() != null) chatsList.setItems(FXCollections.observableArrayList(me.getChats()));

        newChat.setOnAction(event -> manager.showNewChatScreen(me));
        select.setOnAction(event -> {
            Chat chat = chatsList.getSelectionModel().getSelectedItem();

            if (chat != null) {
                chatId = chat.getId();
                String login = chat.getUser1();
                this.he = new User();
                if (me.getLogin().equals(login)) {
                    this.he.setLogin(chat.getUser2());
                } else {
                    this.he.setLogin(chat.getUser1());
                }

                messages.setDisable(false);
                textField.setDisable(false);
                sendButton.setDisable(false);
                messages.clear();
                messages.appendText(chat.getMessages());
                isEmpty = messages.getText().equals("");
                isFirst = true;

            } else System.out.println("fail");
        });
        sendButton.setOnAction(event -> {
            String msg = textField.getText();
            if (Main.connection.sendMessage(this.me, this.he, chatId, msg)) {
                if (!isEmpty && isFirst) {
                    messages.appendText("\n" + me.getLogin() + ": " + msg + "\n");
                    isFirst = false;
                } else {
                    messages.appendText(me.getLogin() + ": " + msg + "\n");
                }

                textField.clear();
            } else
                messages.appendText("failed to send");
            textField.clear();
        });
    }


}
