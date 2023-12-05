package GUI;

import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.shape.*;
import SQL.CurrentUser;


public class LoginController {

    @FXML
    private Label newUserLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label forgotPasswordLabel;
    @FXML
    private Label loginError;
    @FXML
    private ImageView icon;
    @FXML
    private TextField usernameEntry;
    @FXML
    private PasswordField passwordEntry;
    @FXML
    private Rectangle loginBox;
    @FXML
    private ImageView background;
    @FXML private Hyperlink mainMenuHyperlink;
    @FXML private Hyperlink forgotPasswordHyperlink;
    @FXML private Hyperlink signUpHyperlink;


    @FXML
    public void onLoginButtonClicked() {
        // retrieving the information the user entered in username and password
        String username = usernameEntry.getText();
        String password = passwordEntry.getText();
        loginError.setVisible(false);
        try {
            // if login was successful
            if (CurrentUser.login(username, password)) {
                System.out.println("login successful");
                // set the current user
                Starting.setCurrentUser(username);
                // switch scenes to the main menu where they can book flights
                Starting.switchScenes("MainMenuAccess");
            } else {
                // if login was not successful, display the login error msg
                loginError.setVisible(!loginError.isVisible());
            }
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    @FXML
    public void onMainMenuHyperlinkClicked() {
        System.out.println("going to main menu...");
        Starting.switchScenes("MainMenuNoAccess");

    }

    @FXML
    public void onSignUpHyperlinkClicked() {
        System.out.println("going to sign up page...");
        Starting.switchScenes("SignUp");

    }

    @FXML
    public void onForgotPasswordHyperlinkClicked() {
        System.out.println("you forgot your password? that's crazy");
        Starting.switchScenes("ForgotPassword");


    }

}