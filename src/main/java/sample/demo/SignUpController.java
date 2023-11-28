package sample.demo;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import java.util.ArrayList;
import userInformation.userProfile.*;
import SQL.*;
import javafx.scene.control.ComboBox;
public class SignUpController implements Initializable {

        @FXML private Label AlreadyaMemberLabel;
        @FXML private Hyperlink LogoHyperlink;
        @FXML private Hyperlink MemberLoginHyperlink;
        @FXML private Label MemberRegistrationLabel;
        @FXML private TextField SignUpAddressBox;
        @FXML private Label SignUpAddressLabel;
        @FXML private TextField SignUpAdminCodeBox;
        @FXML private Label SignUpAdminCodeLabel;
        @FXML private TextField SignUpFirstNameBox;
        @FXML private Label SignUpFirstNameLabel;
        @FXML private TextField SignUpLastNameBox;
        @FXML private Label SignUpLastNameLabel;
        @FXML private ChoiceBox<String> SignUpMemberTypeDropBox;
        @FXML private Label SignUpMemberTypeLabel;
        @FXML private TextField SignUpPasswordBox;
        @FXML private Label SignUpPasswordLabel;
        @FXML private TextField SignUpSecurityAnswerBox;
        @FXML private Label SignUpSecurityAnswerLabel;
        @FXML private ChoiceBox<String> SignUpSecurityQuestionDropBox;
        @FXML private Label SignUpSecurityQuestionLabel;
        @FXML private TextField SignUpStateBox;
        @FXML private Label SignUpStateLabel;
        @FXML private Button SignUpSubmitButton;
        @FXML private TextField SignUpUsernameBox;
        @FXML private Label SignUpUsernameLabel;
        @FXML private TextField SignUpZipCodeBox;
        @FXML private Label SignUpZipCodeLabel;
        @FXML private Label SignUpEmailLabel;
        @FXML private TextField SignUpEmailBox;
        @FXML private Label SignUpSSNLabel;
        @FXML private TextField SignUpSSNBox;
        @FXML private ImageView SignUpImageView;
        @FXML private ComboBox<String> SignUpStateComboBox;
        @FXML private Label firstNameError;
        @FXML private Label lastNameError;
        @FXML private Label addressError;
        @FXML private Label zipCodeError;
        @FXML private Label adminCodeError;
        @FXML private Label usernameError;
        @FXML private Label passwordError;
        @FXML private Label emailError;
        @FXML private Label ssnError;
        @FXML private Label securityQuestionError;
        @FXML private Label stateError;
        @FXML private Label securityAnswerError;
        //@FXML private ChoiceBox<String> SignUpStateDropBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SignUpMemberTypeDropBox.getItems().addAll("Customer", "Admin");
        SignUpMemberTypeDropBox.setValue("Customer");
        SignUpSecurityQuestionDropBox.getItems().addAll(SecurityQuestion.getQuestions());
        SignUpStateComboBox.getItems().addAll(State.getStateAbbreviations());

    }
    @FXML
    public void onSubmitButtonClicked() {
        clearEntryErrors();
        ArrayList<userInformation.UserInformation> newUserDetails = new ArrayList<>();
        newUserDetails = testUserInformation();
        if (displayEntryErrors(newUserDetails)) {
            System.out.println("should be new user");
            registerUser.addNewUser(newUserDetails);
            Starting.setCurrentUser(newUserDetails.get(6).getInfo());
            Starting.window.setScene(Starting.mainMenuAccessScene);
        }

    }

    @FXML
    protected void onMainMenuHyperlinkClicked() {
        System.out.println("going back to the main menu");
        Starting.window.setScene(Starting.mainMenuNoAccessScene);
    }

    @FXML
    protected void onMemberLoginHyperlinkClicked() {
        System.out.println("going back to the login page");
        Starting.window.setScene(Starting.loginScene);
    }

    private void clearEntryErrors() {
        firstNameError.setText("");
        lastNameError.setText("");
        addressError.setText("");
        zipCodeError.setText("");
        stateError.setText("");
        adminCodeError.setText("");
        usernameError.setText("");
        passwordError.setText("");
        emailError.setText("");
        ssnError.setText("");
        securityQuestionError.setText("");
        securityAnswerError.setText("");

    }

    private boolean displayEntryErrors(ArrayList<userInformation.UserInformation> userDetails) {
        boolean continueRegistration = true;
        for (userInformation.UserInformation info: userDetails) {
            if (info.isValid()) {
                continue;
            }
            continueRegistration = false;

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
                case "Admin":
                    adminCodeError.setText(info.getErrorMsg());
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

    private userInformation.UserInformation determineUserType(ChoiceBox<String> SignUpMemberTypeDropBox, TextField SignUpAdminCodeBox) {
        if (SignUpMemberTypeDropBox.getValue() == "Customer") {
            return new Customer();
        } else {
            return new Admin(SignUpAdminCodeBox.getText());
        }
    }

    private ArrayList<userInformation.UserInformation> testUserInformation() {
        userProfileVO newUser = new userProfileVO();

        newUser.userType = determineUserType(SignUpMemberTypeDropBox, SignUpAdminCodeBox);
        newUser.firstName = new FirstName(SignUpFirstNameBox.getText());
        newUser.lastName = new LastName(SignUpLastNameBox.getText());
        newUser.address = new Address(SignUpAddressBox.getText());
        newUser.zipCode = new ZipCode(SignUpZipCodeBox.getText());
        newUser.state = new State(SignUpStateComboBox.getValue());
        newUser.username = new Username(SignUpUsernameBox.getText(), newUser.userType);
        newUser.password = new Password(SignUpPasswordBox.getText());
        newUser.email = new Email(SignUpEmailBox.getText());
        newUser.ssn = new SSN(SignUpSSNBox.getText(), newUser.userType);
        newUser.securityQuestion = new SecurityQuestion(SignUpSecurityQuestionDropBox.getValue());
        newUser.securityAnswer = new SecurityAnswer(SignUpSecurityAnswerBox.getText());


        return newUser.getUserInformation();
    }
}
