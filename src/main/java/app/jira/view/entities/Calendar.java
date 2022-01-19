package app.jira.view.entities;


import app.jira.controller.CalendarController;
import app.jira.model.domain.Respond;
import app.jira.view.Page;
import app.jira.view.Window;
import app.jira.view.components.Alert;
import app.jira.view.components.HoverAnimation;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class Calendar extends PageController{


    public Label backButton;
    public TextField searchInput;
    private static final CalendarController controller = new CalendarController();
    public Alert alert;
    public TextArea calendar;

    @FXML
    public void initialize() {
        HoverAnimation.setAnimation(backButton, HoverAnimation.Mode.Hue, -0.1);
        searchInput.textProperty().addListener((observable, oldValue, newValue) -> {
            updateList(newValue);
        });
        getCalendar();
    }

    private void getCalendar() {
        Respond respond = controller.request(() -> controller.showCalendar(Window.getInstance().getUser()));
        if (respond.isSuccess()) {
            if(respond.getContent().toString().equals("no deadlines")){
                alert.alert("no deadlines");
            }
            else
                calendar.setText(respond.getContent().toString());
        } else alert.alert(respond.getContent().toString());

    }

    private void updateList(String newValue) {
        //update your list for search here MATIN
    }

    public void back(MouseEvent mouseEvent) {
        Window.getInstance().paginate(new Page("main-menu"));
    }
}
