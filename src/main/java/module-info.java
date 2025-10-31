module com.sgat {
    // Declara que nosso módulo precisa dos módulos de controls e fxml do JavaFX
    requires javafx.controls;
    requires javafx.fxml;

    // Declara que nosso módulo precisa do módulo de SQL do Java (para o JDBC)
    requires java.sql;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.materialdesign2;


    // Abre nosso pacote para a biblioteca do JavaFX, permitindo que ela acesse nosso código
    opens com.sgat.controller to javafx.fxml;
    exports com.sgat;
}