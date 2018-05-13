package com.danila.diplom.client.fxmlControllers;

import com.danila.diplom.client.Main;
import com.danila.diplom.client.NetConnection;
import com.danila.diplom.client.config.StageManager;
import com.danila.diplom.entity.Chat;
import com.danila.diplom.entity.User;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class ClientChatController {

    User me;
    User he;
    //public static Timer timer;
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

    public static void update() {
        System.out.println("update");
    }

    public void initManager(StageManager manager, User me1, User he, boolean isMe) throws IOException {

        if (isMe) this.me = me1;
        if (!isMe) {
            this.me = me1;
            this.he = he;
        }
        if (me.getChats() != null) chatsList.setItems(FXCollections.observableArrayList(me.getChats()));

//        timer = new Timer();
//        timer.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//
//                List<Integer> list = chatsList.getSelectionModel().getSelectedIndices();
//                Integer item = list.get(0);
//                me = NetConnection.getInstance().authenticate(me);
//                if (me.getChats() != null) {
//                    chatsList.setItems(FXCollections.observableArrayList(me.getChats()));
//                    chatsList.getSelectionModel().selectIndices(item);
//                }
//                if (!messages.isDisabled()) {
//                    isEmpty = messages.getText().equals("");
//                    messages.setText(NetConnection.getInstance().getChat(me, chatId).getMessages());
//                    messages.setScrollTop(1000);
//                }
//            }
//        }, 2000, 2000);
        newChat.setOnAction(event -> manager.showNewChatScreen(me));
        select.setOnAction(event -> {
            chatId = chatsList.getSelectionModel().getSelectedItem().getId();
            chat = NetConnection.getInstance().getChat(me, chatId);
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
                messages.appendText(chat.getMessages());
                isEmpty = messages.getText().equals("");
                isFirst = true;

            } else System.out.println("fail");
        });
        sendButton.setOnAction(event -> {
            String msg = textField.getText();
            if (NetConnection.getInstance().sendMessage(this.me, chatId, me.getLogin() + ": " + msg)) {
                if (isEmpty && isFirst) {
                    messages.appendText(me.getLogin() + ": " + msg + "\n");

                    isFirst = false;
                } else {
                    messages.appendText("\n" + me.getLogin() + ": " + msg + "\n");
                }

                textField.clear();
            } else
                messages.appendText("\nfailed to send");
            textField.clear();
        });
    }


}
