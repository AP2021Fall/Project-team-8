package app.jira.view.components;

import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.Collections;

public class DraggableList extends Draggable {
    /* Instance Fields */
    private final Pane sceneRoot;
    private final Label shadow;
    private final List list;
    private int reOrderingDelay = 0;
    private double scrollSpeed;

    /* Constructor */
    public DraggableList(List list, Pane sceneRoot) {
        this.sceneRoot = sceneRoot;
        this.list = list;
        this.shadow = new Label();
        this.shadow.setStyle("-fx-background-color: rgba(0,0,0,0.11);-fx-background-radius: 3px;");

        addGesture(list);

        // Init Timer
        this.timer = new AnimationTimer() {

            private long lastUpdate = -1;

            @Override
            public void start() {
                super.start();
            }

            @Override
            public void handle(long now) {
                if (lastUpdate < 0) {
                    lastUpdate = now;
                    return;
                }

                list.getBoard().getScrollPane().setHvalue(list.getBoard().getScrollPane().getHvalue() + scrollSpeed);

                lastUpdate = now;
            }
        };
    }

    /* Instance Methods */
    private void moveListHorizontally(Point2D localPoint) {
        double x, w;
        HBox hBox = list.getBoard().getListsPane();
        scrollSpeed = scrollHorizontally(localPoint, list.getBoard().getScrollPane());

        // Move Shadow According To The Snapshot's Position
        if (hBox.getChildren().contains(shadow) && reOrderingDelay == 3) {
            for (Node child : hBox.getChildren()) {
                if (hBox.getChildren().indexOf(child) == hBox.getChildren().size() - 1) break;
                x = child.localToScene(child.getBoundsInLocal()).getMinX();
                w = child.localToScene(child.getBoundsInLocal()).getWidth();

                if (x < localPoint.getX() && localPoint.getX() < x + w) {
                    ObservableList<Node> workingCollection = FXCollections.observableArrayList(hBox.getChildren());
                    Collections.swap(workingCollection, hBox.getChildren().indexOf(child), hBox.getChildren().indexOf(shadow));
                    hBox.getChildren().setAll(workingCollection);
                    break;
                }
            }
        }
    }

    @Override
    protected void addGesture(Node node) {
        super.addGesture(node);
        node.setOnMouseDragged(e -> {
            HBox hBox;

            // First Time Encounter Dragged
            if (dragImageView == null) {
                // Create Snapshot From Node And Add It To Root Scene
                dragImageView = new ImageView();
                SnapshotParameters snapParams = new SnapshotParameters();
                snapParams.setFill(Color.TRANSPARENT);
                dragImageView.setImage(node.snapshot(snapParams, null));
                dragImageView.setRotate(5);
                sceneRoot.getChildren().add(dragImageView);

                // Set Shadow Size According To Node Size
                shadow.setPrefWidth(node.getBoundsInParent().getWidth());
                shadow.setPrefHeight(node.getBoundsInParent().getHeight());

                // Replace Node With Shadow Of It
                node.setStyle("-fx-min-width: 0; -fx-pref-width: 0;-fx-max-width: 0 ;-fx-min-height: 0;-fx-pref-height: 0;-fx-max-height: 0");
                node.setVisible(false);
                hBox = list.getBoard().getListsPane();
                hBox.getChildren().add(hBox.getChildren().indexOf(node) + 1, shadow);
            }

            // Relocate Snapshot
            Point2D localPoint = sceneRoot.sceneToLocal(new Point2D(e.getSceneX(), e.getSceneY()));
            dragImageView.relocate(
                    (int) (localPoint.getX() - dragImageView.getBoundsInLocal().getWidth() / 2),
                    (int) (localPoint.getY())
            );

            // Move Shadow
            moveListHorizontally(localPoint);

            if (reOrderingDelay-- == 0) reOrderingDelay = 3;

            // Consume Event
//            e.consume();
        });

        node.setOnMouseReleased(e -> {
            HBox hBox = list.getBoard().getListsPane();
            timer.stop();


            if (hBox.getChildren().contains(shadow)) {
                hBox.getChildren().remove(node);
                hBox.getChildren().add(hBox.getChildren().indexOf(shadow), node);
                hBox.getChildren().remove(shadow);
                node.setStyle("");
                node.setVisible(true);
            }
            list.changeOrder(hBox.getChildren().indexOf(node));
            node.setCursor(Cursor.DEFAULT);
            sceneRoot.getChildren().remove(dragImageView);
            dragImageView = null;
        });
    }

}
