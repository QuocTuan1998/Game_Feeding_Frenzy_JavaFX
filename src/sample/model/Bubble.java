package sample.model;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.util.Duration;
import sample.Game;

public class Bubble extends Sphere {
    private PhongMaterial phongMaterial;
    private TranslateTransition translateTransition;
    private boolean alive = false;

    public Bubble(){
        super();
    }

    public Bubble(double radius, double x, double y, double z, String type){
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.setTranslateZ(z);
        this.setRadius(radius);
        phongMaterial = new PhongMaterial();
        if (type.equals("coin")) {
            phongMaterial.setDiffuseMap(new Image("sample/src/gold.jpg"));
            phongMaterial.setSelfIlluminationMap(new Image("sample/src/gold.jpg"));
        } else if (type.equals("buble")) {
            phongMaterial.setDiffuseColor(Color.web("#b5bfcc6e"));
        }

        this.setMaterial(phongMaterial);

    }

    public void moveUp(Node node, double x){
        translateTransition = new TranslateTransition(Duration.seconds(x), node);
        translateTransition.setByY(-1200);
        translateTransition.setCycleCount(10);
        translateTransition.play();
    }

}
