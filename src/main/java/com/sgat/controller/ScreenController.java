package com.sgat.controller;

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
        MainLayout mainLayout = new MainLayout(stage, this);
        scene.setRoot(mainLayout.getLayout());
        stage.setMaximized(true);
    }
}
