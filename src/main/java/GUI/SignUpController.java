package GUI;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.util.ArrayList;
import Account.UserInformation.*;
import javafx.scene.control.ComboBox;
public class SignUpController implements Initializable {


        @FXML private TextField SignUpAddressBox;
        @FXML private TextField SignUpFirstNameBox;
        @FXML private TextField SignUpLastNameBox;
        @FXML private TextField SignUpPasswordBox;
        @FXML private TextField SignUpSecurityAnswerBox;
        @FXML private ChoiceBox<String> SignUpSecurityQuestionDropBox;
        @FXML private TextField SignUpUsernameBox;
        @FXML private TextField SignUpZipCodeBox;
        @FXML private TextField SignUpEmailBox;
        @FXML private TextField SignUpSSNBox;
        @FXML private ComboBox<String> SignUpStateComboBox;
        @FXML private Label firstNameError;
        @FXML private Label lastNameError;
        @FXML private Label addressError;
        @FXML private Label zipCodeError;
        @FXML private Label usernameError;
        @FXML private Label passwordError;
        @FXML private Label emailError;
        @FXML private Label ssnError;
        @FXML private Label securityQuestionError;
        @FXML private Label stateError;
        @FXML private Label securityAnswerError;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SignUpSecurityQuestionDropBox.getItems().addAll(SecurityQuestion.getQuestions());
        SignUpStateComboBox.getItems().addAll(State.getStateAbbreviations());

    }
    @FXML
    protected void onMainMenuHyperlinkClicked() {
        System.out.println("going back to the main menu");
        Starting.switchScenes("MainMenuNoAccess");
    }

    @FXML
    protected void onMemberLoginHyperlinkClicked() {
        System.out.println("going back to the login page");
        Starting.switchScenes("Login");
    }
    @FXML
    public void onSubmitButtonClicked() {
        clearEntryErrors();
        ArrayList<Account.Information> newUserDetails = testUserInformation();
        // once all the entry fields are filled correctly, sign the user up
        if (canContinueRegistration(newUserDetails)) {
            SQL.NewUser.addNewUser(newUserDetails);

            // once user is added to the database, set the current user and log them in
            Starting.setCurrentUser(newUserDetails.get(5).getInfo());
            Starting.switchScenes("MainMenuAccess");
        }

    }

    private void clearEntryErrors() {
        firstNameError.setText("");
        lastNameError.setText("");
        addressError.setText("");
        zipCodeError.setText("");
        stateError.setText("");
        usernameError.setText("");
        passwordError.setText("");
        emailError.setText("");
        ssnError.setText("");
        securityQuestionError.setText("");
        securityAnswerError.setText("");

    }

    private boolean canContinueRegistration(ArrayList<Account.Information> userDetails) {
        boolean continueRegistration = true;

        // iterate through the sign-up details
        for (Account.Information info: userDetails) {
            // if any of the information the user entered is invalid, they cannot sign up
            if (info.isValid()) {
                continue;
            }
            continueRegistration = false;

            // display the respective areas the user needs to fix before they can sign up
            switch (info.getClass().getSimpleName()) {
                case "FirstName":
                    firstNameError.setText(info.getErrorMsg());
                    break;
                case "LastName":
                    lastNameError.setText(info.getErrorMsg());
                    break;
                case "Address":
                    addressError.setText(info.getErrorMsg());
                    break;
                case "ZipCode":
                    zipCodeError.setText(info.getErrorMsg());
                    break;
                case "State":
                    stateError.setText(info.getErrorMsg());
                    break;
                case "Username":
                    usernameError.setText(info.getErrorMsg());
                    break;
                case "Password":
                    passwordError.setText(info.getErrorMsg());
                    break;
                case "Email":
                    emailError.setText(info.getErrorMsg());
                    break;
                case "SSN":
                    ssnError.setText(info.getErrorMsg());
                    break;
                case "SecurityQuestion":
                    securityQuestionError.setText(info.getErrorMsg());
                    break;
                case "SecurityAnswer":
                    securityAnswerError.setText(info.getErrorMsg());

            }
        }

        return continueRegistration;
    }
    private ArrayList<Account.Information> testUserInformation() {
        // grabbing all the fields the user entered and putting it into a value object
        VOUserProfile newUser = new VOUserProfile();

        newUser.firstName = new FirstName(SignUpFirstNameBox.getText());
        newUser.lastName = new LastName(SignUpLastNameBox.getText());
        newUser.address = new Address(SignUpAddressBox.getText());
        newUser.zipCode = new ZipCode(SignUpZipCodeBox.getText());
        newUser.state = new State(SignUpStateComboBox.getValue());
        newUser.username = new Username(SignUpUsernameBox.getText());
        newUser.password = new Password(SignUpPasswordBox.getText());
        newUser.email = new Email(SignUpEmailBox.getText());
        newUser.ssn = new SSN(SignUpSSNBox.getText());
        newUser.securityQuestion = new SecurityQuestion(SignUpSecurityQuestionDropBox.getValue());
        newUser.securityAnswer = new SecurityAnswer(SignUpSecurityAnswerBox.getText());

        // returning the value object
        return newUser.getUserInformation();
    }



}
