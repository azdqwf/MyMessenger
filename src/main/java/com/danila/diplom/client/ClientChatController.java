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
import java.util.Timer;
import java.util.TimerTask;


public class ClientChatController {

    User me;
    User he;
    public static Timer timer;
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
    Chat chat;

    public void initManager(StageManager manager, User me1, User he, boolean isMe) throws IOException {

        if (isMe) this.me = me1;
        if (!isMe) {
            this.me = me1;
            this.he = he;
        }
        if (me.getChats() != null) chatsList.setItems(FXCollections.observableArrayList(me.getChats()));

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                me = Main.connection.authenticate(me);
                if (me.getChats() != null) {
                    chatsList.setItems(FXCollections.observableArrayList(me.getChats()));
                    chatsList.getSelectionModel().selectFirst();
                }
                if (!messages.isDisabled()) {
                    isEmpty = messages.getText().equals("");
                    messages.setText(Main.connection.getChat(me, chatId).getMessages());
                }
            }
        }, 2000, 2000);
        newChat.setOnAction(event -> manager.showNewChatScreen(me));
        select.setOnAction(event -> {
            chatId = chatsList.getSelectionModel().getSelectedItem().getId();
            chat = Main.connection.getChat(me, chatId);
            if (chat != null) {
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
            if (Main.connection.sendMessage(this.me, this.he, chatId, me.getLogin() + ": " + msg)) {
                if (!isEmpty && isFirst) {
                    messages.appendText("\n" + me.getLogin() + ": " + msg + "\n");
                    isFirst = false;
                } else {
                    messages.appendText(me.getLogin() + ": " + msg + "\n");
                }

                textField.clear();
            } else
                messages.appendText("\nfailed to send");
            textField.clear();
        });
    }


}
