package com.sgat.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PackageDAO {

    public List<Package> getAllPackages() {
        List<Package> packages = new ArrayList<>();
        String sql = "SELECT * FROM pacote_turistico";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                packages.add(new Package(
                        rs.getInt("id"),
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
            Logger.getLogger(PackageDAO.class.getName()).log(Level.SEVERE, "Error getting all packages", e);
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
            Logger.getLogger(PackageDAO.class.getName()).log(Level.SEVERE, "Error adding package", e);
        }
    }

    public void updatePackage(Package pkg) {
        String sql = "UPDATE pacote_turistico SET nome_pacote = ?, destino = ?, descricao = ?, duracao = ?, preco = ?, data_inicio = ?, data_fim = ?, itinerario = ? WHERE id = ?";

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
            pstmt.setInt(9, pkg.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(PackageDAO.class.getName()).log(Level.SEVERE, "Error updating package", e);
        }
    }

    public void deletePackage(Package pkg) {
        String sql = "DELETE FROM pacote_turistico WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, pkg.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(PackageDAO.class.getName()).log(Level.SEVERE, "Error deleting package", e);
        }
    }

    public Package getPackageById(int id) {
        String sql = "SELECT * FROM pacote_turistico WHERE id = ?";
        Package pkg = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                pkg = new Package(
                        rs.getInt("id"),
                        rs.getString("nome_pacote"),
                        rs.getString("destino"),
                        rs.getString("descricao"),
                        rs.getString("duracao"),
                        rs.getDouble("preco"),
                        rs.getDate("data_inicio").toLocalDate(),
                        rs.getDate("data_fim").toLocalDate(),
                        rs.getString("itinerario")
                );
            }
        } catch (SQLException e) {
            Logger.getLogger(PackageDAO.class.getName()).log(Level.SEVERE, "Error getting package by ID", e);
        }
        return pkg;
    }
}
