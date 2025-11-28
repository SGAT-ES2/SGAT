package com.sgat.view;

import com.sgat.controller.PaymentsController;
import com.sgat.model.Pagamento;
import com.sgat.model.Reservation;
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
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;

import java.text.NumberFormat;
import java.util.Locale;

public class PaymentsView {

    private final VBox view;
    private final PaymentsController controller;
    private VBox tableBody;

    private Label receivedValueLabel;
    private Label pendingValueLabel;

    // --- LARGURAS REFINADAS (Tornando a tabela mais compacta e coesa) ---
    private static final double COL_RES_WIDTH = -1;
    private static final double COL_VAL_WIDTH = 130; // Reduzido de 140 para 130 (aproxima as colunas)
    private static final double COL_MET_WIDTH = 140; // Reduzido de 150 para 140
    private static final double COL_DAT_WIDTH = 110;
    private static final double COL_STA_WIDTH = 100;
    private static final double COLUMN_GAP = 10;

    private static final int ICON_SIZE = 20;

    public PaymentsView() {
        view = new VBox(24);
        view.setPadding(new Insets(24));
        view.getStyleClass().addAll("dashboard-pane", "payments-view");

        this.controller = new PaymentsController(this);

        Node header = createHeader();
        Node summaryCards = createSummaryCards();
        Node historyCard = createHistoryCard();

        view.getChildren().addAll(header, summaryCards, historyCard);
        VBox.setVgrow(historyCard, Priority.ALWAYS);

        refreshAll();
    }

    public Node getView() { return view; }

    public void refreshAll() {
        if (controller == null) return;
        updateSummaryCards();
        refreshHistory();
    }

