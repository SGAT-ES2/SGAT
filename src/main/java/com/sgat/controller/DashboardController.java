package com.sgat.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DashboardController {

    private final Connection conn;

    public DashboardController(Connection conn) {
        this.conn = conn;
    }

    // -------------------------
    // MÉTODOS PARA ESTATÍSTICAS
    // -------------------------

    public int getTotalPacotes() throws SQLException {
        String sql = "SELECT COUNT(*) FROM pacote_turistico";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    public int getTotalClientes() throws SQLException {
        String sql = "SELECT COUNT(*) FROM clientes";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    public int getReservasPendentes() throws SQLException {
        String sql = "SELECT COUNT(*) FROM reserva WHERE status = 'Pendente'";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    public double getReceitaTotal() throws SQLException {
        String sql = "SELECT SUM(valor_pago) FROM pagamento";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getDouble(1);
    }

    // -------------------------
    // LISTAS PARA OS CARDS
    // -------------------------

    public List<ReservaRecentesDTO> getReservasRecentes() throws SQLException {
        String sql = """
            SELECT c.name, p.nome_pacote, r.data_reserva, r.status
            FROM reserva r
            JOIN clientes c ON r.cliente_id = c.id
            JOIN pacote_turistico p ON r.pacote_id = p.id
            ORDER BY r.data_reserva DESC
            LIMIT 4
        """;

        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        List<ReservaRecentesDTO> lista = new ArrayList<>();

        while (rs.next()) {
            lista.add(new ReservaRecentesDTO(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getDate(3).toLocalDate(),
                    rs.getString(4)
            ));
        }

        return lista;
    }

    public List<PacotePopularDTO> getPacotesMaisPopulares() throws SQLException {
        String sql = """
            SELECT p.nome_pacote, COUNT(r.id) AS vendas, SUM(r.valor_total) AS receita
            FROM reserva r
            JOIN pacote_turistico p ON r.pacote_id = p.id
            GROUP BY p.nome_pacote
            ORDER BY vendas DESC
            LIMIT 3
        """;

        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        List<PacotePopularDTO> lista = new ArrayList<>();

        while (rs.next()) {
            lista.add(new PacotePopularDTO(
                    rs.getString(1),
                    rs.getInt(2),
                    rs.getDouble(3)
            ));
        }

        return lista;
    }

    // -------------------------
    // DTOs
    // -------------------------

    public static class ReservaRecentesDTO {
        public final String cliente;
        public final String pacote;
        public final LocalDate data;
        public final String status;

        public ReservaRecentesDTO(String cliente, String pacote, LocalDate data, String status) {
            this.cliente = cliente;
            this.pacote = pacote;
            this.data = data;
            this.status = status;
        }
    }

    public static class PacotePopularDTO {
        public final String nome;
        public final int vendas;
        public final double receita;

        public PacotePopularDTO(String nome, int vendas, double receita) {
            this.nome = nome;
            this.vendas = vendas;
            this.receita = receita;
        }
    }
}
