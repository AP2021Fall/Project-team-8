package app.jira.view;

import app.jira.model.domain.Board;
import app.jira.model.domain.User;
import javafx.stage.Stage;

public class Window {
    /* Static Fields */
    private static Window window;

    /* Instance Fields */
    private Stage stage;
    private User user;
    private Board board;


    /* Constructor */
    private Window() {
        // Singleton Class
    }

    /* Static methods */
    public static Window getInstance() {
        if (Window.window == null) Window.window = new Window();
        return Window.window;
    }

    public void init(Stage stage) {
        this.stage = stage;
    }

    /* Getters And Setters */
    public Stage getStage() {
        return stage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }

    /* Instance Methods */
    public void paginate(Page page) {
        stage.setScene(page.getScene());
    }
}
