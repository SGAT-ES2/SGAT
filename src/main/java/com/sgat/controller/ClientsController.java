package com.sgat.controller;

import com.sgat.model.Client;
import com.sgat.model.ClientDAO;
import com.sgat.view.ClientsView;

import java.util.List;

public class ClientsController {

    private final ClientsView view;
    private final ClientDAO clientDAO;

    public ClientsController(ClientsView view) {
        this.view = view;
        this.clientDAO = new ClientDAO();
    }

    public void addClient(Client client) {
        clientDAO.addClient(client);
    }

    public void updateClient(Client client) {
        clientDAO.updateClient(client);
    }

    public void deleteClient(Client client) {
        clientDAO.deleteClient(client);
    }

    public Client getClient(int id) {
        // This is inefficient, but for now it's fine.
        // A better approach would be a getById in the DAO.
        for (Client client : getAllClients()) {
            if (client.getId() == id) {
                return client;
            }
        }
        return null;
    }

    public List<Client> getAllClients() {
        return clientDAO.getAllClients();
    }
}