// File: src/main/java/module-info.java

module com.example.motebangtatolo {
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

    // --- CRITICAL: Export the main package containing the Main class ---
    // This allows the JavaFX launcher (javafx.graphics) to find and instantiate the main application class.
    exports com.example.motebangtatolo to javafx.graphics;

    // --- CRITICAL: Open the controller package ---
    // This allows FXMLLoader (from javafx.fxml) to access @FXML annotated members (fields, methods) within controllers.
    // This is essential for linking FXML elements to Java code.
    opens com.example.motebangtatolo.controller to javafx.fxml;

    // --- CRITICAL: Open the model package (if models are used in FXML, e.g., in TableView) ---
    // If your model classes (like Customer, Flight, Fare) are used directly as items in a TableView or other JavaFX controls
    // via PropertyValueFactory or custom cell factories, they might need to be accessible via reflection.
    // This is often the case. PropertyValueFactory needs to call getter methods on the model objects.
    // While the model classes themselves don't necessarily need to be *exported*, they often need to be *opened* for reflection.
    // However, opening the *entire* model package might be broad. A safer approach is often to ensure the model classes
    // have public getters/setters and are accessible within the module.
    // For now, let's open the model package as it's a common requirement for PropertyValueFactory.
    opens com.example.motebangtatolo.model to javafx.fxml;

    // --- CRITICAL: Open the service package (if services are loaded via FXML, less common) ---
    // This is generally NOT needed. Services are usually instantiated by controllers programmatically.
    // opens com.example.motebangtatolo.service to javafx.fxml; // Usually not needed

    // --- Optional: Export other packages if other modules depend on them (rare for a standalone app) ---
    // exports com.example.motebangtatolo.controller; // Only if another module directly instantiates controllers
    // exports com.example.motebangtatolo.model; // Only if another module directly uses these models
    // exports com.example.motebangtatolo.service; // Only if another module directly uses these services
}