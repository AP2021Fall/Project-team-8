package app.jira.view.entities;

import app.jira.view.Page;
import app.jira.view.Window;
import app.jira.view.components.HoverAnimation;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class AdminMenu extends PageController {
    public Button UserBtn;
    public Button boardBtn;
    public Button NotificationBtn;
    public Button logoutBtn;
    public Button acceptBtn;

    public void initialize() {
        HoverAnimation.setAnimation(UserBtn, HoverAnimation.Mode.Hue, -0.3);
        HoverAnimation.setAnimation(boardBtn, HoverAnimation.Mode.Hue, -0.3);
        HoverAnimation.setAnimation(acceptBtn, HoverAnimation.Mode.Hue, -0.3);
        HoverAnimation.setAnimation(NotificationBtn, HoverAnimation.Mode.Hue, -0.3);
        HoverAnimation.setAnimation(logoutBtn, HoverAnimation.Mode.Hue, -0.3);
    }

    public void goToUsersMenu(MouseEvent mouseEvent) {
        Window.getInstance().paginate(new Page("users-list"));
    }

    public void goToBoardMenu(MouseEvent mouseEvent) {
        Window.getInstance().paginate(new Page("workspace"));
    }

    public void goToNotificationMenu(MouseEvent mouseEvent) {
        Workspace.lastMenu = "admin-menu";
        Window.getInstance().paginate(new Page("admin-notification"));
    }

    public void logout(MouseEvent mouseEvent) {
        Window.getInstance().paginate(new Page("login"));
    }

    public void acceptMenu(MouseEvent mouseEvent) {
        Window.getInstance().paginate(new Page("accept-menu"));
    }
}
