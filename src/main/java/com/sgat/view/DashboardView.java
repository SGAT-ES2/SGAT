package com.sgat.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.shape.SVGPath;

public class DashboardView {

    public Node getView() {
        VBox dashboardPane = new VBox();
        dashboardPane.getStyleClass().add("dashboard-pane");

        // Título
        Label title = new Label("Dashboard");
        title.getStyleClass().add("dashboard-title");

        Label subtitle = new Label("Visão geral do sistema de gestão.");
        subtitle.getStyleClass().add("dashboard-subtitle");

        // Grade de Estatísticas - Convertido para GridPane para responsividade
        GridPane statsGrid = new GridPane();
        statsGrid.getStyleClass().add("stats-grid");
        statsGrid.setHgap(16);
        statsGrid.setVgap(16);

        // Configura 4 colunas com largura percentual igual
        for (int i = 0; i < 4; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(25);
            col.setHgrow(Priority.ALWAYS);
            statsGrid.getColumnConstraints().add(col);
        }

        Node statCard1 = createStatCard("Pacotes Ativos", "78", "+5.2% vs. mês passado", "M19 21l-7-5-7 5V5a2 2 0 0 1 2-2h10a2 2 0 0 1 2 2z");
        Node statCard2 = createStatCard("Clientes Cadastrados", "1,204", "+120 novos este mês", "M16 21v-2a4 4 0 0 0-4-4H6a4 4 0 0 0-4 4v2");
        Node statCard3 = createStatCard("Reservas Pendentes", "32", "-10% vs. semana passada", "M8 22h8M12 16.5A2.5 2.5 0 0 1 9.5 14h5a2.5 2.5 0 0 1-2.5 2.5Z");
        Node statCard4 = createStatCard("Receita (Mês)", "R$ 45.8k", "+15% vs. mês passado", "M12 2v20M17 5H9.5a3.5 3.5 0 0 0 0 7h5a3.5 3.5 0 0 1 0 7H6");

        statsGrid.add(statCard1, 0, 0);
        statsGrid.add(statCard2, 1, 0);
        statsGrid.add(statCard3, 2, 0);
        statsGrid.add(statCard4, 3, 0);

        // Grade de Informações
        GridPane infoGrid = new GridPane();
        infoGrid.getStyleClass().add("info-grid");

        // Configura 2 colunas com largura percentual igual
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        col1.setHgrow(Priority.ALWAYS);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        col2.setHgrow(Priority.ALWAYS);
        infoGrid.getColumnConstraints().addAll(col1, col2);

        Node reservationsCard = createRecentReservationsCard();
        Node popularPackagesCard = createPopularPackagesCard();

        infoGrid.add(reservationsCard, 0, 0);
        infoGrid.add(popularPackagesCard, 1, 0);

        dashboardPane.getChildren().addAll(title, subtitle, statsGrid, infoGrid);
        return dashboardPane;
    }

    private Node createStatCard(String title, String value, String description, String iconSvgPath) {
        VBox card = new VBox();
        card.getStyleClass().add("stat-card");
        GridPane.setHgrow(card, Priority.ALWAYS); // Permite que o card cresça horizontalmente

        HBox header = new HBox();
        header.getStyleClass().add("stat-card-header");
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("stat-card-title");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        SVGPath icon = new SVGPath();
        icon.setContent(iconSvgPath);
        icon.getStyleClass().add("icon-svg");
        header.getChildren().addAll(titleLabel, spacer, icon);

        Label valueLabel = new Label(value);
        valueLabel.getStyleClass().add("stat-card-value");

        Label descriptionLabel = new Label(description);
        descriptionLabel.getStyleClass().add("stat-card-description");

        card.getChildren().addAll(header, valueLabel, descriptionLabel);
        return card;
    }

