package com.danila.diplom;


import com.danila.diplom.client.ClientChatController;
import com.danila.diplom.client.NetConnection;
import com.danila.diplom.config.StageManager;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {

public static NetConnection connection = new NetConnection();


    private StageManager stageManager;

    @Override
    public void start(Stage stage) throws Exception {

        stageManager = new StageManager(stage);
        stage.setResizable(false);
        stageManager.showLoginScreen();
    }

    @Override
    public void stop() throws Exception {
        ClientChatController.timer.cancel();
    }

    public static void main(String[] args) {
        launch(args);
    }

}