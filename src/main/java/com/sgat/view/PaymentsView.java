package com.sgat.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color; 

public class PaymentsView { 

    private final VBox view;

    public PaymentsView() {
        view = new VBox(24);
        view.setPadding(new Insets(24));
        view.getStyleClass().add("dashboard-pane");

        Node header = createHeader();
        Node summaryCards = createSummaryCards();
        Node historyCard = createHistoryCard();

        view.getChildren().addAll(header, summaryCards, historyCard);
        VBox.setVgrow(historyCard, Priority.ALWAYS); 
    }

    public Node getView() {
        return view;
    }

    // --- 1. Cabeçalho (COM BOTÃO VERDE MAIOR) ---
    private Node createHeader() {
        HBox header = new HBox(12);
        header.setAlignment(Pos.CENTER_LEFT);

        VBox titleBox = new VBox(-4);
        Label title = new Label("Pagamentos");
        title.getStyleClass().add("page-title");
        
        Label subtitle = new Label("Todos os pagamentos das reservas"); 
        subtitle.getStyleClass().add("page-subtitle");
        titleBox.getChildren().addAll(title, subtitle);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Botão Registrar Pagamento (MAIOR E VERDE)
        Button registerButton = new Button("+ Registrar Pagamento");
        
        registerButton.setStyle(
            "-fx-background-color: #4CAF50;" + 
            "-fx-text-fill: white;" +
            "-fx-font-weight: bold;" +
            "-fx-padding: 12px 24px;" +
            "-fx-background-radius: 8px;" +
            "-fx-cursor: hand;" +
            "-fx-font-size: 14px;"
        );
        
        registerButton.setOnAction(event -> {
            System.out.println("Ação: Abrir formulário para registrar um novo pagamento.");
        });

        header.getChildren().addAll(titleBox, spacer, registerButton);
        return header;
    }

    // --- 2. Cards de Sumário (Receita e Pendente) ---
    private Node createSummaryCards() {
        HBox summaryBox = new HBox(24);
        
        VBox receivedRevenue = createRevenueCard("Receita Recebida", "R$ 41.000,00", "Total de pagamentos confirmados", Color.web("#388e3c"), "$"); 
        HBox.setHgrow(receivedRevenue, Priority.ALWAYS);

        VBox pendingValues = createRevenueCard("Valores Pendentes", "R$ 36.600,00", "Aguardando confirmação de pagamento", Color.web("#ef6c00"), null); 
        HBox.setHgrow(pendingValues, Priority.ALWAYS);

        summaryBox.getChildren().addAll(receivedRevenue, pendingValues);
        return summaryBox;
    }

    private VBox createRevenueCard(String title, String value, String description, Color valueColor, String icon) {
        VBox box = new VBox(8);
        box.getStyleClass().add("stat-card"); 
        box.setPadding(new Insets(20));
        box.setStyle("-fx-background-color: white;");

        HBox titleContainer = new HBox(8);
        Label lblTitle = new Label(title);
        lblTitle.getStyleClass().add("stat-card-title");
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        if (icon != null) {
            Label lblIcon = new Label(icon);
            lblIcon.setStyle("-fx-font-size: 18px; -fx-text-fill: #999999;");
            titleContainer.getChildren().addAll(lblTitle, spacer, lblIcon);
        } else {
            titleContainer.getChildren().addAll(lblTitle, spacer);
        }

        Label lblValue = new Label(value);
        lblValue.getStyleClass().add("stat-card-value");
        lblValue.setTextFill(valueColor);

        Label lblDescription = new Label(description);
        lblDescription.getStyleClass().add("stat-card-description");

        box.getChildren().addAll(titleContainer, lblValue, lblDescription);
        
        box.setOnMouseClicked(event -> {
            System.out.println("Ação: Card '" + title + "' clicado. Abrir filtro de transações.");
        });

        return box;
    }

    // --- 3. Histórico de Pagamentos (Simulação de Tabela) ---
    private Node createHistoryCard() {
        VBox card = new VBox(12);
        card.getStyleClass().add("info-card");
        VBox.setVgrow(card, Priority.ALWAYS);

        VBox titleBox = new VBox(-4);
        Label title = new Label("Histórico de Pagamentos");
        title.getStyleClass().add("info-card-title");
        Label subtitle = new Label("Todos os pagamentos registrados no sistema");
        subtitle.getStyleClass().add("info-card-subtitle");
        titleBox.getChildren().addAll(title, subtitle);

        HBox headerRow = createHistoryHeader();
        
        VBox tableBody = new VBox(0); 
        tableBody.getStyleClass().add("table-view"); 
        VBox.setVgrow(tableBody, Priority.ALWAYS);
        
        // Linhas de Dados com STATUS CORRIGIDO
        tableBody.getChildren().add(createHistoryRow("RES-001\nMaria Silva", "R$ 17.000,00", "R$ 17.000,00", "R$ 0,00", "Cartão de Crédito", "10/02/2025", "Paga")); 
        tableBody.getChildren().add(createHistoryRow("RES-002\nJoão\nSantos", "R$ 48.000,00", "R$ 24.000,00", "R$ 24.000,00", "Transferência", "12/02/2025", "Parcial")); // AGORA É "Parcial"
        tableBody.getChildren().add(createHistoryRow("RES-003\nAna\nCosta", "R$ 12.600,00", "R$ 0,00", "R$ 12.600,00", "-", "-", "Pendente")); // Continua "Pendente" (para o status crítico/vermelho)

        card.getChildren().addAll(titleBox, headerRow, tableBody);
        return card;
    }

