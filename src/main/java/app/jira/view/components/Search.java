package app.jira.view.components;

import app.jira.App;
import app.jira.view.entities.Board;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Objects;

public class Search extends VBox {
    /* Instance Fields */
    protected final AnchorPane sceneRoot;
    protected final Board board;

    /* FXML Fields */
    @FXML
    public VBox body;
    public Button inviteBtn;
    public TextField user;
    public Label header;

    /* Constructor */
    public Search(AnchorPane sceneRoot, Board board, String title, String btnText) {
        this.board = board;
        this.sceneRoot = sceneRoot;
        // Init
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("components/invite-to.fxml"));
        getStylesheets().add(Objects.requireNonNull(App.class.getResource("styles/invite-to.css")).toExternalForm());
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
            header.setText(title);
            inviteBtn.setText(btnText);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    /* FXML Methods */
    // Initialization
    @FXML
    public void initialize() {
        HoverAnimation.setAnimation(inviteBtn, HoverAnimation.Mode.Hue, -0.1);
    }

    // Actions
    public void close(MouseEvent mouseEvent) {
        if (!Task.inHierarchy(mouseEvent.getPickResult().getIntersectedNode(), body)) {
            board.sceneRoot.getChildren().remove(this);
        }
    }

    public void close(MouseEvent mouseEvent, AnchorPane deleteFrom) {
        if (!Task.inHierarchy(mouseEvent.getPickResult().getIntersectedNode(), body)) {
            deleteFrom.getChildren().remove(this);
        }
    }
}
