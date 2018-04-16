package client;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pomoyka.server.NetConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatApp extends Application {

    private static BufferedReader reader;
    private static PrintWriter writer;
    private TextArea messages = new TextArea();
    private NetConnection connection;

    @Override
    public void start(Stage primaryStage) throws Exception {
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(ChatApp.class.getResource("/authorization.fxml"));
//        AuthorizationController controller = loader.getController();
        Pane mainPane = FXMLLoader.load(ChatApp.class.getResource("/clientChat.fxml"));
        Scene mainScene = new Scene(mainPane);
        AuthorizationManager manager = new AuthorizationManager(mainScene);
        manager.showLoginScreen();
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static void setNet() {
        try {
            Socket socket = new Socket("127.0.0.1", 5000);
            InputStreamReader is = new InputStreamReader(socket.getInputStream());
            reader = new BufferedReader(is);
            writer = new PrintWriter(socket.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}