package com.sgat;

import com.sgat.view.LoginView;
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
        LoginView loginView = new LoginView();

        // --- Scene and Stage ---
        Scene scene = new Scene(loginView.getView());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/sgat/views/MainStyles.css")).toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}