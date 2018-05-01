package com.danila.diplom.client;

import com.danila.diplom.entity.Chat;
import com.danila.diplom.entity.User;
import org.eclipse.persistence.jaxb.MarshallerProperties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;

public class NetConnection {

    private User user1;
    private User user2;
    private Socket socket;
    private OutputStreamWriter outputStreamWriter;
    private JAXBContext userJaxbContext;
    private Marshaller userMarshaller;
    private Unmarshaller userUnmarshaller;
    private StringWriter stringWriter;
    private BufferedReader reader;
    StreamSource json;


    public NetConnection() {
        System.setProperty("javax.xml.bind.context.factory", "org.eclipse.persistence.jaxb.JAXBContextFactory");
        try {
            socket = new Socket(InetAddress.getLocalHost(), 25000);
            userJaxbContext = JAXBContext.newInstance(User.class);
            userMarshaller = userJaxbContext.createMarshaller();
            userMarshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");
            userUnmarshaller = userJaxbContext.createUnmarshaller();
            userUnmarshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");

            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            stringWriter = new StringWriter();

        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    public boolean okOrFail(String query) throws IOException {

        outputStreamWriter.write(query, 0, query.length());
        outputStreamWriter.write("\r\n");
        outputStreamWriter.flush();

        String msg = reader.readLine();
        switch (msg) {
            case "ok":
                return true;
            case "fail":
                return false;
        }
        return false;
    }

    public boolean sendMessage(User me, User he, String chatId, String msg) {
        String query = "msg~" + me + "~" + he + "~" + chatId + "~" + me.getLogin() + ":" + msg;
        try {
            return okOrFail(query);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;

    }


    public boolean newChat(String me, String he) {
        String query = "nc~" + me + "~" + he;
        try {
            return okOrFail(query);
        } catch (IOException e) {
            e.printStackTrace();

        }
        return false;

    }

    public boolean register(User u) {
        stringWriter = new StringWriter();
        try {
            userMarshaller.marshal(u, stringWriter);
            String query = "reg~" + stringWriter.toString();
            return okOrFail(query);

        } catch (JAXBException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public User authenticate(User u) {
        stringWriter = new StringWriter();
        try {
            userMarshaller.marshal(u, stringWriter);
            String query = "auth~" + stringWriter.toString();
            outputStreamWriter.write(query, 0, query.length());
            outputStreamWriter.write("\r\n");
            outputStreamWriter.flush();

            String[] msg = reader.readLine().split(" ");
            switch (msg[0]) {
                case "ok":
                    System.out.println(msg[1]);
                    json = new StreamSource(new StringReader(msg[1]));
                    return userUnmarshaller.unmarshal(json, User.class).getValue();
                case "fail":
                    return null;
            }
            return null;
        } catch (IOException | JAXBException e) {
            e.printStackTrace();
        }
        return null;

    }

    public NetConnection(User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;
    }
}
