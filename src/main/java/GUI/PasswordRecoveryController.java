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
        // Going to the Main Menu page
        System.out.println("Going to the main menu no access Page .....");
        Starting.window.setScene(Starting.mainMenuNoAccessScene);
    }

    @FXML
    void onPasswordRecoveryLoginButtonClicked(ActionEvent event) {
        //Going to the Login Page
        System.out.println("Going to the Login Page .....");
        Starting.window.setScene(Starting.loginScene);
    }

    @FXML
    void onPassRecovSearchButtonClicked(ActionEvent event) throws SQLException {
        //Getting the text for the username, firstname, and lastname textfield when the customer insert their info and clicked search
        //The entered text by customers is stored in the variables username, firstName, and lastName
        String username = PassRecovUsernameBox.getText();
        String firstName = PassRecovFirstNameBox.getText();
        String lastName = PassRecovLastNameBox.getText();

        //The CurrentUser.accountExists is the code we pu t in the CurrentUser to check for if accounts exist
        //the following is to check if the 3 variables the customer insert in matches the one in the database
        if (CurrentUser.accountExists(username, firstName, lastName)) {
            PaneForQA.setVisible(true); //If yes, the rest of the box will show in the form
            //if yes, the security question box will grab the security question from the database that correspond to the username
            //The username is the parameter that associates to the security question
            PassRecovSQuestBox.setText(CurrentUser.getSecurityQuestion(username));
        } else { //if no, the PaneforQA will not pop up, and showing an error of Account not found
            PaneForQA.setVisible(false);
            //The BookTicketsController is where I put the showError Popup method
            //Account Error is the title of the popup name then is the message
            BookTicketsController.showErrorPopup("Account Error", "Account Not Found. Please check your answer");
        }
        //what we want to do:
        // check if username, firstname, and lastname exists in database
        // if true: display the question
        // if false: display account not found, check your information
    }


    @FXML
    void onPassRecovSubmitButtonClicked(ActionEvent event) throws SQLException {
        //This is the method to check the password is the same as the database
        //Getting the text from the 2 boxes
        String securityAnswer = PassRecovSAnsBox.getText();  // Get the entered security answer
        String username = PassRecovUsernameBox.getText();

        //Compares if the Current.getSecurityAnswer in the database is equal to the securityAnswer that they put in
        //the (username) retrieves the security answer associated with the specific username
        if (CurrentUser.getSecurityAnswer(username).equals(securityAnswer)) {
            //If the answer is correct, the password will be revealed in the PasswordRevealBox
            //setText to get the password from the database to the PasswordRevealBox
            PasswordRevealBox.setText(CurrentUser.getUserPassword(PassRecovUsernameBox.getText()));
        }
        else{
            //if answer is incorrect, the reveal box will show the text password not found
                PasswordRevealBox.setText("Password not found. Please check your answer.");

        }
    }
}


