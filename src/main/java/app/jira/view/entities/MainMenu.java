package app.jira.view.entities;

import app.jira.view.Page;
import app.jira.view.Window;
import app.jira.view.components.HoverAnimation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainMenu {
    /* FXML Fields */
    @FXML
    public Button profileBtn;
    public Button boardBtn;
    public Button calendarBtn;
    public Button logoutBtn;

    /* FXML Methods */
    // Initialization
    public void initialize() {
        HoverAnimation.setAnimation(logoutBtn, HoverAnimation.Mode.Hue, -0.1);
        HoverAnimation.setAnimation(calendarBtn, HoverAnimation.Mode.Hue, -0.1);
        HoverAnimation.setAnimation(boardBtn, HoverAnimation.Mode.Hue, -0.1);
        HoverAnimation.setAnimation(profileBtn, HoverAnimation.Mode.Hue, -0.1);
    }

    // Actions
    public void logout() {
        Window.getInstance().paginate(new Page("login"));
        Window.getInstance().setUser(null);
        System.out.println("go to login");
    }

    public void goToCalendarMenu() {
        Window.getInstance().paginate(new Page("calendar"));
        System.out.println("go to calendar");
    }

    public void goToBoardMenu() {
        Workspace.lastMenu = "main-menu";
        Window.getInstance().paginate(new Page("workspace"));
        System.out.println("go to board");
    }

    public void goToProfileMenu() {
        Window.getInstance().paginate(new Page("profile"));
        System.out.println("go to profile");
    }
}
