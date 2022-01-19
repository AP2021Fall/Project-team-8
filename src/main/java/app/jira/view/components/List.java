package app.jira.view.components;

import app.jira.App;
import app.jira.controller.BoardController;
import app.jira.model.domain.Respond;
import app.jira.view.Window;
import app.jira.view.entities.Board;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Objects;

public class List extends VBox {
    /* Static Fields */
    private final static BoardController controller = new BoardController();

    /* Instance Fields */
    private final Board board;
    public final app.jira.model.domain.List list;

    /* FXML Fields */
    @FXML
    public VBox body;
    public Button addBtn;
    public VBox tasks;
    public ScrollPane scrollPane;
    public TextField header;
    public Label remove;

    /* Constructor */
    public List(Board board, app.jira.model.domain.List list) {
        this.board = board;
        this.list = list;
        // Init
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("components/list.fxml"));
        getStylesheets().add(Objects.requireNonNull(App.class.getResource("styles/list.css")).toExternalForm());
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    /* Getters */
    public Board getBoard() {
        return board;
    }

    public VBox getTasks() {
        return tasks;
    }

    public ScrollPane getScrollPane() {
        return scrollPane;
    }

    /* Instance Methods */
    @Override
    protected void layoutChildren() {
        super.layoutChildren();
    }

    /* FXML Methods */
    // Initialization
    @FXML
    public void initialize() {
        HoverAnimation.setAnimation(addBtn, HoverAnimation.Mode.Hue, -0.1);
        HoverAnimation.setAnimation(remove, HoverAnimation.Mode.Hue, -0.1);
        changeListName();
        removeList();
        addBtn.setOnMouseClicked(mouseEvent -> addTask());
    }

    // Action
    public void changeListName() {
        header.setText(list.getName());
        header.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (!newPropertyValue) {
                    if (!header.getText().equals(list.getName())) {
                        Respond respond = controller.request(() -> controller.changeList(Window.getInstance().getUser(), Window.getInstance().getBoard(), list.getName(), header.getText(), null));

                        list.setName(list.getName());
                        // Now Handle Respond
                        System.out.println(respond.getMessage());
                    }
                }
            }
        });
    }

    public void changeOrder(int newOrder) {
        Respond respond = controller.request(() -> controller.changeList(Window.getInstance().getUser(), Window.getInstance().getBoard(), list.getName(), null, (newOrder + 1) + ""));

        // Now Handle Respond
        System.out.println(respond.getMessage());
    }

    public void removeList() {
        remove.setOnMouseClicked(mouseEvent -> {
            Respond respond = controller.request(() -> controller.removeList(Window.getInstance().getUser(), Window.getInstance().getBoard(), list.getName()));
            // Now Handle Respond
            board.setLists();
            System.out.println(respond.getMessage());
        });
    }

    public void addTask() {
        Respond respond = controller.request(() -> controller.addTask(Window.getInstance().getUser(), Window.getInstance().getBoard(), "new task", "2022-12-12|12:12", list, null, null));
        this.board.setLists();
        // Now Handle Respond
        System.out.println(respond.getMessage());
    }
}
