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
    private Label lblTotalReservasValue;
    private Label lblReceitaTotalValue;
    private Label lblNovosClientesValue;

    private ComboBox<String> cmbYearSelector;

    private ComboBox<PdfExportMode> cmbExportMode;

    public ReportsView() {
        view = new VBox(24);
        view.setPadding(new Insets(24));
        view.getStyleClass().add("dashboard-pane");

        Node header = createHeader();
        Node summaryCards = createSummaryCards();
        Node detailsSection = createDetailsSection();

        view.getChildren().addAll(header, summaryCards, detailsSection);
    }

    public Node getView() {
        return view;
    }

    private Node createHeader() {
        HBox header = new HBox(12);
        header.setAlignment(Pos.CENTER_LEFT);

        VBox titleBox = new VBox(-4);
        Label title = new Label("Relatórios");
        title.getStyleClass().add("page-title");
        Label subtitle = new Label("Análises e estatísticas do sistema");
        subtitle.getStyleClass().add("page-subtitle");
        titleBox.getChildren().addAll(title, subtitle);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        cmbYearSelector = new ComboBox<>(FXCollections.observableArrayList("2025", "2024", "2023"));
        cmbYearSelector.setValue("2025");

        String comboStyle = "-fx-border-color: #E0E0E0; -fx-border-width: 1; -fx-border-radius: 8; -fx-padding: 4 8; -fx-background-color: white; -fx-font-weight: bold; -fx-cursor: hand;";
        cmbYearSelector.setStyle(comboStyle);

        cmbYearSelector.setOnAction(event -> {
            String selectedYear = cmbYearSelector.getValue();
            if (selectedYear != null && controller != null) {
                controller.updateView(selectedYear);
                System.out.println("Ação: Trocando relatório para o ano selecionado: " + selectedYear);
            }
        });

        cmbExportMode = new ComboBox<>();
        cmbExportMode.getItems().setAll(PdfExportMode.values());
        cmbExportMode.setValue(PdfExportMode.COMPLETE);
        cmbExportMode.setStyle(comboStyle);
        cmbExportMode.setPrefWidth(110);

        Button exportButton = new Button("📈 Exportar PDF");
        exportButton.getStyleClass().add("export-button");

        exportButton.setOnAction(event -> {
            System.out.println("Ação: Exportando relatório para PDF...");
            if (controller != null) {
                controller.exportPDF();
            }
        });

        header.getChildren().addAll(titleBox, spacer, cmbYearSelector, cmbExportMode, exportButton);
        return header;
    }

    private Node createSummaryCards() {
        HBox summaryBox = new HBox(24);

        VBox totalReservas = createMetricCard("Total de Reservas", "64", "↗ +15% vs período anterior", "📅");
        lblTotalReservasValue = (Label) ((VBox) totalReservas.getChildren().get(1)).getChildren().get(0);
        HBox.setHgrow(totalReservas, Priority.ALWAYS);

        VBox receitaTotal = createMetricCard("Receita Total", "R$ 519.200", "↗ +18% vs período anterior", "$");
        lblReceitaTotalValue = (Label) ((VBox) receitaTotal.getChildren().get(1)).getChildren().get(0);
        HBox.setHgrow(receitaTotal, Priority.ALWAYS);

        VBox novosClientes = createMetricCard("Novos Clientes", "28", "↗ +22% vs período anterior", "👥");
        lblNovosClientesValue = (Label) ((VBox) novosClientes.getChildren().get(1)).getChildren().get(0);
        HBox.setHgrow(novosClientes, Priority.ALWAYS);

        summaryBox.getChildren().addAll(totalReservas, receitaTotal, novosClientes);
        return summaryBox;
    }

    private VBox createMetricCard(String label, String value, String variation, String icon) {
        VBox box = new VBox(4);
        box.getStyleClass().add("stat-card");
        box.setPadding(new Insets(20));
        box.setStyle("-fx-background-color: white;");

        HBox labelAndIcon = new HBox(5);
        labelAndIcon.setAlignment(Pos.CENTER_LEFT);

        Label lblLabel = new Label(label);
        lblLabel.getStyleClass().add("stat-card-title");

        Label lblIcon = new Label(icon);
        lblIcon.setStyle("-fx-font-size: 20px; -fx-text-fill: #1E88E5;");

        Region innerSpacer = new Region();
        HBox.setHgrow(innerSpacer, Priority.ALWAYS);
        labelAndIcon.getChildren().addAll(lblLabel, innerSpacer, lblIcon);

        VBox valueContainer = new VBox();
        Label lblValue = new Label(value);
        lblValue.getStyleClass().add("stat-card-value");
        valueContainer.getChildren().add(lblValue);

        Label lblVariation = new Label(variation);
        lblVariation.getStyleClass().add("stat-card-description");
        lblVariation.setTextFill(Color.web("#388e3c"));

        Region middleSpacer = new Region();
        VBox.setVgrow(middleSpacer, Priority.ALWAYS);

        box.getChildren().addAll(labelAndIcon, valueContainer, middleSpacer, lblVariation);

        box.setOnMouseClicked(event -> {
            System.out.println("Ação: Card de métrica '" + label + "' clicado.");
        });

        return box;
    }

    private Node createDetailsSection() {
        VBox detailsContainer = new VBox(24);

        HBox topDetailsRow = new HBox(24);
        topDetailsRow.getStyleClass().add("info-grid");

        VBox popularPackages = createPopularPackagesCard();
        HBox.setHgrow(popularPackages, Priority.ALWAYS);

        VBox frequentClients = createFrequentClientsCard();
        HBox.setHgrow(frequentClients, Priority.ALWAYS);

        topDetailsRow.getChildren().addAll(popularPackages, frequentClients);

        HBox bottomDetailsRow = new HBox(24);
        VBox monthlyPerformance = createMonthlyPerformanceCard();
        HBox.setHgrow(monthlyPerformance, Priority.ALWAYS);
        bottomDetailsRow.getChildren().add(monthlyPerformance);

        detailsContainer.getChildren().addAll(topDetailsRow, bottomDetailsRow);

        return detailsContainer;
    }

    private VBox createPopularPackagesCard() {
        VBox card = new VBox(12);
        card.getStyleClass().add("info-card");

        Label title = new Label("📦 Pacotes Mais Populares");
        title.getStyleClass().add("info-card-title");
        Label subtitle = new Label("Ranking de vendas por pacote turístico");
        subtitle.getStyleClass().add("info-card-subtitle");

        VBox ranking = new VBox(0);

        ranking.getChildren().add(createRankingItem("1", "Paris Romântica", "28 vendas", "R$ 238.000", "+12%"));
        ranking.getChildren().add(createRankingItem("2", "Caribe Premium", "22 vendas", "R$ 264.000", "+8%"));
        ranking.getChildren().add(createRankingItem("3", "Amazônia Aventura", "18 vendas", "R$ 75.600", "+15%"));
        ranking.getChildren().add(createRankingItem("4", "Europa Clássica", "15 vendas", "R$ 210.000", "+5%"));
        ranking.getChildren().add(createRankingItem("5", "Ásia Exótica", "12 vendas", "R$ 180.000", "+20%"));

        card.getChildren().addAll(title, subtitle, ranking);
        return card;
    }

    private Node createRankingItem(String rank, String packageName, String salesCount, String revenue, String variation) {
        HBox item = new HBox(12);
        item.setAlignment(Pos.CENTER_LEFT);
        item.getStyleClass().add("list-item");

        Label lblRank = new Label(rank);
        lblRank.setStyle("-fx-background-color: #1E88E5; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 4 8; -fx-background-radius: 4;");

        VBox packageInfo = new VBox(-2);
        Label lblName = new Label(packageName);
        Label lblSales = new Label(salesCount);

        packageInfo.getChildren().addAll(lblName, lblSales);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        VBox revenueInfo = new VBox(-2);
        revenueInfo.setAlignment(Pos.CENTER_RIGHT);

        Label lblRevenue = new Label(revenue);
        lblRevenue.setStyle("-fx-text-fill: #388e3c; -fx-font-weight: bold;");

        Label lblVariation = new Label(variation);
        lblVariation.setTextFill(Color.web("#388e3c"));

        revenueInfo.getChildren().addAll(lblRevenue, lblVariation);

        item.getChildren().addAll(lblRank, packageInfo, spacer, revenueInfo);

        return item;
    }

    private VBox createFrequentClientsCard() {
        VBox card = new VBox(12);
        card.getStyleClass().add("info-card");

        Label title = new Label("👥 Clientes Frequentes");
        Label subtitle = new Label("Top clientes por número de viagens");

        VBox clientList = new VBox(0);

        clientList.getChildren().add(createClientItem("Ana Costa", "8 viagens realizadas", "R$ 96.000", "Janeiro 2025"));
        clientList.getChildren().add(createClientItem("Carlos Oliveira", "6 viagens realizadas", "R$ 78.000", "Dezembro 2024"));
        clientList.getChildren().add(createClientItem("Maria Silva", "5 viagens realizadas", "R$ 62.500", "Fevereiro 2025"));
        clientList.getChildren().add(createClientItem("Pedro Almeida", "4 viagens realizadas", "R$ 52.000", "Novembro 2024"));

        card.getChildren().addAll(title, subtitle, clientList);
        return card;
    }

    private Node createClientItem(String name, String tripCount, String revenue, String date) {
        HBox item = new HBox(12);
        item.setAlignment(Pos.CENTER_LEFT);

        Label avatar = new Label("👤");
        avatar.setStyle("-fx-font-size: 14px;");

        VBox clientInfo = new VBox(-2);
        Label lblName = new Label(name);
        Label lblTrips = new Label(tripCount);

        clientInfo.getChildren().addAll(lblName, lblTrips);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        VBox revenueInfo = new VBox(-2);
        revenueInfo.setAlignment(Pos.CENTER_RIGHT);
        Label lblRevenue = new Label(revenue);
        Label lblDate = new Label(date);

        revenueInfo.getChildren().addAll(lblRevenue, lblDate);

        item.getChildren().addAll(avatar, clientInfo, spacer, revenueInfo);

        return item;
    }

    private VBox createMonthlyPerformanceCard() {
        VBox card = new VBox(12);
        card.getStyleClass().add("info-card");
        card.setPadding(new Insets(20));

        HBox header = new HBox(8);
        Label titleIcon = new Label("📈");
        titleIcon.setStyle("-fx-font-size: 18px; -fx-text-fill: #1E88E5;");

        Label title = new Label("Desempenho Mensal");

        header.getChildren().addAll(titleIcon, title);

        Label subtitle = new Label("Reservas e receita por mês");

        VBox performanceList = new VBox(0);

        performanceList.getChildren().add(createMonthlyItem("Janeiro", "18 reservas", "R$ 145.200"));
        performanceList.getChildren().add(createMonthlyItem("Fevereiro", "22 reservas", "R$ 178.400"));
        performanceList.getChildren().add(createMonthlyItem("Março", "24 reservas", "R$ 195.600"));

        card.getChildren().addAll(header, subtitle, performanceList);

        return card;
    }

    private Node createMonthlyItem(String monthName, String reservationsCount, String revenue) {
        HBox item = new HBox(12);
        item.setAlignment(Pos.CENTER_LEFT);

        Label icon = new Label("📅");
        icon.setStyle("-fx-background-color: #E3F2FD; -fx-padding: 8; -fx-background-radius: 8; -fx-text-fill: #1E88E5; -fx-font-size: 14px;");

        VBox monthInfo = new VBox(-2);
        Label lblMonth = new Label(monthName);
        Label lblReservations = new Label(reservationsCount);

        monthInfo.getChildren().addAll(lblMonth, lblReservations);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label lblRevenue = new Label(revenue);
        lblRevenue.setStyle("-fx-font-weight: bold; -fx-font-size: 18px; -fx-text-fill: #388e3c;");

        item.getChildren().addAll(icon, monthInfo, spacer, lblRevenue);

        return item;
    }

    public void setController(ReportsController controller) {
        this.controller = controller;
        System.out.println("✅ ReportsView: Controller associado.");
    }

    public void updateSummary(ReportData data) {
        if (lblTotalReservasValue != null) lblTotalReservasValue.setText(data.totalReservas);
        if (lblReceitaTotalValue != null) lblReceitaTotalValue.setText(data.receitaTotal);
        if (lblNovosClientesValue != null) lblNovosClientesValue.setText(data.novosClientes);

        if (cmbYearSelector != null) {
            cmbYearSelector.setValue(data.year);
        }

        System.out.println("✅ ReportsView: Dados de resumo atualizados para o ano: " + data.year);
    }

    public String getSelectedYear() {
        if (cmbYearSelector != null) {
            return cmbYearSelector.getValue();
        }
        return "2025";
    }

    public PdfExportMode getSelectedExportMode() {
        if (cmbExportMode != null) {
            return cmbExportMode.getValue();
        }
        return PdfExportMode.COMPLETE;
    }
}