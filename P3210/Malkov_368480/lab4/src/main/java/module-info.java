module com.example.lab4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.knowm.xchart;
    requires org.controlsfx.controls;
    opens com.example.lab4 to javafx.fxml;
    exports com.example.lab4;
    requires commons.math3;
    requires static lombok;
}
