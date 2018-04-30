package com.danila.diplom;


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


    public static void main(String[] args) {
        launch(args);
    }

}