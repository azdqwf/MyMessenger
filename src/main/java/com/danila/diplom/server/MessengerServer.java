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
import java.util.Arrays;
import java.util.List;
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
                Optional<User> oUser1;
                Optional<User> oUser2;
                User user1;
                User user2;
                while (true) {
                    String msg[] = reader.readLine().split("~");
                    System.out.println(Arrays.toString(msg));
                    StringReader stringReader = new StringReader(msg[1]);
                    StreamSource jsonUser = new StreamSource(stringReader);

                    switch (msg[0]) {
                        case "auth":
                            user1 = unmarshaller.unmarshal(jsonUser, User.class).getValue();
                            oUser1 = userRepository.findById(user1.getLogin());
                            if (oUser1.isPresent() && Objects.equals(user1.getPassword(), oUser1.get().getPassword())) {
                                User u = oUser1.get();
                                marshaller.marshal(u, stringWriter);
                                System.out.println(stringWriter.toString());
                                writer.write("ok " + stringWriter.toString());
                                writer.write("\r\n");
                                writer.flush();
                            } else {
                                writer.write("fail");
                                writer.write("\r\n");
                                writer.flush();
                            }
                            break;
                        case "nc":
                            oUser1 = userRepository.findById(msg[1]);
                            oUser2 = userRepository.findById(msg[2]);
                            if (oUser1.isPresent() && oUser2.isPresent()) {
                                Chat chat = new Chat(oUser1.get().getLogin(), oUser2.get().getLogin());
                                chatRepository.save(chat);
                                user1 = oUser1.get();
                                user2 = oUser2.get();
                                user1.getChats().add(chat);
                                user2.getChats().add(chat);
                                userRepository.saveAll(Arrays.asList(user1, user2));

                                writer.write("ok");
                                writer.write("\r\n");
                                writer.flush();

                            } else {
                                writer.write("fail");
                                writer.write("\r\n");
                                writer.flush();
                            }
                            break;
                        case "reg":
                            user1 = unmarshaller.unmarshal(jsonUser, User.class).getValue();
                            System.out.println(user1.toString());
                            Optional<User> regUser = userRepository.findById(user1.getLogin());
                            if (!regUser.isPresent()) {
                                userRepository.save(user1);
                                writer.write("ok");
                                writer.write("\r\n");
                                writer.flush();
                            } else {
                                writer.write("fail");
                                writer.write("\r\n");
                                writer.flush();
                            }
                            break;
                        case "msg":
                            System.out.println(Arrays.toString(msg));
                            Optional<Chat> oChat = chatRepository.findById(msg[3]);
                            if (oChat.isPresent()) {
                                Chat chat = oChat.get();
                                String oldMessages = chat.getMessages();
                                if (oldMessages.length() != 0)
                                    chat.setMessages(chat.getMessages() + "\n" + msg[4]);
                                else chat.setMessages(chat.getMessages() + msg[4]);
                                chatRepository.save(chat);
                                writer.write("ok");
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
            } catch (IOException | JAXBException e) {
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

