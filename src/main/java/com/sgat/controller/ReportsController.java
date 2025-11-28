package com.sgat.controller;

import com.sgat.model.ReportDAO; // Importamos o novo DAO
import com.sgat.view.ReportsView;
import com.sgat.controller.PDFGenerator.PdfExportMode;

public class ReportsController {

    private final ReportsView view;
    private final ReportDAO reportDAO; // Variável para o DAO

    public ReportsController(ReportsView view) {
        this.view = view;
        this.reportDAO = new ReportDAO(); // Inicializamos o DAO aqui

        view.setController(this);

        // Ao iniciar, carrega dados de 2025 do banco de dados
        updateView("2025");
    }

    // Este método agora busca no BANCO, não mais no HashMap falso
    public ReportData getReportData(String year) {
        System.out.println("Buscando dados reais no banco para o ano: " + year);
        return reportDAO.getReportDataForYear(year);
    }

    public void updateView(String year) {
        ReportData data = getReportData(year);
        view.updateSummary(data);
    }

    public String[] getAvailableYears() {
        return new String[]{"2025", "2024", "2023"};
    }

    public void exportPDF() {
        String year = view.getSelectedYear();

        // Pega os dados mais recentes do banco
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