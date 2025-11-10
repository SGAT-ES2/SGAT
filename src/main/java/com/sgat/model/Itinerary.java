package com.sgat.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class Itinerary {
    private final StringProperty reservationId;
    private final StringProperty clientName;
    private final StringProperty packageName;
    private final ObjectProperty<LocalDate> startDate;
    private final ObjectProperty<LocalDate> endDate;
    private final ObservableList<Day> days;

    public Itinerary(String reservationId, String clientName, String packageName, LocalDate startDate, LocalDate endDate) {
        this.reservationId = new SimpleStringProperty(reservationId);
        this.clientName = new SimpleStringProperty(clientName);
        this.packageName = new SimpleStringProperty(packageName);
        this.startDate = new SimpleObjectProperty<>(startDate);
        this.endDate = new SimpleObjectProperty<>(endDate);
        this.days = FXCollections.observableArrayList();
    }

    // Getters and Property Getters
    public String getReservationId() {
        return reservationId.get();
    }

    public StringProperty reservationIdProperty() {
        return reservationId;
    }

    public String getClientName() {
        return clientName.get();
    }

    public StringProperty clientNameProperty() {
        return clientName;
    }

    public String getPackageName() {
        return packageName.get();
    }

    public StringProperty packageNameProperty() {
        return packageName;
    }

    public LocalDate getStartDate() {
        return startDate.get();
    }

    public ObjectProperty<LocalDate> startDateProperty() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate.get();
    }

    public ObjectProperty<LocalDate> endDateProperty() {
        return endDate;
    }

    public ObservableList<Day> getDays() {
        return days;
    }
}