    private void updateSummaryCards() {
        if (controller == null || receivedValueLabel == null || pendingValueLabel == null) return;
        double[] totals = controller.calculatePaymentTotals();
        receivedValueLabel.setText(formatCurrency(totals[0]));
        pendingValueLabel.setText(formatCurrency(totals[1]));
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
            double paid = p.getValorPago();
            double pending = totalValue - paid;
            String status = (paid >= totalValue && totalValue > 0) ? "Paga" : (paid > 0 ? "Parcial" : "Pendente");

            tableBody.getChildren().add(
                    createHistoryRow(
                            p.getReserva(),
                            formatCurrency(totalValue),
                            formatCurrency(paid),
                            formatCurrency(pending),
                            p.getMetodoPagamento(),
                            p.getDataPagamento() != null ? p.getDataPagamento().toString() : "-",
                            status
                    )
            );
        }
    }

    // --- CABEÇALHO ---
    private HBox createHistoryHeader() {
        HBox header = new HBox(COLUMN_GAP);
        header.getStyleClass().add("column-header-background");
        header.setPadding(new Insets(10, 16, 10, 16));
        header.setAlignment(Pos.CENTER_LEFT);

        Label resLabel = createHeaderLabel("Reserva", Pos.CENTER_LEFT, COL_RES_WIDTH);
        Label totalLabel = createHeaderLabel("Valor Total", Pos.CENTER_RIGHT, COL_VAL_WIDTH);
        Label pagoLabel = createHeaderLabel("Valor Pago", Pos.CENTER_RIGHT, COL_VAL_WIDTH);
        Label pendenteLabel = createHeaderLabel("Pendente", Pos.CENTER_RIGHT, COL_VAL_WIDTH);

        // MUDANÇA PRINCIPAL: Método agora é CENTRALIZADO
        // Isso remove a estranheza visual entre o rótulo e o dado
        Label metodoLabel = createHeaderLabel("Método", Pos.CENTER, COL_MET_WIDTH);

        Label dataLabel = createHeaderLabel("Data", Pos.CENTER, COL_DAT_WIDTH);
        Label statusLabel = createHeaderLabel("Status", Pos.CENTER, COL_STA_WIDTH);

        HBox.setHgrow(resLabel, Priority.ALWAYS);
        HBox.setHgrow(totalLabel, Priority.NEVER);
        HBox.setHgrow(pagoLabel, Priority.NEVER);
        HBox.setHgrow(pendenteLabel, Priority.NEVER);
        HBox.setHgrow(metodoLabel, Priority.NEVER);
        HBox.setHgrow(dataLabel, Priority.NEVER);
        HBox.setHgrow(statusLabel, Priority.NEVER);

        header.getChildren().addAll(resLabel, totalLabel, pagoLabel, pendenteLabel, metodoLabel, dataLabel, statusLabel);
        return header;
    }

    private Label createHeaderLabel(String text, Pos alignment, double width) {
        Label label = new Label(text);
        label.getStyleClass().add("column-header");
        label.setAlignment(alignment);
        if (width > 0) {
            label.setMinWidth(width);
            label.setPrefWidth(width);
            label.setMaxWidth(width);
        } else {
            label.setMaxWidth(Double.MAX_VALUE);
        }
        return label;
    }

    // --- LINHA ---
    private Node createHistoryRow(Reservation reservation, String total, String paid, String pending, String method, String date, String status) {
        HBox row = new HBox(COLUMN_GAP);
        row.getStyleClass().add("list-item");
        row.setPadding(new Insets(12, 16, 12, 16));
        row.getStyleClass().add("clickable-list-item");
        row.setAlignment(Pos.CENTER_LEFT);

        // Coluna Reserva (Elástica)
        VBox resBox = new VBox(-2);
        Label lblRes = new Label("RES-" + reservation.getId());
        lblRes.getStyleClass().add("list-item-package-name");
        Label lblClient = new Label(reservation.getClient().getName());
        lblClient.getStyleClass().add("list-item-package-info");
        resBox.getChildren().addAll(lblRes, lblClient);
        resBox.setAlignment(Pos.CENTER_LEFT);
        resBox.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(resBox, Priority.ALWAYS);

        // Colunas Fixas
        Label lblTotal = createAlignedDataLabel(total, "#666666", Pos.CENTER_RIGHT, COL_VAL_WIDTH);
        Label lblPaid = createAlignedDataLabel(paid, "#388e3c", Pos.CENTER_RIGHT, COL_VAL_WIDTH);
        Label lblPending = createAlignedDataLabel(pending, "#ef6c00", Pos.CENTER_RIGHT, COL_VAL_WIDTH);

        // MUDANÇA PRINCIPAL: Centralizar o dado do Método
        Label lblMethod = createAlignedDataLabel(method, "#424242", Pos.CENTER, COL_MET_WIDTH);

        Label lblDate = createAlignedDataLabel(date, "#666666", Pos.CENTER, COL_DAT_WIDTH);

        // Status
        Label lblStatus = new Label(status);
        lblStatus.getStyleClass().add("status-label");
        lblStatus.setMinWidth(COL_STA_WIDTH);
        lblStatus.setPrefWidth(COL_STA_WIDTH);
        lblStatus.setMaxWidth(COL_STA_WIDTH);
        lblStatus.setAlignment(Pos.CENTER);

        if (status.equalsIgnoreCase("Paga")) lblStatus.getStyleClass().add("status-confirmada");
        else if (status.equalsIgnoreCase("Parcial")) lblStatus.getStyleClass().add("status-pendente");
        else if (status.equalsIgnoreCase("Pendente")) lblStatus.getStyleClass().add("status-cancelada");

        HBox.setHgrow(lblTotal, Priority.NEVER);
        HBox.setHgrow(lblPaid, Priority.NEVER);
        HBox.setHgrow(lblPending, Priority.NEVER);
        HBox.setHgrow(lblMethod, Priority.NEVER);
        HBox.setHgrow(lblDate, Priority.NEVER);
        HBox.setHgrow(lblStatus, Priority.NEVER);

        row.getChildren().addAll(resBox, lblTotal, lblPaid, lblPending, lblMethod, lblDate, lblStatus);

        row.setOnMouseClicked(event -> System.out.println("Ação: Detalhes Reserva " + reservation.getId()));
        return row;
    }

    private Label createAlignedDataLabel(String text, String colorHex, Pos alignment, double width) {
        Label label = new Label(text);
        label.getStyleClass().add("table-cell");
        label.setStyle("-fx-text-fill: " + colorHex + ";");
        label.setAlignment(alignment);
        label.setMinWidth(width);
        label.setPrefWidth(width);
        label.setMaxWidth(width);
        return label;
    }

    // --- RESTANTE DOS MÉTODOS (Mantidos iguais) ---
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
        Button registerButton = new Button("Registrar Pagamento");
        registerButton.getStyleClass().add("add-button");
        FontIcon plusIcon = new FontIcon(MaterialDesignP.PLUS);
        plusIcon.setIconSize(ICON_SIZE);
        plusIcon.setIconColor(Color.WHITE);
        registerButton.setGraphic(plusIcon);
        registerButton.setOnAction(event -> {
            Stage ownerStage = (Stage) registerButton.getScene().getWindow();
            if (controller != null) controller.openPaymentDialog(ownerStage);
        });
        header.getChildren().addAll(titleBox, spacer, registerButton);
        return header;
    }

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
        if (isReceived) this.receivedValueLabel = lblValue; else this.pendingValueLabel = lblValue;
        Label lblDescription = new Label(description);
        lblDescription.getStyleClass().add("stat-card-description");
        box.getChildren().addAll(titleContainer, lblValue, lblDescription);
        return box;
    }

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
}