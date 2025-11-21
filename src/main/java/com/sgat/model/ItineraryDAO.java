package com.sgat.model;

import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.materialdesign2.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ItineraryDAO {

    public Itinerary getItineraryForReservation(int reservationId) {
        ReservationDAO reservationDAO = new ReservationDAO();
        Reservation reservation = reservationDAO.getReservationById(reservationId);

        if (reservation == null) {
            return null;
        }

        Itinerary itinerary = new Itinerary(reservation);
        String sql = "SELECT * FROM itinerario_detalhado WHERE reserva_id = ? ORDER BY data, horario";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, reservationId);
            ResultSet rs = pstmt.executeQuery();

            Map<LocalDate, List<Activity>> activitiesByDate = new java.util.HashMap<>();

            while (rs.next()) {
                LocalDate date = rs.getDate("data").toLocalDate();
                String type = rs.getString("tipo");

                Ikon icon = getIkon(type);

                Activity activity = new Activity(
                        rs.getTime("horario").toLocalTime().toString(),
                        rs.getString("titulo"),
                        rs.getString("descricao"),
                        icon,
                        type
                );
                activitiesByDate.computeIfAbsent(date, k -> new ArrayList<>()).add(activity);
            }

            int dayNumber = 1;
            List<Map.Entry<LocalDate, List<Activity>>> sortedDays = activitiesByDate.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey()).collect(Collectors.toList());

            for (Map.Entry<LocalDate, List<Activity>> entry : sortedDays) {
                Day day = new Day(dayNumber++, entry.getKey().toString()); // Using date as title
                day.getActivities().addAll(entry.getValue());
                itinerary.getDays().add(day);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return itinerary;
    }

    public void addActivityToItinerary(int reservationId, LocalDate date, LocalTime time, String title, String description, String type) {
        String sql = "INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, reservationId);
            pstmt.setDate(2, Date.valueOf(date));
            pstmt.setTime(3, Time.valueOf(time));
            pstmt.setString(4, title);
            pstmt.setString(5, description);
            pstmt.setString(6, type);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private Ikon getIkon(String type) {
        if (type != null) {
            switch (type) {
                case "Vôo":
                    return MaterialDesignF.FILE_SWAP;
                case "Acomodação":
                    return MaterialDesignH.HOME;
                case "Passeio":
                    return MaterialDesignM.MAP_MARKER;
                default:
                    return MaterialDesignA.ALARM;
            }
        }
        return MaterialDesignA.ALARM;
    }
}
