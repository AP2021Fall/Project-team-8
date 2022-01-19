package app.jira.view.components;

import app.jira.App;
import app.jira.controller.BoardController;
import app.jira.model.domain.User;
import app.jira.view.entities.Board;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Objects;

public class ProfileCard extends VBox {
    /* Static Fields */
    private static final BoardController controller = new BoardController();

    /* Instance Fields */
    protected final Board board;
    protected final User user;
    protected final Pane removeFrom;

    /* FXML Fields */
    @FXML
    public VBox body, btnContainer;
    public Label name, username, close;

    /* Constructor */
    public ProfileCard(Board board, User user, Pane removeFrom) {
        this.user = user;
        this.board = board;
        this.removeFrom = removeFrom;

        // Init
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("components/profile-card.fxml"));
        getStylesheets().add(Objects.requireNonNull(App.class.getResource("styles/profile-card.css")).toExternalForm());
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    /* Instance Fields */
    protected Button createButton(String text) {
        Button btn = new Button();
        btn.setText(text);
        btn.setPrefWidth(250);
        btnContainer.getChildren().add(btn);
        return btn;
    }

    /* FXML Methods */
    // Initialization
    @FXML
    public void initialize() {
        closeOthers();
        HoverAnimation.setAnimation(close, HoverAnimation.Mode.Hue, -0.1);
        name.setText(user.getName());
        username.setText(user.getUsername());
        close();
    }

    // Action
    public void close() {
        close.setOnMouseClicked(mouseEvent -> {
            removeFrom.getChildren().remove(this);
        });
    }

    public void closeOthers() {
        removeFrom.getChildren().removeIf(child -> child instanceof ProfileCard);
    }
}
