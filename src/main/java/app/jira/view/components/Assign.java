package app.jira.view.components;

import app.jira.controller.BoardController;
import app.jira.model.domain.Respond;
import app.jira.view.Window;
import app.jira.view.entities.Board;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class Assign extends Search {
    /* Static Fields */
    private static final BoardController controller = new BoardController();

    /* Instance Fields */
    private final Task task;

    /* Constructor */
    public Assign(AnchorPane sceneRoot, Board board, Task task) {
        super(sceneRoot, board, "Assign Task To", "Assign");
        this.task = task;
    }

    /* FXML Methods */
    // Initialization
    @FXML
    public void initialize() {
        super.initialize();
        assign();
    }

    // Actions
    public void assign() {
        inviteBtn.setOnMouseClicked(mouseEvent -> {
            if (user.getText().length() > 0) {
                Respond respond = controller.request(() -> controller.assignTask(Window.getInstance().getUser(), Window.getInstance().getBoard(), task.doTask, user.getText()));
                // Now Handle Respond
                if (respond.isSuccess()) {
                    task.setAssigned();
                    System.out.println("success");
                } else {
                    board.alert.alert(respond.getMessage());
                }
                user.setText("");
            }
        });
    }
}
