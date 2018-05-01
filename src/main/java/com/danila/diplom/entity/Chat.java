package com.danila.diplom.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;


@Document(collection = "chats")
public class Chat implements Serializable {
    private static final long serialVersionUID = 4316522149090787182L;
    @Id
    private String id;

    private String user1;

    private String user2;

    private String messages;

    public Chat(String user1, String user2) {
        this.user1 = user1;
        this.user2 = user2;
    }

    public Chat() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1.getLogin();
    }

    public String getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2.getLogin();
    }

    public String getMessages() {
        return messages != null ? messages : "";
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return user1 + ", " + user2;
    }
}
