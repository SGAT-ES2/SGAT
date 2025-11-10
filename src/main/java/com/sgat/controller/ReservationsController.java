package com.sgat.controller;

import com.sgat.model.Reservation;
import com.sgat.view.ReservationsView;

import java.util.ArrayList;
import java.util.List;

public class ReservationsController {

    private final ReservationsView view;
    private final List<Reservation> reservations;

    public ReservationsController(ReservationsView view, List<Reservation> reservations) {
        this.view = view;
        this.reservations = reservations;
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public void updateReservation(Reservation reservation) {
        reservations.removeIf(r -> r.equals(reservation));
        reservations.add(reservation);
    }

    public void deleteReservation(Reservation reservation) {
        reservations.remove(reservation);
    }

    public Reservation getReservation(int id) {
        for (Reservation reservation : reservations) {
            if (reservation.getId() == id)
                return reservation;
        }
        return null;
    }

    public List<Reservation> getAllReservations() {
        return reservations;
    }
}