package sample;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class AnimationObject {

    public void rotate(Node node){
        RotateTransition obj = new RotateTransition(Duration.millis(200), node);
        obj.setAxis(Rotate.Y_AXIS);
        obj.setFromAngle(-3);
        obj.setToAngle(3);
        obj.setAutoReverse(true);
        obj.setCycleCount(RotateTransition.INDEFINITE);
        obj.setInterpolator(Interpolator.LINEAR);
        obj.play();
    }

    public void move(Node node, int secound) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(secound), node);
        translateTransition.setByX(2200);
        translateTransition.setAutoReverse(true);
        translateTransition.play();
    }

    public void moveDown(Node node, int sec) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(sec), node);
        translateTransition.setByY(2000);
        translateTransition.setAutoReverse(true);
        translateTransition.play();
    }
}
