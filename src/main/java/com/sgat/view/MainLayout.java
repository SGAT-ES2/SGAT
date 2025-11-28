package com.sgat.view;

// --- IMPORTS CRITICOS ---
import com.sgat.controller.ScreenController;
import com.sgat.controller.ReportsController;
import com.sgat.view.ItinerariesView;
// ------------------------

import java.util.HashMap;
import java.util.Map;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.*;
import org.kordamp.ikonli.materialdesign2.MaterialDesignA;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;
import org.kordamp.ikonli.materialdesign2.MaterialDesignL;
import org.kordamp.ikonli.materialdesign2.MaterialDesignM;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;
import org.kordamp.ikonli.materialdesign2.MaterialDesignV;

import com.sgat.controller.DashboardController;
import com.sgat.controller.ReportsController;
import com.sgat.controller.ScreenController;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainLayout {

    private final BorderPane mainLayout;
    private final Stage stage;
    private final ScreenController screenController;
    private final DashboardController dashboardController;

    private final BooleanProperty sidebarCollapsed = new SimpleBooleanProperty(false);
    private VBox sidebar;
    private final StackPane contentStack;
    private final Map<String, Node> views = new HashMap<>();
    private final ToggleGroup toggleGroup = new ToggleGroup();
    private final int ICON_SIZE = 20;

    // Controladores que precisam ser acessados depois
    private ItinerariesView itinerariesController;
    private ReportsController reportsController;
    private DashboardView dashboardView;

    // ------------- AJUSTE NECESSÁRIO: CONSTRUTOR RECEBE DashboardController --------------
    public MainLayout(Stage stage, ScreenController screenController, DashboardController dashboardController) {
        this.stage = stage;
        this.screenController = screenController;
        this.dashboardController = dashboardController;

        mainLayout = new BorderPane();
        contentStack = new StackPane();

        // --- INICIALIZAR VIEWS ---
        views.put("Pagamentos", new PaymentsView().getView());

        // Configuração dos Relatórios
        ReportsView reportsView = new ReportsView();
        this.reportsController = new ReportsController(reportsView); // Guarda a referência!
        views.put("Relatórios", reportsView.getView());

        // ------------- AJUSTE: DashboardView agora recebe controller --------------
        this.dashboardView = new DashboardView(dashboardController);
        views.put("Dashboard", dashboardView.getView());

        views.put("Pacotes", new PackagesView(stage).getView());
        views.put("Clientes", new ClientsView(stage).getView());
        views.put("Reservas", new ReservationsView(stage).getView());

        this.itinerariesController = new ItinerariesView(stage);
        views.put("Itinerários", itinerariesController.getView());

        contentStack.getChildren().addAll(views.values());

        createSidebar();
        Node mainContent = createMainContentArea();

        mainLayout.setLeft(sidebar);
        mainLayout.setCenter(mainContent);

        setupViewSwitching();
        showView("Dashboard");
    }

    public Parent getLayout() { return mainLayout; }

    // --- SIDEBAR (Resumida para focar no erro) ---
    private void createSidebar() {
        sidebar = new VBox();
        sidebar.getStyleClass().add("sidebar");
        sidebar.prefWidthProperty().bind(Bindings.when(sidebarCollapsed).then(70).otherwise(250));
        sidebar.minWidthProperty().bind(sidebar.prefWidthProperty());
        sidebar.maxWidthProperty().bind(sidebar.prefWidthProperty());

        Node header = createSidebarHeader();
        Node navigation = createSidebarNavigation();
        Node footer = createSidebarFooter();

        VBox.setVgrow(navigation, Priority.ALWAYS);
        sidebar.getChildren().addAll(header, navigation, footer);
    }

    private Node createSidebarHeader() {
        HBox header = new HBox();
        header.getStyleClass().add("sidebar-header");

        StackPane iconContainer = new StackPane();
        iconContainer.getStyleClass().add("sidebar-header-icon-container");
        SVGPath planeIcon = new SVGPath();
        planeIcon.setContent("M17.8 19.2 16 11l3.5-3.5C21 6 21.5 4 21 3c-1-.5-3 0-4.5 1.5L13 8 4.8 6.2c-.5-.1-.9.1-1.1.5l-.3.5c-.2.5-.1 1 .3 1.3L9 12l-2 3H4l-1 1 3 2 2 3 1-1v-3l3-2 3.5 5.3c.3.4.8.5 1.3.3l.5-.2c.4-.3.6-.7.5-1.2z");
        planeIcon.getStyleClass().add("icon-svg");
        iconContainer.getChildren().add(planeIcon);

        VBox titleBox = new VBox(-2);
        titleBox.setAlignment(Pos.CENTER_LEFT);
        Label title = new Label("TravelManager");
        title.getStyleClass().add("sidebar-header-title");
        Label subtitle = new Label("Sistema de Gestão");
        subtitle.getStyleClass().add("sidebar-header-subtitle");
        titleBox.getChildren().addAll(title, subtitle);

        titleBox.visibleProperty().bind(sidebarCollapsed.not());
        titleBox.managedProperty().bind(sidebarCollapsed.not());

        header.getChildren().addAll(iconContainer, titleBox);
        return header;
    }

    private Node createSidebarNavigation() {
        VBox navigation = new VBox(8);
        VBox buttons = new VBox(4);

        // Ícones
        FontIcon dashIcon = new FontIcon(MaterialDesignV.VIEW_DASHBOARD); dashIcon.setIconSize(ICON_SIZE);
        FontIcon packIcon = new FontIcon(MaterialDesignP.PACKAGE_VARIANT_CLOSED); packIcon.setIconSize(ICON_SIZE);
        FontIcon cliIcon = new FontIcon(MaterialDesignA.ACCOUNT_MULTIPLE); cliIcon.setIconSize(ICON_SIZE);
        FontIcon resIcon = new FontIcon(MaterialDesignC.CALENDAR); resIcon.setIconSize(ICON_SIZE);
        FontIcon payIcon = new FontIcon(MaterialDesignC.CREDIT_CARD); payIcon.setIconSize(ICON_SIZE);
        FontIcon itiIcon = new FontIcon(MaterialDesignM.MAP_MARKER); itiIcon.setIconSize(ICON_SIZE);
        FontIcon repIcon = new FontIcon(MaterialDesignC.CHART_BAR); repIcon.setIconSize(ICON_SIZE);

        buttons.getChildren().addAll(
                createNavButton("Dashboard", dashIcon),
                createNavButton("Pacotes", packIcon),
                createNavButton("Clientes", cliIcon),
                createNavButton("Reservas", resIcon),
                createNavButton("Pagamentos", payIcon),
                createNavButton("Itinerários", itiIcon),
                createNavButton("Relatórios", repIcon)
        );
        navigation.getChildren().addAll(buttons);
        return navigation;
    }

    private ToggleButton createNavButton(String text, Node iconNode) {
        ToggleButton button = new ToggleButton();
        button.setToggleGroup(toggleGroup);
        button.getStyleClass().add("sidebar-menu-button");
        button.setUserData(text);
        iconNode.getStyleClass().add("icon-svg");
        Label label = new Label(text);
        label.visibleProperty().bind(sidebarCollapsed.not());
        label.managedProperty().bind(sidebarCollapsed.not());
        HBox content = new HBox(12, iconNode, label);
        content.setAlignment(Pos.CENTER_LEFT);
        button.setGraphic(content);
        button.setAlignment(Pos.CENTER_LEFT);
        if (text.equals("Dashboard")) button.setSelected(true);
        return button;
    }

    private Node createSidebarFooter() {
        Button logoutButton = new Button();
        logoutButton.getStyleClass().add("sidebar-logout-button");
        logoutButton.setOnAction(e -> screenController.showLoginScreen());

        FontIcon logoutIcon = new FontIcon(MaterialDesignL.LOGOUT);
        logoutIcon.setIconSize(ICON_SIZE);
        logoutIcon.getStyleClass().add("icon-svg");
        
        Label logoutLabel = new Label("Sair");
        logoutLabel.visibleProperty().bind(sidebarCollapsed.not());
        logoutLabel.managedProperty().bind(sidebarCollapsed.not());

        HBox content = new HBox(12, logoutIcon, logoutLabel);
        content.setAlignment(Pos.CENTER_LEFT);
        logoutButton.setGraphic(content);
        logoutButton.setAlignment(Pos.CENTER_LEFT);

        return new VBox(logoutButton);
    }

    private Node createMainContentArea() {
        BorderPane contentArea = new BorderPane();
        contentArea.setTop(createMainContentHeader());
        ScrollPane scrollPane = new ScrollPane(contentStack);
        scrollPane.getStyleClass().add("content-scroll-pane");
        scrollPane.setFitToHeight(true);
        contentArea.setCenter(scrollPane);
        return contentArea;
    }

    private Node createMainContentHeader() {
        HBox header = new HBox();
        header.getStyleClass().add("main-header");
        Button trigger = new Button("☰");
        trigger.setOnAction(e -> sidebarCollapsed.set(!sidebarCollapsed.get()));
        header.getChildren().add(trigger);
        return header;
    }

    // --- LÓGICA DE TROCA DE TELA ---
    private void setupViewSwitching() {
        toggleGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle == null) {
                oldToggle.setSelected(true);
            } else {
                String viewName = (String) newToggle.getUserData();
                showView(viewName);
            }
        });
    }

    private void showView(String viewName) {
        // Esconde todas e mostra a selecionada
        for (Map.Entry<String, Node> entry : views.entrySet()) {
            boolean isVisible = entry.getKey().equals(viewName);
            entry.getValue().setVisible(isVisible);
            entry.getValue().setManaged(isVisible);
        }
        
        if ("Dashboard".equals(viewName)) {
            dashboardView.update();
        }
        if ("Itinerários".equals(viewName)) {
            itinerariesController.loadReservations();
        }
        else if ("Relatórios".equals(viewName)) {
            // AQUI ACONTECE A MÁGICA
            reportsController.updateView("2025");
            System.out.println(" Relatórios atualizados pelo menu!");
        }
    }
}