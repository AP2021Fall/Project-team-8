package app.jira.view.entities;

import app.jira.controller.ScoreboardController;
import app.jira.model.domain.Respond;
import app.jira.model.domain.Scoreboard;
import app.jira.view.Page;
import app.jira.view.Window;
import app.jira.view.components.HoverAnimation;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ScoreBoard extends PageController {
    private static final ScoreboardController controller = new ScoreboardController();

    public TextField searchInput;
    public Label backButton;
    public TextArea scores;


    @FXML
    public void initialize() {
        setScoreBoard();
        HoverAnimation.setAnimation(backButton, HoverAnimation.Mode.Hue, -0.1);
    }

    private void setScoreBoard() {
        Respond respond = controller.request(() -> controller.showScoreboard(Window.getInstance().getBoard()));
        scores.setText(((Scoreboard) respond.getContent()).toString());
    }

    public void back(MouseEvent mouseEvent) {
        Window.getInstance().paginate(new Page("board-control"));
    }

}
