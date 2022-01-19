package app.jira.view.components;

import app.jira.App;
import app.jira.controller.RoadmapController;
import app.jira.model.domain.Respond;
import app.jira.view.Window;
import app.jira.view.entities.Board;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Objects;

public class Roadmap extends AnchorPane {
    /* Static Fields */
    private static final RoadmapController controller = new RoadmapController();

    /* Instance Fields */
    private final Board board;

    /* FXML Fields */
    @FXML
    public ScrollPane taskView;
    public AnchorPane roadmap;
    public VBox taskContainer;

    /* Constructor */
    public Roadmap(Board board) {
        this.board = board;
        // Init
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("components/roadmap.fxml"));
        getStylesheets().add(Objects.requireNonNull(App.class.getResource("styles/roadmap.css")).toExternalForm());
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
            roadmap.setOnMouseClicked(this::close);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

    /* FXML Methods */
    // Initialization
    @FXML
    public void initialize() {
        setRoadMap();
    }

    // Actions Close
    public void setRoadMap() {
        Respond respond = controller.request(() -> controller.showRoadmap(Window.getInstance().getBoard()));

        // Now Handle Respond
        app.jira.model.domain.Roadmap roadmap = (app.jira.model.domain.Roadmap) respond.getContent();
        roadmap.getTaskPercentage().forEach((task, integer) -> {
            taskContainer.getChildren().add(new Progress(task, integer));
        });
    }

    public void close(MouseEvent mouseEvent) {
        if (!Task.inHierarchy(mouseEvent.getPickResult().getIntersectedNode(), taskView)) {
            board.sceneRoot.getChildren().remove(this);
        }
    }
}
