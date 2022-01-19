package app.jira.view.components;

import app.jira.App;
import app.jira.model.domain.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.Objects;

public class Progress extends HBox {
    /* FXML Fields */
    @FXML
    public Label title, percentage;
    public ProgressBar progress;

    /* Constructor */
    public Progress(Task task, int percentageNum) {
        // Init
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("components/progress.fxml"));
        getStylesheets().add(Objects.requireNonNull(App.class.getResource("styles/progress.css")).toExternalForm());
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
            title.setText(task.getTitle());
            progress.setProgress(percentageNum / 100.0);
            percentage.setText(percentageNum + "%");
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
