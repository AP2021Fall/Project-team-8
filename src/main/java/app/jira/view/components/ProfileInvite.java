package app.jira.view.components;


import app.jira.controller.BoardController;
import app.jira.model.domain.Respond;
import app.jira.model.domain.User;
import app.jira.view.Window;
import app.jira.view.entities.Board;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class ProfileInvite extends ProfileCard {
    /* Static Fields */
    private static final BoardController controller = new BoardController();

    /* Constructor */
    public ProfileInvite(Board board, User user) {
        super(board, user, board.sceneRoot);
    }

    /* FXML Methods */
    // Initialization
    @FXML
    public void initialize() {
        super.initialize();
        super.createButton("Remove User").setOnMouseClicked(this::removeUser);
        super.createButton("Suspend User").setOnMouseClicked(this::suspendUser);
        super.createButton("Promote To Leader").setOnMouseClicked(this::promoteUser);
    }

    // Action
    public void removeUser(MouseEvent mouseEvent) {
        Respond respond = controller.request(() -> controller.removeUser(Window.getInstance().getUser(), Window.getInstance().getBoard(), user.getUsername()));

        // Now Handle Respond
        if (respond.isSuccess()) {
            System.out.println(respond.getMessage());
            board.setMembers();
        } else board.alert.alert(respond.getMessage());
    }

    public void promoteUser(MouseEvent mouseEvent) {
        Respond respond = controller.request(() -> controller.changeLeader(Window.getInstance().getUser(), Window.getInstance().getBoard(), user.getUsername()));

        // Now Handle Respond
        if (respond.isSuccess()) {
            System.out.println(respond.getMessage());
            board.setMembers();
        } else board.alert.alert(respond.getMessage());
    }

    public void suspendUser(MouseEvent mouseEvent) {
        Respond respond = controller.request(() -> controller.suspendUser(Window.getInstance().getUser(), Window.getInstance().getBoard(), user.getUsername(), null));

        // Now Handle Respond
        if (respond.isSuccess()) System.out.println(respond.getMessage());
        else board.alert.alert(respond.getMessage());
    }
}
