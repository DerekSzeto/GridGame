module com.example.gridgame {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.gridgame to javafx.fxml;
    exports com.example.gridgame;
}