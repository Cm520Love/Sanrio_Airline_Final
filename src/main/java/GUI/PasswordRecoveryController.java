package GUI;

import SQL.Customers;  // Assuming you have a Customer class for database operations
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

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

        Customers customer = new Customers();
        String url = "jdbc:sqlserver://test-sunnysqlserver.database.windows.net:1433;database=Sanrio_Airlines;user=stinkysnoopy@test-sunnysqlserver;password=AdminPass!!!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
        Connection conn = DriverManager.getConnection(url);

        try (Connection connection = DriverManager.getConnection(url)) {
            // Use PreparedStatement to prevent SQL injection
            String query = "SELECT SecurityQuestion FROM Customer WHERE Username = ? AND FirstName = ? AND LastName = ?";
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setString(1, username);
                statement.setString(2, firstName);
                statement.setString(3, lastName);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String securityQuestion = resultSet.getString("SecurityQuestion");
                        PassRecovSQuestBox.setText(securityQuestion);
                    } else {
                        PassRecovSQuestBox.setText("Security question not found.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Handle the exception appropriately
        }
    }

    @FXML
    void onPassRecovSubmitButtonClicked(ActionEvent event) throws SQLException{
        String username = PassRecovUsernameBox.getText();
        String firstName = PassRecovFirstNameBox.getText();
        String lastName = PassRecovLastNameBox.getText();
        String securityAnswer = PassRecovSAnsBox.getText();  // Get the entered security answer

        Customers customer = new Customers();
        String url = "jdbc:sqlserver://test-sunnysqlserver.database.windows.net:1433;database=Sanrio_Airlines;user=stinkysnoopy@test-sunnysqlserver;password=AdminPass!!!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";

        try (Connection conn = DriverManager.getConnection(url)) {
            // Use PreparedStatement to prevent SQL injection
            String query = "SELECT Password FROM Customer WHERE Username = ? AND FirstName = ? AND LastName = ? AND SecurityAnswer = ?";
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setString(1, username);
                statement.setString(2, firstName);
                statement.setString(3, lastName);
                statement.setString(4, securityAnswer);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String password = resultSet.getString("Password");
                        PasswordRevealBox.setText(password);
                    } else {
                        PasswordRevealBox.setText("Password not found. Please check your information.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Handle the exception appropriately
        }
    }

    }




