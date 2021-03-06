package com.danila.diplom.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
public class User implements Serializable {

    private static final long serialVersionUID = 6091422997532114805L;
    @Id
    private String login;

    private String password;

    private String email;
    @DBRef
    private List<Chat> chats;

    public User() {
    }

    public User(String login, String password, String email) {
        this.password = password;
        this.login = login;
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Chat> getChats() {
        if (chats != null) return chats;
        else chats = new ArrayList<>();
        return chats;
    }

    public void setChats(List<Chat> chats) {
        this.chats = chats;
    }//getters and setters

    @Override
    public String toString() {
        return login;
    }
}
