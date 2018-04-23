package com.danila.diplom.view;
import java.util.ResourceBundle;

public enum FxmlView {

    AUTHORIZATION {
        @Override
        public String getTitle() {
          return "Authorization";
        }

        @Override
        public String getFxmlFile() {
            return "/authorization.fxml";
        }
    },
    MAIN {
        @Override
        public String getTitle() {
            return "MyMessenger";
        }

        @Override
        public String getFxmlFile() {
            return "/clientChat.fxml";
        }
    },
    NEWCHAT {
        @Override
        public String getTitle() {
           return "New chat";
        }

        @Override
        public String getFxmlFile() {
            return "/newChat.fxml";
        }
    };

    public abstract String getTitle();
    public abstract String getFxmlFile();

    String getStringFromResourceBundle(String key){
        return ResourceBundle.getBundle("Bundle").getString(key);
    }

}