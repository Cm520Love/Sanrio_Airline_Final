package GUI;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
public class SplashScreenController {

    protected static void transition() {
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(1), event -> Starting.window.setScene(GUI.Starting.mainMenuNoAccessScene))
        );

        timeline.play();
    }
}




