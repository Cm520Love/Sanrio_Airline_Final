package GUI;

import SQL.BookedFlights;
import SQL.CurrentUser;
import SQL.Customers;  // Assuming you have a Customer class for database operations
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.sql.*;

import static java.sql.DriverManager.getConnection;

public class PasswordRecoveryController {

    @FXML
    private TextField PassRecovFirstNameBox;

    @FXML
    private ImageView PassRecovImageView;

    @FXML
    private TextField PassRecovLastNameBox;

    @FXML
    private TextField PassRecovSAnsBox;

    @FXML
    private TextField PassRecovSQuestBox;

    @FXML
    private Button PassRecovSearchButton;
    @FXML
    private Pane PaneForQA;
    @FXML
    private Button PassRecovSubmitButton;

    @FXML
    private TextField PassRecovUsernameBox;

    @FXML
    private Button PasswordRecoveryLoginButton;

    @FXML
    private TextField PasswordRevealBox;

    @FXML
    private Hyperlink SanrioAirline;

    @FXML
    void onMainMenuHyperlinkClicked(ActionEvent event) {
        System.out.println("Going to the main menu no access Page .....");
        Starting.window.setScene(Starting.mainMenuNoAccessScene);
    }

    @FXML
    void onPasswordRecoveryLoginButtonClicked(ActionEvent event) {
        System.out.println("Going to the Login Page .....");
        Starting.window.setScene(Starting.loginScene);
    }

    @FXML
    void onPassRecovSearchButtonClicked(ActionEvent event) throws SQLException {

        String username = PassRecovUsernameBox.getText();
        String firstName = PassRecovFirstNameBox.getText();
        String lastName = PassRecovLastNameBox.getText();

        if (CurrentUser.accountExists(username, firstName, lastName)) {
            PaneForQA.setVisible(true);
            PassRecovSQuestBox.setText(CurrentUser.getSecurityQuestion(username));
        } else {
            PaneForQA.setVisible(false);
            BookTicketsController.showErrorPopup("Account Error", "Account Not Found. Please check your answer");
        }
        // check if username, firstname, and lastname exists in database
        // if true: display the question
        // if false: display account not found, check your information
    }


    @FXML
    void onPassRecovSubmitButtonClicked(ActionEvent event) throws SQLException {
        //This is the method to check the password is the same as the database
        String securityAnswer = PassRecovSAnsBox.getText();  // Get the entered security answer
        String username = PassRecovUsernameBox.getText();

        //Compare security answer from the database to the answer that they put in
        //the (username) is to match with security answer in the database
        if (CurrentUser.getSecurityAnswer(username).equals(securityAnswer)) {
            PasswordRevealBox.setText(CurrentUser.getUserPassword(PassRecovUsernameBox.getText()));
        }
        else{
                PasswordRevealBox.setText("Password not found. Please check your answer.");

        }
    }
}


