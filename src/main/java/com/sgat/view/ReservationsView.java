package com.sgat.view;

import com.sgat.controller.ReservationsController;
import com.sgat.model.Client;
import com.sgat.model.ClientDAO;
import com.sgat.model.Package;
import com.sgat.model.PackageDAO;
import com.sgat.model.Reservation;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.SVGPath;
import javafx.util.StringConverter;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignA;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;

import java.time.LocalDate;
import java.util.Optional;

public class ReservationsView {
    private final Stage stage;
    private final VBox view;
    private final TableView<Reservation> table;
    private final ReservationsController controller;
    private final ClientDAO clientDAO;
    private final PackageDAO packageDAO;

    public ReservationsView(Stage stage) {
        this.stage = stage;
        this.controller = new ReservationsController();
        this.clientDAO = new ClientDAO();
        this.packageDAO = new PackageDAO();

        view = new VBox(24);
        view.setPadding(new Insets(24));

        Node header = createHeader();
        table = createTable();
        Node tableCard = createTableCard(table);

        view.getChildren().addAll(header, tableCard);
        VBox.setVgrow(tableCard, Priority.ALWAYS);

        loadReservations();
    }

    private void loadReservations() {
        ObservableList<Reservation> reservations = FXCollections.observableArrayList(controller.getAllReservations());
        table.setItems(reservations);
    }

    public Node getView() {
        return view;
    }

    private Node createHeader() {
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);

        VBox titleBox = new VBox(-2);
        Label title = new Label("Reservas");
        title.getStyleClass().add("page-title");
        Label subtitle = new Label("Gerencie as reservas de pacotes");
        subtitle.getStyleClass().add("page-subtitle");
        titleBox.getChildren().addAll(title, subtitle);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button newReservationButton = new Button("Nova Reserva");
        newReservationButton.getStyleClass().add("add-button");
        SVGPath plusIcon = new SVGPath();
        plusIcon.setContent("M12 5v14m-7-7h14");
        newReservationButton.setGraphic(plusIcon);
        newReservationButton.setOnAction(e -> handleAddReservation());

