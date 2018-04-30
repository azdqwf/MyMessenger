package com.danila.diplom.server;

import com.danila.diplom.entity.Chat;
import com.danila.diplom.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.xml.ws.Service;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;


public class MessengerServer {
    static class ClientThread implements Runnable {
        Socket socket;

        public ClientThread(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {

            InputStream is = null;
            try {
                is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                while (true) {
                    String msg = br.readLine();
                    System.out.println("Message received from client is " + msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Autowired
    ChatRepository chatRepository;


    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(25000);
            System.out.println("Server Started and listening to the port 25000");
            while (true) {
                Socket socket;
                socket = serverSocket.accept();
                new Thread(new ClientThread(socket)).start();

            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}

