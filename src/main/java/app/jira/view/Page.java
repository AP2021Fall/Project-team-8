package app.jira.view;

import app.jira.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.Objects;

public class Page {
    /* Instance Fields */
    private Scene scene;

    /* Constructor */
    public Page(String layout) {
        try {
            init(layout);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* Instance Methods */
    private void init(String layout) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("layouts/" + layout + ".fxml"));
        scene = new Scene(loader.load(), Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
        scene.getStylesheets().add(Objects.requireNonNull(App.class.getResource("styles/" + layout + ".css")).toExternalForm());
        scene.getRoot().requestFocus();
    }

    public Scene getScene() {
        return scene;
    }
}
