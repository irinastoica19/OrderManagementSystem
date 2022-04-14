module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;

    opens org.example.presentation to javafx.fxml;
    opens org.example.model;
    opens org.example.bll;
    opens org.example.bll.validators;
    opens org.example.dao;
    exports org.example.presentation;
}
