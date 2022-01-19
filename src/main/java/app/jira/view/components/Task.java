package app.jira.view.components;

import app.jira.App;
import app.jira.controller.BoardController;
import app.jira.model.domain.Respond;
import app.jira.model.domain.User;
import app.jira.view.Window;
import app.jira.view.entities.Board;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Objects;

public class Task extends AnchorPane {
    /* Static Fields */
    private static final BoardController controller = new BoardController();

    /* Instance Fields */
    private AnchorPane sceneRoot;
    public final app.jira.model.domain.Task doTask;
    private final Board board;
    private final Assign assignTaskTo;


    /* FXML Fields */
    @FXML
    public ScrollPane taskView;
    public AnchorPane task;
    public HBox members;
    public VBox commentInsert, comments, description;
    public Button apply, discard;
    public TextField boardTitle;
    public Label assign;

    /* Constructor */
    public Task(AnchorPane sceneRoot, app.jira.model.domain.Task doTask, Board board) {
        this.sceneRoot = sceneRoot;
        this.doTask = doTask;
        this.board = board;
        // Init
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("components/task.fxml"));
        getStylesheets().add(Objects.requireNonNull(App.class.getResource("styles/task.css")).toExternalForm());
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
            task.setOnMouseClicked(this::close);
            assignTaskTo = new Assign(sceneRoot, board, this);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

    /* Instance Methods */
    public static boolean inHierarchy(Node node, Node potentialHierarchyElement) {
        if (potentialHierarchyElement == null) {
            return true;
        }
        while (node != null) {
            if (node == potentialHierarchyElement) {
                return true;
            }
            node = node.getParent();
        }
        return false;
    }

    /* FXML Methods */
    // Initialization
    @FXML
    public void initialize() {
        HoverAnimation.setAnimation(apply, HoverAnimation.Mode.Hue, -0.1);
        HoverAnimation.setAnimation(discard, HoverAnimation.Mode.Hue, -0.1);
        sendComment();
        setDescription();
        setComments();
        setTitle();
        assignTask();
        setAssigned();
    }

    // Actions Close
    public void assignTask() {
        assign.setOnMouseClicked(mouseEvent -> {
            if (!task.getChildren().contains(assignTaskTo)) {
                assignTaskTo.setLayoutX(this.assign.localToScene(this.assign.getBoundsInLocal()).getMinX());
                assignTaskTo.setLayoutY(this.assign.localToScene(this.assign.getBoundsInLocal()).getMaxY() + 10);
                task.getChildren().add(assignTaskTo);
            } else {
                task.getChildren().remove(assignTaskTo);
            }
        });
    }

    public void setAssigned() {
        members.getChildren().clear();
        ProfilePicture profilePicture;
        // Set Members
        for (User user : doTask.getAssignedUsers().keySet()) {
            profilePicture = new ProfilePicture(user, board);
            ProfilePicture finalProfilePicture = profilePicture;
            profilePicture.setOnMouseClicked(mouseEvent -> {
                ProfileAssign profileAssign = new ProfileAssign(board, user, this);
                profileAssign.setLayoutX(finalProfilePicture.localToScene(finalProfilePicture.getBoundsInLocal()).getMinX());
                profileAssign.setLayoutY(finalProfilePicture.localToScene(finalProfilePicture.getBoundsInLocal()).getMaxY() + 10);
                this.getChildren().add(profileAssign);
            });
            members.getChildren().add(finalProfilePicture);
        }
    }

    public void sendComment() {
        TextInput textInput = new TextInput("Write a comment...", "Send");
        commentInsert.getChildren().add(textInput);
        textInput.saveBtn.setOnMouseClicked(mouseEvent -> {
            if (textInput.textArea.getText().length() > 1) {
                Respond respond = controller.request(() -> controller.setCommentTask(Window.getInstance().getUser(), Window.getInstance().getBoard(), doTask, textInput.textArea.getText()));

                // Now Handle Respond
                textInput.textArea.setText("");
                setComments();
                board.setLists();
                System.out.println(respond.getMessage());
            }
        });
    }

    public void setDescription() {
        TextInput textInput = new TextInput("Add a more detailed description...", "Save");
        textInput.textArea.setText(doTask.getDescription());
        description.getChildren().add(textInput);
        textInput.saveBtn.setOnMouseClicked(mouseEvent -> {
            if (!textInput.textArea.getText().equals(doTask.getDescription())) {
                Respond respond = controller.request(() -> controller.changeTask(Window.getInstance().getUser(), Window.getInstance().getBoard(), doTask, null, null, null, null, textInput.textArea.getText()));

                // Now Handle Respond
                board.setLists();
                System.out.println(respond.getMessage());
            }
        });
    }

    public void setTitle() {
        boardTitle.setText(doTask.getTitle());
        boardTitle.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (!newPropertyValue) {
                    if (!boardTitle.getText().equals(doTask.getTitle())) {
                        Respond respond = controller.request(() -> controller.changeTask(Window.getInstance().getUser(), Window.getInstance().getBoard(), doTask, boardTitle.getText(), null, null, null, null));
                        board.setLists();
                        // Now Handle Respond
                        System.out.println(respond.getMessage());
                    }
                }
            }
        });
    }

    public void setComments() {
        comments.getChildren().clear();
        for (int i = doTask.getComments().size() - 1; i >= 0; i--) {
            comments.getChildren().add(new Comment(doTask.getComments().get(i)));
        }
    }

    public void close(MouseEvent mouseEvent) {
        if (!inHierarchy(mouseEvent.getPickResult().getIntersectedNode(), taskView) && mouseEvent.getTarget() != assign &&
                !(mouseEvent.getTarget() instanceof Label)) {
            sceneRoot.getChildren().remove(this);
        }
    }
}
