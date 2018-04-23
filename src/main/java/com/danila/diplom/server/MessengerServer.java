package com.danila.diplom.server;

import com.danila.diplom.entity.Chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MessengerServer {
    private static ArrayList<Chat> chats;
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(5432);
            while (true){
                Socket socket = serverSocket.accept();
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
