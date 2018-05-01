package com.danila.diplom.entity;

import java.io.Serializable;

public class Query implements Serializable {
    private static final long serialVersionUID = 7271208744095795841L;
    User user1;
    User user2;
    Chat chat;

    public Query() {
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }
}
