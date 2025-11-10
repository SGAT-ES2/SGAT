package com.sgat.view;

import com.sgat.controller.ClientsController;
import com.sgat.model.Client;
import javafx.stage.Stage;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.SVGPath;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.*;

import java.util.Optional;

public class ClientsView {
    private final Stage stage;
    private final VBox view;
    private final ClientsController controller;
    private TableView<Client> table;
    private final ObservableList<Client> clients;

    public ClientsView(Stage stage) {
        this.stage = stage;
        this.clients = FXCollections.observableArrayList();
        this.controller = new ClientsController(this, clients);
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
        clients.addAll(
            new Client(1, "Beatriz Oliveira", "beatriz.oliveira@example.com", "(11) 98765-4321", "123.456.789-00", "Rua das Flores, 123, São Paulo, SP", "Prefere destinos de praia e resorts all-inclusive. Gosta de viajar em família.", 5),
            new Client(2, "Carlos Pereira", "carlos.pereira@example.com", "(21) 91234-5678", "987.654.321-00", "Avenida Copacabana, 456, Rio de Janeiro, RJ", "Interessado em turismo de aventura, como trilhas e montanhismo. Viagens solo.", 8),
            new Client(3, "Fernanda Costa", "fernanda.costa@example.com", "(31) 99999-8888", "456.789.123-00", "Rua da Bahia, 789, Belo Horizonte, MG", "Foco em viagens culturais, museus, e gastronomia local. Viaja com o parceiro.", 3)
        );
    }

    private Node createHeader() {
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);

        VBox titleBox = new VBox(-2);
        Label title = new Label("Clientes");
        title.getStyleClass().add("page-title");
        Label subtitle = new Label("Gerencie o cadastro de clientes");
        subtitle.getStyleClass().add("page-subtitle");
        titleBox.getChildren().addAll(title, subtitle);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button newClientButton = new Button("Novo Cliente");
        newClientButton.getStyleClass().add("add-button");
        FontIcon plusIcon = new FontIcon(MaterialDesignP.PLUS);
        newClientButton.setGraphic(plusIcon);
        newClientButton.setOnAction(e -> handleAddClient());

