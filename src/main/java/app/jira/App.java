package app.jira;

import app.jira.model.dao.DataBase;
import app.jira.view.Config;
import app.jira.view.Page;
import app.jira.view.Window;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class App extends Application {
    public static Stage currentStage;

    /* Instance Methods */
    @Override
    public void start(Stage stage) {
        currentStage = stage;
        stage.setTitle(Config.APP_TITLE);

        // Set window size
        Rectangle2D screen = Screen.getPrimary().getBounds();
        stage.setX((screen.getWidth() - Config.SCREEN_WIDTH) / 2);
        stage.setY((screen.getHeight() - Config.SCREEN_HEIGHT) / 2);

        // Render main page
        Window.getInstance().init(stage);
        Window.getInstance().paginate(new Page("login"));

        stage.show();
    }

    /* Static Methods */
    public static void main(String[] args) {
        // Run database
        DataBase.getInstance().run();

        // Launch GUI
        launch();

        // Close database
        DataBase.getInstance().close();
    }
}