    private Node createRecentReservationsCard() {
        VBox card = new VBox();
        card.getStyleClass().add("info-card");
        GridPane.setHgrow(card, Priority.ALWAYS); // Permite que o card cresça horizontalmente

        Label title = new Label("Reservas Recentes");
        title.getStyleClass().add("info-card-title");
        Label subtitle = new Label("Últimas reservas confirmadas e pendentes.");
        subtitle.getStyleClass().add("info-card-subtitle");

        VBox list = new VBox();
        list.getChildren().addAll(
            createReservationItem("Ana Clara", "Pacote Férias em Cancún", "20/10/2025", "Confirmada"),
            createReservationItem("Bruno Costa", "Aventura na Patagônia", "18/10/2025", "Pendente"),
            createReservationItem("Carlos Dias", "Cruzeiro pelo Caribe", "15/10/2025", "Confirmada"),
            createReservationItem("Diana Souza", "Tour pela Europa Imperial", "14/10/2025", "Confirmada")
        );

        card.getChildren().addAll(title, subtitle, list);
        return card;
    }

    private Node createReservationItem(String customer, String packageName, String date, String status) {
        BorderPane item = new BorderPane();
        item.getStyleClass().add("list-item");

        VBox leftBox = new VBox(2);
        Label customerLabel = new Label(customer);
        customerLabel.getStyleClass().add("list-item-customer-name");
        Label packageLabel = new Label(packageName);
        packageLabel.getStyleClass().add("list-item-package-info");
        leftBox.getChildren().addAll(customerLabel, packageLabel);

        VBox rightBox = new VBox(2);
        rightBox.setAlignment(Pos.CENTER_RIGHT);
        Label dateLabel = new Label(date);
        dateLabel.getStyleClass().add("list-item-date");
        Label statusLabel = new Label(status);
        statusLabel.getStyleClass().addAll("status-label", status.equalsIgnoreCase("Confirmada") ? "status-confirmada" : "status-pendente");
        rightBox.getChildren().addAll(dateLabel, statusLabel);

        item.setLeft(leftBox);
        item.setRight(rightBox);
        return item;
    }

    private Node createPopularPackagesCard() {
        VBox card = new VBox();
        card.getStyleClass().add("info-card");
        GridPane.setHgrow(card, Priority.ALWAYS); // Permite que o card cresça horizontalmente

        Label title = new Label("Pacotes Mais Populares");
        title.getStyleClass().add("info-card-title");
        Label subtitle = new Label("Pacotes com maior número de vendas este mês.");
        subtitle.getStyleClass().add("info-card-subtitle");

        VBox list = new VBox();
        list.getChildren().addAll(
            createPackageItem("Tour pela Europa Imperial", 42, "R$ 120.5k", "M21 10c0 7-9 13-9 13s-9-6-9-13a9 9 0 0 1 18 0z"),
            createPackageItem("Aventura na Patagônia", 35, "R$ 95.2k", "M21 10c0 7-9 13-9 13s-9-6-9-13a9 9 0 0 1 18 0z"),
            createPackageItem("Férias em Cancún", 28, "R$ 88.9k", "M21 10c0 7-9 13-9 13s-9-6-9-13a9 9 0 0 1 18 0z")
        );

        card.getChildren().addAll(title, subtitle, list);
        return card;
    }

    private Node createPackageItem(String name, int sales, String revenue, String svgPath) {
        BorderPane item = new BorderPane();
        item.getStyleClass().add("list-item");

        HBox leftBox = new HBox(12);
        leftBox.setAlignment(Pos.CENTER_LEFT);

        StackPane iconContainer = new StackPane();
        iconContainer.getStyleClass().add("package-icon-container");
        SVGPath icon = new SVGPath();
        icon.setContent(svgPath);
        icon.getStyleClass().add("icon-svg");
        iconContainer.getChildren().add(icon);

        VBox infoBox = new VBox(2);
        Label nameLabel = new Label(name);
        nameLabel.getStyleClass().add("list-item-package-name");
        Label salesLabel = new Label(sales + " vendas");
        salesLabel.getStyleClass().add("list-item-package-info");
        infoBox.getChildren().addAll(nameLabel, salesLabel);

        leftBox.getChildren().addAll(iconContainer, infoBox);

        Label revenueLabel = new Label(revenue);
        revenueLabel.getStyleClass().add("list-item-revenue");
        BorderPane.setAlignment(revenueLabel, Pos.CENTER_RIGHT);

        item.setLeft(leftBox);
        item.setRight(revenueLabel);
        return item;
    }
}