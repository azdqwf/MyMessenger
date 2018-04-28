package com.danila.diplom.repository;

import com.danila.diplom.entity.Chat;
import com.danila.diplom.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
public class M implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ChatRepository chatRepository;

    @Override
    public void run(String... args) throws Exception {


        User user1 = userRepository.findById("Vasea").get();
        User user2 = userRepository.findById("Jora").get();
        Chat chat = new Chat(user1, user2);
        chat.setMessages("dasdasd\n" +
                "asdasdasdda\n" +
                "asdasdasd\n" +
                "asdasdasdas\n");
        chatRepository.save(chat);
        String id = chat.getId();
        userRepository.save(user1);
        userRepository.save(user2);

    }

    public static void main(String[] args) {
        SpringApplication.run(M.class);
    }
}
