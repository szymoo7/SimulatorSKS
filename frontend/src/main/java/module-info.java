module sks.frontend {
    requires javafx.controls;
    requires javafx.fxml;
    requires sks.backend;


    opens sks.frontend to javafx.fxml;
    exports sks.frontend;
}