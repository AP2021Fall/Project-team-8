module app.jira {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens app.jira to javafx.fxml;
    exports app.jira;
    exports app.jira.view;
    exports app.jira.view.entities;
    exports app.jira.view.components;
}