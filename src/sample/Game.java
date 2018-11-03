package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.util.List;

public class Game extends Application {

    private List<Node> listBarrier; /* là danh sách vật cản, khi vật cản là boom hoặc cá được tạo thì
     add nó vào đây luôn đểmỗi giây sẽ dùng 1 vòng lặp để check
    */

    @Override
    public void start(Stage primaryStage) throws Exception{
        // TODO: 11/3/2018 Tạo giao diện và tạo mới một số cá

        initGamePlay();
        initBoom();
        initFish();

        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                checkPossition();
            }
        };
        animationTimer.start();
    }

    private void initGamePlay() {
        // TODO: 11/3/2018 hàm xử lí khởi tạo gamePlay, khởi tạo cá chính
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
    }

    private void checkPossition() {
        // TODO: 11/3/2018 Check vị trí gamePlay và các cá khác để xử lí va chạm
    }

    private void handleCollision(){
        // TODO: 11/3/2018 Xử lí va chạm khi gamePlay va chạm với các đối tượng khác
    }

    private void hanldeDied(){
        // TODO: 11/3/2018 Hàm xử lí khi gamePlay chết (va chạm với cá lớn hơn, hoặc va chạm với bom)
    }

    private void hanldeLevelUp(){
        // TODO: 11/3/2018 hàm xử lí khi gamePlay được thăng cấp Level
    }


    public static void main(String[] args) {
        launch(args);
    }
}
