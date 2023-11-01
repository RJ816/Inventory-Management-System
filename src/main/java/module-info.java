module gui.mainform {
    requires javafx.controls;
    requires javafx.fxml;


    opens imsapp to javafx.fxml;
    exports imsapp;
}