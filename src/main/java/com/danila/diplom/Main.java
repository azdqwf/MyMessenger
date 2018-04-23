package com.danila.diplom;


import com.danila.diplom.config.StageManager;
import com.danila.diplom.view.FxmlView;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
public class Main extends Application {

    Stage stage;
    private ConfigurableApplicationContext springContext;
    private StageManager stageManager;

    @Override
    public void start(Stage stage) throws Exception {
        stageManager = springContext.getBean(StageManager.class, stage);
        this.stage = stage;
        displayInitialScene();

    }

    @Bean
    Stage getStage() {
        return stage;
    }

    @Override
    public void stop() throws Exception {
        springContext.close();
    }

    @Override
    public void init() throws Exception {
        springContext = springBootApplicationContext();
    }

    protected void displayInitialScene() {
        stageManager.switchScene(FxmlView.MAIN);
    }

    public static void main(String[] args) {
        launch(args);
    }

    private ConfigurableApplicationContext springBootApplicationContext() {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(Main.class);
        String[] args = getParameters().getRaw().toArray(new String[0]);
        return builder.run(args);
    }
//
//    private static void setNet() {
//        try {
//            Socket socket = new Socket("127.0.0.1", 5000);
//            InputStreamReader is = new InputStreamReader(socket.getInputStream());
//            reader = new BufferedReader(is);
//            writer = new PrintWriter(socket.getOutputStream());
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


}