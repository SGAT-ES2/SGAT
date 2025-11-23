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
        String sql = "SELECT " +
                     "pay.id as payment_id, pay.data_pagamento, pay.valor_pago, pay.metodo_pagamento, pay.status as payment_status, pay.notas, " +
                     "r.id as reservation_id, r.data_reserva, r.numero_passageiros, r.valor_total, r.status as reservation_status, " +
                     "c.id as client_id, c.name, c.email, c.phone, c.cpf, c.address, c.preferences, c.travel_count, " +
                     "p.id as package_id, p.nome_pacote, p.destino, p.descricao, p.duracao, p.preco, p.data_inicio, p.data_fim, p.itinerario " +
                     "FROM pagamento pay " +
                     "JOIN reserva r ON pay.reserva_id = r.id " +
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

                Reservation reservation = new Reservation(
                        rs.getInt("reservation_id"),
                        client,
                        pkg,
                        rs.getDate("data_reserva").toLocalDate(),
                        rs.getInt("numero_passageiros"),
                        rs.getDouble("valor_total"),
                        rs.getString("reservation_status")
                );

                payments.add(new Pagamento(
                        rs.getInt("payment_id"),
                        reservation,
                        rs.getDate("data_pagamento").toLocalDate(),
                        rs.getDouble("valor_pago"),
                        rs.getString("metodo_pagamento"),
                        rs.getString("payment_status"),
                        rs.getString("notas")
                ));
            }
        } catch (SQLException e) {
            Logger.getLogger(PaymentDAO.class.getName()).log(Level.SEVERE, "Error getting all payments with details", e);
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
