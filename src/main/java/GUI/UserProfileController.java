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

    // Instantiate a Customer object
    private Customers customer = new Customers();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String Username = Starting.getCurrentUser();
        GettingAllCustomerInfo();

    }

    private void GettingAllCustomerInfo() {
        String Username = Starting.getCurrentUser();
        ProfileUsernameBox.setText(Username);
        String url = "jdbc:sqlserver://test-sunnysqlserver.database.windows.net:1433;database=Sanrio_Airlines;user=stinkysnoopy@test-sunnysqlserver;password=AdminPass!!!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";

        try (Connection connection = DriverManager.getConnection(url)) {
            // Use PreparedStatement to prevent SQL injection
            String sql = "SELECT * FROM Customer WHERE Username = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, Username);

                try (ResultSet resultSet = statement.executeQuery()) {
                    // Check if the result set has data
                    if (resultSet.next()) {
                        // Retrieve customer information from the result set
                        ProfileFirstNameBox.setText(resultSet.getString("FirstName"));
                        ProfileLastNameBox.setText(resultSet.getString("LastName"));
                        ProfileAddressBox.setText(resultSet.getString("Address"));
                        ProfileStateBox.setText(resultSet.getString("State"));
                        ProfileZipCodeBox.setText(resultSet.getString("ZipCode"));
                        ProfileSSNBox.setText(resultSet.getString("SSN"));
                        ProfileEmailBox.setText(resultSet.getString("Email"));
                        ProfilePasswordBox.setText(resultSet.getString("Password"));
                        ProfileSQuestionBox.setText(resultSet.getString("SecurityQuestion"));
                        ProfileSAnsBox.setText(resultSet.getString("SecurityAnswer"));

                    }
                }
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }


    public void onSanioAirlineClicked(javafx.event.ActionEvent actionEvent) {
        System.out.println("Going to the main menu.....");
        Starting.window.setScene(Starting.mainMenuAccessScene);
    }
}

