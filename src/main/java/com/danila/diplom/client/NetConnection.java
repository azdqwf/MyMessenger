package com.danila.diplom.client;

import com.danila.diplom.entity.Chat;
import com.danila.diplom.entity.Query;
import com.danila.diplom.entity.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class NetConnection {

    private static NetConnection instance;

    public static NetConnection getInstance() {
        if (instance != null) return instance;
        return new NetConnection();
    }

    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;


    public NetConnection() {
        try {
            socket = new Socket("192.168.0.106", 25000);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());

        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    public boolean okOrFail(Query query) throws IOException, ClassNotFoundException {
        objectOutputStream.writeObject(query);
        objectOutputStream.flush();

        Query msg = (Query) objectInputStream.readObject();
        switch (msg.getType()) {
            case "ok":
                return true;
            case "fail":
                return false;
        }
        return false;
    }

    public boolean sendMessage(User me, User he, String chatId, String msg) {
        Query query = new Query().setType("msg").setUser1(me).setUser2(he).setParam1(chatId).setParam2(msg);
        try {
            return okOrFail(query);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;

    }


    Chat newChat(User me, String he) {

        try {
            Query query = new Query().setType("nc").setUser1(me).setParam1(he);
            objectOutputStream.writeObject(query);
            objectOutputStream.flush();

            Query msg = (Query) objectInputStream.readObject();
            switch (msg.getType()) {
                case "ok":
                    return msg.getChat();
                case "fail":
                    return null;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;

    }

    public boolean register(User me) {
        Query query = new Query().setType("reg").setUser1(me);
        try {

            return okOrFail(query);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Chat getChat(User me, String chatId) {
        Query query = new Query().setType("gc").setUser1(me).setParam1(chatId);
        try {
            objectOutputStream.writeObject(query);
            objectOutputStream.flush();
            Query response = (Query) objectInputStream.readObject();

            switch (response.getType()) {
                case "ok":
                    return response.getChat();
                case "fail":
                    return null;
                default:
                    break;
            }
            return null;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }

    public User authenticate(User me) {
        Query query = new Query().setType("auth").setUser1(me);
        try {
            objectOutputStream.writeObject(query);
            objectOutputStream.flush();
            Query response = (Query) objectInputStream.readObject();
            switch (response.getType()) {
                case "ok":
                    return response.getUser1();
                case "fail":
                    return null;
            }
            return null;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }

}
