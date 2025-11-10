package com.sgat.controller;

import com.sgat.model.Client;
import com.sgat.view.ClientsView;

import java.util.ArrayList;
import java.util.List;

public class ClientsController {

    private final ClientsView view;
    private final List<Client> clients;

    public ClientsController(ClientsView view, List<Client> clients) {
        this.view = view;
        this.clients = clients;
    }

    public void addClient(Client client) {
        clients.add(client);
    }

    public void updateClient(Client client) {
        clients.removeIf(c -> c.equals(client));
        clients.add(client);
    }

    public void deleteClient(Client client) {
        clients.remove(client);
    }

    public Client getClient(int id) {
        for (Client client : clients) {
            if (client.getId() == id) {
                return client;
            }
        }
        return null;
    }

    public List<Client> getAllClients() {
        return clients;
    }
}
