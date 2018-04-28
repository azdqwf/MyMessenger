package com.danila.diplom.client;

import com.danila.diplom.entity.User;
import com.danila.diplom.repository.UserRepository;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class AuthorizationManager {

    private Scene scene;
    public AuthorizationManager(Scene scene) {
        this.scene = scene;
    }

    public static boolean isValid(UserRepository userRepository, String login, String password) {
        Optional<User> temp = userRepository.findById(login);
        return temp.isPresent() && Objects.equals(temp.get().getPassword(), password);
    }
}
