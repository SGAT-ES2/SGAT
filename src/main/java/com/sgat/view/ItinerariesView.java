package com.sgat.view;

import com.sgat.model.Activity;
import com.sgat.model.Day;
import com.sgat.model.Itinerary;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.*;

import java.time.LocalDate;
import java.util.Optional;

public class ItinerariesView {

    private final Stage stage;
    private final VBox view;
    private final ObservableList<Itinerary> itineraries = FXCollections.observableArrayList();
    private static final int ICON_SIZE = 20;

    public ItinerariesView(Stage stage) {
        this.stage = stage;
        view = new VBox(24);
        view.setPadding(new Insets(24));
        view.getStyleClass().add("page");

        setupData();

        Node header = createHeader();
        Node itineraryCard = createItineraryCard(itineraries.get(0)); // Display the first itinerary by default

        view.getChildren().addAll(header, itineraryCard);
        VBox.setVgrow(itineraryCard, Priority.ALWAYS);
    }

    public Node getView() {
        return view;
    }

    private void setupData() {
        Itinerary itinerary = new Itinerary("RES-001", "Maria Silva", "Paris Romântica", LocalDate.of(2024, 10, 20), LocalDate.of(2024, 10, 25));

        Day day1 = new Day(1, "Chegada em Paris");
        day1.getActivities().addAll(
                new Activity("14:00", "Check-in no Hotel", "Chegada e check-in no Hotel Le Bristol.", MaterialDesignH.HOME),
                new Activity("16:00", "Passeio pelo Rio Sena", "Cruzeiro panorâmico pelo Rio Sena para uma primeira vista da cidade.", MaterialDesignF.FERRY),
                new Activity("20:00", "Jantar no Le Jules Verne", "Jantar no restaurante da Torre Eiffel.", MaterialDesignS.SILVERWARE_FORK_KNIFE)
        );

        Day day2 = new Day(2, "Cultura e Arte");
        day2.getActivities().addAll(
                new Activity("09:00", "Museu do Louvre", "Visita guiada aos principais destaques do museu.", MaterialDesignB.BANK),
                new Activity("13:00", "Almoço no Café Marly", "Almoço com vista para a pirâmide do Louvre.", MaterialDesignS.SILVERWARE_FORK_KNIFE),
                new Activity("15:00", "Catedral de Notre-Dame", "Visita à área externa e arredores da catedral.", MaterialDesignC.CHURCH)
        );
        
        Day day3 = new Day(3, "Retorno");
        day3.getActivities().addAll(
                new Activity("11:00", "Check-out do Hotel", "Check-out e transfer para o aeroporto.", MaterialDesignH.HOME_EXPORT_OUTLINE)
        );

        itinerary.getDays().addAll(day1, day2, day3);
        itineraries.add(itinerary);
    }

    private Node createHeader() {
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);

        VBox titleBox = new VBox(-2);
        Label title = new Label("Itinerários");
        title.getStyleClass().add("page-title");
        Label subtitle = new Label("Gere e gerencie itinerários detalhados");
        subtitle.getStyleClass().add("page-subtitle");
        titleBox.getChildren().addAll(title, subtitle);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button generateButton = new Button("Gerar Itinerário");
        generateButton.getStyleClass().add("add-button");
        FontIcon plusIcon = new FontIcon(MaterialDesignP.PLUS);
        plusIcon.setIconSize(ICON_SIZE);
        generateButton.setGraphic(plusIcon);
        generateButton.setOnAction(e -> showGenerateItineraryDialog());

