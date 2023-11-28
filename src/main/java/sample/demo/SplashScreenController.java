package sample.demo;

import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.shape.*;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
public class SplashScreenController {
    @FXML private Rectangle splashRectangle;
    @FXML private Label splashText;
    @FXML private ImageView splashImage;

    protected static void transition() {
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(3), event -> Starting.window.setScene(sample.demo.Starting.mainMenuNoAccessScene))
        );

        timeline.play();
    }
}




