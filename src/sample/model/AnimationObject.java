package sample.model;

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
        obj.setFromAngle(-5);
        obj.setToAngle(5);
        obj.setAutoReverse(true);
        obj.setCycleCount(RotateTransition.INDEFINITE);
        obj.setInterpolator(Interpolator.LINEAR);
        obj.play();
    }

    public void moveRight(GameObject node, int secound) {

        node.setRotate(false);

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(secound), node.getNode());
        translateTransition.setByX(2300);
        translateTransition.setAutoReverse(true);
        translateTransition.play();
    }

    public void moveLeft(GameObject node, int secound) {
        node.setRotate(true);

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(secound), node.getNode());
        translateTransition.setByX(-1900);
        translateTransition.setAutoReverse(true);
        translateTransition.play();
    }


    public void moveDown(Node node, int sec) {}

    public void moveUp(Node node, int sec) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(sec), node);
        translateTransition.setByY(2000);
        translateTransition.setAutoReverse(true);
        translateTransition.play();
    }
}
