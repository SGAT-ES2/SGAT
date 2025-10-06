package com.sgat.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label statusLabel;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Usuário e senha necessários.");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        // Simple authentication
        if (username.equals("admin") && password.equals("123")) {
            statusLabel.setText("Login aceito!");
            statusLabel.setStyle("-fx-text-fill: green;");
        } else {
            statusLabel.setText("Usuário ou senha inválidos.");
            statusLabel.setStyle("-fx-text-fill: red;");
        }
    }
}