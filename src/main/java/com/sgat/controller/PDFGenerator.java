package com.sgat.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class PDFGenerator {

    public enum PdfExportMode {
        TEXT("Apenas Texto"),
        GRAPHICS("Apenas Gráficos"),
        COMPLETE("Relatório Completo");

        private final String label;

        PdfExportMode(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    public static File generatePDF(ReportData data, PdfExportMode mode) throws IOException {
        boolean includeText = (mode == PdfExportMode.TEXT || mode == PdfExportMode.COMPLETE);
        boolean includeGraphics = (mode == PdfExportMode.GRAPHICS || mode == PdfExportMode.COMPLETE);

        String fileName = "Relatorio_Analitico_" + data.year + ".pdf";
        File output = new File(fileName);

        try (PDDocument document = new PDDocument()) {

            PDPage page1 = new PDPage(PDRectangle.LETTER);
            document.addPage(page1);
            try (PDPageContentStream cs = new PDPageContentStream(document, page1)) {
                float margin = 50;
                float width = page1.getMediaBox().getWidth();
                float height = page1.getMediaBox().getHeight();
                float cursorY = height - margin;

                cs.beginText();
                cs.setFont(PDType1Font.HELVETICA_BOLD, 22);
                cs.newLineAtOffset(margin, cursorY);
                cs.showText("Relatório Analítico - " + data.year);
                cs.endText();
                cursorY -= 30;

                cs.beginText();
                cs.setFont(PDType1Font.HELVETICA_OBLIQUE, 10);
                cs.newLineAtOffset(margin, cursorY);
                cs.showText("Gerado em " + LocalDate.now() + " (Modo: " + mode.toString() + ")");
                cs.endText();
                cursorY -= 25;

                cs.setStrokingColor(180, 180, 180);
                cs.moveTo(margin, cursorY);
                cs.lineTo(width - margin, cursorY);
                cs.stroke();
                cursorY -= 20;

                if (includeText) {
                    float cardWidth = (width - margin * 2 - 20) / 3f;
                    float cardHeight = 50;
                    float cardY = cursorY - cardHeight;

                    drawMetricCard(cs, margin, cardY, cardWidth, cardHeight, "Total de Reservas", data.totalReservas);
                    drawMetricCard(cs, margin + cardWidth + 10, cardY, cardWidth, cardHeight, "Receita Total", data.receitaTotal);
                    drawMetricCard(cs, margin + (cardWidth + 10) * 2, cardY, cardWidth, cardHeight, "Novos Clientes", data.novosClientes);

                    cursorY = cardY - 40;
                } else {
                    cursorY -= 60;
                }

                if (includeGraphics) {
                    double[] monthlySales = mockMonthlySales(data.year);
                    drawLineChart(cs, margin, cursorY - 220, width - margin * 2, 200, monthlySales, "Vendas Mensais (unidades)");
                    drawMonthLabels(cs, margin, cursorY - 230, width - margin * 2);
                }
            }

            PDPage page2 = new PDPage(PDRectangle.LETTER);
            document.addPage(page2);
            try (PDPageContentStream cs = new PDPageContentStream(document, page2)) {
                float margin = 50;
                float width = page2.getMediaBox().getWidth();
                float cursorY = page2.getMediaBox().getHeight() - margin;

                cs.beginText();
                cs.setFont(PDType1Font.HELVETICA_BOLD, 16);
                cs.newLineAtOffset(margin, cursorY);
                cs.showText("Análises Detalhadas");
                cs.endText();
                cursorY -= 40;

                List<String> pkgNames = Arrays.asList("Paris Romântica", "Caribe Premium", "Amazônia", "Europa Clássica", "Ásia Exótica");
                double[] pkgRevenue = mockPackageRevenue(data.year);

                float currentY = cursorY;

                if (includeGraphics) {
                    drawBarChart(cs, margin, currentY - 200, width - margin * 2, 200, pkgRevenue, pkgNames, "Receita por Pacote (R$)");

                    currentY -= 250;

                    float pieRadius = 100;
                    float pieX = width / 2;
                    float pieY = currentY - pieRadius;

                    double[] shares = computePercentages(pkgRevenue);
                    drawPieChart(cs, pieX, pieY, pieRadius, shares, pkgNames);

                    currentY -= (pieRadius * 2) + 40;
                }

                if (includeText) {
                    drawSimpleTable(cs, margin, currentY, pkgNames, pkgRevenue);
                }
            }

            document.save(output);
        }

        System.out.println("PDF gerado em: " + output.getAbsolutePath());
        return output; // Return the generated file

    }

    private static void drawMetricCard(PDPageContentStream cs, float x, float y, float w, float h, String title, String value) throws IOException {
        cs.setNonStrokingColor(245, 245, 245);
        cs.addRect(x, y, w, h);
        cs.fill();
        cs.setNonStrokingColor(0, 0, 0);
        cs.beginText();
        cs.setFont(PDType1Font.HELVETICA_BOLD, 10);
        cs.newLineAtOffset(x + 8, y + h - 14);
        cs.showText(title);
        cs.endText();
        cs.beginText();
        cs.setFont(PDType1Font.HELVETICA_BOLD, 14);
        cs.newLineAtOffset(x + 8, y + 8);
        cs.showText(value);
        cs.endText();
    }

    private static void drawLineChart(PDPageContentStream cs, float x, float y, float w, float h, double[] values, String title) throws IOException {
        cs.setStrokingColor(200, 200, 200);
        cs.addRect(x, y, w, h);
        cs.stroke();
        cs.beginText();
        cs.setFont(PDType1Font.HELVETICA_BOLD, 12);
        cs.newLineAtOffset(x + 4, y + h + 6);
        cs.showText(title);
        cs.endText();
        double max = Arrays.stream(values).max().orElse(1);
        double min = Arrays.stream(values).min().orElse(0);
        double range = Math.max(1, max - min);
        int n = values.length;
        double stepX = w / (n - 1);
        int gridLines = 4;
        cs.setStrokingColor(230, 230, 230);
        for (int i = 0; i <= gridLines; i++) {
            float yy = (float) (y + (h * i / (double) gridLines));
            cs.moveTo(x, yy);
            cs.lineTo(x + w, yy);
            cs.stroke();
        }
        cs.setStrokingColor(30, 120, 210);
        cs.setLineWidth(2f);
        for (int i = 0; i < n; i++) {
            float px = (float) (x + stepX * i);
            float py = (float) (y + (h * ((values[i] - min) / range)));
            if (i == 0) cs.moveTo(px, py); else cs.lineTo(px, py);
        }
        cs.stroke();
        cs.setNonStrokingColor(30, 120, 210);
        for (int i = 0; i < n; i++) {
            float px = (float) (x + stepX * i);
            float py = (float) (y + (h * ((values[i] - min) / range)));
            cs.addRect(px - 2, py - 2, 4, 4);
            cs.fill();
        }
        cs.setNonStrokingColor(0, 0, 0);
    }

    private static void drawMonthLabels(PDPageContentStream cs, float x, float y, float w) throws IOException {
        String[] months = new String[]{"Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"};
        int n = months.length;
        float stepX = w / (n - 1);
        cs.setFont(PDType1Font.HELVETICA, 8);
        for (int i = 0; i < n; i++) {
            float px = (float) (x + stepX * i) - 10;
            cs.beginText();
            cs.newLineAtOffset(px, y);
            cs.showText(months[i]);
            cs.endText();
        }
    }

    private static void drawBarChart(PDPageContentStream cs, float x, float y, float w, float h, double[] values, List<String> labels, String title) throws IOException {
        cs.setStrokingColor(200, 200, 200);
        cs.addRect(x, y, w, h);
        cs.stroke();
        cs.beginText();
        cs.setFont(PDType1Font.HELVETICA_BOLD, 12);
        cs.newLineAtOffset(x + 4, y + h + 6);
        cs.showText(title);
        cs.endText();
        int n = values.length;
        double max = Arrays.stream(values).max().orElse(1);
        float barAreaWidth = w - 60;
        float barWidth = barAreaWidth / n * 0.6f;
        float gap = (barAreaWidth - n * barWidth) / (n - 1);
        float startX = x + 8;
        float baseY = y + 20;
        for (int i = 0; i < n; i++) {
            float bx = startX + i * (barWidth + gap);
            float bh = (float) ((values[i] / max) * (h - 60));
            cs.setNonStrokingColor(60, 180, 90);
            cs.addRect(bx, baseY, barWidth, bh);
            cs.fill();
            cs.beginText();
            cs.setFont(PDType1Font.HELVETICA, 8);
            cs.newLineAtOffset(bx, baseY - 10);
            cs.showText(truncate(labels.get(i), 12));
            cs.endText();
            cs.beginText();
            cs.setFont(PDType1Font.HELVETICA_BOLD, 9);
            cs.newLineAtOffset(bx, baseY + bh + 4);
            cs.showText(String.format("R$ %.0f", values[i]));
            cs.endText();
        }
        cs.setNonStrokingColor(0, 0, 0);
    }

    private static void drawPieChart(PDPageContentStream cs, float centerX, float centerY, float radius, double[] shares, List<String> labels) throws IOException {
        float startAngle = 0f;
        java.util.Random rand = new java.util.Random(0);
        for (int i = 0; i < shares.length; i++) {
            float sweep = (float) (shares[i] / 100.0 * 360.0);
            int r = 80 + rand.nextInt(160);
            int g = 80 + rand.nextInt(160);
            int b = 80 + rand.nextInt(160);
            cs.setNonStrokingColor(r, g, b);
            int steps = Math.max(8, Math.round(Math.abs(sweep) / 5f));
            double startRad = Math.toRadians(startAngle);
            double sweepRad = Math.toRadians(sweep);
            cs.moveTo(centerX, centerY);
            for (int s = 0; s <= steps; s++) {
                double angle = startRad + sweepRad * s / steps;
                double px = centerX + radius * Math.cos(angle);
                double py = centerY + radius * Math.sin(angle);
                cs.lineTo((float) px, (float) py);
            }
            cs.closePath();
            cs.fill();
            double midAngle = Math.toRadians(startAngle + sweep / 2.0);
            float lx = (float) (centerX + (radius + 20) * Math.cos(midAngle));
            float ly = (float) (centerY + (radius + 20) * Math.sin(midAngle));
            cs.setNonStrokingColor(0, 0, 0);
            cs.beginText();
            cs.setFont(PDType1Font.HELVETICA, 8);
            cs.newLineAtOffset(lx - 10, ly - 4);
            cs.showText(truncate(labels.get(i), 12) + " (" + Math.round(shares[i]) + "%)");
            cs.endText();
            startAngle += sweep;
        }
        cs.setNonStrokingColor(0, 0, 0);
    }

    private static void drawSimpleTable(PDPageContentStream cs, float x, float y, List<String> pkgNames, double[] pkgRevenue) throws IOException {
        cs.beginText();
        cs.setFont(PDType1Font.HELVETICA_BOLD, 11);
        cs.newLineAtOffset(x, y);
        cs.showText("Tabela: Receita por Pacote");
        cs.endText();
        float rowY = y - 16;
        cs.setFont(PDType1Font.HELVETICA, 10);
        for (int i = 0; i < pkgNames.size(); i++) {
            cs.beginText();
            cs.newLineAtOffset(x, rowY - i * 14);
            cs.showText(String.format("%d. %s - R$ %.0f", i + 1, pkgNames.get(i), pkgRevenue[i]));
            cs.endText();
        }
    }

    private static double[] mockMonthlySales(String year) {
        int base = Math.abs(year.hashCode()) % 50 + 20;
        double[] v = new double[12];
        for (int i = 0; i < 12; i++) {
            v[i] = base + Math.round(10 * Math.sin(i * 0.9 + (year.hashCode() % 10)) + i * 2);
            if (v[i] < 5) v[i] = 5;
        }
        return v;
    }

    private static double[] mockPackageRevenue(String year) {
        int seed = Math.abs(year.hashCode()) % 100;
        double[] vals = new double[5];
        for (int i = 0; i < 5; i++) {
            vals[i] = 80_000 + (seed * (i + 1) * 7) + (i * 15000);
        }
        return vals;
    }

    private static double[] computePercentages(double[] values) {
        double sum = 0;
        for (double v : values) sum += v;
        double[] pct = new double[values.length];
        for (int i = 0; i < values.length; i++) pct[i] = (values[i] / Math.max(1, sum)) * 100.0;
        return pct;
    }

    private static String truncate(String s, int len) {
        if (s == null) return "";
        if (s.length() <= len) return s;
        return s.substring(0, len - 1) + "…";
    }
}