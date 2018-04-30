package com.danila.diplom.server;

import com.danila.diplom.entity.Chat;
import com.danila.diplom.entity.User;
import com.danila.diplom.repository.ChatRepository;
import com.danila.diplom.repository.UserRepository;
import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.Optional;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.danila.diplom.repository")
public class MessengerServer implements CommandLineRunner {

    @Autowired
    ChatRepository chatRepository;
    @Autowired
    UserRepository userRepository;

    class ClientThread implements Runnable {

        JAXBContext jaxbContext;
        Unmarshaller unmarshaller;
        Marshaller marshaller;

        Socket socket;


        public ClientThread(Socket socket) {

            this.socket = socket;
            try {
                jaxbContext = JAXBContext.newInstance(User.class);
                unmarshaller = jaxbContext.createUnmarshaller();
                marshaller = jaxbContext.createMarshaller();
                unmarshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");
                marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");
            } catch (JAXBException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {

            try {

                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
                StringWriter stringWriter = new StringWriter();
                while (true) {
                    String msg[] = reader.readLine().split(" ");
                    StringReader stringReader = new StringReader(msg[1]);
                    StreamSource jsonUser = new StreamSource(stringReader);
                    switch (msg[0]) {
                        case "auth":
                            User got = unmarshaller.unmarshal(jsonUser, User.class).getValue();
                            Optional<User> user = userRepository.findById(got.getLogin());
                            if (user.isPresent() && Objects.equals(got.getPassword(), user.get().getPassword())) {
                                User u = user.get();
                                marshaller.marshal(u, stringWriter);
                                writer.write("ok " + stringWriter.toString());
                                writer.write("\r\n");
                                writer.flush();
                            } else {
                                writer.write("fail");
                                writer.write("\r\n");
                                writer.flush();
                            }
                            break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JAXBException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {
        SpringApplication.run(MessengerServer.class);
    }

    @Override
    public void run(String... args) throws Exception {
        System.setProperty("javax.xml.bind.context.factory", "org.eclipse.persistence.jaxb.JAXBContextFactory");
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

