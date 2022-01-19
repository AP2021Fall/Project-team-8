package app.jira.view.components;

import app.jira.App;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Objects;

public class Comment extends GridPane {
    /* Instance Fields */
    private final app.jira.model.domain.Comment sendComment;

    /* FXML Fields */
    @FXML
    public TextArea comment;
    public VBox profile;
    public Text textHolder = new Text();

    /* Constructor */
    public Comment(app.jira.model.domain.Comment sendComment) {
        this.sendComment = sendComment;
        // Init
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("components/comment.fxml"));
        getStylesheets().add(Objects.requireNonNull(App.class.getResource("styles/comment.css")).toExternalForm());
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
            comment.setText(sendComment.getText());
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    /* FXML Methods */
    // Initialization
    @FXML
    public void initialize() {
        // Set Height For Text Area
        comment.setPrefSize(200, 40);
        comment.setWrapText(true);
        textHolder.textProperty().bind(comment.textProperty());
        textHolder.layoutBoundsProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {
                if (0 != newValue.getHeight()) {
                    comment.setPrefHeight(textHolder.getLayoutBounds().getHeight() + (newValue.getHeight() + 65) / 2); // const value for paddings
                }
            }
        });
        textHolder.setWrappingWidth(comment.getWidth() - 10);
    }
}
