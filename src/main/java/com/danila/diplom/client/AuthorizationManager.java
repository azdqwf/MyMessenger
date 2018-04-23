package com.danila.diplom.client;

import com.danila.diplom.entity.User;
import com.danila.diplom.repository.UserRepository;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class AuthorizationManager {

    private Scene scene;

    public AuthorizationManager(Scene scene) {
        this.scene = scene;
    }

    public void showMainView() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/clientChat.fxml")
            );
            Parent sc = loader.load();
            scene.setRoot(sc);
            scene.getWindow().sizeToScene();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void showNewChatScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/newChat.fxml")
            );
            scene.setRoot(loader.load());
            AuthorizationController controller =
                    loader.getController();
            controller.initManager(this);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void showLoginScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/authorization.fxml")
            );
            scene.setRoot(loader.load());
            AuthorizationController controller =
                    loader.getController();
            controller.initManager(this);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static boolean isValid(UserRepository userRepository, String login, String password) {
        Optional<User> temp =  userRepository.findById(login);
       return temp.isPresent() && Objects.equals(temp.get().getPassword(), password);
    }
}
