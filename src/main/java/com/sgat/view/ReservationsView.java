package com.sgat.view;

import com.sgat.model.Client;
import com.sgat.model.Package;
import com.sgat.model.Reservation;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.SVGPath;

import java.time.LocalDate;
import java.util.Optional;

public class ReservationsView {
    private final VBox view;
    private TableView<Reservation> table;
    private final ObservableList<Reservation> reservations = FXCollections.observableArrayList();

    public ReservationsView() {
        view = new VBox(24);
        view.setPadding(new Insets(24));

        Node header = createHeader();
        Node tableCard = createTableCard();

        view.getChildren().addAll(header, tableCard);
        VBox.setVgrow(tableCard, Priority.ALWAYS);

        setupData();
    }

    public Node getView() {
        return view;
    }

    private void setupData() {
        Client client1 = new Client("Beatriz Oliveira", "beatriz.oliveira@example.com", "(11) 98765-4321", "123.456.789-00", "Rua das Flores, 123, São Paulo, SP", "Prefere destinos de praia e resorts all-inclusive. Gosta de viajar em família.", 5);
        Client client2 = new Client("Carlos Pereira", "carlos.pereira@example.com", "(21) 91234-5678", "987.654.321-00", "Avenida Copacabana, 456, Rio de Janeiro, RJ", "Interessado em turismo de aventura, como trilhas e montanhismo. Viagens solo.", 8);

        Package package1 = new Package("Férias em Cancún", "Cancún", "Pacote de 7 dias em resort all-inclusive", "7 dias", 2500.00, LocalDate.of(2025, 10, 20), LocalDate.of(2025, 10, 27), "Inclui passagem aérea, hospedagem e passeios.");
        Package package2 = new Package("Aventura na Patagônia", "Patagônia", "Pacote de 10 dias com trilhas e escaladas", "10 dias", 4500.00, LocalDate.of(2025, 11, 15), LocalDate.of(2025, 11, 25), "Inclui guias, equipamentos e acomodação em refúgios de montanha.");

        reservations.addAll(
            new Reservation(client1, package1, LocalDate.of(2025, 10, 20), 2, 5000.00, "Confirmada"),
            new Reservation(client2, package2, LocalDate.of(2025, 11, 15), 1, 4500.00, "Pendente")
        );
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

    private Node createTableCard() {
        VBox card = new VBox(12);
        card.getStyleClass().add("table-card");
        VBox.setVgrow(card, Priority.ALWAYS);

        Label title = new Label("Lista de Reservas");
        title.getStyleClass().add("info-card-title");
        Label subtitle = new Label("Todas as reservas cadastradas no sistema.");
        subtitle.getStyleClass().add("info-card-subtitle");

        table = createTable();
        VBox.setVgrow(table, Priority.ALWAYS);

        card.getChildren().addAll(title, subtitle, table);
        return card;
    }

    private TableView<Reservation> createTable() {
        TableView<Reservation> tableView = new TableView<>(reservations);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        TableColumn<Reservation, String> clientCol = new TableColumn<>("Cliente");
        clientCol.setCellValueFactory(cellData -> cellData.getValue().getClient().nameProperty());
        clientCol.setPrefWidth(200);

        TableColumn<Reservation, String> packageCol = new TableColumn<>("Pacote");
        packageCol.setCellValueFactory(cellData -> cellData.getValue().getTravelPackage().nameProperty());
        packageCol.setPrefWidth(250);

        TableColumn<Reservation, LocalDate> dateCol = new TableColumn<>("Data da Viagem");
        dateCol.setCellValueFactory(cellData -> cellData.getValue().travelDateProperty());
        dateCol.setPrefWidth(150);

        TableColumn<Reservation, Integer> passengersCol = new TableColumn<>("Passageiros");
        passengersCol.setCellValueFactory(cellData -> cellData.getValue().numberOfPassengersProperty().asObject());
        passengersCol.setPrefWidth(100);

        TableColumn<Reservation, Double> valueCol = new TableColumn<>("Valor Total");
        valueCol.setCellValueFactory(cellData -> cellData.getValue().totalValueProperty().asObject());
        valueCol.setPrefWidth(150);

        TableColumn<Reservation, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        statusCol.setPrefWidth(100);

        TableColumn<Reservation, Void> actionsCol = new TableColumn<>("Ações");
        actionsCol.setCellFactory(col -> new ActionsCell());
        actionsCol.setMinWidth(100);
        actionsCol.setMaxWidth(100);

        tableView.getColumns().addAll(clientCol, packageCol, dateCol, passengersCol, valueCol, statusCol, actionsCol);
        return tableView;
    }

    private void handleAddReservation() {
        // Placeholder for adding a new reservation
        showAlert(Alert.AlertType.INFORMATION, "Funcionalidade não implementada", "A adição de novas reservas ainda não foi implementada.");
    }

    private void handleEditReservation(Reservation reservation) {
        // Placeholder for editing a reservation
        showAlert(Alert.AlertType.INFORMATION, "Funcionalidade não implementada", "A edição de reservas ainda não foi implementada.");
    }

    private void handleDeleteReservation(Reservation reservation) {
        // Placeholder for deleting a reservation
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmar Exclusão");
        confirmation.setHeaderText("Excluir Reserva");
        confirmation.setContentText("Você tem certeza que deseja remover esta reserva?");

        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                reservations.remove(reservation);
                showAlert(Alert.AlertType.INFORMATION, "Reserva Removida", "A reserva foi removida com sucesso.");
            }
        });
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private class ActionsCell extends TableCell<Reservation, Void> {
        private final HBox box;
        private final Button editButton = new Button();
        private final Button deleteButton = new Button();

        public ActionsCell() {
            box = new HBox(8);
            box.setAlignment(Pos.CENTER);

            editButton.getStyleClass().addAll("icon-button");
            SVGPath editIcon = new SVGPath();
            editIcon.setContent("M12 20h9M16.5 3.5a2.121 2.121 0 0 1 3 3L7 19l-4 1 1-4L16.5 3.5z");
            editButton.setGraphic(editIcon);

            deleteButton.getStyleClass().addAll("icon-button", "delete-button");
            SVGPath deleteIcon = new SVGPath();
            deleteIcon.setContent("M3 6h18M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2");
            deleteButton.setGraphic(deleteIcon);

            box.getChildren().addAll(editButton, deleteButton);
            setGraphic(box);

            editButton.setOnAction(e -> {
                Reservation reservation = getTableView().getItems().get(getIndex());
                handleEditReservation(reservation);
            });

            deleteButton.setOnAction(e -> {
                Reservation reservation = getTableView().getItems().get(getIndex());
                handleDeleteReservation(reservation);
            });
        }

        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                setGraphic(box);
            }
        }
    }
}