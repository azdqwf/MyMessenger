package com.danila.diplom.server;

import com.danila.diplom.entity.Chat;
import com.danila.diplom.entity.Query;
import com.danila.diplom.entity.User;
import com.danila.diplom.server.repository.ChatRepository;
import com.danila.diplom.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.danila.diplom.server.repository")
public class MessengerServer implements CommandLineRunner {

    @Autowired
    ChatRepository chatRepository;
    @Autowired
    UserRepository userRepository;

    Map<String, ObjectOutputStream> usersMap = new HashMap<>();


    class ClientThread implements Runnable {
        Socket socket;
        Socket updateSocket;


        ClientThread(Socket socket, Socket updateSocket) throws IOException {
            this.socket = socket;
            this.updateSocket = updateSocket;
        }

        @Override
        public void run() {
            try (ObjectInputStream reader = new ObjectInputStream(socket.getInputStream());
                 ObjectOutputStream writer = new ObjectOutputStream(socket.getOutputStream());
                 ObjectOutputStream updateOutputSteam = new ObjectOutputStream(updateSocket.getOutputStream())) {
                Optional<User> oUser1;
                Optional<User> oUser2;
                User user1;
                User user2;
                Optional<Chat> oChat;
                Chat chat;
                while (true) {
                    Query msg = (Query) reader.readObject();
                    System.out.println(msg);
                    updateOutputSteam.writeObject(msg);
                    updateOutputSteam.flush();
//                    if (msg == null) {
//                        break;
//                    }
                    switch (msg.getType()) {
                        case "auth":
                            user1 = msg.getUser1();
                            oUser1 = userRepository.findById(user1.getLogin());
                            if (oUser1.isPresent() && Objects.equals(user1.getPassword(), oUser1.get().getPassword())) {
                               // usersMap.put(user1.getLogin(), writer);
                                User u = oUser1.get();
                                Query query = new Query().setType("ok").setUser1(u);
                                System.out.println(Arrays.toString(query.getUser1().getChats().toArray()));
                                writer.writeObject(query);
                                writer.flush();
                            } else {
                                writer.writeObject(new Query().setType("fail"));
                                writer.flush();
                            }
                            break;
                        case "nc":
                            oUser1 = userRepository.findById(msg.getUser1().getLogin());
                            oUser2 = userRepository.findById(msg.getParam1());
                            if (oUser1.isPresent() && oUser2.isPresent()) {
                                chat = new Chat(oUser1.get().getLogin(), oUser2.get().getLogin());
                                chatRepository.save(chat);
                                user1 = oUser1.get();
                                user2 = oUser2.get();
                                user1.getChats().add(chat);
                                user2.getChats().add(chat);
                                userRepository.saveAll(Arrays.asList(user1, user2));
                                writer.writeObject(new Query().setType("ok").setChat(chat));
                                writer.flush();


                            } else {
                                writer.writeObject(new Query().setType("fail"));
                                writer.flush();
                            }
                            break;
                        case "reg":
                            user1 = msg.getUser1();
                            Optional<User> regUser = userRepository.findById(user1.getLogin());
                            if (!regUser.isPresent()) {
                                userRepository.save(user1);
                                writer.writeObject(new Query().setType("ok"));
                                writer.flush();
                            } else {
                                writer.writeObject(new Query().setType("fail"));
                                writer.flush();
                            }
                            break;
                        case "msg":
                            String chatId = msg.getParam1();
                            String message = msg.getParam2();

                            oChat = chatRepository.findById(chatId);
                            if (oChat.isPresent()) {
                                chat = oChat.get();
                                String oldMessages = chat.getMessages();
                                if (oldMessages.length() != 0)
                                    chat.setMessages(chat.getMessages() + "\n" + message);
                                else chat.setMessages(chat.getMessages() + message);
                                chatRepository.save(chat);
                                writer.writeObject(new Query().setType("ok"));
                                writer.flush();
                            } else {
                                writer.writeObject(new Query().setType("fail"));
                                writer.flush();
                            }
                            break;
                        case "gc":
                            user1 = msg.getUser1();
                            oUser1 = userRepository.findById(user1.getLogin());
                            if (oUser1.isPresent() && Objects.equals(user1.getPassword(), oUser1.get().getPassword())) {
                                oChat = chatRepository.findById(msg.getParam1());
                                if (oChat.isPresent()) {
                                    chat = oChat.get();
                                    writer.writeObject(new Query().setType("ok").setChat(chat));
                                    writer.flush();
                                }

                            } else {
                                writer.writeObject(new Query().setType("fail"));
                                writer.flush();
                            }
                            break;
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {
        SpringApplication.run(MessengerServer.class);
    }

    @Override
    public void run(String... args) {
        try {
            ServerSocket serverSocket = new ServerSocket(25000);
            ServerSocket updateServerSocket = new ServerSocket(5000);
            System.out.println("Server Started and listening to the port 25000");
            while (true) {
                Socket socket = serverSocket.accept();
                Socket updateSocket = updateServerSocket.accept();
                new Thread(new ClientThread(socket, updateSocket)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

