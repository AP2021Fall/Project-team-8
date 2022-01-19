package app.jira.view.entities;

import app.jira.view.Window;
import javafx.fxml.FXML;

public abstract class PageController {
    /* FXML Methods */
    @FXML
    public void clearFocus() {
        Window.getInstance().getStage().getScene().getRoot().requestFocus();
    }
}
