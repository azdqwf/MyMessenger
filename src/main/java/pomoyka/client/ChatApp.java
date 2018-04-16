package pomoyka.client;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import pomoyka.server.ChatServer;
import pomoyka.server.NetConnection;

public class ChatApp extends Application {
    private boolean isServer = false;

    private TextArea messages = new TextArea();
    private NetConnection connection = isServer ? createServer() : createClient();

    private Parent createContent() {
        messages.setPrefHeight(550);
        TextField input = new TextField();
        input.setOnAction(event -> {
            String message = isServer ? "Server: " : "Client: ";
            message += input.getText();
            input.clear();
            messages.appendText(message + "\n");
            try {
                connection.send(message);
            } catch (Exception e) {
                messages.appendText("Failed to send\n");
            }
        });
        VBox root = new VBox(20, messages, input);
        root.setPrefSize(600, 600);
        return root;
    }

    @Override
    public void init() throws Exception {
        connection.startConnection();
    }

    @Override
    public void stop() throws Exception {
        connection.closeConnetion();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    private ChatServer createServer() {
        return new ChatServer(46373, data -> Platform.runLater(() -> messages.appendText(data + "\n")));
    }

    private ChatClient createClient() {
        return new ChatClient("127.0.0.1", 46373, data -> Platform.runLater(() -> messages.appendText(data + "\n")));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
