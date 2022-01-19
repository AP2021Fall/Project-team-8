package app.jira.view.entities;


import app.jira.controller.ProfileController;
import app.jira.model.domain.Respond;
import app.jira.view.Page;
import app.jira.view.Window;
import app.jira.view.components.Alert;
import app.jira.view.components.HoverAnimation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ChangeUsername extends PageController{

    public Label errorLabel;
    public Label backButton;
    public Button changeUserBtn;
    public Alert alert;
    public TextField usernameInput;
    private ProfileController controller = new ProfileController();
    @FXML
    public void initialize() {
        HoverAnimation.setAnimation(backButton, HoverAnimation.Mode.Hue, -0.1);
        HoverAnimation.setAnimation(changeUserBtn, HoverAnimation.Mode.Hue, -0.1);
    }


    public void back(MouseEvent mouseEvent) { Window.getInstance().paginate(new Page("profile"));    }

    public void changeUser(MouseEvent mouseEvent) {
        String username = usernameInput.getText();
        Respond respond = controller.request(() -> controller.changeUsername(Window.getInstance().getUser(), username));
        alert.alert(respond.getMessage());
        if(respond.isSuccess()){
            Window.getInstance().paginate(new Page("login"));
        }
    }
}
