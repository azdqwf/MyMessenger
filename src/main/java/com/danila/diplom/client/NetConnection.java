package com.danila.diplom.client;

import com.danila.diplom.entity.User;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class NetConnection {


    User user1;
    User user2;
    Socket socket;

    public NetConnection(User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;
    }

    public void init() {
        try {
            socket = new Socket("127.0.0.1", 25000);

            //Send the message to the server
            OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
            String str = user1 + " " + user2;
            osw.write(str, 0, str.length());
            osw.flush();

        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
