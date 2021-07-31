package com.godzilla;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

     
    @Override
    /**
     * Este método se encarga de lanzar la vista del programa.
     * @param primaryStage Recibe como parámetro el Stage primario.
     */
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        URL path = new URL("file:src/main/java/resources/WebCrawler.fxml");
        System.out.println(path.toString());
        loader.setLocation(path);

        Parent root = (Parent) loader.load();
        primaryStage.setTitle("Web Crawler");
        System.out.println(root.toString());
        primaryStage.setScene(new Scene(root, 457, 314));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
