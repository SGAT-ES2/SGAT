package com.sgat.view;

import java.text.NumberFormat;
import java.util.Locale;

import com.sgat.controller.PaymentsController;
import com.sgat.model.Pagamento; 

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node; 
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox; 
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class PaymentsView { 

    private final VBox view;
    private PaymentsController controller;
    private VBox tableBody; 

    // 🚨 NOVOS CAMPOS: Referências aos labels dos cards de sumário
    private Label receivedValueLabel; 
    private Label pendingValueLabel;  

    // --- Definições de Larguras de Colunas (AJUSTE FORÇADO) ---
    // Larguras mínimas/preferenciais para controle
    private static final double COL_RES_WIDTH = 200; // Maior espaço garantido para a Reserva
    private static final double COL_VAL_WIDTH = 120; // Largura suficiente para valores e o R$
    private static final double COL_MET_WIDTH = 130; 
    private static final double COL_DAT_WIDTH = 120;
    private static final double COL_STA_WIDTH = 80;
    private static final double COLUMN_GAP = 12; // Espaçamento fixo entre colunas (substitui o spacer)

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
    
    public void setController(PaymentsController controller) {
        this.controller = controller;
        refreshAll(); 
    }
    
    public void setPaymentsList(ObservableList<Pagamento> list) {
        refreshAll();
    }
    
    public void refreshAll() {
        if (controller == null) return;
        
        updateSummaryCards(); 
        refreshHistory();     
    }
    
    private void updateSummaryCards() {
        if (controller == null || receivedValueLabel == null || pendingValueLabel == null) return;
        
        double[] totais = controller.calcularTotaisDePagamento();
        double totalPago = totais[0];
        double totalPendente = totais[1];
        
        receivedValueLabel.setText(formatCurrency(totalPago));
        pendingValueLabel.setText(formatCurrency(totalPendente));
    }
    
    private String formatCurrency(double value) {
        Locale localeBR = new Locale("pt", "BR");
        NumberFormat nf = NumberFormat.getCurrencyInstance(localeBR);
        return nf.format(value);
    }
    
    public void refreshHistory() {
        if (controller == null || tableBody == null) return;
        
        tableBody.getChildren().clear();
        
        for (Pagamento p : controller.getPagamentos()) {
            
            double totalValue = controller.getReservationTotal(p.getReserva()); 
            double pago = p.getValor();
            double pendente = totalValue - pago;
            
            String status = (pago >= totalValue && totalValue > 0) ? "Paga" : (pago > 0 ? "Parcial" : "Pendente");
            
            String totalFormatted = formatCurrency(totalValue);
            String pagoFormatted = formatCurrency(pago);
            String pendenteFormatted = formatCurrency(pendente);
            
            tableBody.getChildren().add(
                createHistoryRow(
                    p.getReserva(),
                    totalFormatted, 
                    pagoFormatted, 
                    pendenteFormatted, 
                    p.getMetodo(), 
                    p.getData() != null ? p.getData().toString() : "-", 
                    status
                )
            );
        }
    }
    
    // --- 1. CABEÇALHO (Omitido por brevidade) ---
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
            Stage ownerStage = (Stage) registerButton.getScene().getWindow();
            if (controller != null) {
                controller.abrirDialogPagamento(ownerStage);
            }
        });

        header.getChildren().addAll(titleBox, spacer, registerButton);
        return header;
    }

    // --- 2. CARDS DE SUMÁRIO (Omitido por brevidade) ---
    private Node createSummaryCards() {
        HBox summaryBox = new HBox(24);
        
        VBox receivedRevenue = createRevenueCard("Receita Recebida", "R$ 0,00", "Total de pagamentos confirmados", Color.web("#388e3c"), "$", true); 
        HBox.setHgrow(receivedRevenue, Priority.ALWAYS);

        VBox pendingValues = createRevenueCard("Valores Pendentes", "R$ 0,00", "Aguardando confirmação de pagamento", Color.web("#ef6c00"), null, false); 
        HBox.setHgrow(pendingValues, Priority.ALWAYS);

        summaryBox.getChildren().addAll(receivedRevenue, pendingValues);
        return summaryBox;
    }

    private VBox createRevenueCard(String title, String initialValue, String description, Color valueColor, String icon, boolean isReceived) {
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

        Label lblValue = new Label(initialValue);
        lblValue.getStyleClass().add("stat-card-value");
        lblValue.setTextFill(valueColor);

        if (isReceived) {
            this.receivedValueLabel = lblValue;
        } else {
            this.pendingValueLabel = lblValue;
        }

        Label lblDescription = new Label(description);
        lblDescription.getStyleClass().add("stat-card-description");

        box.getChildren().addAll(titleContainer, lblValue, lblDescription);
        
        box.setOnMouseClicked(event -> {
            System.out.println("Ação: Card '" + title + "' clicado. Abrir filtro de transações.");
        });

        return box;
    }

    // --- 3. HISTÓRICO DE PAGAMENTOS (Ajustado) ---
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
        
        tableBody = new VBox(0); 
        tableBody.getStyleClass().add("table-view"); 
        VBox.setVgrow(tableBody, Priority.ALWAYS);
        
        card.getChildren().addAll(titleBox, headerRow, tableBody);
        return card;
    }

    private HBox createHistoryHeader() {
        // 🚨 Adicionado espaçamento fixo ao HBox para separar colunas
        HBox header = new HBox(COLUMN_GAP); 
        header.getStyleClass().add("column-header-background"); 
        header.setPadding(new Insets(10, 16, 10, 16));

        // 🚨 Configuração de Larguras e Prioridades
        Label resLabel = createHeaderLabel("Reserva", COL_RES_WIDTH, Pos.CENTER_LEFT); 
        Label totalLabel = createHeaderLabel("Valor Total", COL_VAL_WIDTH, Pos.CENTER_RIGHT); 
        Label pagoLabel = createHeaderLabel("Valor Pago", COL_VAL_WIDTH, Pos.CENTER_RIGHT); 
        Label pendenteLabel = createHeaderLabel("Pendente", COL_VAL_WIDTH, Pos.CENTER_RIGHT); 
        
        // 🚨 SPACER REMOVIDO
        
        Label metodoLabel = createHeaderLabel("Método", COL_MET_WIDTH, Pos.CENTER_LEFT); 
        Label dataLabel = createHeaderLabel("Data", COL_DAT_WIDTH, Pos.CENTER_LEFT); 
        Label statusLabel = createHeaderLabel("Status", COL_STA_WIDTH, Pos.CENTER); 

        // PRIORIDADES: Reserva deve absorver mais espaço, as colunas de valor crescem SOMETIMES.
        HBox.setHgrow(resLabel, Priority.ALWAYS); // Crescimento máximo para a Reserva
        HBox.setHgrow(totalLabel, Priority.SOMETIMES);
        HBox.setHgrow(pagoLabel, Priority.SOMETIMES);
        HBox.setHgrow(pendenteLabel, Priority.SOMETIMES);
        
        HBox.setHgrow(metodoLabel, Priority.SOMETIMES); 
        HBox.setHgrow(dataLabel, Priority.SOMETIMES); 
        HBox.setHgrow(statusLabel, Priority.NEVER); // Status não precisa crescer

        // 🚨 SPACER REMOVIDO, lista simplificada
        header.getChildren().addAll(resLabel, totalLabel, pagoLabel, pendenteLabel, metodoLabel, dataLabel, statusLabel);
        return header;
    }
    
    private Node createSpacer(double width) {
        Region spacer = new Region();
        spacer.setMinWidth(width);
        spacer.setPrefWidth(width);
        return spacer;
    }
    
    private Label createHeaderLabel(String text, double width, Pos alignment) {
        Label label = new Label(text);
        label.getStyleClass().add("column-header"); 
        label.setMinWidth(width);
        label.setPrefWidth(width); 
        label.setMaxWidth(Double.MAX_VALUE);
        label.setAlignment(alignment); 
        return label;
    }

    private Node createHistoryRow(String reservation, String total, String paid, String pending, String method, String date, String status) {
        // 🚨 Adicionado espaçamento fixo ao HBox para separar colunas
        HBox row = new HBox(COLUMN_GAP); 
        row.getStyleClass().add("list-item"); 
        row.setPadding(new Insets(12, 16, 12, 16));
        row.getStyleClass().add("clickable-list-item"); 

        VBox resBox = new VBox(-2);
        String[] parts = reservation.split("\n");
        Label lblRes = new Label(parts[0]);
        lblRes.getStyleClass().add("list-item-package-name");
        
        String clientName = (parts.length > 1) ? parts[1].trim() : "";
        Label lblClient = new Label(clientName);
        lblClient.getStyleClass().add("list-item-package-info");
        resBox.getChildren().addAll(lblRes, lblClient);
        
        resBox.setMinWidth(COL_RES_WIDTH);
        resBox.setPrefWidth(COL_RES_WIDTH);
        HBox.setHgrow(resBox, Priority.ALWAYS); 
        resBox.setAlignment(Pos.CENTER_LEFT); 
        
        // Colunas de Valor: Usam COL_VAL_WIDTH e Priority.SOMETIMES
        Label lblTotal = createAlignedDataLabel(total, "#666666", COL_VAL_WIDTH, Pos.CENTER_RIGHT, Priority.SOMETIMES);
        Label lblPaid = createAlignedDataLabel(paid, "#388e3c", COL_VAL_WIDTH, Pos.CENTER_RIGHT, Priority.SOMETIMES); 
        Label lblPending = createAlignedDataLabel(pending, "#ef6c00", COL_VAL_WIDTH, Pos.CENTER_RIGHT, Priority.SOMETIMES); 
        
        // 🚨 SPACER REMOVIDO
        
        // Colunas de texto: Usam suas larguras e Priority.SOMETIMES
        Label lblMethod = createAlignedDataLabel(method, "#424242", COL_MET_WIDTH, Pos.CENTER_LEFT, Priority.SOMETIMES);
        Label lblDate = createAlignedDataLabel(date, "#666666", COL_DAT_WIDTH, Pos.CENTER_LEFT, Priority.SOMETIMES);

        Label lblStatus = new Label(status);
        lblStatus.getStyleClass().add("status-label");
        
        if (status.equalsIgnoreCase("Paga")) {
            lblStatus.getStyleClass().add("status-confirmada");
        } else if (status.equalsIgnoreCase("Parcial")) {
            lblStatus.getStyleClass().add("status-pendente");
        } else if (status.equalsIgnoreCase("Pendente")) {
            lblStatus.getStyleClass().add("status-cancelada"); 
        }
        
        lblStatus.setMinWidth(COL_STA_WIDTH);
        lblStatus.setPrefWidth(COL_STA_WIDTH);
        HBox.setHgrow(lblStatus, Priority.NEVER); // Status tem tamanho fixo
        lblStatus.setAlignment(Pos.CENTER); 


        // 🚨 SPACER REMOVIDO, lista simplificada
        row.getChildren().addAll(resBox, lblTotal, lblPaid, lblPending, lblMethod, lblDate, lblStatus);

        row.setOnMouseClicked(event -> {
            System.out.println("Ação: Linha de pagamento clicada. Abrir detalhes da Reserva: " + reservation.split("\n")[0]);
        });
        
        return row;
    }
    
    private Label createAlignedDataLabel(String text, String colorHex, double width, Pos alignment, Priority growPriority) {
        Label label = new Label(text);
        label.getStyleClass().add("table-cell"); 
        label.setStyle("-fx-text-fill: " + colorHex + ";");
        label.setMinWidth(width);
        label.setPrefWidth(width); 
        label.setAlignment(alignment); 
        label.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(label, growPriority); 
        return label;
    }
}