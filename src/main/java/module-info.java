module com.sgat {
    // JavaFX
    requires javafx.controls;
    requires javafx.fxml;

    // JDBC
    requires java.sql;

    // Ícones
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.materialdesign2;

    // PDFBox (já estava faltando isso)
    requires org.apache.pdfbox;

    // Permite abrir arquivos (Desktop.getDesktop())
    requires java.desktop;

    // Abre controllers para o JavaFX
    opens com.sgat.controller to javafx.fxml;

    // Exporta o módulo principal
    exports com.sgat;
}
