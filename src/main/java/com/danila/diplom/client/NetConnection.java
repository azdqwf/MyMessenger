package com.danila.diplom.client;

import com.danila.diplom.client.fxmlControllers.ClientChatController;
import com.danila.diplom.entity.Chat;
import com.danila.diplom.entity.Query;
import com.danila.diplom.entity.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class NetConnection implements Runnable {



    private static NetConnection instance;
    @Override
    public void run() {
        instance = new NetConnection();
    }
    public static NetConnection getInstance() {
        if (instance == null)
            instance = new NetConnection();
        return instance;
    }

    private Socket socket;
    private Socket updateSocket;

    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private ObjectInputStream updateObjectStream;


    private NetConnection() {

        try {
            socket = new Socket("95.65.114.53", 25000);
            updateSocket = new Socket(InetAddress.getLocalHost(), 5000);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    private boolean okOrFail(Query query) throws IOException, ClassNotFoundException{
        objectOutputStream.writeObject(query);
        Query msg = (Query) objectInputStream.readObject();
        switch (msg.getType()) {
            case "ok":
                return true;
            case "fail":
                return false;
        }
        return false;
    }

    public boolean sendMessage(User me, String chatId, String msg) {
        Query query = new Query().setType("msg").setUser1(me).setParam1(chatId).setParam2(msg);
        try {
            return okOrFail(query);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;

    }


    public Chat newChat(User me, String he) {

        try {
            Query query = new Query().setType("nc").setUser1(me).setParam1(he);
            objectOutputStream.writeObject(query);

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
            Query response = (Query) objectInputStream.readObject();
            switch (response.getType()) {
                case "ok":
                    new Thread(new UpdateListener()).start();
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

    void end() {
        try {
            objectOutputStream.writeObject(null);
            Thread.sleep(500);
            updateObjectStream.close();
            updateSocket.close();
            objectOutputStream.close();
            objectInputStream.close();
            socket.close();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }


    class UpdateListener implements Runnable {

        @Override
        public void run() {
            try {
                updateObjectStream = new ObjectInputStream(updateSocket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (!updateSocket.isClosed()) {
                try {
                    Query query = (Query) updateObjectStream.readObject();
                    if (query == null) break;
                    switch (query.getType()) {
                        case "nc":
                            ClientChatController.updateChatList(query.getChat());
                            break;
                        case "msg":
                            ClientChatController.updateMessages(query.getParam1(), query.getParam2());
                            break;
                    }
                } catch (IOException | ClassNotFoundException e) {
                    try {
                        updateSocket.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    e.printStackTrace();
                    System.out.println("fail th");
                }
            }

        }
    }
}
