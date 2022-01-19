package app.jira.view.components;

import app.jira.App;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Objects;

public class TextInput extends VBox {
    /* Instance Fields */
    private double oldHeight = 0;

    /* FXML Fields */
    @FXML
    public VBox container;
    public Text textHolder = new Text();
    public TextArea textArea;
    public Button saveBtn;

    /* Constructor */
    public TextInput(String promptText, String btnText) {
        // Init
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("components/text-input.fxml"));
        getStylesheets().add(Objects.requireNonNull(App.class.getResource("styles/text-input.css")).toExternalForm());
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
            textArea.setPromptText(promptText);
            saveBtn.setText(btnText);

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    /* Instance Methods */
    private void hideNode(Node node) {
        node.setStyle("-fx-min-width: 0; -fx-pref-width: 0;-fx-max-width: 0 ;-fx-min-height: 0;-fx-pref-height: 0;-fx-max-height: 0");
        node.setVisible(true);
    }

    private void showNode(Node node) {
        node.setStyle("");
        node.setVisible(true);
    }


    /* FXML Methods */
    // Initialization
    @FXML
    public void initialize() {
        HoverAnimation.setAnimation(saveBtn, HoverAnimation.Mode.Hue, -0.1);
        hideAndShowBtn();
        expandableTextArea();
    }

    private void hideAndShowBtn() {
        hideNode(saveBtn);
        textArea.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (newPropertyValue) {
                    showNode(saveBtn);
                } else {
//                    hideNode(saveBtn);
                    container.requestFocus();
                }
            }
        });
    }

    private void expandableTextArea() {
        textArea.setPrefSize(280, 40);
        textArea.setWrapText(true);
        textHolder.textProperty().bind(textArea.textProperty());
        textHolder.layoutBoundsProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {
                if (oldHeight != newValue.getHeight()) {
                    oldHeight = newValue.getHeight();
                    textArea.setPrefHeight(textHolder.getLayoutBounds().getHeight() + (oldHeight + 65) / 2); // const value for paddings
                }
            }
        });
        textHolder.setWrappingWidth(textArea.getWidth() - 10);
    }
}
