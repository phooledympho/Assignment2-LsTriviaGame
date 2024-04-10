module com.example.limphodee {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.example.limphodee to javafx.fxml;
    exports com.example.limphodee;
}