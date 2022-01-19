package app.jira.view.components;

import javafx.animation.AnimationTimer;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Draggable {
    /* Instance Fields */
    protected AnimationTimer timer;
    protected ImageView dragImageView;

    /* Constructor */
    protected Draggable() {
    }

    /* Protected Methods */
    protected double scrollVertically(Point2D localPoint, ScrollPane verticalScroll) {
        double top, bottom, scrollVSpeed;

        Bounds bound = verticalScroll.localToScene(verticalScroll.getBoundsInLocal());
        top = bound.getCenterY() - verticalScroll.getHeight() / 2;
        bottom = bound.getCenterY() + verticalScroll.getHeight() / 2;
        scrollVSpeed = 0;

        if (localPoint.getY() < top + 100)
            scrollVSpeed = (localPoint.getY() - (top + 100)) / 10000;
        else if (localPoint.getY() > bottom - 100)
            scrollVSpeed = (localPoint.getY() - (bottom - 100)) / 10000;
        return scrollVSpeed;
    }

    protected double scrollHorizontally(Point2D localPoint, ScrollPane horizontalScroll) {
        double right, left, scrollHSpeed;

        Bounds bound = horizontalScroll.localToScene(horizontalScroll.getBoundsInLocal());
        left = bound.getCenterX() - horizontalScroll.getWidth() / 2;
        right = bound.getCenterX() + horizontalScroll.getWidth() / 2;
        scrollHSpeed = 0;

        if (localPoint.getX() < left + 100)
            scrollHSpeed = (localPoint.getX() - (left + 100)) / 20000;
        else if (localPoint.getX() > right - 100)
            scrollHSpeed = (localPoint.getX() - (right - 100)) / 20000;
        return scrollHSpeed;
    }

    protected void createSnapshot(Node node) {
        // Create Snapshot From Node And Add It To Root Scene
        dragImageView = new ImageView();
        SnapshotParameters snapParams = new SnapshotParameters();
        snapParams.setFill(Color.TRANSPARENT);
        dragImageView.setImage(node.snapshot(snapParams, null));
        dragImageView.setRotate(5);
    }

    protected void hideNode(Node node) {
        node.setStyle("-fx-min-width: 0; -fx-pref-width: 0;-fx-max-width: 0 ;-fx-min-height: 0;-fx-pref-height: 0;-fx-max-height: 0");
        node.setVisible(false);
    }

    protected void showNode(Node node) {
        node.setStyle("");
        node.setVisible(true);
    }

    protected void addGesture(final Node node) {
        node.setOnMouseEntered(e -> node.setCursor(Cursor.HAND));
        node.setOnMousePressed(e -> {
            node.setCursor(Cursor.CLOSED_HAND);
            timer.start();
        });


        node.setOnDragDetected(e -> {
            // Create Snapshot From Node And Add It To Root Scene
            if (dragImageView != null) dragImageView.startFullDrag();
        });

    }
}
