
module GUI {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires java.desktop;
    requires org.junit.jupiter.api;
    opens GUI to javafx.fxml;
    opens main to javafx.fxml;
    exports GUI;
    exports main;
}