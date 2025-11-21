package com.sgat.controller;

public class ReportData {
    public final String year;
    public final String totalReservas;
    public final String receitaTotal;
    public final String novosClientes;
    
    public ReportData(String year, String totalReservas, String receitaTotal, String novosClientes) {
        this.year = year;
        this.totalReservas = totalReservas;
        this.receitaTotal = receitaTotal;
        this.novosClientes = novosClientes;
    }
    
    public String getYear() {
        return year;
    }
}