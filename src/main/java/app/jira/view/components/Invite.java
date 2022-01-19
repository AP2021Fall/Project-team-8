package app.jira.view.components;

import app.jira.controller.BoardController;
import app.jira.model.domain.Respond;
import app.jira.view.Window;
import app.jira.view.entities.Board;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;


public class Invite extends Search {
    /* Static Fields */
    private static final BoardController controller = new BoardController();

    /* Constructor */
    public Invite(AnchorPane sceneRoot, Board board) {
        super(sceneRoot, board, "Invite To Board", "Invite");
    }

    /* FXML Methods */
    // Initialization
    @FXML
    public void initialize() {
        invite();
    }

    // Actions
    public void invite() {
        inviteBtn.setOnMouseClicked(mouseEvent -> {
            if (user.getText().length() > 0) {
                Respond respond = controller.request(() -> controller.inviteUser(Window.getInstance().getUser(), Window.getInstance().getBoard(), user.getText()));
                // Now Handle Respond
                if (respond.isSuccess()) {
                    board.setMembers();
                } else {
                    board.alert.alert(respond.getMessage());
                }
                user.setText("");
            }
        });
    }
}
