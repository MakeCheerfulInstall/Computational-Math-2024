module com.example.lab6 {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.example.lab6 to javafx.fxml;
    exports com.example.lab6;
    exports com.example.lab6.util;
    opens com.example.lab6.util to javafx.fxml;
}