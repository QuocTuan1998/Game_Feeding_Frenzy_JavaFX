package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game extends Application {
    public static final int WIDTH = 900;
    public static final int HEIGHT = 500;
    private double mousePosition = 0;

    private GameObject player;
    private GameObject smallFish, normalFish, bigFish;
    private Bubble bubble;

    private double point = 0;
    private int heart = 3;

    private List<GameObject> listSmallFish= new ArrayList<>();
    private List<GameObject> listNormalFish= new ArrayList<>();
    private List<GameObject> listBigFish= new ArrayList<>();

    private AnimationTimer timer;

    private Random random = new Random();

    private Pane root;
    private ProgressBar bar;

//    private List<Node> listBarrier; /* là danh sách vật cản, khi vật cản là boom hoặc cá được tạo thì
//     add nó vào đây luôn đểmỗi giây sẽ dùng 1 vòng lặp để check
//    */

    @Override
    public void start(Stage primaryStage) throws Exception{

        Scene scene = new Scene(initGamePlay());

        scene.getStylesheets().add("sample/style.css");

        scene.setOnMouseMoved(event -> {
            if (event.getX() < mousePosition){
                player.setRotate(true);
            }else{
                player.setRotate(false);
            }
            player.setPosition(event.getX(), event.getY());
            mousePosition = event.getX();
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Parent initGamePlay() {
        // TODO: 11/3/2018 hàm xử lí khởi tạo gamePlay, khởi tạo cá chính
        root = new Pane();
        root.setPrefSize(WIDTH, HEIGHT);

        ImageView imageView = new ImageView(new Image("sample/src/bg.jpg"));
        imageView.setFitHeight(HEIGHT);
        imageView.setFitWidth(WIDTH);
        root.getChildren().add(imageView);

        bar = new ProgressBar(0);
        bar.setPrefSize(300, 20);
        bar.setTranslateX(WIDTH - bar.getPrefWidth() - 10);
        bar.setTranslateY(bar.getPrefHeight());
        root.getChildren().add(bar);

        player = new GameObject();
        player.drawFish(root, "Angel0.3ds", "sample/src/AngelT.bmp",600, 600, 100, 100, Color.AQUA);

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                onUpdate();
//                if (player.isDead()){
//                    showDialog("you lose!");
//                    stop();
//                }
            }
        };
        timer.start();

        return root;
    }

    private void onUpdate() {
        initFish();
    }

    private void initBoom() {
        // TODO: 11/3/2018 hàm khởi tạo bom
    }

    private void initFish() {
        // TODO: 11/3/2018 hàm khởi tạo một số con cá (10 cá)
        /*
        * ví dụ: cá sẽ có 3 level: nhỏ, vừa, lớn
        * lúc đầu sẽ tạo 10 con cá với nhỏ và vừa
        * ví dụ gamePlay đã đạt 5 cá thì tạo thêm 10 con cá nữa,
        * sau đó nếu gamePlay ví dụ đạt 7 điểm thì sẽ tạo thêm cá, và có thêm cá lớn
        * Mục đích để giảm lag khi khởi tạo nhiều đối tượng và animation
        * */
        bar.setProgress(point / 50);
        // CREATE SMALL FISH
        if (Math.random() < 0.008) {
            addSmallFish();
        }
        // CREATE NORMAL FISH
        if (Math.random() < 0.004) {
            addNormalFish();
        }
        // CREATE BIG FISH
        if (Math.random() < 0.003) {
//            addBigFish();
        }
        // CREATE BUBLE
        if (Math.random() < 0.001) {
            addBuble();
        }
        handleCollision();
    }


    private void addBuble() {
        bubble = new Bubble(random.nextInt(25 - 10) + 10,
                random.nextInt(WIDTH - 30),
                500,
                100);
        bubble.move(bubble, random.nextInt(15 - 5) + 5);
        root.getChildren().add(bubble);
    }

    private void addBigFish() {
        bigFish = new GameObject();
//        bigFish.drawFish(root,
//                "ORCA.3DS", "sample/src/ORCA.TIF",
//                -200,
//                random.nextInt(HEIGHT),
//                150,
//                100,
//                null);
//        bigFish.objImporter(root);
        bigFish.move(bigFish.getNode(), random.nextInt(25 - 20) + 20);
        bigFish.getNode().setRotate(50);
        listBigFish.add(bigFish);
    }

    private void addNormalFish() {
        normalFish = new GameObject();
        normalFish.drawFish(root,
                "Blackbass0.3ds", "sample/src/BlackbT.bmp",
                -200,
                random.nextInt(HEIGHT),
                150,
                300,
                null);
        normalFish.move(normalFish.getNode(), random.nextInt(25 - 20) + 20);
        listNormalFish.add(normalFish);
    }

    private void addSmallFish() {
        smallFish = new GameObject();
        smallFish.drawFish(root,
                "BrownTrout0.3ds", "sample/src/BrownTT.bmp",
                -200,
                random.nextInt(HEIGHT),
                100,
                150,
                null);
        smallFish.move(smallFish.getNode(), random.nextInt(15 - 10) + 10);
        listSmallFish.add(smallFish);
    }


    private void checkPossition() {
        // TODO: 11/3/2018 Check vị trí gamePlay và các cá khác để xử lí va chạm
    }

    private void handleCollision(){
        // TODO: 11/3/2018 Xử lí va chạm khi gamePlay va chạm với các đối tượng khác

        for (GameObject small : listSmallFish) {
            if (player.isColliding(player.getNode(), small.getNode())) {
                small.setAlive(false);
                point ++;
                root.getChildren().remove(small.getNode());
            }
        }

        if (point == 15) { // Level Up
            hanldeLevelUp();
            point ++;
        }

        if (point >= 15) {
            for (GameObject normal : listNormalFish) {
                if (player.isColliding(player.getNode(), normal.getNode())) {
                    normal.setAlive(false);
                    point ++;
                    root.getChildren().remove(normal.getNode());
                }
            }
        } else {
            for (GameObject normal : listNormalFish) {
                if (player.isColliding(player.getNode(), normal.getNode())) { // fix this
//                    player.setAlive(false);
//                    point --;
//                    heart --;
//                    System.out.println("heart : " + heart);
//                    System.out.println("point : " + point);
                }
            }
        }

        if (point == 30) {
            hanldeLevelUp();
            point ++;
        }

        for (GameObject normal : listNormalFish) {
            for (GameObject small : listSmallFish) {
                if (normal.isColliding(normal.getNode(), small.getNode())) {
                    small.setAlive(false);
                    root.getChildren().remove(small.getNode());
                }
            }
        }
        if (point < 0) {
            point = 0;
        }
        if (heart < 0) {
            heart = 0;
        }
        if (player.isDead()) {
            hanldeDied();
        }

        listSmallFish.removeIf(GameObject::isDead);
        listNormalFish.removeIf(GameObject::isDead);
        listBigFish.removeIf(GameObject::isDead);

    }

    private void hanldeDied(){
        // TODO: 11/3/2018 Hàm xử lí khi gamePlay chết (va chạm với cá lớn hơn, hoặc va chạm với bom)

    }

    private void hanldeLevelUp(){
        // TODO: 11/3/2018 hàm xử lí khi gamePlay được thăng cấp Level

        player.getNode().setScaleX(player.getNode().getScaleX() + 50);
        player.getNode().setScaleY(player.getNode().getScaleY() + 50);
        player.getNode().setScaleZ(player.getNode().getScaleZ() + 50);
        heart ++;

    }


    public static void main(String[] args) {
        launch(args);
    }
}
