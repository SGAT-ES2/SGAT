package com.sgat.controller;

import com.sgat.model.ReportDAO;
import com.sgat.view.ReportsView;

// --- IMPORTS OBRIGATÓRIOS PARA O PDF FUNCIONAR ---
import com.sgat.controller.PDFGenerator;
import com.sgat.controller.PDFGenerator.PdfExportMode;
// -------------------------------------------------

public class ReportsController {

    private final ReportsView view;
    private final ReportDAO reportDAO;

    public ReportsController(ReportsView view) {
        this.view = view;
        this.reportDAO = new ReportDAO();

        view.setController(this);

        // Carrega dados iniciais
        updateView("2025");
    }

    public ReportData getReportData(String year) {
        // Busca do banco de dados
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
        try {
            String year = view.getSelectedYear();
            ReportData data = getReportData(year);

            // Pega o modo selecionado no ComboBox (Texto, Gráfico ou Completo)
            PdfExportMode mode = view.getSelectedExportMode();

            System.out.println("Gerando PDF (" + mode + ") para o ano: " + year);

            // Chama o gerador
            PDFGenerator.generatePDF(data, mode);

            System.out.println("PDF gerado com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erro ao gerar PDF: " + e.getMessage());
        }
    }
}