package com.danila.diplom.client.fxmlControllers;

import com.danila.diplom.client.NetConnection;
import com.danila.diplom.client.config.StageManager;
import com.danila.diplom.entity.Chat;
import com.danila.diplom.entity.User;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.io.IOException;


public class ClientChatController {

    private User me;
    private User he;

    private static ClientChatController instance;

    private static ClientChatController getInstance() {
        return instance;
    }

    public ClientChatController() {
        instance = this;
    }

    @FXML
    private Button newChat;
    @FXML
    private ListView<Chat> chatsList;
    @FXML
    private TextArea messages;
    @FXML
    private TextField textField;
    private String chatId;
    private Chat chat;


    public static void updateChatList(Chat chat) {
        getInstance().chatsList.getItems().add(chat);
        System.out.println("chatListUpdated");
    }

    public static void updateMessages(String msg, String chat) {
        System.out.println(chat);
        Chat selectedChat = getInstance().chatsList.getSelectionModel().getSelectedItem();
        if ((selectedChat.getUser1() + selectedChat.getUser2()).equals(chat)) {
            getInstance().messages.clear();
            getInstance().messages.appendText(msg);
            System.out.println("messages updated");
        }

    }

    public void initManager(StageManager manager, User me1, User he, boolean isMe) throws IOException {
        chatsList.getSelectionModel().selectedItemProperty().addListener(observable -> {
            messages.clear();
            chatId = chatsList.getSelectionModel().getSelectedItem().getId();
            chat = NetConnection.getInstance().getChat(me, chatId);

            String login = chat.getUser1();
            this.he = new User();
            if (me.getLogin().equals(login)) {
                this.he.setLogin(chat.getUser2());
            } else {
                this.he.setLogin(chat.getUser1());
            }

            messages.setDisable(false);
            textField.setDisable(false);
            messages.appendText(chat.getMessages());
        });
        if (isMe) this.me = me1;
        if (!isMe) {
            this.me = me1;
            this.he = he;
        }
        if (me.getChats() != null) chatsList.setItems(FXCollections.observableArrayList(me.getChats()));

        newChat.setOnAction(event -> manager.showNewChatScreen(me));

textField.setOnKeyPressed(event -> {if(event.getCode() == KeyCode.ENTER){
    String msg = textField.getText();
    if (NetConnection.getInstance().sendMessage(this.me, chatId, me.getLogin() + ": " + msg)) {
        textField.clear();
    } else
        messages.appendText("\nFailed to send");
    textField.clear();
}});
    }


}
