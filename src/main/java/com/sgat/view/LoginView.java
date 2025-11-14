package com.sgat.view;

import com.sgat.view.MainLayout;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignK;
import org.kordamp.ikonli.materialdesign2.MaterialDesignL;

public class LoginView {

    public Parent getView() {
        StackPane root = new StackPane();
        root.getStyleClass().add("login-root");

        VBox card = new VBox(15);
        card.getStyleClass().add("card");
        card.setMaxWidth(400);

        Node iconContainer = createIconContainer();
        Label title = new Label("TravelManager");
        title.getStyleClass().add("card-title");
        Label subtitle = new Label("Sistema de Gestão de Agência de Turismo");
        subtitle.getStyleClass().add("card-subtitle");

        Label errorLabel = new Label();
        errorLabel.getStyleClass().add("error-label");
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);

        Label emailLabel = new Label("Email");
        TextField emailField = new TextField();
        emailField.setPromptText("seu@email.com");
        FontIcon emailIcon = new FontIcon(MaterialDesignK.KEYBOARD_OUTLINE);
        emailIcon.setIconSize(18);
        Node emailInput = createIconTextField(emailField, emailIcon);

        Label passwordLabel = new Label("Senha");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("••••••••");
        FontIcon passwordIcon = new FontIcon(MaterialDesignL.LOCK_OUTLINE);
        passwordIcon.setIconSize(18);
        Node passwordInput = createIconTextField(passwordField, passwordIcon);

        Button loginButton = new Button("Entrar");
        loginButton.getStyleClass().add("login-button");
        loginButton.setDefaultButton(true);

        loginButton.setOnAction(e -> {
            String username = emailField.getText();
            String password = passwordField.getText();
            Stage stage = (Stage) loginButton.getScene().getWindow();

            errorLabel.setVisible(false);
            errorLabel.setManaged(false);

            if (username.isEmpty() || password.isEmpty()) {
                showError(errorLabel, "Por favor, preencha todos os campos");
            } else if (username.equals("admin") && password.equals("admin123")) {
                MainLayout mainLayout = new MainLayout(stage);
                Scene scene = stage.getScene();
                scene.setRoot(mainLayout.getLayout());
                stage.setFullScreen(true);
            } else {
                showError(errorLabel, "Usuário ou senha inválidos.");
            }
        });


        card.getChildren().addAll(iconContainer, title, subtitle,
                emailLabel, emailInput,
                passwordLabel, passwordInput,
                errorLabel,
                loginButton);
        root.getChildren().add(card);

        return root;
    }

    private void showError(Label errorLabel, String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        errorLabel.setManaged(true);
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

    private Node createIconTextField(TextField textField, Node icon) {
        StackPane stack = new StackPane();
        icon.getStyleClass().add("input-icon");
        StackPane.setAlignment(icon, Pos.CENTER_LEFT);
        StackPane.setMargin(icon, new Insets(0, 0, 0, 10));

        textField.getStyleClass().add("input-field");
        textField.setPadding(new Insets(10, 10, 10, 40));

        stack.getChildren().addAll(textField, icon);
        return stack;
    }
}