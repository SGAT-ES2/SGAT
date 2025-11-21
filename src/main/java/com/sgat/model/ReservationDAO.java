package com.sgat.model;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReservationDAO {

    public List<Reservation> getAllReservations() {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT r.id as reservation_id, r.data_reserva, r.numero_passageiros, r.valor_total, r.status, " +
                     "c.id as client_id, c.name, c.email, c.phone, c.cpf, c.address, c.preferences, c.travel_count, " +
                     "p.id as package_id, p.nome_pacote, p.destino, p.descricao, p.duracao, p.preco, p.data_inicio, p.data_fim, p.itinerario " +
                     "FROM reserva r " +
                     "JOIN clientes c ON r.cliente_id = c.id " +
                     "JOIN pacote_turistico p ON r.pacote_id = p.id";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Client client = new Client(
                        rs.getInt("client_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("cpf"),
                        rs.getString("address"),
                        rs.getString("preferences"),
                        rs.getInt("travel_count")
                );

                Package pkg = new Package(
                        rs.getInt("package_id"),
                        rs.getString("nome_pacote"),
                        rs.getString("destino"),
                        rs.getString("descricao"),
                        rs.getString("duracao"),
                        rs.getDouble("preco"),
                        rs.getDate("data_inicio").toLocalDate(),
                        rs.getDate("data_fim").toLocalDate(),
                        rs.getString("itinerario")
                );

                reservations.add(new Reservation(
                        rs.getInt("reservation_id"),
                        client,
                        pkg,
                        rs.getDate("data_reserva").toLocalDate(),
                        rs.getInt("numero_passageiros"),
                        rs.getDouble("valor_total"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            Logger.getLogger(ReservationDAO.class.getName()).log(Level.SEVERE, "Error getting all reservations", e);
        }
        return reservations;
    }

    public void addReservation(Reservation reservation) {
        String sql = "INSERT INTO reserva (cliente_id, pacote_id, data_reserva, numero_passageiros, valor_total, status) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, reservation.getClient().getId());
            pstmt.setInt(2, reservation.getTravelPackage().getId());
            pstmt.setDate(3, Date.valueOf(reservation.getTravelDate()));
            pstmt.setInt(4, reservation.getNumberOfPassengers());
            pstmt.setDouble(5, reservation.getTotalValue());
            pstmt.setString(6, reservation.getStatus());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(ReservationDAO.class.getName()).log(Level.SEVERE, "Error adding reservation", e);
        }
    }

    public void updateReservation(Reservation reservation) {
        String sql = "UPDATE reserva SET cliente_id = ?, pacote_id = ?, data_reserva = ?, numero_passageiros = ?, valor_total = ?, status = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, reservation.getClient().getId());
            pstmt.setInt(2, reservation.getTravelPackage().getId());
            pstmt.setDate(3, Date.valueOf(reservation.getTravelDate()));
            pstmt.setInt(4, reservation.getNumberOfPassengers());
            pstmt.setDouble(5, reservation.getTotalValue());
            pstmt.setString(6, reservation.getStatus());
            pstmt.setInt(7, reservation.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(ReservationDAO.class.getName()).log(Level.SEVERE, "Error updating reservation", e);
        }
    }

    public void deleteReservation(Reservation reservation) {
        String sql = "DELETE FROM reserva WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, reservation.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(ReservationDAO.class.getName()).log(Level.SEVERE, "Error deleting reservation", e);
        }
    }
    
    public Reservation getReservationById(int id) {
        Reservation reservation = null;
        String sql = "SELECT r.id as reservation_id, r.data_reserva, r.numero_passageiros, r.valor_total, r.status, " +
                     "c.id as client_id, c.name, c.email, c.phone, c.cpf, c.address, c.preferences, c.travel_count, " +
                     "p.id as package_id, p.nome_pacote, p.destino, p.descricao, p.duracao, p.preco, p.data_inicio, p.data_fim, p.itinerario " +
                     "FROM reserva r " +
                     "JOIN clientes c ON r.cliente_id = c.id " +
                     "JOIN pacote_turistico p ON r.pacote_id = p.id " +
                     "WHERE r.id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Client client = new Client(
                        rs.getInt("client_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("cpf"),
                        rs.getString("address"),
                        rs.getString("preferences"),
                        rs.getInt("travel_count")
                );

                Package pkg = new Package(
                        rs.getInt("package_id"),
                        rs.getString("nome_pacote"),
                        rs.getString("destino"),
                        rs.getString("descricao"),
                        rs.getString("duracao"),
                        rs.getDouble("preco"),
                        rs.getDate("data_inicio").toLocalDate(),
                        rs.getDate("data_fim").toLocalDate(),
                        rs.getString("itinerario")
                );

                reservation = new Reservation(
                        rs.getInt("reservation_id"),
                        client,
                        pkg,
                        rs.getDate("data_reserva").toLocalDate(),
                        rs.getInt("numero_passageiros"),
                        rs.getDouble("valor_total"),
                        rs.getString("status")
                );
            }
        } catch (SQLException e) {
            Logger.getLogger(ReservationDAO.class.getName()).log(Level.SEVERE, "Error getting reservation by ID", e);
        }
        return reservation;
    }
}