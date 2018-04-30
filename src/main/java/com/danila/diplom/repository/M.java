//package com.danila.diplom.repository;
//
//import com.danila.diplom.entity.Chat;
//import com.danila.diplom.entity.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.data.domain.Example;
//import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//@SpringBootApplication
//public class M implements CommandLineRunner {
//    @Autowired
//    UserRepository userRepository;
//    @Autowired
//    ChatRepository chatRepository;
//
//    @Override
//    public void run(String... args) throws Exception {
//        User vasea = new User();
//        vasea.setLogin("Vasea");
//        vasea.setPassword("1");
//        vasea.setEmail("1");
//
//
//        User jora = new User();
//        jora.setLogin("Jora");
//        jora.setPassword("1");
//        jora.setEmail("1");
//
//
//        User petea = new User();
//        petea.setLogin("Petea");
//        petea.setPassword("1");
//        petea.setEmail("1");
//
//        Chat chat = new Chat(vasea.getLogin(),
//                jora.getLogin());
//        chat.setMessages("dasdasd\n" +
//                "asdasdasdda\n" +
//                "asdasdasd\n" +
//                "asdasdasdas\n");
//        chatRepository.save(chat);
//        vasea.setChats(Collections.singletonList(chatRepository.findById(chat.getId()).get()));
//        userRepository.save(vasea);
//        userRepository.save(petea);
//
//    }
//
//    public static void main(String[] args) {
//        SpringApplication.run(M.class);
//    }
//}
