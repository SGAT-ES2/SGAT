package com.sgat.model;

import com.sgat.controller.ReportData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.util.Locale;

public class ReportDAO {

    // Método que calcula os dados reais do ano selecionado
    public ReportData getReportDataForYear(String year) {
        String totalReservas = "0";
        String receitaTotal = "R$ 0,00";
        String novosClientes = "0";

        int anoSelecionado = Integer.parseInt(year);
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        // Usamos sua classe DBConnection para conectar
        try (Connection conn = DBConnection.getConnection()) {

            // 1. Total de Reservas no ano
            String sqlReservas = "SELECT COUNT(*) FROM reserva WHERE EXTRACT(YEAR FROM data_reserva) = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sqlReservas)) {
                stmt.setInt(1, anoSelecionado);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    totalReservas = String.valueOf(rs.getInt(1));
                }
            }

            // 2. Receita Total (Soma dos pagamentos do ano)
            String sqlReceita = "SELECT SUM(valor) FROM pagamento WHERE EXTRACT(YEAR FROM data_pagamento) = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sqlReceita)) {
                stmt.setInt(1, anoSelecionado);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    double valor = rs.getDouble(1);
                    receitaTotal = nf.format(valor);
                }
            }

            // 3. Novos Clientes (Clientes que fizeram a primeira reserva neste ano)
            // Simplificado: conta quantos clientes distintos compraram neste ano
            String sqlClientes = "SELECT COUNT(DISTINCT cliente_id) FROM reserva WHERE EXTRACT(YEAR FROM data_reserva) = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sqlClientes)) {
                stmt.setInt(1, anoSelecionado);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    novosClientes = String.valueOf(rs.getInt(1));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erro ao buscar dados do relatório: " + e.getMessage());
        }

        // Retorna o pacote com os dados reais
        return new ReportData(year, totalReservas, receitaTotal, novosClientes);
    }
}
