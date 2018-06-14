package com.danila.diplom.entity;

import java.io.Serializable;

public class Query implements Serializable {
    private static final long serialVersionUID = 7271208744095795841L;
    private String type;
    private User user1;
    private User user2;
    private String param1;
    private String param2;
    private Chat chat;

    public Query() {
    }

    public String getType() {
        return type;
    }

    public Query setType(String type) {
        this.type = type;
        return this;
    }
    public User getUser1() {
        return user1;
    }

    public Query setUser1(User user1) {
        this.user1 = user1;
        return this;
    }

    public User getUser2() {
        return user2;
    }

    public Query setUser2(User user2) {
        this.user2 = user2;
        return this;
    }

    public String getParam1() {
        return param1;
    }

    public Query setParam1(String param1) {
        this.param1 = param1;
        return this;
    }

    public String getParam2() {
        return param2;
    }

    public Query setParam2(String param2) {
        this.param2 = param2;
        return this;
    }


    public Chat getChat() {
        return chat;
    }

    public Query setChat(Chat chat) {
        this.chat = chat;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getType()).append(", user1: ");
        User user = getUser1();
        if (user == null) stringBuilder.append("null, user2:");
        else stringBuilder.append(user.getLogin()).append(", user2: ");
        User user2 = getUser2();
        if (user2 == null) stringBuilder.append("null").append(", chat: ");
        else stringBuilder.append(user2.getLogin()).append(", chat :");
        Chat chat = getChat();
        if (chat == null) stringBuilder.append("null").append(", param1: ");
        else stringBuilder.append(chat.toString()).append(", param1 :");
        stringBuilder.append(param1).append(", param2: ").append(param2);
        return stringBuilder.toString();

    }
}
