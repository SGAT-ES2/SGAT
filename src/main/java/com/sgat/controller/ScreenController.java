package com.sgat.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.sgat.view.LoginView;
import com.sgat.view.MainLayout;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScreenController {

    private final Stage stage;
    private final Scene scene;

    public ScreenController(Stage stage, Scene scene) {
        this.stage = stage;
        this.scene = scene;
    }

    public void showLoginScreen() {
        LoginView loginView = new LoginView(this);
        Parent loginRoot = loginView.getView();
        scene.setRoot(loginRoot);
        stage.setMaximized(true);
    }

    public void showMainScreen() {

        // -----------------------------
        // 🔥 Criar conexão com o banco
        // -----------------------------
        Connection conn;
        try {
            conn = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/sgat",
                    "postgres",
                    "123"
            );
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados!", e);
        }

        // ----------------------------------------
        // 🔥 Criar DashboardController com o banco
        // ----------------------------------------
        DashboardController dashboardController = new DashboardController(conn);

        // ----------------------------------------------
        // 🔥 Passar DashboardController para MainLayout
        // ----------------------------------------------
        MainLayout mainLayout = new MainLayout(stage, this, dashboardController);

        scene.setRoot(mainLayout.getLayout());
        stage.setMaximized(true);
    }
}
