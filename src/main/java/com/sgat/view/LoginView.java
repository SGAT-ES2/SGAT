package com.sgat.view;

import com.sgat.view.MainLayout;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

public class LoginView {

    public Parent getView() {
        StackPane root = new StackPane();
        root.getStyleClass().add("login-root");

        VBox card = new VBox(15);
        card.getStyleClass().add("card");
        card.setMaxWidth(400);

        // Header
        Node iconContainer = createIconContainer();
        Label title = new Label("TravelManager");
        title.getStyleClass().add("card-title");
        Label subtitle = new Label("Sistema de Gestão de Agência de Turismo");
        subtitle.getStyleClass().add("card-subtitle");

        // Form
        Label emailLabel = new Label("Email");
        TextField emailField = new TextField();
        emailField.setPromptText("seu@email.com");
        Node emailInput = createIconTextField(emailField, "M4 4h16c1.1 0 2 .9 2 2v12c0 1.1-.9 2-2 2H4c-1.1 0-2-.9-2-2V6c0-1.1.9-2 2-2z");

        Label passwordLabel = new Label("Senha");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("••••••••");
        Node passwordInput = createIconTextField(passwordField, "M12 17a2 2 0 0 0 2-2 2 2 0 0 0-2-2 2 2 0 0 0-2 2 2 2 0 0 0 2 2zm6-9a2 2 0 0 1 2 2v10a2 2 0 0 1-2 2H6a2 2 0 0 1-2-2V10a2 2 0 0 1 2-2h1V6a5 5 0 0 1 10 0v2h1z");

        // Login Button
        Button loginButton = new Button("Entrar");
        loginButton.setMaxWidth(Double.MAX_VALUE);
        loginButton.getStyleClass().add("login-button");

        loginButton.setOnAction(e -> {
            if (emailField.getText().isEmpty() || passwordField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Erro no login", "Por favor, preencha todos os campos");
            } else {
                Stage stage = (Stage) loginButton.getScene().getWindow();
                MainLayout mainLayout = new MainLayout();
                Scene scene = stage.getScene();
                scene.setRoot(mainLayout.getLayout());
                stage.setFullScreen(true);
            }
        });

        card.getChildren().addAll(iconContainer, title, subtitle, emailLabel, emailInput, passwordLabel, passwordInput, loginButton);
        root.getChildren().add(card);

        return root;
    }

    private Node createIconContainer() {
        StackPane iconContainer = new StackPane();
        iconContainer.getStyleClass().add("plane-icon-container");

        SVGPath planeIcon = new SVGPath();
        planeIcon.setContent("M17.8 19.2 16 11l3.5-3.5C21 6 21.5 4 21 3c-1-.5-3 0-4.5 1.5L13 8 4.8 6.2c-.5-.1-.9.1-1.1.5l-.3.5c-.2.5-.1 1 .3 1.3L9 12l-2 3H4l-1 1 3 2 2 3 1-1v-3l3-2 3.5 5.3c.3.4.8.5 1.3.3l.5-.2c.4-.3.6-.7.5-1.2z");
        planeIcon.getStyleClass().add("plane-icon");

        iconContainer.getChildren().add(planeIcon);
        return iconContainer;
    }

    private Node createIconTextField(TextField textField, String svgContent) {
        StackPane stack = new StackPane();
        SVGPath icon = new SVGPath();
        icon.setContent(svgContent);
        icon.getStyleClass().add("input-icon");
        StackPane.setAlignment(icon, Pos.CENTER_LEFT);
        StackPane.setMargin(icon, new Insets(0, 0, 0, 10));

        textField.getStyleClass().add("input-field");
        textField.setPadding(new Insets(10, 10, 10, 40));

        stack.getChildren().addAll(textField, icon);
        return stack;
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}