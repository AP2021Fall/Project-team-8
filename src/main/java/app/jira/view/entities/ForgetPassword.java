package app.jira.view.entities;

import app.jira.controller.ForgetPasswordController;
import app.jira.model.domain.Respond;
import app.jira.view.Page;
import app.jira.view.Window;
import app.jira.view.components.Alert;
import app.jira.view.components.HoverAnimation;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ForgetPassword extends PageController {
    public static ForgetPasswordController controller = new ForgetPasswordController();
    @FXML
    public Label backButton;
    public Button changePassBtn;
    public Label errorLabel;
    public Alert alert;
    public DatePicker birthdayInput;
    public PasswordField confirmInput;
    public PasswordField passwordInput;
    public TextField emailInput;
    public TextField usernameInput;

    public void back() {
        Window.getInstance().paginate(new Page("login"));
    }

    @FXML
    public void initialize() {
        HoverAnimation.setAnimation(changePassBtn, HoverAnimation.Mode.Hue, -0.1);
        HoverAnimation.setAnimation(backButton, HoverAnimation.Mode.Hue, -0.1);

    }

    public void changePass() {
        Respond respond = controller.request(() -> controller.changePassword(usernameInput.getText(), birthdayInput.getValue() != null ? birthdayInput.getValue().toString() : "", emailInput.getText(), passwordInput.getText(), confirmInput.getText()));
        alert.alert(respond.getMessage());
        System.out.println(birthdayInput.getValue().toString());
        // Now Handle Respond
        if (respond.isSuccess()) {
            System.out.println(respond.getMessage());
            Window.getInstance().paginate(new Page("login"));
        } else {
            alert.alert(respond.getMessage());
        }
    }
}
