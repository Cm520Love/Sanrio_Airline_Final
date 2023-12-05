package GUI;

import SQL.Customers;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.sql.*;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String Username = Starting.getCurrentUser();
        String[] userInformation = SQL.CurrentUser.getAllUserInformation(Username);

        ProfileFirstNameBox.setText(userInformation[0]);
        ProfileLastNameBox.setText(userInformation[1]);
        ProfileAddressBox.setText(userInformation[2]);
        ProfileStateBox.setText(userInformation[3]);
        ProfileZipCodeBox.setText(userInformation[4]);
        ProfileUsernameBox.setText(Username);
        ProfilePasswordBox.setText(userInformation[6]);
        ProfileSSNBox.setText(userInformation[7]);
        ProfileEmailBox.setText(userInformation[8]);
        ProfileSQuestionBox.setText(userInformation[9]);
        ProfileSAnsBox.setText(userInformation[10]);

    }


    public void onSanioAirlineClicked(javafx.event.ActionEvent actionEvent) {
        System.out.println("Going to the main menu.....");
        Starting.window.setScene(Starting.mainMenuAccessScene);
    }
}

