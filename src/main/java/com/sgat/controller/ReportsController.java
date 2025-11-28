package com.sgat.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import com.sgat.model.ReportDAO;
import com.sgat.view.ReportsView;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

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
        System.err.println("ReportsController.exportPDF() called on thread: " + Thread.currentThread().getName());
        try {
            String year = view.getSelectedYear();
            ReportData data = getReportData(year);
            PdfExportMode mode = view.getSelectedExportMode();

            PDFGenerationTask task = new PDFGenerationTask(data, mode);

            view.updateStatus("Gerando PDF...");
            task.messageProperty().addListener((obs, oldMsg, newMsg) -> {
                System.err.println("Task message updated on thread: " + Thread.currentThread().getName() + " - " + newMsg);
                view.updateStatus(newMsg);
            });

            task.setOnSucceeded(event -> {
                System.err.println("PDFGenerationTask succeeded handler on thread: " + Thread.currentThread().getName());
                view.updateStatus(""); // Clear status message
                File pdfFile = task.getValue();
                if (pdfFile != null) {
                    System.err.println("Scheduling UI update for success dialog on thread: " + Thread.currentThread().getName());
                    Platform.runLater(() -> {
                        System.err.println("Showing success dialog on thread: " + Thread.currentThread().getName());
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Geração de PDF Concluída");
                        alert.setHeaderText("Relatório PDF gerado com sucesso!");
                        alert.setContentText("O arquivo está em: " + pdfFile.getAbsolutePath() + "\nDeseja abri-lo agora?");

                        ButtonType openButton = new ButtonType("Abrir");
                        ButtonType okButton = new ButtonType("OK");
                        alert.getButtonTypes().setAll(openButton, okButton);

                        alert.showAndWait().ifPresent(response -> {
                            System.err.println("Dialog response received on thread: " + Thread.currentThread().getName());
                            if (response == openButton && Desktop.isDesktopSupported()) {
                                System.err.println("Attempting to open PDF in new background thread from UI thread: " + Thread.currentThread().getName());
                                new Thread(() -> {
                                    System.err.println("Desktop.getDesktop().open() called on thread: " + Thread.currentThread().getName());
                                    try {
                                        Desktop.getDesktop().open(pdfFile);
                                        System.err.println("Desktop.getDesktop().open() finished on thread: " + Thread.currentThread().getName());
                                    } catch (IOException e) {
                                        System.err.println("Error opening PDF in background thread: " + e.getMessage());
                                        Platform.runLater(() -> { // Ensure UI updates are on JavaFX Application Thread
                                            System.err.println("Showing error dialog for opening PDF on thread: " + Thread.currentThread().getName());
                                            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                                            errorAlert.setTitle("Erro ao Abrir PDF");
                                            errorAlert.setHeaderText("Não foi possível abrir o arquivo PDF.");
                                            errorAlert.setContentText("Verifique se você tem um visualizador de PDF instalado e se o arquivo não está corrompido.\nDetalhes: " + e.getMessage());
                                            errorAlert.showAndWait();
                                            System.err.println("Error dialog for opening PDF finished on thread: " + Thread.currentThread().getName());
                                        });
                                        System.err.println("Erro ao abrir PDF: " + e.getMessage());
                                    }
                                }).start();
                            } else {
                                System.err.println("User chose not to open PDF or Desktop not supported.");
                            }
                        });
                        System.err.println("Success dialog closed on thread: " + Thread.currentThread().getName());
                    });
                }
            });

            task.setOnFailed(event -> {
                System.err.println("PDFGenerationTask failed handler on thread: " + Thread.currentThread().getName());
                Throwable e = task.getException();
                view.updateStatus("Erro ao gerar PDF."); // Clear status message
                System.err.println("Scheduling UI update for failure dialog on thread: " + Thread.currentThread().getName());
                Platform.runLater(() -> {
                    System.err.println("Showing failure dialog on thread: " + Thread.currentThread().getName());
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Erro na Geração de PDF");
                    errorAlert.setHeaderText("Ocorreu um erro ao gerar o relatório PDF.");
                    errorAlert.setContentText("Por favor, tente novamente. Se o problema persistir, contate o suporte.\nDetalhes: " + (e != null ? e.getMessage() : "Erro desconhecido."));
                    errorAlert.showAndWait();
                    System.err.println("Failure dialog closed on thread: " + Thread.currentThread().getName());
                });
                System.err.println("Erro ao gerar PDF: " + e.getMessage());
                e.printStackTrace();
            });

            System.err.println("Starting PDFGenerationTask in new thread from UI thread: " + Thread.currentThread().getName());
            new Thread(task).start();
            System.err.println("PDFGenerationTask started. Remaining in exportPDF() on thread: " + Thread.currentThread().getName());

        } catch (Exception e) {
            System.err.println("Error initiating PDF generation in exportPDF() on thread: " + Thread.currentThread().getName() + " - " + e.getMessage());
            view.updateStatus("Erro ao iniciar geração de PDF: " + e.getMessage());
            e.printStackTrace();
        }
    }
}