        header.getChildren().addAll(titleBox, spacer, generateButton);
        return header;
    }

    private Node createItineraryCard(Itinerary itinerary) {
        VBox card = new VBox(20);
        card.getStyleClass().add("table-card");
        VBox.setVgrow(card, Priority.ALWAYS);

        // Card Header
        HBox cardHeader = new HBox(8);
        cardHeader.setAlignment(Pos.CENTER_LEFT);
        FontIcon mapIcon = new FontIcon(MaterialDesignM.MAP_MARKER);
        mapIcon.setIconSize(ICON_SIZE);
        cardHeader.getChildren().addAll(
                mapIcon,
                new Label(String.format("%s - %s - %s", itinerary.getReservationId(), itinerary.getClientName(), itinerary.getPackageName()))
        );
        cardHeader.getStyleClass().add("info-card-title");


        // Tabs for each day
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
        VBox dayContent = new VBox(20);
        dayContent.setPadding(new Insets(16));

        HBox dayTitleBox = new HBox(8);
        dayTitleBox.setAlignment(Pos.CENTER_LEFT);
        FontIcon calendarIcon = new FontIcon(MaterialDesignC.CALENDAR);
        calendarIcon.setIconSize(ICON_SIZE);
        dayTitleBox.getChildren().addAll(
                calendarIcon,
                new Label(String.format("Dia %d - %s", day.getDayNumber(), day.getTitle()))
        );
        dayTitleBox.getStyleClass().add("day-title");

        VBox timeline = new VBox();
        ObservableList<Activity> activities = day.getActivities();
        for (int i = 0; i < activities.size(); i++) {
            timeline.getChildren().add(createActivityRow(activities.get(i), i == activities.size() - 1));
        }

        dayContent.getChildren().addAll(dayTitleBox, timeline);

        ScrollPane scrollPane = new ScrollPane(dayContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.getStyleClass().add("day-scroll-pane");

        return scrollPane;
    }

    private Node createActivityRow(Activity activity, boolean isLast) {
        HBox activityRow = new HBox(16);

        // Icon Column
        VBox iconColumn = new VBox();
        iconColumn.setAlignment(Pos.TOP_CENTER);
        iconColumn.setSpacing(8);

        FontIcon activityIcon = new FontIcon(activity.getIcon());
        activityIcon.setIconSize(ICON_SIZE);
        StackPane iconContainer = new StackPane(activityIcon);
        iconContainer.getStyleClass().add("timeline-icon-container");
        iconContainer.setAlignment(Pos.CENTER);

        iconColumn.getChildren().add(iconContainer);

        if (!isLast) {
            Region line = new Region();
            line.getStyleClass().add("timeline-line");
            VBox.setVgrow(line, Priority.ALWAYS);
            iconColumn.getChildren().add(line);
        }

        // Content Column
        VBox contentColumn = new VBox(4);

        HBox timeBox = new HBox(8);
        timeBox.setAlignment(Pos.CENTER_LEFT);
        FontIcon clockIcon = new FontIcon(MaterialDesignC.CLOCK_OUTLINE);
        clockIcon.setIconSize(ICON_SIZE);
        timeBox.getChildren().addAll(clockIcon, new Label(activity.getTime()));
        timeBox.getStyleClass().add("activity-time");

        Label titleLabel = new Label(activity.getTitle());
        titleLabel.getStyleClass().add("activity-title");

        Label descriptionLabel = new Label(activity.getDescription());
        descriptionLabel.setWrapText(true);
        descriptionLabel.getStyleClass().add("activity-description");

        contentColumn.getChildren().addAll(timeBox, titleLabel, descriptionLabel);
        HBox.setHgrow(contentColumn, Priority.ALWAYS);

        activityRow.getChildren().addAll(iconColumn, contentColumn);
        return activityRow;
    }

    private void showGenerateItineraryDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(this.stage);
        dialog.setTitle("Gerar Novo Itinerário");
        dialog.setHeaderText("Preencha as informações para gerar um novo itinerário.");

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        ComboBox<String> reservationCombo = new ComboBox<>();
        reservationCombo.setItems(FXCollections.observableArrayList("RES-001 - Maria Silva", "RES-002 - João Santos"));
        DatePicker startDatePicker = new DatePicker(LocalDate.now());
        DatePicker endDatePicker = new DatePicker(LocalDate.now().plusDays(5));
        TextArea flightsArea = new TextArea();
        TextArea accommodationArea = new TextArea();
        TextArea activitiesArea = new TextArea();

        grid.add(new Label("Reserva:"), 0, 0);
        grid.add(reservationCombo, 1, 0);
        grid.add(new Label("Data de Início:"), 0, 1);
        grid.add(startDatePicker, 1, 1);
        grid.add(new Label("Data de Término:"), 0, 2);
        grid.add(endDatePicker, 1, 2);
        grid.add(new Label("Voos:"), 0, 3);
        grid.add(flightsArea, 1, 3);
        grid.add(new Label("Acomodações:"), 0, 4);
        grid.add(accommodationArea, 1, 4);
        grid.add(new Label("Atividades:"), 0, 5);
        grid.add(activitiesArea, 1, 5);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                // Here you would normally create a new Itinerary object
                // For this example, we just print the values
                System.out.println("Gerando Itinerário:");
                System.out.println("Reserva: " + reservationCombo.getValue());
                System.out.println("Data de Início: " + startDatePicker.getValue());
                System.out.println("Data de Término: " + endDatePicker.getValue());
                // In a real app, you would process this data
                return dialogButton;
            }
            return null;
        });

        dialog.showAndWait();
    }
}