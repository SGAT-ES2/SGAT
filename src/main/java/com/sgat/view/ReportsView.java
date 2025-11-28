package com.sgat.view;

import com.sgat.controller.ReportData;
import com.sgat.controller.ReportsController;
import com.sgat.controller.PDFGenerator.PdfExportMode;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ReportsView {

    private final VBox view;
    private ReportsController controller;

    // 1. CRIAMOS OS LABELS AQUI DIRETO (Para garantir que existem)
    private final Label lblTotalReservasValue = new Label("0");
    private final Label lblReceitaTotalValue = new Label("R$ 0,00");
    private final Label lblNovosClientesValue = new Label("0");

    private ComboBox<String> cmbYearSelector;
    private ComboBox<PdfExportMode> cmbExportMode;

    public ReportsView() {
        view = new VBox(24);
        view.setPadding(new Insets(24));
        view.getStyleClass().add("dashboard-pane");

        // Inicializa estilos dos labels principais
        styleLabel(lblTotalReservasValue);
        styleLabel(lblReceitaTotalValue);
        styleLabel(lblNovosClientesValue);

        Node header = createHeader();
        Node summaryCards = createSummaryCards();
        Node detailsSection = createDetailsSection();

        view.getChildren().addAll(header, summaryCards, detailsSection);
    }

    private void styleLabel(Label lbl) {
        lbl.getStyleClass().add("stat-card-value");
    }

    public Node getView() { return view; }

    // --- CABEÇALHO ---
    private Node createHeader() {
        HBox header = new HBox(12);
        header.setAlignment(Pos.CENTER_LEFT);

        VBox titleBox = new VBox(-4);
        Label title = new Label("Relatórios");
        title.getStyleClass().add("page-title");
        Label subtitle = new Label("Análises financeiras e operacionais");
        subtitle.getStyleClass().add("page-subtitle");
        titleBox.getChildren().addAll(title, subtitle);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Seletor de Ano
        cmbYearSelector = new ComboBox<>(FXCollections.observableArrayList("2027", "2026","2025", "2024", "2023"));
        cmbYearSelector.setValue("2025");
        String comboStyle = "-fx-border-color: #E0E0E0; -fx-border-width: 1; -fx-border-radius: 8; -fx-padding: 4 8; -fx-background-color: white; -fx-font-weight: bold; -fx-cursor: hand;";
        cmbYearSelector.setStyle(comboStyle);

        cmbYearSelector.setOnAction(event -> {
            if (controller != null) controller.updateView(cmbYearSelector.getValue());
        });

        // Seletor de Exportação
        cmbExportMode = new ComboBox<>();
        cmbExportMode.getItems().setAll(PdfExportMode.values());
        cmbExportMode.setValue(PdfExportMode.COMPLETE);
        cmbExportMode.setStyle(comboStyle);
        cmbExportMode.setPrefWidth(120);

        Button exportButton = new Button("Exportar PDF");
        exportButton.getStyleClass().add("export-button");
        exportButton.setOnAction(event -> { if (controller != null) controller.exportPDF(); });

        header.getChildren().addAll(titleBox, spacer, cmbYearSelector, cmbExportMode, exportButton);
        return header;
    }

    // --- CARDS (AGORA USANDO OS LABELS CERTOS) ---
    private Node createSummaryCards() {
        HBox summaryBox = new HBox(24);

        // Passamos o Label pronto para o método
        // Substituímos os emojis por caracteres simples: # (numero), $ (dinheiro), @ (pessoas)
        VBox cardReservas = createMetricCard("Total de Reservas", lblTotalReservasValue, "+15% vs ano anterior", "#");
        HBox.setHgrow(cardReservas, Priority.ALWAYS);

        VBox cardReceita = createMetricCard("Receita Total", lblReceitaTotalValue, "+18% vs ano anterior", "$");
        HBox.setHgrow(cardReceita, Priority.ALWAYS);

        VBox cardClientes = createMetricCard("Novos Clientes", lblNovosClientesValue, "+22% vs ano anterior", "@");
        HBox.setHgrow(cardClientes, Priority.ALWAYS);

        summaryBox.getChildren().addAll(cardReservas, cardReceita, cardClientes);
        return summaryBox;
    }

    // Método ajustado para receber o Label já criado
    private VBox createMetricCard(String title, Label valueLabel, String variation, String icon) {
        VBox box = new VBox(4);
        box.getStyleClass().add("stat-card");
        box.setPadding(new Insets(20));
        box.setStyle("-fx-background-color: white;");

        HBox labelAndIcon = new HBox(5);
        labelAndIcon.setAlignment(Pos.CENTER_LEFT);

        Label lblTitle = new Label(title);
        lblTitle.getStyleClass().add("stat-card-title");
        Label lblIcon = new Label(icon);
        lblIcon.setStyle("-fx-font-size: 20px; -fx-text-fill: #1E88E5; -fx-font-weight: bold;");

        Region innerSpacer = new Region();
        HBox.setHgrow(innerSpacer, Priority.ALWAYS);
        labelAndIcon.getChildren().addAll(lblTitle, innerSpacer, lblIcon);

        VBox valueContainer = new VBox();
        valueContainer.getChildren().add(valueLabel); // Adiciona o label que passamos

        Label lblVariation = new Label(variation);
        lblVariation.getStyleClass().add("stat-card-description");
        lblVariation.setTextFill(Color.web("#388e3c"));

        Region middleSpacer = new Region();
        VBox.setVgrow(middleSpacer, Priority.ALWAYS);

        box.getChildren().addAll(labelAndIcon, valueContainer, middleSpacer, lblVariation);
        return box;
    }

    // --- ATUALIZAÇÃO DA TELA (Agora com debug para você ver) ---
    public void updateSummary(ReportData data) {
        System.out.println(">>> TELA RECEBEU: Reservas=" + data.totalReservas + " | Receita=" + data.receitaTotal);

        // Atualização direta e segura
        lblTotalReservasValue.setText(data.totalReservas);
        lblReceitaTotalValue.setText(data.receitaTotal);
        lblNovosClientesValue.setText(data.novosClientes);

        if (cmbYearSelector != null) cmbYearSelector.setValue(data.year);
    }

    // --- MÉTODOS AUXILIARES ---
    public void setController(ReportsController controller) { this.controller = controller; }
    public String getSelectedYear() { return cmbYearSelector.getValue(); }
    public PdfExportMode getSelectedExportMode() { return cmbExportMode.getValue(); }

    // --- SEÇÃO DE DETALHES (FIXA POR ENQUANTO) ---
    private Node createDetailsSection() {
        VBox details = new VBox(24);
        HBox row = new HBox(24);
        row.getChildren().addAll(createPlaceholderCard("Pacotes Populares"), createPlaceholderCard("Clientes Frequentes"));
        details.getChildren().addAll(row, createPlaceholderCard("Desempenho Mensal"));
        return details;
    }

    private VBox createPlaceholderCard(String title) {
        VBox card = new VBox(12);
        card.getStyleClass().add("info-card");
        card.getChildren().add(new Label(title));
        card.setPadding(new Insets(20));
        return card;
    }
}