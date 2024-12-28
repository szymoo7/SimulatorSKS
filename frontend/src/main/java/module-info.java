module org.gui.frontend {
    requires javafx.controls;
    requires javafx.fxml;


    opens sks.frontend to javafx.fxml;
    exports sks.frontend;
}