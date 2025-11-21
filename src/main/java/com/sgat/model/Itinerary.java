package com.sgat.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Itinerary {
    private final ObjectProperty<Reservation> reservation;
    private final ObservableList<Day> days;

    public Itinerary(Reservation reservation) {
        this.reservation = new SimpleObjectProperty<>(reservation);
        this.days = FXCollections.observableArrayList();
    }

    // Getters and Property Getters
    public Reservation getReservation() {
        return reservation.get();
    }

    public ObjectProperty<Reservation> reservationProperty() {
        return reservation;
    }

    public ObservableList<Day> getDays() {
        return days;
    }
}
