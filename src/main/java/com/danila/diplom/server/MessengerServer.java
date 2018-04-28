package com.danila.diplom.server;

import com.danila.diplom.entity.Chat;
import com.danila.diplom.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.ws.Service;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class MessengerServer {
    private static Socket socket;
    @Autowired
    ChatRepository chatRepository;


    public static void main(String[] args)
    {
        try
        {
            ServerSocket serverSocket = new ServerSocket( 25000);
            System.out.println("Server Started and listening to the port 5000");
            while(true)
            {
                socket = serverSocket.accept();
                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String number = br.readLine();
                System.out.println("Message received from client is "+number);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                socket.close();
            }
            catch(Exception e){}
        }
    }
}

//    private static class Listener implements Runnable {
//
//        BufferedReader reader;
//
//        public Listener(Socket socket) {
//            InputStreamReader inputStreamReader;
//            try {
//                inputStreamReader = new InputStreamReader(socket.getInputStream());
//                reader = new BufferedReader(inputStreamReader);
//                System.out.println(reader.readLine());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//
//        @Override
//        public void run() {
//            String message;
//            try {
//                while ((message = reader.readLine()) != null) {
//                    System.out.println(message);
//                  //  sendHim(message);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
//    }
