package com.sgat.controller;

import java.util.HashMap;
import java.util.Map;

import com.sgat.view.ReportsView;

public class ReportsController {

    private final ReportsView view;
    private final Map<String, ReportData> mockData = new HashMap<>();

    public ReportsController(ReportsView view) {
        this.view = view;

        view.setController(this);

        // Mock de dados
        mockData.put("2025", new ReportData("2025", "64", "R$ 519.200", "28"));
        mockData.put("2024", new ReportData("2024", "55", "R$ 420.000", "22"));
        mockData.put("2023", new ReportData("2023", "40", "R$ 310.000", "15"));

        // Dados iniciais exibidos
        updateView("2025");
    }

    public ReportData getReportData(String year) {
        return mockData.getOrDefault(year, mockData.get("2025"));
    }

    /** Atualiza a View para o ano selecionado */
    public void updateView(String year) {
        ReportData data = getReportData(year);
        view.updateSummary(data);
        System.out.println("Controller: Atualizando relatório para o ano " + year);
    }

    /** Retorna anos disponíveis para o ComboBox */
    public String[] getAvailableYears() {
        return new String[]{"2025", "2024", "2023"};
    }

    /** Método acionado quando o botão "Exportar PDF" é clicado */
    public void exportPDF() {
        String year = view.getSelectedYear();
        ReportData data = getReportData(year);

        System.out.println("Gerando PDF para o ano: " + year);

        try {
            PDFGenerator.generatePDF(data);
            System.out.println("PDF gerado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erro ao gerar PDF: " + e.getMessage());
        }
    }
}
