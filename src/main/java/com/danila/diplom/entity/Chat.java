package com.danila.diplom.entity;

import org.eclipse.persistence.oxm.annotations.XmlInverseReference;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Document(collection = "chats")
public class Chat {
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
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return user2;
    }
}
