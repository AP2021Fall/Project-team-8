package app.jira.view.components;

import app.jira.App;
import javafx.animation.FadeTransition;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.util.Objects;

public class Alert extends Label {
    /* Instance Fields */
    private FadeTransition ft;

    /* Constructor */
    public Alert() {
        getStylesheets().add(Objects.requireNonNull(App.class.getResource("styles/alert.css")).toExternalForm());
    }

    // Action
    public void alert(String text) {
        Alert alertS = this;
        this.setManaged(true);
        this.setVisible(true);
        ft = new FadeTransition(Duration.millis(500), this);
        ft.setFromValue(0);
        ft.setToValue(1);
        this.setText(text);
        ft.setOnFinished(actionEvent -> {
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            ft = new FadeTransition(Duration.millis(1000), alertS);
                            ft.setFromValue(1);
                            ft.setToValue(0);
                            ft.setOnFinished(actionEvent1 -> {
                                alertS.setManaged(false);
                                alertS.setVisible(false);
                            });
                            ft.play();
                        }
                    },
                    2000
            );
        });
        ft.play();
    }
}
