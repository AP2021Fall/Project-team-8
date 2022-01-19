package app.jira.view.components;

import app.jira.App;
import app.jira.controller.WorkspaceController;
import app.jira.model.domain.Board;
import app.jira.model.domain.Respond;
import app.jira.view.Page;
import app.jira.view.Window;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class BoardCard extends HBox {
    /* Static Fields */
    private static final WorkspaceController controller = new WorkspaceController();
    private final static String[] colors = {"#e27d60", "#e8a87c", "#c38d9e", "#41b3a3", "#242582", "#553d67", "#f64c72", "#99738e", "#2f2fa2", "#e52165",
            "#0d1137", "#d72631", "#a2d5c6", "#077b8a", "#5c3c92", "#e2d810", "#d9138a", "#12a4d9", "#322e2f", "#f3ca20", "#000000", "#cf1578", "#e8d21d", "#039fbe",
            "#b20238", "#e75874", "#be1558", "#fbcbc9", "#322514", "#ef9d10", "#3b4d61", "#6b7b8c", "#1e3d59", "#f5f0e1", "#ff6e40", "#ffc13b", "#ecc19c", "#1e847f",
            "#000000", "#26495c", "#c4a35a", "#c66b3d", "#e5e5dc", "#d9a5b3", "#1868ae", "#c6d7eb", "#408ec6", "#7a2048", "#1e2761", "#8a307f", "#79a7d3", "#6883bc",
            "#1d3c45", "#d2601a", "#fff1e1", "#aed6dc", "#ff9a8d", "#4a536b", "#da68a0", "#77c593", "#ed3572", "#316879", "#f47a60", "#7fe7dc", "#ced7d8", "#d902ee",
            "#ffd79d", "#f162ff", "#320d3e", "#ffcce7", "#daf2dc", "#81b7d2", "#4d5198", "#ddc3a5", "#201e20", "#e0a96d", "#edca82", "#097770", "#e0cdbe", "#a9c0a6",
            "#e1dd72", "#a8c66c", "#1b6535", "#d13ca4", "#ffea04", "#fe3a9e", "#e3b448", "#cbd18f", "#3a6b35", "#f6ead4", "#a2a595", "#b4a284", "#79cbb8", "#500472",
            "#f5beb4", "#9bc472", "#cbf6db", "#b85042", "#e7e8d1", "#a7beae", "#d71b3b", "#e8d71e", "#16acea", "#4203c9", "#829079", "#ede6b9", "#b9925e", "#1fbfb8",
            "#05716c", "#1978a5", "#031163", "#7fc3c0", "#cfb845", "#141414", "#efb5a3", "#f57e7e", "#315f72"};


    /* Instance Fields */
    private final Board board;
    private final Alert alert;

    /* FXML Fields */
    @FXML
    public HBox body;
    public Label boardName;

    /* Constructor */
    public BoardCard(Board board, Alert alert) {
        this.board = board;
        this.alert = alert;
        // Init
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("components/board-card.fxml"));
        getStylesheets().add(Objects.requireNonNull(App.class.getResource("styles/board-card.css")).toExternalForm());
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    /* Instance Methods */
    private String randomColor() {
        int rnd = new Random().nextInt(colors.length);
        return colors[rnd];
    }


    /* FXML Methods */
    // Initialization
    @FXML
    public void initialize() {
        // Init
        boardName.setText(board.getName());
        body.setOpacity(!board.getStatus().equals("accepted") ? 0.2 : 1);
        HoverAnimation.setAnimation(body, HoverAnimation.Mode.Hue, -0.1);
        body.setStyle("-fx-background-color: " + randomColor() + ";");
        body.setOnMouseClicked(mouseEvent -> goToBoard());
    }

    // Action
    public void goToBoard() {

        Respond respond = controller.request(() -> controller.selectBoard(Window.getInstance().getUser(), board.getName()));
        if(Window.getInstance().getUser().getUsername().equals("SystemAdmin"))
             respond = controller.request(() -> controller.selectBoard(board.getLeader(), board.getName()));
        if (respond.isSuccess()) {
            Window.getInstance().setBoard((Board) respond.getContent());
            Window.getInstance().paginate(new Page("board-control"));
        } else {
            alert.alert(respond.getMessage());
        }
    }
}
