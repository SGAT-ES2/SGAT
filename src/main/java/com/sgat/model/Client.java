package com.sgat.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Client {
    private final StringProperty name;
    private final StringProperty email;
    private final StringProperty phone;
    private final StringProperty cpf;
    private final StringProperty address;
    private final StringProperty preferences;
    private final IntegerProperty travelCount;

    public Client(String name, String email, String phone, String cpf, String address, String preferences, int travelCount) {
        this.name = new SimpleStringProperty(name);
        this.email = new SimpleStringProperty(email);
        this.phone = new SimpleStringProperty(phone);
        this.cpf = new SimpleStringProperty(cpf);
        this.address = new SimpleStringProperty(address);
        this.preferences = new SimpleStringProperty(preferences);
        this.travelCount = new SimpleIntegerProperty(travelCount);
    }

    // --- Getters and Properties ---

    public String getName() { return name.get(); }
    public StringProperty nameProperty() { return name; }
    public void setName(String name) { this.name.set(name); }

    public String getEmail() { return email.get(); }
    public StringProperty emailProperty() { return email; }
    public void setEmail(String email) { this.email.set(email); }

    public String getPhone() { return phone.get(); }
    public StringProperty phoneProperty() { return phone; }
    public void setPhone(String phone) { this.phone.set(phone); }

    public String getCpf() { return cpf.get(); }
    public StringProperty cpfProperty() { return cpf; }
    public void setCpf(String cpf) { this.cpf.set(cpf); }

    public String getAddress() { return address.get(); }
    public StringProperty addressProperty() { return address; }
    public void setAddress(String address) { this.address.set(address); }

    public String getPreferences() { return preferences.get(); }
    public StringProperty preferencesProperty() { return preferences; }
    public void setPreferences(String preferences) { this.preferences.set(preferences); }

    public int getTravelCount() { return travelCount.get(); }
    public IntegerProperty travelCountProperty() { return travelCount; }
    public void setTravelCount(int travelCount) { this.travelCount.set(travelCount); }
}
