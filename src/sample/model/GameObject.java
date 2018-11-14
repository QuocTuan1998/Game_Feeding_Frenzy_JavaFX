package sample.model;

import com.interactivemesh.jfx.importer.tds.TdsModelImporter;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.transform.Rotate;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class GameObject {
    private Node node;
    private AnimationObject animation = new AnimationObject();
    private Rotate rot2;
    private boolean alive = false;
    private Random random;
    public void drawFish(Pane root,  String name, String url, double x, double y, double z, int scale, Color color){
        alive = true;
        TdsModelImporter model = new TdsModelImporter();
        model.read(this.getClass().getResource("../object/" + name));
        Node[] arr = model.getImport();
        Map<String, PhongMaterial> list = model.getNamedMaterials();

        Iterator<String> it = list.keySet().iterator();
        while (it.hasNext()){
            String key = it.next();
            if (!url.equals("")) {
                list.get(key).setDiffuseMap(new Image(url));
                list.get(key).setSpecularMap(new Image(url));
            }
            if (color != null){
                list.get(key).setDiffuseColor(color);
                list.get(key).setSpecularColor(color);
            }
        }

        node = arr[0];

        node.setTranslateX(x);
        node.setTranslateY(y);
        node.setTranslateZ(z);

        node.setScaleX(scale);
        node.setScaleY(scale);
        node.setScaleZ(scale);
        Rotate rotate = new Rotate(0, Rotate.X_AXIS);
        rot2 = new Rotate(160, Rotate.Z_AXIS);
        node.getTransforms().addAll(rotate, rot2);
        animation.rotate(node);
        root.getChildren().add(node);
    }

    public void drawCoin(Pane root, double x) {
        Bubble coin = new Bubble(50,
                x,
                500,
                100,
                "coin");
        root.getChildren().add(coin);
    }

    public Node getNode() {
        return node;
    }

    public boolean isDead() {
        return !alive;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void moveRight(GameObject node, int sec) {
        animation.moveRight(node, sec);
    }

    public void moveLeft(GameObject node, int sec) {
        animation.moveLeft(node, sec);
    }

    public void setPosition(double x, double y){
        if (node != null) {
            node.setTranslateX(x);
            node.setTranslateY(y);
        }
    }

    public void setRotate(boolean isLeft){
        if (isLeft) {
            rot2.setAngle(10);
        }else{
            rot2.setAngle(190);
        }
    }

}
