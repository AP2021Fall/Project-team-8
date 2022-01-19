package app.jira.view.components;

import app.jira.App;
import app.jira.controller.BoardController;
import app.jira.model.domain.Respond;
import app.jira.model.domain.Task;
import app.jira.view.Window;
import app.jira.view.entities.Board;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Objects;

public class Card extends VBox {
    /* Static Fields */
    private static final BoardController controller = new BoardController();

    /* Instance Fields */
    private List list;
    private final Task task;
    private final Board board;

    /* FXML Fields */
    @FXML
    public Label commentCount, comment, description, title, remove;

    /* Constructor */
    public Card(List list, Task task, Board board) {
        // Init
        this.board = board;
        this.list = list;
        this.task = task;
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("components/card.fxml"));
        getStylesheets().add(Objects.requireNonNull(App.class.getResource("styles/card.css")).toExternalForm());
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    /* Getters */
    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
        Respond respond = controller.request(() -> controller.changeTask(Window.getInstance().getUser(), Window.getInstance().getBoard(), task, null, list.list.getName(), list.tasks.getChildren().indexOf(this) + 1 + "", null, null));
        board.sceneRoot.getChildren().remove(this);
        // Now Handle Respond
        System.out.println(respond.getMessage());
    }


    /* Instance Methods */
    private void hideNode(Node node) {
        node.setStyle("-fx-min-width: 0; -fx-pref-width: 0;-fx-max-width: 0 ;-fx-min-height: 0;-fx-pref-height: 0;-fx-max-height: 0");
        node.setVisible(false);
    }

    private void showNode(Node node) {
        node.setStyle("");
        node.setVisible(true);
    }

    public void setComment() {
        if (task.getComments().size() == 0) {
            hideNode(commentCount);
            hideNode(comment);
        } else {
            showNode(commentCount);
            showNode(comment);
            commentCount.setText(task.getComments().size() + "");
        }
    }

    public void setDescription() {
        if (task.getDescription() != null && task.getDescription().length() > 0) showNode(description);
        else hideNode(description);
    }

    /* FXML Methods */
    // Initialization
    public void initialize() {
        commentCount.setText(task.getComments().size() + "");
        setDescription();
        setComment();
        title.setText(task.getTitle());

    }

    // Actions
    public void clickAction(MouseEvent e) {
        if (e.getTarget() == remove) {
            Respond respond = controller.request(() -> controller.removeTask(Window.getInstance().getUser(), Window.getInstance().getBoard(), task.getId() + ""));
            board.sceneRoot.getChildren().remove(this);
            // Now Handle Respond
            System.out.println(respond.getMessage());
        } else if (e.isDragDetect()) {
            board.sceneRoot.getChildren().add(new app.jira.view.components.Task(board.sceneRoot, task, board));
        }
    }
}
