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
        String username = usernameEntry.getText();
        String password = passwordEntry.getText();

        try {
            if (CurrentUser.login(username, password)) {
                System.out.println("login successful");
                Starting.setCurrentUser(username);
                Starting.window.setScene(Starting.mainMenuAccessScene);
                Starting.profilePage = new FXMLLoader(getClass().getResource("Profile.fxml"));
                Starting.profileScene = new Scene(Starting.profilePage.load());
            }
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    @FXML
    public void onMainMenuHyperlinkClicked() {
        System.out.println("going to main menu...");
        Starting.window.setScene(Starting.mainMenuNoAccessScene);

    }

    @FXML
    public void onSignUpHyperlinkClicked() {
        System.out.println("going to sign up page...");
        Starting.window.setScene(Starting.signUpScene);
    }

    @FXML
    public void onForgotPasswordHyperlinkClicked() {
        System.out.println("you forgot your password? that's crazy");
        Starting.window.setScene(Starting.forgotPasswordScene);

    }

}