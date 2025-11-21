package com.sgat.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PackageDAO {

    public List<Package> getAllPackages() {
        List<Package> packages = new ArrayList<>();
        String sql = "SELECT * FROM pacote_turistico";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                packages.add(new Package(
                        rs.getString("nome_pacote"),
                        rs.getString("destino"),
                        rs.getString("descricao"),
                        rs.getString("duracao"),
                        rs.getDouble("preco"),
                        rs.getDate("data_inicio").toLocalDate(),
                        rs.getDate("data_fim").toLocalDate(),
                        rs.getString("itinerario")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return packages;
    }

    public void addPackage(Package pkg) {
        String sql = "INSERT INTO pacote_turistico (nome_pacote, destino, descricao, duracao, preco, data_inicio, data_fim, itinerario) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, pkg.getNomePacote());
            pstmt.setString(2, pkg.getDestination());
            pstmt.setString(3, pkg.getDescription());
            pstmt.setString(4, pkg.getDuration());
            pstmt.setDouble(5, pkg.getPrice());
            pstmt.setDate(6, Date.valueOf(pkg.getStartDate()));
            pstmt.setDate(7, Date.valueOf(pkg.getEndDate()));
            pstmt.setString(8, pkg.getItinerary());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePackage(Package pkg) {
        String sql = "UPDATE pacote_turistico SET destino = ?, descricao = ?, duracao = ?, preco = ?, data_inicio = ?, data_fim = ?, itinerario = ? WHERE nome_pacote = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, pkg.getDestination());
            pstmt.setString(2, pkg.getDescription());
            pstmt.setString(3, pkg.getDuration());
            pstmt.setDouble(4, pkg.getPrice());
            pstmt.setDate(5, Date.valueOf(pkg.getStartDate()));
            pstmt.setDate(6, Date.valueOf(pkg.getEndDate()));
            pstmt.setString(7, pkg.getItinerary());
            pstmt.setString(8, pkg.getNomePacote());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePackage(Package pkg) {
        String sql = "DELETE FROM pacote_turistico WHERE nome_pacote = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, pkg.getNomePacote());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
