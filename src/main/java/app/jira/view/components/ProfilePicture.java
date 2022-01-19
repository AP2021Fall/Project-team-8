package app.jira.view.components;

import app.jira.model.domain.User;
import app.jira.view.Config;
import app.jira.view.entities.Board;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;


public class ProfilePicture extends Label {
    /* Instance Fields */
    private final User user;
    private final Board board;

    /* Constructor */
    public ProfilePicture(User user, Board board) {
        this.user = user;
        this.board = board;
        this.setCursor(Cursor.HAND);
        // Set Profile Picture
        String style = "-fx-background-image: url(" + Config.getProfilePic(user.getId()) + "); -fx-background-position: center center; -fx-background-repeat: no-repeat;-fx-background-size: contain;-fx-background-color: rgb(0,0,0);-fx-background-radius: 50%; -fx-border-radius: 50%;";
        this.setPrefWidth(40);
        this.setPrefHeight(40);
        this.setStyle(style);
        Circle circle = new Circle(this.getPrefWidth() / 2);
        circle.setCenterX(this.getPrefWidth() / 2);
        circle.setCenterY(this.getPrefWidth() / 2);
        this.setClip(circle);
    }
}