        header.getChildren().addAll(titleBox, spacer, newReservationButton);
        return header;
    }

    private Node createTableCard(TableView<Reservation> table) {
        VBox card = new VBox(12);
        card.getStyleClass().add("table-card");
        VBox.setVgrow(card, Priority.ALWAYS);

        Label title = new Label("Lista de Reservas");
        title.getStyleClass().add("info-card-title");
        Label subtitle = new Label("Todas as reservas cadastradas no sistema.");
        subtitle.getStyleClass().add("info-card-subtitle");

        VBox.setVgrow(table, Priority.ALWAYS);

        card.getChildren().addAll(title, subtitle, table);
        return card;
    }

    private TableView<Reservation> createTable() {
        TableView<Reservation> tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        TableColumn<Reservation, Client> clientCol = new TableColumn<>("Cliente");
        clientCol.setCellValueFactory(cellData -> cellData.getValue().clientProperty());
        clientCol.setCellFactory(col -> new ClientCell());
        clientCol.setPrefWidth(200);

        TableColumn<Reservation, Package> packageCol = new TableColumn<>("Pacote");
        packageCol.setCellValueFactory(cellData -> cellData.getValue().travelPackageProperty());
        packageCol.setCellFactory(col -> new PackageCell());
        packageCol.setPrefWidth(250);

        TableColumn<Reservation, LocalDate> dateCol = new TableColumn<>("Data da Viagem");
        dateCol.setCellValueFactory(cellData -> cellData.getValue().travelDateProperty());
        dateCol.setCellFactory(col -> new DateCell());
        dateCol.setPrefWidth(150);

        TableColumn<Reservation, Integer> passengersCol = new TableColumn<>("Passageiros");
        passengersCol.setCellValueFactory(cellData -> cellData.getValue().numberOfPassengersProperty().asObject());
        passengersCol.setPrefWidth(100);

        TableColumn<Reservation, Double> valueCol = new TableColumn<>("Valor Total");
        valueCol.setCellValueFactory(cellData -> cellData.getValue().totalValueProperty().asObject());
        valueCol.setCellFactory(col -> new ValueCell());
        valueCol.setPrefWidth(150);

        TableColumn<Reservation, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        statusCol.setCellFactory(col -> new StatusCell());
        statusCol.setPrefWidth(100);

        tableView.getColumns().addAll(clientCol, packageCol, dateCol, passengersCol, valueCol, statusCol);
        return tableView;
    }

    private void handleAddReservation() {
        showReservationDialog(null).ifPresent(reservation -> {
            controller.addReservation(reservation);
            loadReservations();
            showAlert(Alert.AlertType.INFORMATION, "Reserva Adicionada", "Nova reserva cadastrada com sucesso.");
        });
    }

    private Optional<Reservation> showReservationDialog(Reservation reservation) {
        Dialog<Reservation> dialog = new Dialog<>();
        dialog.initOwner(this.stage);
        dialog.setTitle(reservation == null ? "Adicionar Nova Reserva" : "Editar Reserva");
        dialog.setHeaderText(reservation == null ? "Preencha as informações da nova reserva." : "Atualize as informações da reserva.");

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.getDialogPane().setPrefWidth(900);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 10, 10));

        ComboBox<Client> clientComboBox = new ComboBox<>();
        ObservableList<Client> clients = FXCollections.observableArrayList(clientDAO.getAllClients());
        clientComboBox.setItems(clients);

        ComboBox<Package> packageComboBox = new ComboBox<>();
        ObservableList<Package> packages = FXCollections.observableArrayList(packageDAO.getAllPackages());
        packageComboBox.setItems(packages);

        DatePicker travelDatePicker = new DatePicker();
        TextField passengersField = new TextField();
        TextField valueField = new TextField();
        HBox valueBox = new HBox(5, new Label("R$"), valueField);
        valueBox.setAlignment(Pos.CENTER_LEFT);
        ComboBox<String> statusComboBox = new ComboBox<>();
        statusComboBox.setItems(FXCollections.observableArrayList("Confirmada", "Pendente", "Cancelada"));

        class StatusListCell extends ListCell<String> {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    Label statusLabel = new Label(item);
                    statusLabel.getStyleClass().setAll("status-label", item.equalsIgnoreCase("Confirmada") ? "status-confirmada" : (item.equalsIgnoreCase("Pendente") ? "status-pendente" : "status-cancelada"));
                    setGraphic(statusLabel);
                }
            }
        }

        statusComboBox.setCellFactory(cell -> new StatusListCell());
        statusComboBox.setButtonCell(new StatusListCell());
        TextArea observationsArea = new TextArea();
        
        clientComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Client client) {
                return client == null ? "" : client.getName();
            }

            @Override
            public Client fromString(String string) {
                return null;
            }
        });

        packageComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Package pkg) {
                return pkg == null ? "" : pkg.getNomePacote();
            }

            @Override
            public Package fromString(String string) {
                return null;
            }
        });

        grid.add(new Label("Cliente:"), 0, 0);
        grid.add(clientComboBox, 1, 0);
        grid.add(new Label("Pacote:"), 2, 0);
        grid.add(packageComboBox, 3, 0);
        grid.add(new Label("Data da Viagem:"), 0, 1);
        grid.add(travelDatePicker, 1, 1);
        grid.add(new Label("Número de Passageiros:"), 2, 1);
        grid.add(passengersField, 3, 1);
        grid.add(new Label("Valor Total:"), 0, 2);
        grid.add(valueBox, 1, 2);
        grid.add(new Label("Status:"), 2, 2);
        grid.add(statusComboBox, 3, 2);
        grid.add(new Label("Observações:"), 0, 3);
        grid.add(observationsArea, 1, 3, 3, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return new Reservation(
                        clientComboBox.getValue(),
                        packageComboBox.getValue(),
                        travelDatePicker.getValue(),
                        Integer.parseInt(passengersField.getText()),
                        Double.parseDouble(valueField.getText()),
                        statusComboBox.getValue()
                );
            }
            return null;
        });

        return dialog.showAndWait();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private static class StatusCell extends TableCell<Reservation, String> {
        private final Label statusLabel;

        public StatusCell() {
            statusLabel = new Label();
            setGraphic(statusLabel);
            setAlignment(Pos.CENTER_LEFT);
        }

        @Override
        protected void updateItem(String status, boolean empty) {
            super.updateItem(status, empty);
            if (empty || status == null) {
                setGraphic(null);
            } else {
                statusLabel.setText(status);
                statusLabel.getStyleClass().setAll("status-label", status.equalsIgnoreCase("Confirmada") ? "status-confirmada" : "status-pendente");
                setGraphic(statusLabel);
            }
        }
    }

    private static class ValueCell extends TableCell<Reservation, Double> {
        @Override
        protected void updateItem(Double item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
            } else {
                setText(String.format("R$ %.2f", item));
            }
        }
    }

    private static class ClientCell extends TableCell<Reservation, Client> {
        private final HBox box;
        private final FontIcon icon;
        private final Label label;

        public ClientCell() {
            box = new HBox(12);
            box.setAlignment(Pos.CENTER_LEFT);
            icon = new FontIcon(MaterialDesignA.ACCOUNT);
            icon.getStyleClass().add("table-icon");
            label = new Label();
            box.getChildren().addAll(icon, label);
            setGraphic(box);
        }

        @Override
        protected void updateItem(Client item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setGraphic(null);
            } else {
                label.setText(item.getName());
                setGraphic(box);
            }
        }
    }

    private static class PackageCell extends TableCell<Reservation, Package> {
        private final HBox box;
        private final FontIcon icon;
        private final Label label;

        public PackageCell() {
            box = new HBox(12);
            box.setAlignment(Pos.CENTER_LEFT);
            icon = new FontIcon(MaterialDesignP.PACKAGE_VARIANT_CLOSED);
            icon.getStyleClass().add("table-icon");
            label = new Label();
            box.getChildren().addAll(icon, label);
            setGraphic(box);
        }

        @Override
        protected void updateItem(Package item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setGraphic(null);
            } else {
                label.setText(item.getNomePacote());
                setGraphic(box);
            }
        }
    }

    private static class DateCell extends TableCell<Reservation, LocalDate> {
        private final HBox box;
        private final FontIcon icon;
        private final Label label;

        public DateCell() {
            box = new HBox(12);
            box.setAlignment(Pos.CENTER_LEFT);
            icon = new FontIcon(MaterialDesignC.CALENDAR);
            icon.getStyleClass().add("table-icon");
            label = new Label();
            box.getChildren().addAll(icon, label);
            setGraphic(box);
        }

        @Override
        protected void updateItem(LocalDate item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setGraphic(null);
            } else {
                label.setText(item.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                setGraphic(box);
            }
        }
    }
}
