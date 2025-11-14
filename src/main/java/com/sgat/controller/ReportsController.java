package com.sgat.controller;

import java.util.HashMap;
import java.util.Map;

import com.sgat.view.ReportsView;
import com.sgat.controller.ReportData; // 🚨 LINHA ADICIONADA (CORREÇÃO)

public class ReportsController {

    private final ReportsView view;
    // Agora o compilador sabe o que é ReportData
    private final Map<String, ReportData> mockData = new HashMap<>(); 

    public ReportsController(ReportsView view) {
        this.view = view;
    
        view.setController(this); 
        
        // 2. Define os dados mockados por ano
        mockData.put("2025", new ReportData("2025", "64", "R$ 519.200", "28"));
        mockData.put("2024", new ReportData("2024", "55", "R$ 420.000", "22"));
        mockData.put("2023", new ReportData("2023", "40", "R$ 310.000", "15"));
        
        // 3. Carrega os dados iniciais (2025)
        updateView("2025");
    }

    public ReportData getReportData(String year) {
        // Usa getOrDefault para garantir que sempre retorne algo, mesmo se o ano não existir
        return mockData.getOrDefault(year, mockData.get("2025")); 
    }
    
    /**
     * Aciona a atualização da View com os dados do ano selecionado.
     */
    public void updateView(String year) {
        ReportData data = getReportData(year);
        // Chama o método na View para atualizar a interface
        view.updateSummary(data); 
        System.out.println("Controller: Atualizando relatório para o ano " + year);
    }
    
    /**
     * Retorna a lista de anos disponíveis para o ComboBox da View.
     */
    public String[] getAvailableYears() {
        return new String[]{"2025", "2024", "2023"};
    }
}