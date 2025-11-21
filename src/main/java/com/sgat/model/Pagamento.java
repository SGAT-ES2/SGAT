package com.sgat.model;

import java.time.LocalDate;

public class Pagamento {

    private int id;
    private Reservation reserva;
    private LocalDate dataPagamento;
    private double valorPago;
    private String metodoPagamento;
    private String status;
    private String notas;

    // Constructors
    public Pagamento(int id, Reservation reserva, LocalDate dataPagamento, double valorPago, String metodoPagamento, String status, String notas) {
        this.id = id;
        this.reserva = reserva;
        this.dataPagamento = dataPagamento;
        this.valorPago = valorPago;
        this.metodoPagamento = metodoPagamento;
        this.status = status;
        this.notas = notas;
    }
    
    public Pagamento(Reservation reserva, LocalDate dataPagamento, double valorPago, String metodoPagamento, String status, String notas) {
        this(0, reserva, dataPagamento, valorPago, metodoPagamento, status, notas);
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Reservation getReserva() {
        return reserva;
    }

    public void setReserva(Reservation reserva) {
        this.reserva = reserva;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public double getValorPago() {
        return valorPago;
    }

    public void setValorPago(double valorPago) {
        this.valorPago = valorPago;
    }

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }
}