package app.jira.view.entities;

import app.jira.controller.LoginController;
import app.jira.model.domain.Respond;
import app.jira.model.domain.User;
import app.jira.view.Page;
import app.jira.view.Window;
import app.jira.view.components.Alert;
import app.jira.view.components.HoverAnimation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class Login extends PageController {
    /* Static Fields */
    private static final LoginController controller = new LoginController();

    /* FXML Fields */
    @FXML
    public Button loginBtn;
    public Label register, forgotPassword;
    public Alert alert;
    public TextField usernameInput, passwordInput;

    /* FXML Methods */
    // Initialization
    public void initialize() {
        HoverAnimation.setAnimation(loginBtn, HoverAnimation.Mode.Hue, -0.1);
        HoverAnimation.setAnimation(register, HoverAnimation.Mode.Hue, -0.3);
        HoverAnimation.setAnimation(forgotPassword, HoverAnimation.Mode.Hue, -0.3);
    }

    // Actions
    public void login() {
        Respond respond = controller.request(() -> controller.login(usernameInput.getText(), passwordInput.getText()));
        // Now Handle Respond
        if (respond.isSuccess()) {
            System.out.println(respond.getMessage());
            Window.getInstance().setUser((User) respond.getContent());
            if (usernameInput.getText().equals("SystemAdmin"))
                Window.getInstance().paginate(new Page("admin-menu"));
            else
                Window.getInstance().paginate(new Page("main-menu"));
        } else {
            alert.alert(respond.getMessage());
        }
    }

    public void goToRegister() {
        Window.getInstance().paginate(new Page("register"));
    }

    public void forgetPassword(MouseEvent mouseEvent) {
        Window.getInstance().paginate(new Page("forget-password"));
    }
}
