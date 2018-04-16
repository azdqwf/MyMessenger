package client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class ClientChatController {
    private BufferedReader reader;
    private PrintWriter writer;
    private NetConnection connection;

//    {
//           //this.connection = new NetConnection();
//    }

    @FXML
    private Button sendButton;
    @FXML
    private TextArea messages;
    @FXML
    private TextField textField;

    @FXML
    public void sendButtonClicked() {
//       connection.getWriter().print("hi");
        messages.appendText(textField.getText() + "\n");
        textField.clear();
    }
}
