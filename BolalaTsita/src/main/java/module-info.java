// File: src/main/java/module-info.java

module com.example.bolalatsita { // Replace with your actual module name if different
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql; // Required for JDBC database connectivity

    // Add other required modules if you use them (e.g., controlsfx, formsfx)
    // requires org.controlsfx.controls;
    // requires com.dlsc.formsfx;

    // Export the main package containing Main.java (for the launcher)
    exports com.example.bolalatsita;

    // Open the controller package to javafx.fxml for @FXML access
    opens com.example.bolalatsita.controller to javafx.fxml;

    // Open the main package if Main.java itself has @FXML annotations (unlikely but possible)
    opens com.example.bolalatsita to javafx.fxml;

    // Open the model package if models are used directly in FXML (e.g., in PropertyValueFactory if they lack public setters/getters)
    // This is often not needed if models just have standard getters/setters.
    // opens com.example.bolalatsita.model to javafx.fxml;

    // Open the service package if services are loaded directly via FXML (unlikely)
    // opens com.example.bolalatsita.service to javafx.fxml;

    // Open the util package if utils are loaded directly via FXML (very unlikely)
    // opens com.example.bolalatsita.util to javafx.fxml;
}