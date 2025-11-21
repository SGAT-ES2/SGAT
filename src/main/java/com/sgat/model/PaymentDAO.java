package com.sgat.model;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PaymentDAO {

    public List<Pagamento> getAllPayments() {
        List<Pagamento> payments = new ArrayList<>();
        String sql = "SELECT * FROM pagamento";
        ReservationDAO reservationDAO = new ReservationDAO();

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Reservation reservation = reservationDAO.getReservationById(rs.getInt("reserva_id"));
                payments.add(new Pagamento(
                        rs.getInt("id"),
                        reservation,
                        rs.getDate("data_pagamento").toLocalDate(),
                        rs.getDouble("valor_pago"),
                        rs.getString("metodo_pagamento"),
                        rs.getString("status"),
                        rs.getString("notas")
                ));
            }
        } catch (SQLException e) {
            Logger.getLogger(PaymentDAO.class.getName()).log(Level.SEVERE, "Error getting all payments", e);
        }
        return payments;
    }

    public void addPayment(Pagamento payment) {
        String sql = "INSERT INTO pagamento (reserva_id, data_pagamento, valor_pago, metodo_pagamento, status, notas) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, payment.getReserva().getId());
            pstmt.setDate(2, Date.valueOf(payment.getDataPagamento()));
            pstmt.setDouble(3, payment.getValorPago());
            pstmt.setString(4, payment.getMetodoPagamento());
            pstmt.setString(5, payment.getStatus());
            pstmt.setString(6, payment.getNotas());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(PaymentDAO.class.getName()).log(Level.SEVERE, "Error adding payment", e);
        }
    }
}
