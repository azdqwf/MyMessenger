package com.danila.diplom.client;

import com.danila.diplom.entity.User;
import org.eclipse.persistence.jaxb.MarshallerProperties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.Socket;

public class NetConnection {

    User user1;
    User user2;
    Socket socket;
    OutputStreamWriter outputStreamWriter;
    JAXBContext userJaxbContext;
    Marshaller userMarshaller;
    StringWriter stringWriter;


    private void newChat() {

    }

    public NetConnection() {
        System.setProperty("javax.xml.bind.context.factory", "org.eclipse.persistence.jaxb.JAXBContextFactory");
        try {
            socket = new Socket(InetAddress.getLocalHost(), 25000);
            userJaxbContext = JAXBContext.newInstance(User.class);
            userMarshaller = userJaxbContext.createMarshaller();
            userMarshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");
            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");

        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    public void authenticate(User u) {


        try {

            String query = "Hello " ;
            outputStreamWriter.write(query, 0, query.length());
            outputStreamWriter.write("\r\n");
            outputStreamWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public NetConnection(User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;
    }
}
