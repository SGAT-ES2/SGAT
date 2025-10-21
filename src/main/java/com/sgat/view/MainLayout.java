package com.sgat.view;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.SVGPath;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;

public class MainLayout {

    private final BorderPane mainLayout;
    private final BooleanProperty sidebarCollapsed = new SimpleBooleanProperty(false);
    private VBox sidebar; // Tornando a sidebar um campo da classe
    private final StackPane contentStack;
    private final Map<String, Node> views = new HashMap<>();
    private final ToggleGroup toggleGroup = new ToggleGroup();

    public MainLayout() {
        mainLayout = new BorderPane();
        contentStack = new StackPane();

        // Pre-carrega as views
        views.put("Dashboard", new DashboardView().getView());
        views.put("Pacotes", new PackagesView().getView());
        views.put("Clientes", new ClientsView().getView());

        contentStack.getChildren().addAll(views.values());

        createSidebar();
        Node mainContent = createMainContentArea();

        mainLayout.setLeft(sidebar);
        mainLayout.setCenter(mainContent);

        setupViewSwitching();
        showView("Dashboard"); // Mostra a view inicial
    }

    public Parent getLayout() {
        return mainLayout;
    }

    private void createSidebar() {
        sidebar = new VBox();
        sidebar.getStyleClass().add("sidebar");

        sidebar.prefWidthProperty().bind(Bindings.when(sidebarCollapsed)
            .then(70)
            .otherwise(250));
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

        Label menuHeader = new Label("MENU PRINCIPAL");
        menuHeader.getStyleClass().add("sidebar-menu-header");
        menuHeader.visibleProperty().bind(sidebarCollapsed.not());
        menuHeader.managedProperty().bind(sidebarCollapsed.not());

        VBox buttons = new VBox(4);
        buttons.getChildren().addAll(
            createNavButton("Dashboard", "M3 3v18h18V3H3zm16 16H5V5h14v14zM11 7h2v2h-2zm0 4h2v6h-2z"),
            createNavButton("Pacotes", "M21.2,8.4l-1.8-1.8L12,14,4.6,6.6,2.8,8.4,12,17.6Z"),
            createNavButton("Clientes", "M16 21v-2a4 4 0 0 0-4-4H6a4 4 0 0 0-4 4v2"),
            createNavButton("Reserva", "M8 22h8M12 16.5A2.5 2.5 0 0 1 9.5 14h5a2.5 2.5 0 0 1-2.5 2.5Z"),
            createNavButton("Pagamentos", "M22 10a2 2 0 0 0-2-2H4a2 2 0 0 0-2 2v8a2 2 0 0 0 2 2h16a2 2 0 0 0 2-2v-8zm-2 0H4v-2h16v2z"),
            createNavButton("Itinerários", "M14.5 2.5a2.5 2.5 0 0 0-5 0V5h5V2.5zM11 14h-1v5h1v-5zm4 0h-1v5h1v-5zM12 7a1 1 0 0 1 1 1v2h-2V8a1 1 0 0 1 1-1zM5 22V9a2 2 0 0 1 2-2h10a2 2 0 0 1 2 2v13H5z"),
            createNavButton("Relatórios", "M16 6h3v12h-3zM2 12h7v6H2zm9-10h3v18h-3z")
        );

        navigation.getChildren().addAll(menuHeader, buttons);
        return navigation;
    }

    private ToggleButton createNavButton(String text, String svgPath) {
        ToggleButton button = new ToggleButton();
        button.setToggleGroup(toggleGroup);
        button.getStyleClass().add("sidebar-menu-button");
        button.setUserData(text); // Associa o nome da view ao botão

        SVGPath icon = new SVGPath();
        icon.setContent(svgPath);
        icon.getStyleClass().add("icon-svg");

        Label label = new Label(text);
        label.visibleProperty().bind(sidebarCollapsed.not());
        label.managedProperty().bind(sidebarCollapsed.not());

        HBox content = new HBox(12, icon, label);
        content.setAlignment(Pos.CENTER_LEFT);
        button.setGraphic(content);
        button.setAlignment(Pos.CENTER_LEFT);

        if (text.equals("Dashboard")) {
            button.setSelected(true);
        }

        return button;
    }

    private Node createSidebarFooter() {
        VBox footer = new VBox();
        footer.setAlignment(Pos.BOTTOM_CENTER);

        Button logoutButton = new Button();
        logoutButton.getStyleClass().add("sidebar-logout-button");
        logoutButton.setMaxWidth(Double.MAX_VALUE);

        SVGPath icon = new SVGPath();
        icon.setContent("M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4");
        icon.getStyleClass().add("icon-svg");
        Label label = new Label("Sair");
        label.visibleProperty().bind(sidebarCollapsed.not());
        label.managedProperty().bind(sidebarCollapsed.not());

        HBox content = new HBox(12, icon, label);
        content.setAlignment(Pos.CENTER_LEFT);
        logoutButton.setGraphic(content);
        logoutButton.setOnAction(e -> Platform.exit());

        footer.getChildren().add(logoutButton);
        return footer;
    }

    private Node createMainContentArea() {
        BorderPane contentArea = new BorderPane();

        Node header = createMainContentHeader();
        contentArea.setTop(header);

        ScrollPane scrollPane = new ScrollPane(contentStack);
        scrollPane.getStyleClass().add("content-scroll-pane");
        contentArea.setCenter(scrollPane);

        return contentArea;
    }

    private Node createMainContentHeader() {
        HBox header = new HBox();
        header.getStyleClass().add("main-header");

        Button sidebarTrigger = new Button();
        sidebarTrigger.getStyleClass().add("sidebar-trigger-button");
        SVGPath hamburgerIcon = new SVGPath();
        hamburgerIcon.setContent("M3 12h18M3 6h18M3 18h18");
        hamburgerIcon.getStyleClass().add("icon-svg");
        sidebarTrigger.setGraphic(hamburgerIcon);

        sidebarTrigger.setOnAction(e -> {
            boolean collapsed = sidebarCollapsed.get();
            final Timeline timeline = new Timeline();
            timeline.setCycleCount(1);
            timeline.setAutoReverse(false);
            KeyValue kv = new KeyValue(sidebar.prefWidthProperty(), collapsed ? 250 : 70);
            KeyFrame kf = new KeyFrame(Duration.millis(250), kv);
            timeline.getKeyFrames().add(kf);
            timeline.setOnFinished(event -> sidebarCollapsed.set(!collapsed));
            timeline.play();
        });

        header.getChildren().add(sidebarTrigger);
        return header;
    }

    private void setupViewSwitching() {
        toggleGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle == null) {
                // Impede que nenhum botão seja selecionado
                oldToggle.setSelected(true);
            } else {
                String viewName = (String) newToggle.getUserData();
                showView(viewName);
            }
        });
    }

    private void showView(String viewName) {
        for (Map.Entry<String, Node> entry : views.entrySet()) {
            boolean isVisible = entry.getKey().equals(viewName);
            entry.getValue().setVisible(isVisible);
            entry.getValue().setManaged(isVisible);
        }
    }
}