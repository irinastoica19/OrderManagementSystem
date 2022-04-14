package org.example.presentation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Order Management Application");
        scene = new Scene(loadFXML("view"), 850, 700);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        URL url = new File("src/main/java/org/example/presentation/" + fxml + ".fxml").toURI().toURL();
        return FXMLLoader.load(url);
    }

    public static void main(String[] args) {
        launch();
    }

}