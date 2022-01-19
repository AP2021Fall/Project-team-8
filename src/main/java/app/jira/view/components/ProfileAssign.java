package app.jira.view.components;

import app.jira.controller.BoardController;
import app.jira.model.domain.Respond;
import app.jira.model.domain.User;
import app.jira.view.Window;
import app.jira.view.entities.Board;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class ProfileAssign extends ProfileCard {
    /* Static Fields */
    private static final BoardController controller = new BoardController();
    /* Instance Fields */
    private final Task task;

    /* Constructor */
    public ProfileAssign(Board board, User user, Task task) {
        super(board, user, task.task);
        this.task = task;
    }

    /* FXML Methods */
    // Initialization
    @FXML
    public void initialize() {
        super.initialize();
        super.createButton("Remove User").setOnMouseClicked(this::removeUser);
    }

    // Action
    public void removeUser(MouseEvent mouseEvent) {
        Respond respond = controller.request(() -> controller.removeAssignTask(Window.getInstance().getUser(), Window.getInstance().getBoard(), task.doTask, user.getUsername()));

        // Now Handle Respond
        if (respond.isSuccess()) {
            System.out.println(respond.getMessage());
            task.setAssigned();
        } else board.alert.alert(respond.getMessage());
    }

}
