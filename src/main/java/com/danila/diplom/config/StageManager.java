package com.danila.diplom.config;


import com.danila.diplom.Main;
import com.danila.diplom.client.AuthorizationController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

/**
 * Manages switching Scenes on the Primary Stage
 */


public class StageManager {

    private final Stage primaryStage;
    private Scene scene;

    public StageManager(Stage stage) {
        this.primaryStage = stage;
    }

    public void switchScene(String path, String title) {

        Parent viewRootNodeHierarchy = null;
        try {
            viewRootNodeHierarchy = FXMLLoader.load(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        show(viewRootNodeHierarchy, title);
    }

    public void showLoginScreen() {

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/authorization.fxml")
            );
            Parent root = loader.load();
            AuthorizationController controller = loader.getController();

            (Main.springContext).getAutowireCapableBeanFactory().autowireBean(controller);

            ((GenericApplicationContext) Main.springContext).registerBean(AuthorizationController.class, () -> controller);

            AuthorizationController bean = Main.springContext.getBean(AuthorizationController.class);

            System.out.println(bean == controller);

            controller.initManager(this);
            show(root, "Auth");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void show(final Parent rootnode, String title) {
        scene = primaryStage.getScene();

        if (scene == null) {
            scene = new Scene(rootnode);
        }
        scene.setRoot(rootnode);

        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();

        try {
            primaryStage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }




}