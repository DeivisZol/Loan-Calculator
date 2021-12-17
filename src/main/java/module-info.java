module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens ui to javafx.fxml;
    opens logic to javafx.base;
    exports ui;
}