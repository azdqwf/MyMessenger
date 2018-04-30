package com.danila.diplom.client;

import com.danila.diplom.Main;
import com.danila.diplom.entity.User;
import javafx.scene.Scene;


public class AuthorizationManager {

    public static User isValid(String login, String password) {
        User user = new User(login, password, "");
      return Main.connection.authenticate(user);

    }
}