    private HBox createHistoryHeader() {
        HBox header = new HBox(0);
        header.getStyleClass().add("column-header-background"); 
        header.setPadding(new Insets(10, 16, 10, 16));

        Label resLabel = createHeaderLabel("Reserva", 100);
        Label totalLabel = createHeaderLabel("Valor Total", 100);
        Label pagoLabel = createHeaderLabel("Valor Pago", 100);
        Label pendenteLabel = createHeaderLabel("Pendente", 100);
        Label metodoLabel = createHeaderLabel("Método", 120);
        Label dataLabel = createHeaderLabel("Data", 100);
        Label statusLabel = createHeaderLabel("Status", 80);

        HBox.setHgrow(resLabel, Priority.SOMETIMES);
        HBox.setHgrow(totalLabel, Priority.SOMETIMES);
        HBox.setHgrow(pagoLabel, Priority.SOMETIMES);
        HBox.setHgrow(pendenteLabel, Priority.SOMETIMES);
        HBox.setHgrow(metodoLabel, Priority.SOMETIMES);
        HBox.setHgrow(dataLabel, Priority.SOMETIMES);
        HBox.setHgrow(statusLabel, Priority.SOMETIMES);

        header.getChildren().addAll(resLabel, totalLabel, pagoLabel, pendenteLabel, metodoLabel, dataLabel, statusLabel);
        return header;
    }
    
    private Label createHeaderLabel(String text, double minWidth) {
        Label label = new Label(text);
        label.getStyleClass().add("column-header"); 
        label.setMinWidth(minWidth);
        return label;
    }

    private Node createHistoryRow(String reservation, String total, String paid, String pending, String method, String date, String status) {
        HBox row = new HBox(0);
        row.getStyleClass().add("list-item"); 
        row.setPadding(new Insets(12, 16, 12, 16));
        row.getStyleClass().add("clickable-list-item"); 

        // Coluna 1: Reserva / Nome
        VBox resBox = new VBox(-2);
        Label lblRes = new Label(reservation.split("\n")[0]);
        lblRes.getStyleClass().add("list-item-package-name");
        
        String clientName = (reservation.split("\n").length > 1) ? 
                            reservation.substring(reservation.indexOf('\n') + 1).replace('\n', ' ') : "";
        Label lblClient = new Label(clientName.trim());
        lblClient.getStyleClass().add("list-item-package-info");
        resBox.getChildren().addAll(lblRes, lblClient);
        resBox.setMinWidth(100);
        HBox.setHgrow(resBox, Priority.SOMETIMES);


        Label lblTotal = createDataLabel(total, "#666666", 100);
        Label lblPaid = createDataLabel(paid, "#388e3c", 100); 
        Label lblPending = createDataLabel(pending, "#ef6c00", 100); 
        Label lblMethod = createDataLabel(method, "#424242", 120);
        Label lblDate = createDataLabel(date, "#666666", 100);

        // Coluna Status (Badge) - Lógica ATUALIZADA pelo texto do status
        Label lblStatus = new Label(status);
        lblStatus.getStyleClass().add("status-label");
        
        if (status.equalsIgnoreCase("Paga")) {
            // Verde: Pagamento 100%
            lblStatus.getStyleClass().add("status-confirmada");
        } else if (status.equalsIgnoreCase("Parcial")) {
            // Laranja: Pagamento incompleto/parcial
            lblStatus.getStyleClass().add("status-pendente");
        } else if (status.equalsIgnoreCase("Pendente")) {
            // Vermelho: Pagamento crítico/não iniciado (R$ 0,00 pago)
            lblStatus.getStyleClass().add("status-cancelada"); 
        }
        
        lblStatus.setMinWidth(80);
        HBox.setHgrow(lblStatus, Priority.SOMETIMES);
        lblStatus.setAlignment(Pos.CENTER);


        row.getChildren().addAll(resBox, lblTotal, lblPaid, lblPending, lblMethod, lblDate, lblStatus);

        row.setOnMouseClicked(event -> {
            System.out.println("Ação: Linha de pagamento clicada. Abrir detalhes da Reserva: " + reservation.split("\n")[0]);
        });
        
        return row;
    }
    
    private Label createDataLabel(String text, String colorHex, double minWidth) {
        Label label = new Label(text);
        label.getStyleClass().add("table-cell"); 
        label.setStyle("-fx-text-fill: " + colorHex + ";");
        label.setMinWidth(minWidth);
        label.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(label, Priority.SOMETIMES);
        return label;
    }
}