package com.sgat.view;

import java.util.List;

import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignA;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;
import org.kordamp.ikonli.materialdesign2.MaterialDesignM;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;

import com.sgat.controller.DashboardController;
import com.sgat.controller.DashboardController.PacotePopularDTO;
import com.sgat.controller.DashboardController.ReservaRecentesDTO;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class DashboardView {

    private final DashboardController controller;

    public DashboardView(DashboardController controller) {
        this.controller = controller;
    }

    public Node getView() {
        VBox dashboardPane = new VBox();
        dashboardPane.getStyleClass().add("dashboard-pane");

        // Título
        Label title = new Label("Dashboard");
        title.getStyleClass().add("dashboard-title");

        Label subtitle = new Label("Visão geral do sistema de gestão.");
        subtitle.getStyleClass().add("dashboard-subtitle");

        // Grade de Estatísticas
        GridPane statsGrid = new GridPane();
        statsGrid.getStyleClass().add("stats-grid");
        statsGrid.setHgap(16);
        statsGrid.setVgap(16);

        for (int i = 0; i < 4; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(25);
            col.setHgrow(Priority.ALWAYS);
            statsGrid.getColumnConstraints().add(col);
        }

        // Dados reais
        int totalPacotes = safeInt(() -> controller.getTotalPacotes());
        int totalClientes = safeInt(() -> controller.getTotalClientes());
        int reservasPendentes = safeInt(() -> controller.getReservasPendentes());
        double receitaMes = safeDouble(() -> controller.getReceitaDoMes());

        Node statCard1 = createStatCard("Pacotes Ativos", String.valueOf(totalPacotes), "", new FontIcon(MaterialDesignP.PACKAGE_VARIANT_CLOSED));
        Node statCard2 = createStatCard("Clientes Cadastrados", String.valueOf(totalClientes), "", new FontIcon(MaterialDesignA.ACCOUNT_GROUP_OUTLINE));
        Node statCard3 = createStatCard("Reservas Pendentes", String.valueOf(reservasPendentes), "", new FontIcon(MaterialDesignC.CALENDAR_CLOCK));
        Node statCard4 = createStatCard("Receita (Mês)", "R$ " + receitaMes, "", new FontIcon(MaterialDesignC.CASH));

        statsGrid.add(statCard1, 0, 0);
        statsGrid.add(statCard2, 1, 0);
        statsGrid.add(statCard3, 2, 0);
        statsGrid.add(statCard4, 3, 0);

        // Grade de Informações
        GridPane infoGrid = new GridPane();
        infoGrid.getStyleClass().add("info-grid");

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        infoGrid.getColumnConstraints().addAll(col1, col2);

        Node reservationsCard = createRecentReservationsCard();
        Node popularPackagesCard = createPopularPackagesCard();

        infoGrid.add(reservationsCard, 0, 0);
        infoGrid.add(popularPackagesCard, 1, 0);

        dashboardPane.getChildren().addAll(title, subtitle, statsGrid, infoGrid);
        return dashboardPane;
    }

    // -------------------------
    // CARDS
    // -------------------------

    private Node createStatCard(String title, String value, String description, Node icon) {
        VBox card = new VBox();
        card.getStyleClass().add("stat-card");
        GridPane.setHgrow(card, Priority.ALWAYS);

        HBox header = new HBox();
        header.getStyleClass().add("stat-card-header");
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("stat-card-title");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        icon.getStyleClass().add("icon-svg");
        ((FontIcon) icon).setIconSize(24);
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

        Label title = new Label("Reservas Recentes");
        title.getStyleClass().add("info-card-title");
        Label subtitle = new Label("Últimas reservas confirmadas e pendentes.");
        subtitle.getStyleClass().add("info-card-subtitle");

        VBox list = new VBox();

        List<ReservaRecentesDTO> reservas = safeList(() -> controller.getReservasRecentes());

        for (ReservaRecentesDTO r : reservas) {
            list.getChildren().add(createReservationItem(
                    r.cliente,
                    r.pacote,
                    r.data.toString(),
                    r.status
            ));
        }

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
        statusLabel.getStyleClass().add("status-label");
        if (status.equalsIgnoreCase("Confirmada")) {
            statusLabel.getStyleClass().add("status-confirmada");
        } else {
            statusLabel.getStyleClass().add("status-pendente");
        }
        rightBox.getChildren().addAll(dateLabel, statusLabel);

        item.setLeft(leftBox);
        item.setRight(rightBox);
        return item;
    }

    private Node createPopularPackagesCard() {
        VBox card = new VBox();
        card.getStyleClass().add("info-card");

        Label title = new Label("Pacotes Mais Populares");
        title.getStyleClass().add("info-card-title");
        Label subtitle = new Label("Pacotes com maior número de vendas este mês.");
        subtitle.getStyleClass().add("info-card-subtitle");

        VBox list = new VBox();

        List<PacotePopularDTO> pacotes = safeList(() -> controller.getPacotesMaisPopulares());

        for (PacotePopularDTO p : pacotes) {
            list.getChildren().add(createPackageItem(
                    p.nome,
                    p.vendas,
                    "R$ " + p.receita,
                    new FontIcon(MaterialDesignM.MAP_MARKER)
            ));
        }

        card.getChildren().addAll(title, subtitle, list);
        return card;
    }

    private Node createPackageItem(String name, int sales, String revenue, Node icon) {
        BorderPane item = new BorderPane();
        item.getStyleClass().add("list-item");

        HBox leftBox = new HBox(12);
        leftBox.setAlignment(Pos.CENTER_LEFT);

        ((FontIcon) icon).setIconSize(20);

        VBox infoBox = new VBox(2);
        Label nameLabel = new Label(name);
        nameLabel.getStyleClass().add("list-item-package-name");
        Label salesLabel = new Label(sales + " vendas");
        salesLabel.getStyleClass().add("list-item-package-info");
        infoBox.getChildren().addAll(nameLabel, salesLabel);

        leftBox.getChildren().addAll(icon, infoBox);

        Label revenueLabel = new Label(revenue);
        revenueLabel.getStyleClass().add("list-item-revenue");

        item.setLeft(leftBox);
        item.setRight(revenueLabel);

        return item;
    }

    // -----------------------------
    // FUNÇÕES SEGURAS (evitam crash)
    // -----------------------------

    private int safeInt(SqlIntSupplier s) {
        try { return s.get(); } catch (Exception e) { return 0; }
    }

    private double safeDouble(SqlDoubleSupplier s) {
        try { return s.get(); } catch (Exception e) { return 0.0; }
    }

    private <T> List<T> safeList(SqlListSupplier<T> s) {
        try { return s.get(); } catch (Exception e) { return List.of(); }
    }

    interface SqlIntSupplier { int get() throws Exception; }
    interface SqlDoubleSupplier { double get() throws Exception; }
    interface SqlListSupplier<T> { List<T> get() throws Exception; }
}
