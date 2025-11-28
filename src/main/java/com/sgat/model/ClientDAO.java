package com.sgat.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientDAO {

    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM clientes";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                clients.add(new Client(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("cpf"),
                        rs.getString("address"),
                        rs.getString("preferences"),
                        rs.getInt("travel_count")
                ));
            }
        } catch (SQLException e) {
            Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, "Error getting all clients", e);
        }
        return clients;
    }

    public void addClient(Client client) {
        String sql = "INSERT INTO clientes (name, email, phone, cpf, address, preferences, travel_count) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, client.getName());
            pstmt.setString(2, client.getEmail());
            pstmt.setString(3, client.getPhone());
            pstmt.setString(4, client.getCpf());
            pstmt.setString(5, client.getAddress());
            pstmt.setString(6, client.getPreferences());
            pstmt.setInt(7, client.getTravelCount());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, "Error adding client", e);
        }
    }

    public void updateClient(Client client) {
        String sql = "UPDATE clientes SET name = ?, email = ?, phone = ?, cpf = ?, address = ?, preferences = ?, travel_count = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, client.getName());
            pstmt.setString(2, client.getEmail());
            pstmt.setString(3, client.getPhone());
            pstmt.setString(4, client.getCpf());
            pstmt.setString(5, client.getAddress());
            pstmt.setString(6, client.getPreferences());
            pstmt.setInt(7, client.getTravelCount());
            pstmt.setInt(8, client.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, "Error updating client", e);
        }
    }

    public void deleteClient(Client client) {
        String sql = "DELETE FROM clientes WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, client.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, "Error deleting client", e);
        }
    }

    public Client getClientById(int id) {
        String sql = "SELECT * FROM clientes WHERE id = ?";
        Client client = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                client = new Client(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("cpf"),
                        rs.getString("address"),
                        rs.getString("preferences"),
                        rs.getInt("travel_count")
                );
            }
        } catch (SQLException e) {
            Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, "Error getting client by ID", e);
        }
        return client;
    }
}
