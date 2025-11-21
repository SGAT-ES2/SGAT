package com.sgat.model;

import java.time.LocalDate;

public class Pagamento {
    
    private int id;
    private String reserva; // O texto formatado para exibição (ex: "RES-001\nMaria Silva")
    private double valor;
    private String metodo;
    private LocalDate data;
    private String observacoes;
    private int clienteId; 
    
    // Construtor original
    public Pagamento(int id, String reserva, double valor, String metodo, LocalDate data, String observacoes) {
        this(id, reserva, valor, metodo, data, observacoes, 0); // Chama o novo construtor
    }

    // NOVO Construtor que aceita o ID do Cliente/Reserva
    public Pagamento(int id, String reserva, double valor, String metodo, LocalDate data, String observacoes, int clienteId) {
        this.id = id;
        this.reserva = reserva;
        this.valor = valor;
        this.metodo = metodo;
        this.data = data;
        this.observacoes = observacoes;
        this.clienteId = clienteId;
    }
    
    // Getters e Setters
    public int getClienteId() {
        return clienteId;
    }
    public int getId() { return id; }
    public String getReserva() { return reserva; }
    public double getValor() { return valor; }
    public String getMetodo() { return metodo; }
    public LocalDate getData() { return data; }
    public String getObservacoes() { return observacoes; }
    
    public void setId(int id) { this.id = id; }
    public void setReserva(String reserva) { this.reserva = reserva; }
    public void setValor(double valor) { this.valor = valor; }
    public void setMetodo(String metodo) { this.metodo = metodo; }
    public void setData(LocalDate data) { this.data = data; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
    public void setClienteId(int clienteId) { this.clienteId = clienteId; }
}