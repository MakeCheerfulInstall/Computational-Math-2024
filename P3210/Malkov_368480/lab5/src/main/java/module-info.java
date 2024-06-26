module com.example.lab5 {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.example.lab5 to javafx.fxml;
    exports com.example.lab5;
    exports com.example.lab5.util;
    opens com.example.lab5.util to javafx.fxml;
}