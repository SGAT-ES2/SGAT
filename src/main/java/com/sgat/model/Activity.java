package com.sgat.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Activity {
    private final StringProperty time;
    private final StringProperty title;
    private final StringProperty description;
    private final StringProperty icon;

    public Activity(String time, String title, String description, String icon) {
        this.time = new SimpleStringProperty(time);
        this.title = new SimpleStringProperty(title);
        this.description = new SimpleStringProperty(description);
        this.icon = new SimpleStringProperty(icon);
    }

    // Getters and Property Getters
    public String getTime() {
        return time.get();
    }

    public StringProperty timeProperty() {
        return time;
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public String getIcon() {
        return icon.get();
    }

    public StringProperty iconProperty() {
        return icon;
    }
}
