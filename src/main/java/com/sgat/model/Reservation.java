
package com.sgat.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class Reservation {

    private final IntegerProperty id;
    private final ObjectProperty<Client> client;
    private final ObjectProperty<Package> travelPackage;
    private final ObjectProperty<LocalDate> travelDate;
    private final IntegerProperty numberOfPassengers;
    private final DoubleProperty totalValue;
    private final StringProperty status;

    public Reservation(int id, Client client, Package travelPackage, LocalDate travelDate, int numberOfPassengers, double totalValue, String status) {
        this.id = new SimpleIntegerProperty(id);
        this.client = new SimpleObjectProperty<>(client);
        this.travelPackage = new SimpleObjectProperty<>(travelPackage);
        this.travelDate = new SimpleObjectProperty<>(travelDate);
        this.numberOfPassengers = new SimpleIntegerProperty(numberOfPassengers);
        this.totalValue = new SimpleDoubleProperty(totalValue);
        this.status = new SimpleStringProperty(status);
    }

    // --- Getters and Properties ---

    public int getId() { return id.get(); }
    public IntegerProperty idProperty() { return id; }

    public Client getClient() {
        return client.get();
    }

    public ObjectProperty<Client> clientProperty() {
        return client;
    }

    public void setClient(Client client) {
        this.client.set(client);
    }

    public Package getTravelPackage() {
        return travelPackage.get();
    }

    public ObjectProperty<Package> travelPackageProperty() {
        return travelPackage;
    }

    public void setTravelPackage(Package travelPackage) {
        this.travelPackage.set(travelPackage);
    }

    public LocalDate getTravelDate() {
        return travelDate.get();
    }

    public ObjectProperty<LocalDate> travelDateProperty() {
        return travelDate;
    }

    public void setTravelDate(LocalDate travelDate) {
        this.travelDate.set(travelDate);
    }

    public int getNumberOfPassengers() {
        return numberOfPassengers.get();
    }

    public IntegerProperty numberOfPassengersProperty() {
        return numberOfPassengers;
    }

    public void setNumberOfPassengers(int numberOfPassengers) {
        this.numberOfPassengers.set(numberOfPassengers);
    }

    public double getTotalValue() {
        return totalValue.get();
    }

    public DoubleProperty totalValueProperty() {
        return totalValue;
    }

    public void setTotalValue(double totalValue) {
        this.totalValue.set(totalValue);
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }
}
