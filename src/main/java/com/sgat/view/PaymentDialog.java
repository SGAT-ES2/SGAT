package com.sgat.view;

import com.sgat.controller.PaymentsController;
import com.sgat.model.Pagamento;
import com.sgat.model.Reservation;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.time.LocalDate;

public class PaymentDialog extends Dialog<Pagamento> {

    private final PaymentsController controller;
    private ComboBox<Reservation> reservationCombo;
    private TextField valueField;
    private ComboBox<String> methodCombo;
    private DatePicker datePicker;
    private TextArea notesArea;
    private Label errorLabel;

    public PaymentDialog(Stage ownerStage, PaymentsController controller) {
        this.controller = controller;

        this.setTitle("Registrar Novo Pagamento");
        this.setHeaderText("Preencha as informações do pagamento");
        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Button okButton = (Button) this.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setText("Registrar Pagamento");
        okButton.getStyleClass().add("primary-button");

        if (ownerStage != null) {
            this.initOwner(ownerStage);
        }

        this.getDialogPane().setPrefWidth(700);
        this.getDialogPane().setPrefHeight(480);
        this.getDialogPane().setMinWidth(700);
        this.getDialogPane().setMinHeight(480);

        buildFormContent();

        okButton.setDisable(true);
        addInputListeners(okButton);
        Platform.runLater(() -> validateInputs(okButton));

        setResultConverter(this::handleResult);
    }

    private void buildFormContent() {
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(15);
        grid.setPadding(new Insets(20, 25, 20, 25));

        errorLabel = new Label(" ");
        errorLabel.setStyle("-fx-text-fill: #D32F2F; -fx-font-weight: bold; -fx-font-size: 13px; -fx-padding: 0 0 10 0;");
        grid.add(errorLabel, 0, 0, 2, 1);

        reservationCombo = new ComboBox<>();
        reservationCombo.setItems(FXCollections.observableArrayList(controller.getAllReservations()));
        reservationCombo.setPromptText("Selecione a reserva");
        reservationCombo.setMaxWidth(Double.MAX_VALUE);
        reservationCombo.setConverter(new StringConverter<>() {
            @Override
            public String toString(Reservation reservation) {
                if (reservation == null) {
                    return "";
                }
                return "RES-" + reservation.getId() + " - " + reservation.getClient().getName();
            }

            @Override
            public Reservation fromString(String string) {
                return null;
            }
        });
        grid.add(new Label("Reserva"), 0, 1, 2, 1);
        grid.add(reservationCombo, 0, 2, 2, 1);
        GridPane.setHgrow(reservationCombo, Priority.ALWAYS);

        valueField = new TextField();
        valueField.setPromptText("R$ 0,00");
        datePicker = new DatePicker(LocalDate.now());
        datePicker.setPromptText("dd/mm/aaaa");
        datePicker.setMaxWidth(Double.MAX_VALUE);
        grid.add(new Label("Valor do Pagamento"), 0, 3);
        grid.add(new Label("Data do Pagamento"), 1, 3);
        grid.add(valueField, 0, 4);
        grid.add(datePicker, 1, 4);
        GridPane.setHgrow(valueField, Priority.ALWAYS);
        GridPane.setHgrow(datePicker, Priority.ALWAYS);

        methodCombo = new ComboBox<>();
        methodCombo.setItems(FXCollections.observableArrayList("Cartão de Crédito", "Transferência", "Pix", "Boleto"));
        methodCombo.setPromptText("Selecione o método");
        methodCombo.setMaxWidth(Double.MAX_VALUE);
        grid.add(new Label("Método de Pagamento"), 0, 5, 2, 1);
        grid.add(methodCombo, 0, 6, 2, 1);
        GridPane.setHgrow(methodCombo, Priority.ALWAYS);

        notesArea = new TextArea();
        notesArea.setPromptText("Informações adicionais sobre o pagamento");
        notesArea.setPrefHeight(90);
        grid.add(new Label("Observações"), 0, 7, 2, 1);
        grid.add(notesArea, 0, 8, 2, 1);
        GridPane.setVgrow(notesArea, Priority.ALWAYS);
        GridPane.setHgrow(notesArea, Priority.ALWAYS);

        VBox content = new VBox(grid);
        VBox.setVgrow(grid, Priority.ALWAYS);

        this.getDialogPane().setContent(content);
    }

    private void addInputListeners(Button okButton) {
        reservationCombo.valueProperty().addListener((obs, oldVal, newVal) -> validateInputs(okButton));
        methodCombo.valueProperty().addListener((obs, oldVal, newVal) -> validateInputs(okButton));
        datePicker.valueProperty().addListener((obs, oldVal, newVal) -> validateInputs(okButton));
        valueField.textProperty().addListener((obs, oldVal, newVal) -> validateInputs(okButton));
    }

    private void validateInputs(Button okButton) {
        boolean validReservation = reservationCombo.getValue() != null;
        boolean validValue = valueField.getText() != null && !valueField.getText().trim().isEmpty();
        boolean validDate = datePicker.getValue() != null;
        boolean validMethod = methodCombo.getValue() != null && !methodCombo.getValue().isEmpty();

        boolean allValid = validReservation && validValue && validDate && validMethod;

        okButton.setDisable(!allValid);

        if (allValid) {
            errorLabel.setText(" ");
        }
    }

    private Pagamento handleResult(ButtonType dialogButton) {
        if (dialogButton == ButtonType.OK) {

            String errorMessage = "";
            double parsedValue = 0.0;

            if (reservationCombo.getValue() == null) {
                errorMessage += " • Selecione a Reserva/Pessoa a ser paga.\n";
            }

            if (valueField.getText() == null || valueField.getText().trim().isEmpty()) {
                errorMessage += " • Preencha o Valor do Pagamento.\n";
            } else {
                try {
                    String cleanValue = valueField.getText().replaceAll("[^\\d,\\.]", "").replace(",", ".");
                    if (cleanValue.isEmpty()) throw new NumberFormatException();
                    parsedValue = Double.parseDouble(cleanValue);
                } catch (NumberFormatException e) {
                    errorMessage += " • O Valor do Pagamento deve ser um número válido.\n";
                }
            }

            if (datePicker.getValue() == null) {
                errorMessage += " • Selecione a Data do Pagamento.\n";
            }

            if (methodCombo.getValue() == null || methodCombo.getValue().isEmpty()) {
                errorMessage += " • Selecione o Método de Pagamento.\n";
            }

            if (!errorMessage.isEmpty()) {
                String header = "🚨 Por favor, corrija os erros a seguir:\n\n";
                errorLabel.setText(header + errorMessage);
                return null;
            }

            return new Pagamento(
                    reservationCombo.getValue(),
                    datePicker.getValue(),
                    parsedValue,
                    methodCombo.getValue(),
                    "Confirmado",
                    notesArea.getText()
            );
        }
        return null;
    }
}