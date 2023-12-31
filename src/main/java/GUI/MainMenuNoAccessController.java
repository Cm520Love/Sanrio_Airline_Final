package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.*;

public class MainMenuNoAccessController {
    @FXML private ImageView airplaneBackground;
    @FXML private Label welcomeLabel;
    @FXML private Label promptLabel;
    @FXML private Hyperlink signUpHyperlink;
    @FXML private Hyperlink loginHyperlink;

    @FXML
    public void onLoginHyperlinkClicked() {
        System.out.println("going to the login page...");
        Starting.switchScenes("Login");
    }

    @FXML
    public void onSignUpHyperlinkClicked() {
        System.out.println("going to the sign up page...");
        Starting.switchScenes("SignUp");
    }

}
