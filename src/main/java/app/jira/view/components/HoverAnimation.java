package app.jira.view.components;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.util.Duration;

public class HoverAnimation extends Node {
    /*  Enums */
    public enum Mode {
        Brightness, Hue, Saturation, Contrast
    }

    /* Static Fields */
    private static final int animationTime = 150;

    /* Static Methods */
    public static void setAnimation(Node node, Mode mode, double fraction) {
        node.setStyle("-fx-cursor: hand");
        if (mode == Mode.Saturation) saturationMode(node, fraction);
        else if (mode == Mode.Brightness) brightnessMode(node, fraction);
        else if (mode == Mode.Contrast) contrastMode(node, fraction);
        else if (mode == Mode.Hue) hueMode(node, fraction);
    }

    private static void contrastMode(Node node, double fraction) {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setContrast(0.0);
        node.setEffect(colorAdjust);
        node.setOnMouseEntered(e -> {
            Timeline fadeInTimeline = new Timeline(
                    new KeyFrame(Duration.seconds(0),
                            new KeyValue(colorAdjust.contrastProperty(), colorAdjust.contrastProperty().getValue(), Interpolator.LINEAR)),
                    new KeyFrame(Duration.millis(150), new KeyValue(colorAdjust.contrastProperty(), fraction, Interpolator.LINEAR)
                    ));
            fadeInTimeline.setCycleCount(1);
            fadeInTimeline.setAutoReverse(false);
            fadeInTimeline.play();
        });

        node.setOnMouseExited(e -> {
            Timeline fadeOutTimeline = new Timeline(
                    new KeyFrame(Duration.seconds(0),
                            new KeyValue(colorAdjust.contrastProperty(), colorAdjust.contrastProperty().getValue(), Interpolator.LINEAR)),
                    new KeyFrame(Duration.millis(animationTime), new KeyValue(colorAdjust.contrastProperty(), 0, Interpolator.LINEAR)
                    ));
            fadeOutTimeline.setCycleCount(1);
            fadeOutTimeline.setAutoReverse(false);
            fadeOutTimeline.play();
        });
    }


    private static void saturationMode(Node node, double fraction) {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setSaturation(0.0);
        node.setEffect(colorAdjust);
        node.setOnMouseEntered(e -> {
            Timeline fadeInTimeline = new Timeline(
                    new KeyFrame(Duration.seconds(0),
                            new KeyValue(colorAdjust.saturationProperty(), colorAdjust.saturationProperty().getValue(), Interpolator.LINEAR)),
                    new KeyFrame(Duration.millis(animationTime), new KeyValue(colorAdjust.saturationProperty(), fraction, Interpolator.LINEAR)
                    ));
            fadeInTimeline.setCycleCount(1);
            fadeInTimeline.setAutoReverse(false);
            fadeInTimeline.play();
        });

        node.setOnMouseExited(e -> {
            Timeline fadeOutTimeline = new Timeline(
                    new KeyFrame(Duration.seconds(0),
                            new KeyValue(colorAdjust.saturationProperty(), colorAdjust.saturationProperty().getValue(), Interpolator.LINEAR)),
                    new KeyFrame(Duration.millis(animationTime), new KeyValue(colorAdjust.saturationProperty(), 0, Interpolator.LINEAR)
                    ));
            fadeOutTimeline.setCycleCount(1);
            fadeOutTimeline.setAutoReverse(false);
            fadeOutTimeline.play();
        });
    }

    private static void brightnessMode(Node node, double fraction) {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(0.0);
        node.setEffect(colorAdjust);
        node.setOnMouseEntered(e -> {
            Timeline fadeInTimeline = new Timeline(
                    new KeyFrame(Duration.seconds(0),
                            new KeyValue(colorAdjust.brightnessProperty(), colorAdjust.brightnessProperty().getValue(), Interpolator.LINEAR)),
                    new KeyFrame(Duration.millis(animationTime), new KeyValue(colorAdjust.brightnessProperty(), fraction, Interpolator.LINEAR)
                    ));
            fadeInTimeline.setCycleCount(1);
            fadeInTimeline.setAutoReverse(false);
            fadeInTimeline.play();
        });

        node.setOnMouseExited(e -> {
            Timeline fadeOutTimeline = new Timeline(
                    new KeyFrame(Duration.seconds(0),
                            new KeyValue(colorAdjust.brightnessProperty(), colorAdjust.brightnessProperty().getValue(), Interpolator.LINEAR)),
                    new KeyFrame(Duration.millis(animationTime), new KeyValue(colorAdjust.brightnessProperty(), 0, Interpolator.LINEAR)
                    ));
            fadeOutTimeline.setCycleCount(1);
            fadeOutTimeline.setAutoReverse(false);
            fadeOutTimeline.play();
        });
    }

    private static void hueMode(Node node, double fraction) {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setHue(0.0);
        node.setEffect(colorAdjust);
        node.setOnMouseEntered(e -> {
            Timeline fadeInTimeline = new Timeline(
                    new KeyFrame(Duration.seconds(0),
                            new KeyValue(colorAdjust.hueProperty(), colorAdjust.hueProperty().getValue(), Interpolator.LINEAR)),
                    new KeyFrame(Duration.millis(animationTime), new KeyValue(colorAdjust.hueProperty(), fraction, Interpolator.LINEAR)
                    ));
            fadeInTimeline.setCycleCount(1);
            fadeInTimeline.setAutoReverse(false);
            fadeInTimeline.play();
        });

        node.setOnMouseExited(e -> {
            Timeline fadeOutTimeline = new Timeline(
                    new KeyFrame(Duration.seconds(0),
                            new KeyValue(colorAdjust.hueProperty(), colorAdjust.hueProperty().getValue(), Interpolator.LINEAR)),
                    new KeyFrame(Duration.millis(animationTime), new KeyValue(colorAdjust.hueProperty(), 0, Interpolator.LINEAR)
                    ));
            fadeOutTimeline.setCycleCount(1);
            fadeOutTimeline.setAutoReverse(false);
            fadeOutTimeline.play();
        });
    }
}
