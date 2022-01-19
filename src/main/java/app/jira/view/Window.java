package app.jira.view;

import javafx.stage.Stage;

public class Window {
    /* Static Fields */
    private static Window window;

    /* Instance Fields */
    private Stage stage;

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

    /* Instance Methods */
    public void paginate(Page page) {
        stage.setScene(page.getScene());
    }
}
