package sample.demo;

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
        Starting.window.setScene(Starting.loginScene);
    }

    @FXML
    public void onSignUpHyperlinkClicked() {
        System.out.println("going to the sign up page...");

        Starting.window.setScene(Starting.signUpScene);
    }
    @FXML
    public void onForgotPasswordHyperlinkClicked() {
        System.out.println("going to the password recovery page...");
        Starting.window.setScene(Starting.forgotPasswordScene);
    }
}
