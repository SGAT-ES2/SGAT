package com.sgat.view;

import com.sgat.model.*;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ItinerariesView {

    private final Stage stage;
    private final VBox view;
    private final ItineraryDAO itineraryDAO;
    private final ReservationDAO reservationDAO;
    private ComboBox<Reservation> reservationComboBox;
    private VBox itineraryContainer;
    private static final int ICON_SIZE = 20;

    public ItinerariesView(Stage stage) {
        this.stage = stage;
        this.itineraryDAO = new ItineraryDAO();
        this.reservationDAO = new ReservationDAO();

        view = new VBox(24);
        view.setPadding(new Insets(24));
        view.getStyleClass().add("page");

        Node header = createHeader();
        itineraryContainer = new VBox(20);
        VBox.setVgrow(itineraryContainer, Priority.ALWAYS);

        view.getChildren().addAll(header, itineraryContainer);

        loadReservations();
    }

    public Node getView() {
        return view;
    }

    public void loadReservations() {
        List<Reservation> reservations = reservationDAO.getAllReservations();
        reservationComboBox.setItems(FXCollections.observableArrayList(reservations));
        if (!reservations.isEmpty()) {
            reservationComboBox.setValue(reservations.get(0));
        }
    }

    private void loadItinerary(Reservation reservation) {
        itineraryContainer.getChildren().clear();
        if (reservation != null) {
            Itinerary itinerary = itineraryDAO.getItineraryForReservation(reservation.getId());
            if (itinerary != null && !itinerary.getDays().isEmpty()) {
                itineraryContainer.getChildren().add(createItineraryCard(itinerary));
            } else {
                Label noItineraryLabel = new Label("Nenhum itinerário detalhado para esta reserva.");
                noItineraryLabel.getStyleClass().add("page-subtitle");
                itineraryContainer.getChildren().add(noItineraryLabel);
            }
        }
    }

    private Node createHeader() {
        HBox header = new HBox(16);
        header.setAlignment(Pos.CENTER);

        VBox titleBox = new VBox(-2);
        Label title = new Label("Itinerários");
        title.getStyleClass().add("page-title");
        Label subtitle = new Label("Gere e gerencie itinerários detalhados");
        subtitle.getStyleClass().add("page-subtitle");
        titleBox.getChildren().addAll(title, subtitle);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label comboLabel = new Label("Reserva:");
        comboLabel.getStyleClass().add("page-subtitle");

        reservationComboBox = new ComboBox<>();
        reservationComboBox.getStyleClass().add("input-field");
        reservationComboBox.setPrefHeight(40);
        reservationComboBox.setPromptText("Selecione uma reserva");
        reservationComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Reservation reservation) {
                return reservation == null ? "" : "RES-" + reservation.getId() + " - " + reservation.getClient().getName();
            }

            @Override
            public Reservation fromString(String string) {
                return null;
            }
        });
        reservationComboBox.valueProperty().addListener((obs, oldVal, newVal) -> loadItinerary(newVal));

        HBox reservationSelector = new HBox(8, comboLabel, reservationComboBox);
        reservationSelector.setAlignment(Pos.CENTER_LEFT);

        Button addActivityButton = new Button("Adicionar Atividade");
        addActivityButton.getStyleClass().add("add-button");
        addActivityButton.setPrefHeight(40);
        FontIcon plusIcon = new FontIcon(MaterialDesignP.PLUS);
        plusIcon.setIconSize(ICON_SIZE);
        plusIcon.setIconColor(javafx.scene.paint.Color.WHITE);
        addActivityButton.setGraphic(plusIcon);
        addActivityButton.setOnAction(e -> {
            showAddActivityDialog();
        });

        header.getChildren().addAll(titleBox, spacer, reservationSelector, addActivityButton);
        return header;
    }

    private Node createItineraryCard(Itinerary itinerary) {
        VBox card = new VBox(20);
        card.getStyleClass().add("table-card");
        VBox.setVgrow(card, Priority.ALWAYS);

        HBox cardHeader = new HBox(8);
        cardHeader.setAlignment(Pos.CENTER_LEFT);
        FontIcon mapIcon = new FontIcon(MaterialDesignM.MAP_MARKER);
        mapIcon.getStyleClass().add("info-card-title-icon");

        Reservation reservation = itinerary.getReservation();

        cardHeader.getChildren().addAll(
                mapIcon,
                new Label(String.format("RES-%d - %s - %s", reservation.getId(), reservation.getClient().getName(), reservation.getTravelPackage().getNomePacote()))
        );
        cardHeader.getStyleClass().add("info-card-title");

        TabPane tabPane = new TabPane();
        tabPane.getStyleClass().add("itinerary-tabs");
        for (Day day : itinerary.getDays()) {
            Tab tab = new Tab("Dia " + day.getDayNumber());
            tab.setClosable(false);
            tab.setContent(createDayContent(day));
            tabPane.getTabs().add(tab);
        }
        VBox.setVgrow(tabPane, Priority.ALWAYS);

        card.getChildren().addAll(cardHeader, tabPane);
        return card;
    }

    private Node createDayContent(Day day) {
        VBox dayContainer = new VBox(10);
        dayContainer.setPadding(new Insets(20));
        dayContainer.getStyleClass().add("rebuilt-day-container");

        // --- HEADER ---
        HBox dayHeader = new HBox(8);
        dayHeader.setAlignment(Pos.CENTER_LEFT);
        FontIcon headerIcon = new FontIcon(MaterialDesignC.CALENDAR);
        headerIcon.getStyleClass().add("rebuilt-day-header-icon");
        Label headerLabel = new Label("Dia " + day.getDayNumber());
        headerLabel.getStyleClass().add("rebuilt-day-header-label");
        dayHeader.getChildren().addAll(headerIcon, headerLabel);

        // --- SCROLLABLE CONTENT ---
        VBox activitiesContainer = new VBox(); // Will hold all activity rows
        List<Activity> activities = day.getActivities();
        for (int i = 0; i < activities.size(); i++) {
            boolean isLast = (i == activities.size() - 1);
            activitiesContainer.getChildren().add(createActivityRow(activities.get(i), isLast));
        }

        ScrollPane scrollPane = new ScrollPane(activitiesContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.getStyleClass().add("rebuilt-scroll-pane");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);


        dayContainer.getChildren().addAll(dayHeader, scrollPane);
        return dayContainer;
    }

    private Node createActivityRow(Activity activity, boolean isLast) {
        // --- Main Row Container ---
        HBox activityRow = new HBox(15);
        activityRow.setPadding(new Insets(10, 0, 10, 0));
        if (!isLast) {
            activityRow.getStyleClass().add("rebuilt-activity-row-border");
        }

        // --- Icon Column ---
        VBox iconColumn = new VBox(5);
        iconColumn.setAlignment(Pos.TOP_CENTER);

        StackPane iconWrapper = new StackPane();
        iconWrapper.getStyleClass().add("rebuilt-activity-icon-wrapper");
        FontIcon activityIcon = new FontIcon(MaterialDesignM.MAP_MARKER);
        activityIcon.getStyleClass().add("rebuilt-activity-icon");
        iconWrapper.getChildren().add(activityIcon);
        
        iconColumn.getChildren().add(iconWrapper);

        // --- Content Column ---
        VBox contentColumn = new VBox(5);
        HBox.setHgrow(contentColumn, Priority.ALWAYS);

        // Title and Time
        HBox titleBar = new HBox(8);
        titleBar.setAlignment(Pos.CENTER_LEFT);
        Label titleLabel = new Label(activity.getTitle());
        titleLabel.getStyleClass().add("rebuilt-activity-title");
        HBox.setHgrow(titleLabel, Priority.ALWAYS);

        HBox timeBox = new HBox(5);
        timeBox.setAlignment(Pos.CENTER_LEFT);
        FontIcon clockIcon = new FontIcon(MaterialDesignC.CLOCK_OUTLINE);
        clockIcon.getStyleClass().add("rebuilt-activity-time-icon");
        Label timeLabel = new Label(activity.getTime());
        timeLabel.getStyleClass().add("rebuilt-activity-time");
        timeBox.getChildren().addAll(clockIcon, timeLabel);

        titleBar.getChildren().addAll(titleLabel, timeBox);

        // Description
        Label descriptionLabel = new Label(activity.getDescription());
        descriptionLabel.setWrapText(true);
        descriptionLabel.getStyleClass().add("rebuilt-activity-description");

        contentColumn.getChildren().addAll(titleBar, descriptionLabel);

        activityRow.getChildren().addAll(iconColumn, contentColumn);
        return activityRow;
    }

    private void showAddActivityDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(this.stage);
        dialog.setTitle("Adicionar Atividade ao Itinerário");
        dialog.setHeaderText("Selecione a reserva e preencha os detalhes da atividade");

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        ComboBox<Reservation> dialogResCombo = new ComboBox<>();
        dialogResCombo.setPromptText("Selecione a Reserva");
        dialogResCombo.setItems(FXCollections.observableArrayList(reservationDAO.getAllReservations()));
        dialogResCombo.setConverter(new StringConverter<>() {
            @Override
            public String toString(Reservation reservation) {
                return reservation == null ? "" : "RES-" + reservation.getId() + " - " + reservation.getClient().getName();
            }
            @Override
            public Reservation fromString(String string) { return null; }
        });
        if (reservationComboBox.getValue() != null) {
            dialogResCombo.setValue(reservationComboBox.getValue());
        }
        DatePicker datePicker = new DatePicker(LocalDate.now());
        TextField timeField = new TextField();
        timeField.setPromptText("HH:MM");
        TextField titleField = new TextField();
        TextArea descriptionArea = new TextArea();
        ComboBox<String> typeComboBox = new ComboBox<>();
        typeComboBox.setItems(FXCollections.observableArrayList("Flight", "Accommodation", "Tour", "Other"));
        typeComboBox.setValue("Other");

        grid.add(new Label("Reserva:"), 0, 0);
        grid.add(dialogResCombo, 1, 0);
        grid.add(new Label("Data:"), 0, 1);
        grid.add(datePicker, 1, 1);
        grid.add(new Label("Horário:"), 0, 2);
        grid.add(timeField, 1, 2);
        grid.add(new Label("Tipo:"), 0, 3);
        grid.add(typeComboBox, 1, 3);
        grid.add(new Label("Título:"), 0, 4);
        grid.add(titleField, 1, 4);
        grid.add(new Label("Descrição:"), 0, 5);
        grid.add(descriptionArea, 1, 5);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                Reservation selectedReservation = dialogResCombo.getValue();
                if (selectedReservation == null) {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "É obrigatório selecionar uma reserva.");
                    alert.initOwner(stage);
                    alert.showAndWait();
                    return null;
                }
                try {
                    LocalTime time = LocalTime.parse(timeField.getText());
                    itineraryDAO.addActivityToItinerary(
                            selectedReservation.getId(),
                            datePicker.getValue(),
                            time,
                            titleField.getText(),
                            descriptionArea.getText(),
                            typeComboBox.getValue()
                    );
                    if (reservationComboBox.getValue() != null &&
                            reservationComboBox.getValue().getId() == selectedReservation.getId()) {
                        loadItinerary(selectedReservation);
                    }
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Formato de hora inválido. Use HH:MM.");
                    alert.initOwner(stage);
                    alert.showAndWait();
                }
                return dialogButton;
            }
            return null;
        });

        dialog.showAndWait();
    }
}