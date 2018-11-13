package sample.model;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.util.Duration;
import sample.Game;

public class Bubble extends Sphere {
    private PhongMaterial phongMaterial;
    private TranslateTransition translateTransition;

    public Bubble(){
        super();
    }

    public Bubble(double radius, double x, double y, double z){
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.setTranslateZ(z);
        this.setRadius(radius);
        phongMaterial = new PhongMaterial();
//        phongMaterial.setDiffuseMap(new Image("sample/src/bb2.png"));
        phongMaterial.setDiffuseColor(Color.web("#b5bfcc6e"));
        this.setMaterial(phongMaterial);

    }

    public void move(Node node, double x){
        translateTransition = new TranslateTransition(Duration.seconds(x), node);
        translateTransition.setByY(-Game.HEIGHT);
        translateTransition.setCycleCount(10);
        translateTransition.play();
    }

}
