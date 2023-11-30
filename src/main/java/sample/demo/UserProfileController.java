package sample.demo;

import SQL.Customers;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
public class UserProfileController implements Initializable {
    @FXML
    private TextField ProfileAddressBox;
    @FXML
    private TextField ProfileEmailBox;
    @FXML
    private TextField ProfileFirstNameBox;
    @FXML
    private ImageView ProfileImageView;
    @FXML
    private TextField ProfileLastNameBox;
    @FXML
    private TextField ProfilePasswordBox;
    @FXML
    private TextField ProfileSAnsBox;
    @FXML
    private TextField ProfileSQuestionBox;
    @FXML
    private TextField ProfileSSNBox;
    @FXML
    private TextField ProfileStateBox;
    @FXML
    private Button ProfileUpdateButton;
    @FXML
    private TextField ProfileUsernameBox;
    @FXML
    private TextField ProfileZipCodeBox;
    @FXML
    private Hyperlink SanrioAirline;

    // Instantiate a Customer object
    private Customers customer = new Customers();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Load user details from the database when the form is initialized
        loadUserDetails();
    }

    private void loadUserDetails() {
        // Implement your logic to load user details from the database
        // You may use the SelectDB method in the Customer class or any other method you have
        customer.selectDB(Starting.getCurrentUser()); // assuming you want to load details for a specific user

        // Set the retrieved information to the corresponding text fields
        ProfileFirstNameBox.setText(customer.getFirstName());
        ProfileLastNameBox.setText(customer.getLastName());
        ProfileAddressBox.setText(customer.getAddress());
        ProfileStateBox.setText(customer.getState());
        ProfileZipCodeBox.setText(customer.getZipCode());
        ProfileEmailBox.setText(customer.getEmail());
        ProfileSSNBox.setText(customer.getSSN());
        ProfileSQuestionBox.setText(customer.getSecurityQuestion());
        ProfileSAnsBox.setText(customer.getSecurityAnswer());
        ProfileUsernameBox.setText(customer.getUsername());
        ProfilePasswordBox.setText(customer.getPassword());
    }


    @FXML
    public void onProfileUpdateButtonClicked() {
        // Get values from text fields (if needed for further processing)
        String profileFirstName = ProfileFirstNameBox.getText();
        String profileLastName = ProfileLastNameBox.getText();
        String profileAddress = ProfileAddressBox.getText();
        String profileState = ProfileStateBox.getText();
        String profileZipCode = ProfileZipCodeBox.getText();
        String profileEmail = ProfileEmailBox.getText();
        String profileSSN = ProfileSSNBox.getText();
        String profileSQuestion = ProfileSQuestionBox.getText();
        String profileAnswer = ProfileSAnsBox.getText();
        String profileUsername = ProfileUsernameBox.getText();
        String profilePassword = ProfilePasswordBox.getText();


        // Update the Customer object with the new values
        customer.setFirstName(profileFirstName);
        customer.setLastName(profileLastName);
        customer.setAddress(profileAddress);
        customer.setState(profileState);
        customer.setZipCode(profileZipCode);
        customer.setEmail(profileEmail);
        customer.setSSN(profileSSN);
        customer.setSecurityQuestion(profileSQuestion);
        customer.setSecurityAnswer(profileAnswer);
        customer.setUsername(profileUsername);
        customer.setPassword(profilePassword);


        // Call the updateDB method
        boolean updateSuccess = customer.updateDB();
        // Show a popup based on the update success
        if (updateSuccess) {
            showSuccessPopup("Update Successful", "Profile updated successfully!");
        } else {
            showErrorPopup("Update Failed", "Failed to update profile. Please try again.");
        }
    }

    private void showSuccessPopup(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showErrorPopup(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

