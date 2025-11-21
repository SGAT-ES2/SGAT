package com.sgat.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.kordamp.ikonli.Ikon;

public class Activity {
    private final StringProperty time;
    private final StringProperty title;
    private final StringProperty description;
    private final ObjectProperty<Ikon> icon;

    public Activity(String time, String title, String description, Ikon icon) {
        this.time = new SimpleStringProperty(time);
        this.title = new SimpleStringProperty(title);
        this.description = new SimpleStringProperty(description);
        this.icon = new SimpleObjectProperty<>(icon);
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

    public Ikon getIcon() {
        return icon.get();
    }

    public ObjectProperty<Ikon> iconProperty() {
        return icon;
    }
}