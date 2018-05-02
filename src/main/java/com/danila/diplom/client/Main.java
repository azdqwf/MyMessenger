package com.danila.diplom.client;


import com.danila.diplom.client.fxmlControllers.ClientChatController;
import com.danila.diplom.client.config.StageManager;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {

    public static NetConnection connection = new NetConnection();


    @Override
    public void start(Stage stage) throws Exception {

        StageManager stageManager = new StageManager(stage);
        stage.setResizable(false);
        stageManager.showLoginScreen();
    }

    @Override
    public void stop() throws Exception {
        ClientChatController.timer.cancel();
        connection.end();

    }

    public static void main(String[] args) {
        launch(args);
    }

}