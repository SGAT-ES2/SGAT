package com.sgat.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class Package {
    private final IntegerProperty id;
    private final StringProperty nomePacote;
    private final StringProperty destination;
    private final StringProperty description;
    private final StringProperty duration;
    private final DoubleProperty price;
    private final ObjectProperty<LocalDate> startDate;
    private final ObjectProperty<LocalDate> endDate;
    private final StringProperty itinerary;

    public Package(int id, String nomePacote, String destination, String description, String duration, double price, LocalDate startDate, LocalDate endDate, String itinerary) {
        this.id = new SimpleIntegerProperty(id);
        this.nomePacote = new SimpleStringProperty(nomePacote);
        this.destination = new SimpleStringProperty(destination);
        this.description = new SimpleStringProperty(description);
        this.duration = new SimpleStringProperty(duration);
        this.price = new SimpleDoubleProperty(price);
        this.startDate = new SimpleObjectProperty<>(startDate);
        this.endDate = new SimpleObjectProperty<>(endDate);
        this.itinerary = new SimpleStringProperty(itinerary);
    }
    
    public Package(String nomePacote, String destination, String description, String duration, double price, LocalDate startDate, LocalDate endDate, String itinerary) {
        this(0, nomePacote, destination, description, duration, price, startDate, endDate, itinerary);
    }

    public int getId() { return id.get(); }
    public IntegerProperty idProperty() { return id; }

    // Getters e Setters
    public String getNomePacote() { return nomePacote.get(); }
    public void setNomePacote(String nomePacote) { this.nomePacote.set(nomePacote); }
    public StringProperty nomePacoteProperty() { return nomePacote; }

    public String getDestination() { return destination.get(); }
    public void setDestination(String destination) { this.destination.set(destination); }
    public StringProperty destinationProperty() { return destination; }

    public String getDescription() { return description.get(); }
    public void setDescription(String description) { this.description.set(description); }
    public StringProperty descriptionProperty() { return description; }

    public String getDuration() { return duration.get(); }
    public void setDuration(String duration) { this.duration.set(duration); }
    public StringProperty durationProperty() { return duration; }

    public double getPrice() { return price.get(); }
    public void setPrice(double price) { this.price.set(price); }
    public DoubleProperty priceProperty() { return price; }

    public LocalDate getStartDate() { return startDate.get(); }
    public void setStartDate(LocalDate startDate) { this.startDate.set(startDate); }
    public ObjectProperty<LocalDate> startDateProperty() { return startDate; }

    public LocalDate getEndDate() { return endDate.get(); }
    public void setEndDate(LocalDate endDate) { this.endDate.set(endDate); }
    public ObjectProperty<LocalDate> endDateProperty() { return endDate; }

    public String getItinerary() { return itinerary.get(); }
    public void setItinerary(String itinerary) { this.itinerary.set(itinerary); }
    public StringProperty itineraryProperty() { return itinerary; }
}