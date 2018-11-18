package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.model.AnimationObject;
import sample.model.Bubble;
import sample.model.GameObject;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game extends Application {

    private static final int WIDTH = 1900;
    private static final int HEIGHT = 770;
    private Scene scene;

    private double mousePosition = 0;
    private GameObject player;
    private GameObject smallFish, normalFish, bigFish, coin;
    private Bubble bubble;

    private double point = 0;
    private double pointLv1 = 5;
    private double pointLv2 = 10;

    private double max = 20;
    private int heart = 3;

    private List<GameObject> listObject = new ArrayList<>();

    private AnimationTimer timer;
    private AnimationObject animationObject;

    private Random random = new Random();

    private Pane root;
    private ProgressBar bar;
    private Text textHeart;

//    private List<Node> listBarrier; /* là danh sách vật cản, khi vật cản là boom hoặc cá được tạo thì
//     add nó vào đây luôn đểmỗi giây sẽ dùng 1 vòng lặp để check
//    */


    private Parent initGamePlay() {
        // TODO: 11/3/2018 hàm xử lí khởi tạo gamePlay, khởi tạo cá chính
        root = new Pane();
        root.setPrefSize(WIDTH, HEIGHT);

        ImageView imageView = new ImageView(new Image("sample/src/bg.jpg"));
        imageView.setFitHeight(HEIGHT);
        imageView.setFitWidth(WIDTH);
        root.getChildren().add(imageView);

        HBox hBox = new HBox();
        hBox.setTranslateX(1000);
        hBox.setTranslateY(20);
        bar = new ProgressBar(0);
        bar.setPrefSize(300, 20);
        hBox.getChildren().add(bar);

        VBox vBox = new VBox();
        vBox.setTranslateX(900);
        vBox.setTranslateY(bar.getPrefHeight());
        vBox.setAlignment(Pos.CENTER);

        ImageView imgHeart = new ImageView(new Image("sample/src/heart.png"));
        imgHeart.setFitHeight(50);
        imgHeart.setFitWidth(50);

        textHeart = new Text();
        textHeart.setFill(Color.ORANGE);
        textHeart.setFont(Font.font ("Verdana", 20));
        vBox.getChildren().addAll(imgHeart, textHeart);
        vBox.setAlignment(Pos.CENTER);
        root.getChildren().add(vBox);
        root.getChildren().add(hBox);


        player = new GameObject();

        player.drawFish(root, "Angel0.3ds", "sample/src/AngelT.bmp",600, 600, 100, 100, Color.AQUA, "player");

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                onUpdate();
            }
        };
        timer.start();

        return root;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        scene = new Scene(initGamePlay());

        scene.getStylesheets().add("sample/style.css");

        if (player.isAlive()) {
            scene.setOnMouseMoved(event -> {
                scene.setCursor(Cursor.NONE);
                if (event.getX() < mousePosition){
                    player.setRotate(true);
                }else{
                    player.setRotate(false);
                }
                player.setPosition(event.getX(), event.getY());
                mousePosition = event.getX();
            });
        }
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
    }

    private void onUpdate() {
        initSprite();
        checkPoint();
        handleCollision();

        if (player.isDead()) { // lose || respawn
            handlePlayerDied();
        }

        textHeart.setText("X" + heart);
        if (heart <= 0) {
            heart = 0;
            textHeart.setText("X0");
        }
    }

    private void initSprite() {
        // CREATE SMALL FISH
        if (Math.random() < 0.008) {
            addSmallFish();
        }
        // CREATE NORMAL FISH
        if (Math.random() < 0.006) {
            addNormalFish();
        }
        // CREATE BIG FISH
        if (Math.random() < 0.003) {
//            addBigFish();
        }
        // CREATE BUBLE
        if (Math.random() < 0.002) {
            addBuble();
        }
        // CREATE COIN
        if (Math.random() < 0.0005) {
            addCoin();
        }

    }

    private void addBuble() {

        bubble = new Bubble(random.nextInt(25 - 10) + 10,
                random.nextInt(WIDTH - 30) + 30,
                1000,
                100,
                "buble");

        bubble.moveUp(bubble, random.nextInt(15 - 5) + 5);
        root.getChildren().add(bubble);
    }

    private void addCoin() {
        coin = new GameObject();
        coin.drawCoin(root,
                random.nextInt(15 - 10) + 10,
                random.nextInt(WIDTH - 30) + 30,
                1000,
                100,
                "coin");
        coin.moveUp(coin, random.nextInt(10 - 5) + 5);
        System.out.println(listObject.size());
        listObject.add(coin);
    }

    private void addBigFish() {
//        bigFish = new GameObject();
//        bigFish.drawFish(root,
//                "ORCA.3DS", "sample/src/ORCA.TIF",
//                -200,
//                random.nextInt(HEIGHT),
//                150,
//                100,
//                null);
//
//        bigFish.moveRight(bigFish, random.nextInt(25 - 20) + 20);
//        bigFish.getNode().setRotate(50);
//        listBigFish.add(bigFish);
    }

    private void addNormalFish() {
        normalFish = new GameObject();
        normalFish.drawFish(root,
                "Blackbass0.3ds", "sample/src/BlackbT.bmp",
                random.nextBoolean() ? 2000 : -200,
//                500,
                random.nextInt(HEIGHT),
                150,
                300,
                null,
                "normal");

        if (normalFish.getNode().getTranslateX() == -200) {
            normalFish.moveRight(normalFish, random.nextInt(20 - 15) + 15);
        } else if (normalFish.getNode().getTranslateX() == 2000){
            normalFish.moveLeft(normalFish, random.nextInt(20 - 15) + 15);
        }
        System.out.println(listObject.size());
        listObject.add(normalFish);
    }

    private void addSmallFish() {
        smallFish = new GameObject();
        smallFish.drawFish(root,
                "BrownTrout0.3ds", "sample/src/BrownTT.bmp",
                random.nextBoolean() ? 2000 : -200,
                random.nextInt(HEIGHT),
                100,
                150,
                null,
                "small");

        if (smallFish.getNode().getTranslateX() == -200) {
            smallFish.moveRight(smallFish, random.nextInt(20 - 15) + 15);
        } else if (smallFish.getNode().getTranslateX() == 2000){
            smallFish.moveLeft(smallFish, random.nextInt(20 - 15) + 15);
        }
        listObject.add(smallFish);
    }

    private void handleCollision() {
        // TODO: 11/3/2018 Xử lí va chạm khi gamePlay va chạm với các đối tượng khác
        for (GameObject normal : listObject) { //normal -- small
            for (GameObject small : listObject) {
                if (isColliding(normal.getNode(), small.getNode()) &&
                        isNormal(normal) &&
                        isSmall(small)) {
                    hanldeDied(small);
                }
            }
        }

        for (GameObject object : listObject) { // player -- coin
            if (isColliding(player.getNode(), object.getNode()) && isCoin(object)) {
                point += 2;
                hanldeDied(object);
            }

            if (isColliding(player.getNode(), object.getNode()) && isSmall(object)) { // player -- small
                point++;
                hanldeDied(object);
            }
        }


        if (point > pointLv1) {
            for (GameObject normal : listObject) { // normal fish -- player lv2
                if (isColliding(player.getNode(), normal.getNode()) && isNormal(normal)) {
                    point++;
                    hanldeDied(normal);
                }
            }
        } else {
            for (GameObject normal : listObject) {
                if (isColliding(player.getNode(), normal.getNode()) && isNormal(normal)) { // normal fish -- player lv1
                    handlePlayerDied();
                    if (heart != 0) {
                        handleRespawn();
                    }
                }

            }
        }

        //remove element if element is dead
        listObject.removeIf(GameObject::isDead);
    }

    private boolean isNormal(GameObject object) {
        return object.getType().equals("normal");
    }

    private boolean isSmall(GameObject object) {
        return object.getType().equals("small");
    }

    private boolean isCoin(GameObject object) {
        return object.getType().equals("coin");
    }

    private void handleRespawn() {
        timer.start();
        scene.setCursor(Cursor.NONE);
        player.drawFish(root, "Angel0.3ds", "sample/src/AngelT.bmp", 600, 600, 100, 100, Color.AQUA, "player");
        moveCursor((int) player.getNode().getTranslateX(), (int) player.getNode().getTranslateY());
    }

    private void checkPoint() {
        // TODO: 11/13/2018 Hàm kiểm tra xem cá của chúng ta thuộc level bao nhiêu
        bar.setProgress(point / max);
        if (point == pointLv1) { // Level Up
            hanldeLevelUp();
        }

        if (point == pointLv2) {
            hanldeLevelUp();
        }

        if (point == max) {
            timer.stop();
            System.out.println("winnn");
        }

        if (point <= 0) {
            point = 0;
        }
    }

    private boolean isColliding(Node who, Node orther) { // return true if colliding
        return who.getBoundsInParent().getMaxX() >= orther.getBoundsInParent().getMinX() &&
                who.getBoundsInParent().getMinX() <= orther.getBoundsInParent().getMaxX() &&
                who.getBoundsInParent().getMaxY() >= orther.getBoundsInParent().getMinY() &&
                who.getBoundsInParent().getMinY() <= orther.getBoundsInParent().getMaxY();
    }

    private void hanldeDied(GameObject who){
        // TODO: 11/3/2018 Hàm xử lí khi gamePlay chết (va chạm với cá lớn hơn, hoặc va chạm với bom)
        who.setAlive(false);
        root.getChildren().remove(who.getNode());
    }

    private void handlePlayerDied() {
        timer.stop();
        point = 0;
        heart --;
        player.setAlive(false);
        root.getChildren().remove(player.getNode());
    }

    private void hanldeLevelUp(){
        // TODO: 11/3/2018 hàm xử lí khi gamePlay được thăng cấp Level
        point ++;
        heart ++;
        player.getNode().setScaleX(player.getNode().getScaleX() + 50);
        player.getNode().setScaleY(player.getNode().getScaleY() + 50);
        player.getNode().setScaleZ(player.getNode().getScaleZ() + 50);
    }

    private void moveCursor(int screenX, int screenY) { // set mouse position for respawn
        Platform.runLater(() -> {
            try {
                Robot robot = new Robot();
                robot.mouseMove(screenX, screenY);
            } catch (AWTException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
