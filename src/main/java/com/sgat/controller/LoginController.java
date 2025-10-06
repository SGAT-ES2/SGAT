package com.sgat.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;


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

    @FXML
    private void handleRegisterClick() throws IOException, IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/sgat/views/CadastroView.fxml"));
        Stage stage = (Stage) usernameField.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Cadastro de Novo Funcionário");
        stage.show();
    }
}