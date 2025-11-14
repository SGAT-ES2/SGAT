package com.sgat.view;

import java.time.LocalDate;
import java.util.Map;
import java.util.HashMap;

import com.sgat.model.Pagamento; // Importa o modelo

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PaymentDialog extends Dialog<Pagamento> {

    private ComboBox<String> reservationCombo;
    private TextField valueField;
    private ComboBox<String> methodCombo;
    private DatePicker datePicker;
    private TextArea notesArea;
    private Label errorLabel;
    
    // NOVO: Mapa para associar o texto de exibição ao ID do Cliente/Reserva e outros dados.
    // CHAVE: Texto de exibição. VALOR: String formatada com IDs (ex: "RES-001|101|Maria Silva")
    private final Map<String, String> reservationDataMap = new HashMap<>();

    public PaymentDialog(Stage ownerStage) {
        
        // POPULANDO O MAPA: Simulação da busca no banco. 
        // A lógica do Controller deve fornecer dados reais.
        // Formato: CodigoReserva|IDCliente|NomeCliente
        reservationDataMap.put("RES-001 - Maria Silva", "RES-001|101|Maria Silva");
        reservationDataMap.put("RES-002 - João Santos", "RES-002|102|João Santos");
        reservationDataMap.put("RES-003 - Ana Costa", "RES-003|103|Ana Costa");
        
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
        // Usa as CHAVES do mapa (o texto de exibição)
        reservationCombo.setItems(FXCollections.observableArrayList(reservationDataMap.keySet()));
        reservationCombo.setPromptText("Selecione a reserva"); 
        reservationCombo.setMaxWidth(Double.MAX_VALUE);
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
        boolean validReservation = reservationCombo.getValue() != null && !reservationCombo.getValue().isEmpty();
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
            
            String selectedText = reservationCombo.getValue();
            String dataString = null;
            
            // Validações
            if (selectedText == null || selectedText.isEmpty()) {
                errorMessage += " • Selecione a Reserva/Pessoa a ser paga.\n";
            } else {
                dataString = reservationDataMap.get(selectedText);
                if (dataString == null) {
                    errorMessage += " • Dados da reserva inválidos.\n";
                }
            }
            
            // ... (Validações de Valor, Data e Método inalteradas) ...
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
            // ... (Fim das validações) ...

            if (!errorMessage.isEmpty()) {
                String header = "🚨 Por favor, corrija os erros a seguir:\n\n";
                errorLabel.setText(header + errorMessage);
                return null; // Não fecha o diálogo
            }
            
            // EXTRAÇÃO DO ID
            String[] parts = dataString.split("\\|");
            String reservationCode = parts[0]; 
            int clientId = Integer.parseInt(parts[1]); // <-- ID DO CLIENTE EXISTENTE
            String clientName = parts[2];
            
            // Formato de texto para exibição no PaymentsView
            String displayReserva = reservationCode + "\n" + clientName;
            
            // Retorna o objeto Pagamento com o ID do Cliente
            return new Pagamento(
                0, 
                displayReserva, 
                parsedValue,
                methodCombo.getValue(),
                datePicker.getValue(),
                notesArea.getText(),
                clientId // PASSANDO O ID DO CLIENTE EXISTENTE
            );
        }
        return null; // Cancel
    }
}