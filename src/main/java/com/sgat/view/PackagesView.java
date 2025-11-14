package com.sgat.view;

import com.sgat.model.Package;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.*;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Optional;

public class PackagesView {
    private final Stage stage;
    private final VBox view;
    private final TilePane packagesGrid;
    private final ObservableList<Package> packages = FXCollections.observableArrayList();
    private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    public PackagesView(Stage stage) {
        this.stage = stage;
        view = new VBox(24);
        view.setPadding(new Insets(24));

        Node header = createHeader();
        packagesGrid = createPackagesGrid();

        view.getChildren().addAll(header, packagesGrid);

        setupData();
        rebuildPackageGrid();
    }

    public Node getView() {
        return view;
    }

    private void setupData() {
        packages.addAll(
            new Package("Aventura na Patagônia", "El Calafate, Argentina", "Explore as geleiras e montanhas da Patagônia em uma viagem inesquecível.", "10 dias / 9 noites", 12500.00, LocalDate.of(2026, 5, 20), LocalDate.of(2026, 5, 30), "Dia 1-3: El Calafate e Glaciar Perito Moreno. Dia 4-7: El Chaltén e trilhas. Dia 8-10: Ushuaia, o fim do mundo."),
            new Package("Cultura e Cores do Japão", "Tóquio e Kyoto, Japão", "Uma imersão completa na cultura japonesa, dos templos antigos à metrópole futurista.", "12 dias / 11 noites", 22000.00, LocalDate.of(2026, 4, 10), LocalDate.of(2026, 4, 22), "Dia 1-5: Exploração de Tóquio (Shibuya, Akihabara). Dia 6-10: Viagem para Kyoto (templos, gueixas). Dia 11-12: Retorno."),
            new Package("Safári na Tanzânia", "Serengeti, Tanzânia", "Testemunhe a grande migração e a vida selvagem africana em um safári de luxo.", "7 dias / 6 noites", 18500.00, LocalDate.of(2026, 7, 15), LocalDate.of(2026, 7, 22), "Dia 1-2: Chegada em Arusha. Dia 3-6: Safári no Parque Nacional Serengeti. Dia 7: Retorno.")
        );

        packages.addListener((ListChangeListener<Package>) c -> rebuildPackageGrid());
    }

    private Node createHeader() {
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);

        VBox titleBox = new VBox(-2);
        Label title = new Label("Pacotes Turísticos");
        title.getStyleClass().add("page-title");
        Label subtitle = new Label("Gerencie os pacotes de viagem disponíveis");
        subtitle.getStyleClass().add("page-subtitle");
        titleBox.getChildren().addAll(title, subtitle);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button newPackageButton = new Button("Novo Pacote");
        newPackageButton.getStyleClass().add("add-button");
        FontIcon plusIcon = new FontIcon(MaterialDesignP.PLUS);
        newPackageButton.setGraphic(plusIcon);
        newPackageButton.setOnAction(e -> handleAddPackage());

