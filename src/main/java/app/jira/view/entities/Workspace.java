package app.jira.view.entities;

import app.jira.controller.WorkspaceController;
import app.jira.model.dao.BoardDao;
import app.jira.model.domain.Board;
import app.jira.model.domain.Respond;
import app.jira.view.Page;
import app.jira.view.Window;
import app.jira.view.components.Alert;
import app.jira.view.components.BoardCard;
import app.jira.view.components.HoverAnimation;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class Workspace extends PageController {
    /* Static Fields */
    private static final WorkspaceController controller = new WorkspaceController();

    /* Instance Fields */
    private final app.jira.model.domain.Workspace workspace;
    public static String lastMenu = "main-menu";
    /* FXML Fields */
    @FXML
    public HBox yourWorkspace, guestWorkspace;
    public Alert alert;
    public Label back;

    /* Constructor */
    public Workspace() {
        Respond respond = controller.request(() -> controller.getWorkspaceNew(Window.getInstance().getUser()));
        workspace = (app.jira.model.domain.Workspace) respond.getContent();
    }

    /* FXML Methods */
    // Initialization
    public void initialize() {
        HoverAnimation.setAnimation(back, HoverAnimation.Mode.Hue, -0.1);
        if(Window.getInstance().getUser().getUsername().equals("SystemAdmin")){
            try {
                for (Board leaderBoard : BoardDao.getAllBoards(false)) {
                    yourWorkspace.getChildren().add(new BoardCard(leaderBoard, alert));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
        for (Board leaderBoard : workspace.getLeaderBoards()) {
            yourWorkspace.getChildren().add(new BoardCard(leaderBoard, alert));
        }
        for (Board guestBoard : workspace.getGuestBoards()) {
            guestWorkspace.getChildren().add(new BoardCard(guestBoard, alert));
        }
        }
    }

    // Actions
    public void back() {
        Window.getInstance().paginate(new Page(lastMenu));
    }
}
