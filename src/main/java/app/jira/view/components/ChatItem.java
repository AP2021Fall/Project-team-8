package app.jira.view.components;


import app.jira.App;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Objects;

public class ChatItem extends GridPane {
    /* FXML Fields */
    @FXML
    public TextArea chatItem;
    public VBox profile;
    public Text textHolder = new Text();
    public HBox info ;
    public ImageView profileImage ;
    public Label name;
    public Label time;
    /* Constructor */
    public ChatItem() {
        // Init
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("components/chat-item.fxml"));
        getStylesheets().add(Objects.requireNonNull(App.class.getResource("styles/chat-item.css")).toExternalForm());
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    /* Instance Methods */
    private void hideNode(Node node) {
        node.setStyle("-fx-min-width: 0; -fx-pref-width: 0;-fx-max-width: 0 ;-fx-min-height: 0;-fx-pref-height: 0;-fx-max-height: 0");
        node.setVisible(false);
    }

    private void showNode(Node node) {
        node.setStyle("");
        node.setVisible(true);
    }


    /* FXML Methods */
    // Initialization
    @FXML
    public void initialize() {
        //profile.getChildren().add(new ProfilePicture(""));


        // Set Height For Text Area
        chatItem.setPrefSize(200, 40);
        chatItem.setWrapText(true);
        textHolder.textProperty().bind(chatItem.textProperty());
        textHolder.layoutBoundsProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {
                if (0 != newValue.getHeight()) {
                    chatItem.setPrefHeight(textHolder.getLayoutBounds().getHeight() + (newValue.getHeight() + 65) / 2); // const value for paddings
                }
            }
        });
        textHolder.setWrappingWidth(chatItem.getWidth() - 10);
    }
}
