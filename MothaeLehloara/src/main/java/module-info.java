// File: src/main/java/module-info.java

module com.example.mothaelehloara {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql; // Required for JDBC database connectivity

    // --- CRITICAL: Open the main package (contains Main.java which might have @FXML members) ---
    opens com.example.mothaelehloara to javafx.fxml;

    // --- CRITICAL: Open the controller package (contains all FXML controllers like FlightDetailsController) ---
    // This is necessary for FXMLLoader to access @FXML annotated fields and methods in controllers.
    opens com.example.mothaelehloara.controller to javafx.fxml;

    // Export the main package so the JavaFX launcher can find the main class
    exports com.example.mothaelehloara;


}