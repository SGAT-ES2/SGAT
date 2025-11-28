package com.sgat.controller;

import javafx.concurrent.Task;
import java.io.IOException;
import java.io.File;

public class PDFGenerationTask extends Task<File> { // Change return type to File

    private final ReportData reportData;
    private final PDFGenerator.PdfExportMode mode;

    public PDFGenerationTask(ReportData reportData, PDFGenerator.PdfExportMode mode) {
        this.reportData = reportData;
        this.mode = mode;
    }

    @Override
    protected File call() throws Exception { // Change return type to File
        System.err.println("PDFGenerationTask.call() started on thread: " + Thread.currentThread().getName());
        updateMessage("Gerando PDF...");

        File generatedFile = null;
        try {
            // Call the static method to generate PDF and get the file
            generatedFile = PDFGenerator.generatePDF(reportData, mode);
            System.err.println("PDFGenerationTask.call() finished PDF generation on thread: " + Thread.currentThread().getName());
            updateMessage("PDF gerado com sucesso: " + generatedFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("PDFGenerationTask.call() error on thread: " + Thread.currentThread().getName() + " - " + e.getMessage());
            updateMessage("Erro ao gerar PDF: " + e.getMessage());
            throw e; // Re-throw to be caught by onFailed
        }
        System.err.println("PDFGenerationTask.call() returning on thread: " + Thread.currentThread().getName());
        return generatedFile; // Return the generated file
    }
}