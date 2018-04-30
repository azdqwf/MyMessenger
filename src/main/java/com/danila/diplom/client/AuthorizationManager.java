package com.danila.diplom.client;

import com.danila.diplom.Main;
import com.danila.diplom.entity.User;
import javafx.scene.Scene;


public class AuthorizationManager {

    private Scene scene;

    public AuthorizationManager(Scene scene) {
        this.scene = scene;
    }

    public static boolean isValid(String login, String password) {
        User user = new User(login, password, "");
       Main.connection.authenticate(user);
        return true;
    }
}
