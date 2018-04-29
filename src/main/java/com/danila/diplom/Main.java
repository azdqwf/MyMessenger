package com.danila.diplom;


import com.danila.diplom.config.StageManager;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Main extends Application {

    public static ConfigurableApplicationContext springContext;

    private StageManager stageManager;

    @Override
    public void start(Stage stage) throws Exception {
        stageManager = new StageManager(stage);
        stage.setResizable(false);
        stageManager.showLoginScreen();
    }

    @Override
    public void init() throws Exception {
        springContext = new SpringApplicationBuilder(Main.class).run(getParameters().getRaw().toArray(new String[0]));
    }

    @Override
    public void stop() throws Exception {
        springContext.close();
    }

    public static void main(String[] args) {
        launch(args);
    }

}