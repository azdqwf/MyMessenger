package com.danila.diplom.client;

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


    private void newChat() {

    }

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

    public User authenticate(User u) {

        try {

            userMarshaller.marshal(u, stringWriter);
            String query = "auth" + stringWriter.toString();
            outputStreamWriter.write(query, 0, query.length());
            outputStreamWriter.write("\r\n");
            outputStreamWriter.flush();

            String[] msg = reader.readLine().split(" ");
            switch (msg[0]) {
                case "ok":
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
