package GUI;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
public class SplashScreenController {

    protected static void transition() {
        // timeline object accepts a Keyframe object
        Timeline timeline = new Timeline(
            // wait 3 seconds at the splash screen, then transition to the main menu
            new KeyFrame(Duration.seconds(3), event -> Starting.switchScenes("MainMenuNoAccess"))
        );

        timeline.play();
    }
}




