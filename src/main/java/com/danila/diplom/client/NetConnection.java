package com.danila.diplom.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class NetConnection {
    private BufferedReader reader;
    private PrintWriter writer;

    public BufferedReader getReader() {
        return reader;
    }

    public PrintWriter getWriter() {
        return writer;
    }


    NetConnection() {
        try {
            Socket socket = new Socket("127.0.0.1", 5000);
            InputStreamReader is = new InputStreamReader(socket.getInputStream());
            reader = new BufferedReader(is);
            writer = new PrintWriter(socket.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
