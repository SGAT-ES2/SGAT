package com.sgat.controller;

import java.util.HashMap;
import java.util.Map;

import com.sgat.view.ReportsView;
import com.sgat.controller.PDFGenerator.PdfExportMode;

public class ReportsController {

    private final ReportsView view;
    private final Map<String, ReportData> mockData = new HashMap<>();

    public ReportsController(ReportsView view) {
        this.view = view;

        view.setController(this);

        mockData.put("2025", new ReportData("2025", "64", "R$ 519.200", "28"));
        mockData.put("2024", new ReportData("2024", "55", "R$ 420.000", "22"));
        mockData.put("2023", new ReportData("2023", "40", "R$ 310.000", "15"));

        updateView("2025");
    }

    public ReportData getReportData(String year) {
        return mockData.getOrDefault(year, mockData.get("2025"));
    }

    public void updateView(String year) {
        ReportData data = getReportData(year);
        view.updateSummary(data);
        System.out.println("Controller: Atualizando relatório para o ano " + year);
    }

    public String[] getAvailableYears() {
        return new String[]{"2025", "2024", "2023"};
    }

    public void exportPDF() {
        String year = view.getSelectedYear();
        ReportData data = getReportData(year);

        PdfExportMode mode = view.getSelectedExportMode();

        System.out.println("Gerando PDF (" + mode + ") para o ano: " + year);

        try {
            PDFGenerator.generatePDF(data, mode);
            System.out.println("PDF gerado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erro ao gerar PDF: " + e.getMessage());
        }
    }
}