        header.getChildren().addAll(titleBox, spacer, newClientButton);
        return header;
    }

    private Node createTableCard() {
        VBox card = new VBox(12);
        card.getStyleClass().add("table-card");
        VBox.setVgrow(card, Priority.ALWAYS);

        Label title = new Label("Lista de Clientes");
        title.getStyleClass().add("info-card-title");
        Label subtitle = new Label("Todos os clientes cadastrados no sistema.");
        subtitle.getStyleClass().add("info-card-subtitle");

        table = createTable();
        VBox.setVgrow(table, Priority.ALWAYS);

        card.getChildren().addAll(title, subtitle, table);
        return card;
    }

    private TableView<Client> createTable() {
        TableView<Client> tableView = new TableView<>(clients);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        TableColumn<Client, String> nameCol = new TableColumn<>("Nome");
        nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        nameCol.setCellFactory(col -> new NameCell());
        nameCol.setPrefWidth(250);

        TableColumn<Client, Client> contactCol = new TableColumn<>("Contato");
        contactCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()));
        contactCol.setCellFactory(col -> new ContactCell());
        contactCol.setPrefWidth(250);

        TableColumn<Client, Integer> travelsCol = new TableColumn<>("Viagens");
        travelsCol.setCellValueFactory(cellData -> cellData.getValue().travelCountProperty().asObject());
        travelsCol.setCellFactory(col -> new TravelsCell());
        travelsCol.setPrefWidth(100);

        TableColumn<Client, String> prefsCol = new TableColumn<>("Preferências");
        prefsCol.setCellValueFactory(cellData -> cellData.getValue().preferencesProperty());
        prefsCol.setPrefWidth(300);

        TableColumn<Client, Void> actionsCol = new TableColumn<>("Ações");
        actionsCol.setCellFactory(col -> new ActionsCell());
        actionsCol.setMinWidth(100);
        actionsCol.setMaxWidth(100);

        tableView.getColumns().addAll(nameCol, contactCol, travelsCol, prefsCol, actionsCol);
        return tableView;
    }

    private void handleAddClient() {
        showClientDialog(null).ifPresent(client -> {
            controller.addClient(client);
            showAlert(Alert.AlertType.INFORMATION, "Cliente Adicionado", "Novo cliente cadastrado com sucesso.");
        });
    }

    private void handleEditClient(Client client) {
        showClientDialog(client).ifPresent(editedClient -> {
            controller.updateClient(editedClient);
            showAlert(Alert.AlertType.INFORMATION, "Cliente Atualizado", "Os dados do cliente foram atualizados.");
        });
    }

    private void handleDeleteClient(Client client) {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmar Exclusão");
        confirmation.setHeaderText("Excluir Cliente: " + client.getName());
        confirmation.setContentText("Você tem certeza que deseja remover este cliente?");

        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                controller.deleteClient(client);
                showAlert(Alert.AlertType.INFORMATION, "Cliente Removido", "O cliente foi removido com sucesso.");
            }
        });
    }

    private Optional<Client> showClientDialog(Client client) {
        Dialog<Client> dialog = new Dialog<>();
        dialog.initOwner(this.stage);
        dialog.setTitle(client == null ? "Adicionar Novo Cliente" : "Editar Cliente");
        dialog.setHeaderText(client == null ? "Preencha as informações do novo cliente." : "Atualize as informações do cliente.");

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nameField = new TextField();
        TextField emailField = new TextField();
        TextField phoneField = new TextField();
        TextField cpfField = new TextField();
        TextField addressField = new TextField();
        TextArea prefsArea = new TextArea();
        prefsArea.setWrapText(true);

        grid.add(new Label("Nome Completo:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Email:"), 0, 1);
        grid.add(emailField, 1, 1);
        grid.add(new Label("Telefone:"), 0, 2);
        grid.add(phoneField, 1, 2);
        grid.add(new Label("CPF:"), 0, 3);
        grid.add(cpfField, 1, 3);
        grid.add(new Label("Endereço:"), 0, 4);
        grid.add(addressField, 1, 4);
        grid.add(new Label("Preferências:"), 0, 5);
        grid.add(prefsArea, 1, 5);

        dialog.getDialogPane().setContent(grid);

        if (client != null) {
            nameField.setText(client.getName());
            emailField.setText(client.getEmail());
            phoneField.setText(client.getPhone());
            cpfField.setText(client.getCpf());
            addressField.setText(client.getAddress());
            prefsArea.setText(client.getPreferences());
        }

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                if (client == null) {
                    return new Client(clients.size() + 1, nameField.getText(), emailField.getText(), phoneField.getText(), cpfField.getText(), addressField.getText(), prefsArea.getText(), 0);
                } else {
                    client.setName(nameField.getText());
                    client.setEmail(emailField.getText());
                    client.setPhone(phoneField.getText());
                    client.setCpf(cpfField.getText());
                    client.setAddress(addressField.getText());
                    client.setPreferences(prefsArea.getText());
                    return client;
                }
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

    // --- Células Customizadas da Tabela ---

    private class NameCell extends TableCell<Client, String> {
        private final HBox box;
        private final StackPane iconContainer;
        private final Label label;

        public NameCell() {
            box = new HBox(12);
            box.setAlignment(Pos.CENTER_LEFT);
            iconContainer = new StackPane();
            FontIcon userIcon = new FontIcon(MaterialDesignA.ACCOUNT);
            userIcon.getStyleClass().add("table-icon");
            iconContainer.getChildren().add(userIcon);
            label = new Label();
            box.getChildren().addAll(iconContainer, label);
            setGraphic(box);
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setGraphic(null);
            } else {
                label.setText(item);
                setGraphic(box);
            }
        }
    }

    private class ContactCell extends TableCell<Client, Client> {
        private final VBox box;

        public ContactCell() {
            box = new VBox(4);
            setGraphic(box);
        }

        @Override
        protected void updateItem(Client client, boolean empty) {
            super.updateItem(client, empty);
            if (empty || client == null) {
                setGraphic(null);
            } else {
                box.getChildren().clear();
                HBox emailBox = new HBox(4);
                FontIcon emailIcon = new FontIcon(MaterialDesignE.EMAIL);
                emailIcon.getStyleClass().add("table-icon");
                emailBox.getChildren().addAll(emailIcon, new Label(client.getEmail()));

                HBox phoneBox = new HBox(4);
                FontIcon phoneIcon = new FontIcon(MaterialDesignP.PHONE);
                phoneIcon.getStyleClass().add("table-icon");
                phoneBox.getChildren().addAll(phoneIcon, new Label(client.getPhone()));

                box.getChildren().addAll(emailBox, phoneBox);
                setGraphic(box);
            }
        }
    }

    private class TravelsCell extends TableCell<Client, Integer> {
        private final Label badge;

        public TravelsCell() {
            badge = new Label();
            badge.getStyleClass().add("travels-badge");
            setGraphic(badge);
            setAlignment(Pos.CENTER_LEFT);
        }

        @Override
        protected void updateItem(Integer count, boolean empty) {
            super.updateItem(count, empty);
            if (empty || count == null || count == 0) {
                setGraphic(null);
            } else {
                badge.setText(count + (count > 1 ? " viagens" : " viagem"));
                setGraphic(badge);
            }
        }
    }

    private class ActionsCell extends TableCell<Client, Void> {
        private final HBox box;
        private final Button editButton = new Button();
        private final Button deleteButton = new Button();

        public ActionsCell() {
            box = new HBox(8);
            box.setAlignment(Pos.CENTER);

            editButton.getStyleClass().addAll("icon-button");
            FontIcon editIcon = new FontIcon(MaterialDesignP.PENCIL);
            editIcon.setIconSize(20);
            editIcon.getStyleClass().add("table-icon");
            editButton.setGraphic(editIcon);

            deleteButton.getStyleClass().addAll("icon-button", "delete-button");
            FontIcon deleteIcon = new FontIcon(MaterialDesignD.DELETE);
            deleteIcon.setIconSize(20);
            deleteIcon.getStyleClass().add("table-icon");
            deleteButton.setGraphic(deleteIcon);

            box.getChildren().addAll(editButton, deleteButton);
            setGraphic(box);

            editButton.setOnAction(e -> {
                Client client = getTableView().getItems().get(getIndex());
                handleEditClient(client);
            });

            deleteButton.setOnAction(e -> {
                Client client = getTableView().getItems().get(getIndex());
                handleDeleteClient(client);
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
