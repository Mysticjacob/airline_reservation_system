// File: src/main/java/module-info.java

module com.example.selekanepentse {
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
    opens com.example.selekanepentse to javafx.fxml;

    // --- CRITICAL: Open the controller package (contains all FXML controllers like FlightDetailsController) ---
    // This is necessary for FXMLLoader to access @FXML annotated fields and methods in controllers.
    opens com.example.selekanepentse.controller to javafx.fxml;

    // If your model classes (e.g., Customer, Flight) also have @FXML annotations (unlikely but possible),
    // you would need to open the model package too.
    // opens com.example.selekanepentse.model to javafx.fxml; // Add if needed

    // Export the main package so the JavaFX launcher can find the main class
    exports com.example.selekanepentse;

    // Export other packages if other modules depend on them (usually not needed for a standalone app)
    // exports com.example.selekanepentse.controller; // Usually not needed unless another module instantiates controllers directly
    // exports com.example.selekanepentse.model; // Usually not needed unless another module uses these models directly
    // exports com.example.selekanepentse.service; // Usually not needed unless another module uses these services directly
}