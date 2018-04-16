package client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AuthorizationManager {
    private Scene scene;

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

    public AuthorizationManager(Scene scene) {
        this.scene = scene;
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

    public static boolean isValid(String name, String password) {
        return true;
    }
}
