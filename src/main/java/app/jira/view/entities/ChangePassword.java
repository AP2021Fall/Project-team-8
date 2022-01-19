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
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;

public class ChangePassword extends PageController{
    public Label backButton;
    public Button changePassBtn;
    public Alert alert;
    public PasswordField passwordInput;
    public PasswordField confirmInput;
    private ProfileController controller = new ProfileController();

    @FXML
    public void initialize() {
        HoverAnimation.setAnimation(backButton, HoverAnimation.Mode.Hue, -0.1);
        HoverAnimation.setAnimation(changePassBtn, HoverAnimation.Mode.Hue, -0.1);
    }

    public void back(MouseEvent mouseEvent) {
        Window.getInstance().paginate(new Page("profile"));
    }

    public void changePass(MouseEvent mouseEvent) {
        String password = passwordInput.getText();
        String confirm = confirmInput.getText();
        if(password.equals(confirm)){
            Respond respond = controller.request(() -> controller.changePass(Window.getInstance().getUser(), password));
            alert.alert(respond.getMessage());
            if(respond.isSuccess()){
                Window.getInstance().paginate(new Page("login"));
            }
        } else {
            alert.alert("Password and its confirm aren't match");
        }
    }
}
