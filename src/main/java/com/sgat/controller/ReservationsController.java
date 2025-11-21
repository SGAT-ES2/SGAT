package com.sgat.controller;

import com.sgat.model.Reservation;
import com.sgat.model.ReservationDAO;
import com.sgat.view.ReservationsView;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReservationsController {

    private final ReservationDAO dao;

    public ReservationsController() {
        this.dao = new ReservationDAO();
    }

    public void addReservation(Reservation reservation) {
        dao.addReservation(reservation);
    }

    public void updateReservation(Reservation reservation) {
        dao.updateReservation(reservation);
    }

    public void deleteReservation(Reservation reservation) {
        dao.deleteReservation(reservation);
    }

    public Reservation getReservation(int id) {
        return dao.getReservationById(id);
    }

    public List<Reservation> getAllReservations() {
        return dao.getAllReservations();
    }
}