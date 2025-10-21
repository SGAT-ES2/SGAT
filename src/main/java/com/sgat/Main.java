package com.sgat;

import com.sgat.view.MainLayout;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("TravelManager");

        // Cria o layout principal
        MainLayout mainLayout = new MainLayout();

        // --- Scene and Stage ---
        Scene scene = new Scene(mainLayout.getLayout(), 1280.0, 720.0);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/sgat/views/MainStyles.css")).toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setMaximized(true); // Maximiza a janela
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}