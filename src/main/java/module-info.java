module com.example.labinf {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.example.labinf to javafx.fxml;
    exports com.example.labinf;
}