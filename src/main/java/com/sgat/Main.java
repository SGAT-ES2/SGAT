package com.sgat;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 1. Cria o texto "Funcionou"
        Label label = new Label("Funcionou");

        // 2. Coloca o texto em um painel
        StackPane root = new StackPane(label);

        // 3. Cria a cena (o conteúdo)
        Scene scene = new Scene(root, 300, 150);

        // 4. Define o título da janela e mostra na tela
        primaryStage.setTitle("Funcionou burro!!! DSADSASAD");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}