package app.jira.view.entities;

import app.jira.controller.LoginController;
import app.jira.model.domain.Respond;
import app.jira.view.Page;
import app.jira.view.Window;
import app.jira.view.components.Alert;
import app.jira.view.components.HoverAnimation;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class Register extends PageController {
    /* Static Fields */
    private static final LoginController controller = new LoginController();

    /* FXML Fields */
    @FXML
    public TextField nameInput, usernameInput, emailInput;
    public PasswordField passwordInput, confirmInput;
    public DatePicker birthdayInput;
    public Button signUpBtn;
    public Label back;
    public Alert alert;

    /* FXML Methods */
    // Initialization
    @FXML
    public void initialize() {
        HoverAnimation.setAnimation(signUpBtn, HoverAnimation.Mode.Hue, -0.1);
        HoverAnimation.setAnimation(back, HoverAnimation.Mode.Hue, -0.1);
    }

    // Actions
    @FXML
    public void register() {
        Respond respond = controller.request(() -> controller.signUp(usernameInput.getText(), passwordInput.getText(),
                confirmInput.getText(), emailInput.getText(), nameInput.getText(), birthdayInput.getValue() != null ? birthdayInput.getValue().toString() : ""));
        // Now Handle Respond
        if (respond.isSuccess()) {
            System.out.println(respond.getMessage());
            Window.getInstance().paginate(new Page("login"));
        } else {
            alert.alert(respond.getMessage());
        }
    }
    public void back() {
        Window.getInstance().paginate(new Page("login"));
    }
}
