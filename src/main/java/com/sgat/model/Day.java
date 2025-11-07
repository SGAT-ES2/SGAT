package com.sgat.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Day {
    private final IntegerProperty dayNumber;
    private final StringProperty title;
    private final ObservableList<Activity> activities;

    public Day(int dayNumber, String title) {
        this.dayNumber = new SimpleIntegerProperty(dayNumber);
        this.title = new SimpleStringProperty(title);
        this.activities = FXCollections.observableArrayList();
    }

    // Getters and Property Getters
    public int getDayNumber() {
        return dayNumber.get();
    }

    public IntegerProperty dayNumberProperty() {
        return dayNumber;
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public ObservableList<Activity> getActivities() {
        return activities;
    }
}
