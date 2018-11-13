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

public class GameObject {
    private Node node;
    private AnimationObject animation = new AnimationObject();
    private Rotate rot2;
    private boolean alive = false;
    private boolean fromLeft = false;
    private int type;

    public void drawGamePlay(Pane root, String name, String url, Color color){
        alive = true;
        TdsModelImporter model = new TdsModelImporter();
        model.read(this.getClass().getResource(name));
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

        node.setScaleX(100);
        node.setScaleY(100);
        node.setScaleZ(100);
        Rotate rotate = new Rotate(0, Rotate.X_AXIS);
        rot2 = new Rotate(160, Rotate.Z_AXIS);
        node.getTransforms().addAll(rotate, rot2);
        animation.rotate(node);
        root.getChildren().add(node);
    }

    public void drawFish(Pane root,  String name, String url, double y, double z, int scale, Color color, boolean fromLeft, int type){
        alive = true;
        this.fromLeft = fromLeft;
        this.type = type;
        TdsModelImporter model = new TdsModelImporter();
        model.read(this.getClass().getResource(name));
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

        node.setScaleX(scale);
        node.setScaleY(scale);
        node.setScaleZ(scale);

        node.setTranslateY(y);
        node.setTranslateZ(z);
        if (fromLeft) node.setTranslateX(-200);
        else {
            node.setTranslateX(1000);
        }

        Rotate rotate = new Rotate(0, Rotate.X_AXIS);
        rot2 = new Rotate(160, Rotate.Z_AXIS);
        if (!fromLeft) rot2.setAngle(10);
        node.getTransforms().addAll(rotate, rot2);
        animation.rotate(node);
        root.getChildren().add(node);
    }

    public Node getNode() {
        return node;
    }

    public boolean isDead() {
        return !alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public int getType(){
        return type;
    }

    public void move(Node node, int sec) {
        if (fromLeft)
        animation.moveLeft(node, sec);
        else animation.moveRight(node, sec);
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

    public boolean isColliding(Node who, Node orther) {
        return who.getBoundsInParent().getMaxX() >= orther.getBoundsInParent().getMinX() &&
                who.getBoundsInParent().getMinX() <= orther.getBoundsInParent().getMaxX() &&
                who.getBoundsInParent().getMaxY() >= orther.getBoundsInParent().getMinY() &&
                who.getBoundsInParent().getMinY() <= orther.getBoundsInParent().getMaxY();
    }

}
