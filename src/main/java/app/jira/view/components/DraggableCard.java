package app.jira.view.components;

import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.Collections;

public class DraggableCard extends Draggable {
    /* Instance Fields */
    private final Pane sceneRoot;
    private final Card card;
    private final Shadow shadow;
    private double scrollVSpeed;
    private double scrollHSpeed;
    private int reOrderingDelay = 0;

    /* Constructor */
    public DraggableCard(Card card, Pane sceneRoot) {
        this.card = card;
        this.sceneRoot = sceneRoot;
        this.shadow = new Shadow(card.getList());

        addGesture(card);

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

                shadow.getList().getScrollPane().setVvalue(shadow.getList().getScrollPane().getVvalue() + scrollVSpeed);
                card.getList().getBoard().getScrollPane().setHvalue(card.getList().getBoard().getScrollPane().getHvalue() + scrollHSpeed);

                lastUpdate = now;
            }
        };
    }

    /* Instance Methods */
    private void moveCardVertically(Point2D localPoint, Node thisNode) {
        double y, h;
        VBox vBox = shadow.getList().getTasks();

        // Move Shadow According To The Snapshot's Position
        if (vBox.getChildren().contains(shadow) && reOrderingDelay == 3) {
            for (Node child : vBox.getChildren()) {
                y = child.localToScene(child.getBoundsInLocal()).getMinY();
                h = child.localToScene(child.getBoundsInLocal()).getHeight();
                if (y - h / 4 < localPoint.getY() && localPoint.getY() < y + h && child != thisNode) {
                    ObservableList<Node> workingCollection = FXCollections.observableArrayList(vBox.getChildren());
                    Collections.swap(workingCollection, vBox.getChildren().indexOf(child), vBox.getChildren().indexOf(shadow));
                    vBox.getChildren().setAll(workingCollection);
                    break;
                }
            }
        }
    }

    private void moveCardHorizontally(Point2D localPoint) {
        double x, w;
        List list;

        // Move Shadow According To The Snapshot's Position
        if (shadow.getList() != null && reOrderingDelay == 3) {
            for (Node hoveredPane : card.getList().getBoard().listsPane.getChildren()) {
                list = (List) hoveredPane;
                x = hoveredPane.localToScene(hoveredPane.getBoundsInLocal()).getMinX();
                w = hoveredPane.localToScene(hoveredPane.getBoundsInLocal()).getWidth();

                if (x < localPoint.getX() && localPoint.getX() < x + w) {
                    if (shadow.getList() == list) break;
                    shadow.getList().getTasks().getChildren().remove(shadow);
                    shadow.setList(list);
                    shadow.getList().getTasks().getChildren().add(shadow);
                    break;
                }
            }
        }
    }

    @Override
    protected void addGesture(Node node) {
        super.addGesture(node);

        node.setOnMouseDragged(e -> {
            // First Time Encounter Dragged
            if (dragImageView == null) {
                // Create Snapshot From Node And Add It To Root Scene
                createSnapshot(node);
                sceneRoot.getChildren().add(dragImageView);

                // Set Shadow Size According To Node Size
                shadow.setPrefWidth(node.getBoundsInParent().getWidth());
                shadow.setPrefHeight(node.getBoundsInParent().getHeight());

                // Replace Node With Shadow Of It
                hideNode(node);
                card.getList().getTasks().getChildren().add(card.getList().getTasks().getChildren().indexOf(node) + 1, shadow);
            }

            // Relocate Snapshot
            Point2D localPoint = sceneRoot.sceneToLocal(new Point2D(e.getSceneX(), e.getSceneY()));
            dragImageView.relocate(
                    (int) (localPoint.getX() - dragImageView.getBoundsInLocal().getWidth() / 2),
                    (int) (localPoint.getY())
            );

            // Move Shadow
            moveCardHorizontally(localPoint);
            moveCardVertically(localPoint, node);

            // Scroll
            scrollVSpeed = scrollVertically(localPoint, shadow.getList().getScrollPane());
            scrollHSpeed = scrollHorizontally(localPoint, card.getList().getBoard().getScrollPane());

            if (reOrderingDelay-- == 0) reOrderingDelay = 3;

            // Consume Event
//            e.consume();
        });


        node.setOnMouseReleased(e -> {
            timer.stop();
            if (shadow.getList() != null) {
                card.getList().getTasks().getChildren().remove(node);
                shadow.getList().getTasks().getChildren().remove(shadow);
                shadow.getList().getTasks().getChildren().add(card);
                card.setList(shadow.getList());
                showNode(node);
            }
            ((Card) node).clickAction(e);

            node.setCursor(Cursor.DEFAULT);
            sceneRoot.getChildren().remove(dragImageView);
            dragImageView = null;
        });
    }
}
