package com.sgat;

import com.sgat.controller.ScreenController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class Main extends Application {
    // por enquanto a conexão está hardcoded, favor colocar as suas credenciais
    static final String DB_URL = "jdbc:postgresql://localhost:5432/sgat";
    static final String DB_USER = "postgres";
    static final String DB_PASS = "123";

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("TravelManager");

        // --- Scene and Stage ---
        Scene scene = new Scene(new Pane()); // Inicia com uma cena vazia
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/sgat/views/MainStyles.css")).toExternalForm());
        primaryStage.setScene(scene);

        ScreenController screenController = new ScreenController(primaryStage, scene);
        screenController.showLoginScreen();

        primaryStage.show();
    }

    public static void main(String[] args) {
        try {
            DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            System.out.println("FUNFOU");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        launch(args);
    }
}