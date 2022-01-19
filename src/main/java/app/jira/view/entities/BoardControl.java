package app.jira.view.entities;

import app.jira.view.Page;
import app.jira.view.Window;
import app.jira.view.components.HoverAnimation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class BoardControl extends PageController {
    public Label backButton;
    public Button scoreboardBtn;
    public Button boardBtn;
    public Button chatRoomBtn;

    @FXML
    public void initialize() {
        HoverAnimation.setAnimation(backButton, HoverAnimation.Mode.Hue, -0.1);
        HoverAnimation.setAnimation(scoreboardBtn, HoverAnimation.Mode.Hue, -0.1);
        HoverAnimation.setAnimation(boardBtn, HoverAnimation.Mode.Hue, -0.1);
        HoverAnimation.setAnimation(chatRoomBtn, HoverAnimation.Mode.Hue, -0.1);
    }

    public void back(MouseEvent mouseEvent) {
        Window.getInstance().paginate(new Page("workspace"));
        Window.getInstance().setBoard(null);
    }


    public void chatRoomBoard(MouseEvent mouseEvent) {
        Window.getInstance().paginate(new Page("chat-room"));
    }

    public void board(MouseEvent mouseEvent) {
        Window.getInstance().paginate(new Page("board"));
    }

    public void scoreboardMenu(MouseEvent mouseEvent) {
        Window.getInstance().paginate(new Page("score-board"));
    }
}