        header.getChildren().addAll(titleBox, spacer, newPackageButton);
        return header;
    }

    private TilePane createPackagesGrid() {
        TilePane grid = new TilePane(16, 16);
        grid.setPadding(new Insets(8));
        return grid;
    }

    private void rebuildPackageGrid() {
        packagesGrid.getChildren().clear();
        for (Package pkg : packages) {
            packagesGrid.getChildren().add(createPackageCard(pkg));
        }
    }

    private Node createPackageCard(Package pkg) {
        VBox card = new VBox(12);
        card.getStyleClass().add("package-card");

        // Header
        BorderPane header = new BorderPane();
        VBox titleBox = new VBox(-2);
        Label nameLabel = new Label();
        nameLabel.textProperty().bind(pkg.nameProperty());
        nameLabel.getStyleClass().add("package-card-title");

        HBox destinationBox = new HBox(4);
        destinationBox.setAlignment(Pos.CENTER_LEFT);
        FontIcon pinIcon = new FontIcon(MaterialDesignM.MAP_MARKER);
        pinIcon.getStyleClass().add("package-card-icon");
        pinIcon.setIconSize(20);
        Label destinationLabel = new Label();
        destinationLabel.textProperty().bind(pkg.destinationProperty());
        destinationLabel.getStyleClass().add("package-card-subtitle");
        destinationBox.getChildren().addAll(pinIcon, destinationLabel);
        titleBox.getChildren().addAll(nameLabel, destinationBox);

        HBox controlsBox = new HBox(8);
        Button editButton = new Button();
        editButton.getStyleClass().add("icon-button");
        FontIcon editIcon = new FontIcon(MaterialDesignP.PENCIL);
        editIcon.setIconSize(20);
        editButton.setGraphic(editIcon);
        editButton.setOnAction(e -> handleEditPackage(pkg));

        Button deleteButton = new Button();
        deleteButton.getStyleClass().addAll("icon-button", "delete-button");
        FontIcon deleteIcon = new FontIcon(MaterialDesignD.DELETE);
        deleteIcon.setIconSize(20);
        deleteButton.setGraphic(deleteIcon);
        deleteButton.setOnAction(e -> handleDeletePackage(pkg));
        controlsBox.getChildren().addAll(editButton, deleteButton);

        header.setCenter(titleBox);
        header.setRight(controlsBox);

        // Content
        VBox content = new VBox(12);
        Label descriptionLabel = new Label();
        descriptionLabel.textProperty().bind(pkg.descriptionProperty());
        descriptionLabel.setWrapText(true);
        descriptionLabel.getStyleClass().add("package-card-description");

        Separator separator = new Separator();

        // Footer
        HBox footer = new HBox();
        footer.setAlignment(Pos.CENTER_LEFT);
        HBox durationBox = new HBox(4);
        durationBox.setAlignment(Pos.CENTER_LEFT);
        FontIcon calendarIcon = new FontIcon(MaterialDesignC.CALENDAR);
        calendarIcon.getStyleClass().add("package-card-icon");
        calendarIcon.setIconSize(18);
        Label durationLabel = new Label();
        durationLabel.textProperty().bind(pkg.durationProperty());
        durationBox.getChildren().addAll(calendarIcon, durationLabel);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox priceBox = new HBox(4);
        priceBox.setAlignment(Pos.CENTER_LEFT);
        FontIcon dollarIcon = new FontIcon(MaterialDesignC.CASH);
        dollarIcon.getStyleClass().add("package-card-icon");
        dollarIcon.setIconSize(20);
        Label priceLabel = new Label();
        priceLabel.textProperty().bind(Bindings.createStringBinding(() -> currencyFormat.format(pkg.getPrice()), pkg.priceProperty()));
        priceLabel.getStyleClass().add("price-label");
        priceBox.getChildren().addAll(dollarIcon, priceLabel);

        footer.getChildren().addAll(durationBox, spacer, priceBox);
        content.getChildren().addAll(descriptionLabel, separator, footer);

        card.getChildren().addAll(header, content);
        return card;
    }

    private void handleAddPackage() {
        showPackageDialog(null).ifPresent(newPackage -> {
            packages.add(newPackage);
            showAlert(Alert.AlertType.INFORMATION, "Pacote Adicionado", "O novo pacote foi cadastrado com sucesso.");
        });
    }

    private void handleEditPackage(Package pkg) {
        showPackageDialog(pkg).ifPresent(editedPackage -> {
            // As propriedades já foram atualizadas, a UI se atualiza sozinha via bindings.
            showAlert(Alert.AlertType.INFORMATION, "Pacote Atualizado", "O pacote foi atualizado com sucesso.");
        });
    }

    private void handleDeletePackage(Package pkg) {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.initOwner(this.stage);
        confirmation.setTitle("Confirmar Exclusão");
        confirmation.setHeaderText("Excluir Pacote: " + pkg.getName());
        confirmation.setContentText("Você tem certeza que deseja remover este pacote? Esta ação não pode ser desfeita.");

        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                packages.remove(pkg);
                showAlert(Alert.AlertType.INFORMATION, "Pacote Removido", "O pacote foi removido com sucesso.");
            }
        });
    }

    private Optional<Package> showPackageDialog(Package pkg) {
        Dialog<Package> dialog = new Dialog<>();
        dialog.initOwner(this.stage);
        dialog.setTitle(pkg == null ? "Adicionar Novo Pacote" : "Editar Pacote");
        dialog.setHeaderText(pkg == null ? "Preencha as informações do novo pacote." : "Atualize as informações do pacote.");

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nameField = new TextField();
        nameField.setPromptText("Ex: Férias em Cancún");
        TextField destField = new TextField();
        destField.setPromptText("Ex: Cancún, México");
        TextField priceField = new TextField();
        priceField.setPromptText("Ex: 8500.00");
        TextField durationField = new TextField();
        durationField.setPromptText("Ex: 7 dias / 6 noites");
        DatePicker startDatePicker = new DatePicker();
        DatePicker endDatePicker = new DatePicker();
        TextArea descriptionArea = new TextArea();
        descriptionArea.setPromptText("Uma breve descrição do que o pacote inclui.");
        descriptionArea.setWrapText(true);
        TextArea itineraryArea = new TextArea();
        itineraryArea.setPromptText("Dia 1: ..., Dia 2: ...");
        itineraryArea.setWrapText(true);

        grid.add(new Label("Nome do Pacote:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Destino:"), 0, 1);
        grid.add(destField, 1, 1);
        grid.add(new Label("Preço (R$):"), 0, 2);
        grid.add(priceField, 1, 2);
        grid.add(new Label("Duração:"), 0, 3);
        grid.add(durationField, 1, 3);
        grid.add(new Label("Data Início:"), 0, 4);
        grid.add(startDatePicker, 1, 4);
        grid.add(new Label("Data Fim:"), 0, 5);
        grid.add(endDatePicker, 1, 5);
        grid.add(new Label("Descrição:"), 0, 6);
        grid.add(descriptionArea, 1, 6);
        GridPane.setVgrow(descriptionArea, Priority.ALWAYS);
        grid.add(new Label("Itinerário:"), 0, 7);
        grid.add(itineraryArea, 1, 7);
        GridPane.setVgrow(itineraryArea, Priority.ALWAYS);

        dialog.getDialogPane().setContent(grid);

        Node okButton = dialog.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setDisable(true);

        // Validação simples
        nameField.textProperty().addListener((obs, old, text) -> okButton.setDisable(text.trim().isEmpty()));

        if (pkg != null) {
            nameField.setText(pkg.getName());
            destField.setText(pkg.getDestination());
            priceField.setText(String.valueOf(pkg.getPrice()));
            durationField.setText(pkg.getDuration());
            startDatePicker.setValue(pkg.getStartDate());
            endDatePicker.setValue(pkg.getEndDate());
            descriptionArea.setText(pkg.getDescription());
            itineraryArea.setText(pkg.getItinerary());
        }

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                try {
                    double price = Double.parseDouble(priceField.getText());
                    if (pkg == null) {
                        return new Package(nameField.getText(), destField.getText(), descriptionArea.getText(), durationField.getText(), price, startDatePicker.getValue(), endDatePicker.getValue(), itineraryArea.getText());
                    } else {
                        pkg.setName(nameField.getText());
                        pkg.setDestination(destField.getText());
                        pkg.setPrice(price);
                        pkg.setDuration(durationField.getText());
                        pkg.setStartDate(startDatePicker.getValue());
                        pkg.setEndDate(endDatePicker.getValue());
                        pkg.setDescription(descriptionArea.getText());
                        pkg.setItinerary(itineraryArea.getText());
                        return pkg;
                    }
                } catch (NumberFormatException e) {
                    showAlert(Alert.AlertType.ERROR, "Erro de Formato", "O preço deve ser um número válido.");
                    return null;
                }
            }
            return null;
        });

        return dialog.showAndWait();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.initOwner(this.stage);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
