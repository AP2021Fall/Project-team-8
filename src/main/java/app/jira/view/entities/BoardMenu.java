package app.jira.view.entities;

import app.jira.view.Page;
import app.jira.view.Window;
import app.jira.view.components.HoverAnimation;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class BoardMenu extends PageController{
    public static String lastMenu = "main-menu";
    public Label backButton;
    public TextField searchInput;

    @FXML
    public void initialize() {
        HoverAnimation.setAnimation(backButton, HoverAnimation.Mode.Hue, -0.1);
        searchInput.textProperty().addListener((observable, oldValue, newValue) -> {
            updateList(newValue);
        });
    }

    private void updateList(String newValue) {
        //update your list for search here MATIN
    }

    public void back(MouseEvent mouseEvent) {
        Window.getInstance().paginate(new Page(lastMenu));
    }
    
}
