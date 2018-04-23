package com.danila.diplom.config;

import java.io.IOException;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * Will load the FXML hierarchy as specified in the load method and register
 * Spring as the FXML Controller Factory. Allows Spring and Java FX to coexist
 * once the Spring Application context has been bootstrapped.
 */
@Component
public class SpringFXMLLoader {

    private final ApplicationContext context;
    @Autowired
    public SpringFXMLLoader(ApplicationContext context) {
        this.context = context;
    }


    public Parent load(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(context::getBean); //Spring now FXML Controller Factory
        loader.setLocation(getClass().getResource(fxmlPath));
        return loader.load();
    }
}