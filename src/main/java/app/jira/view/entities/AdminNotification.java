package app.jira.view.entities;

import app.jira.controller.AdminController;
import app.jira.model.domain.Respond;
import app.jira.view.Page;
import app.jira.view.Window;
import app.jira.view.components.Alert;
import app.jira.view.components.HoverAnimation;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class AdminNotification extends PageController{
    public TextArea notificationInput;
    public Label backButton;
    public CheckBox sendToAll;
    public Button changeUserBtn;
    public TextField usernameInput;
    public Alert alert;
    private AdminController controller = new AdminController();

    @FXML
    public void initialize() {
        HoverAnimation.setAnimation(changeUserBtn, HoverAnimation.Mode.Hue, -0.1);
        HoverAnimation.setAnimation(backButton, HoverAnimation.Mode.Hue, -0.1);
    }
    public void back(MouseEvent mouseEvent) {
        Window.getInstance().paginate(new Page("admin-menu"));
    }

    public void send(MouseEvent mouseEvent) {
        if(sendToAll.isSelected()){
            Respond respond = controller.request(() -> controller.sendNotificationToAll(Window.getInstance().getUser(), notificationInput.getText()));
            alert.alert(respond.getMessage());

        } else {
            Respond respond = controller.request(() -> controller.sendNotificationToUser(Window.getInstance().getUser(), notificationInput.getText(),usernameInput.getText()));
            alert.alert(respond.getMessage());
        }
    }
}
