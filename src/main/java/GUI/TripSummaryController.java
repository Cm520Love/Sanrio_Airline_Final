package GUI;

import SQL.SearchFlight;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.*;

public class TripSummaryController implements Initializable {

    @FXML
    private Button DeleteFlightButton;

    @FXML
    private Button GetFlightInformationButton;
    @FXML
    private TextArea FlightDetailsTextArea;

    @FXML
    private TextField InsertUsernameBox;

    @FXML
    private Button MainMenuButton;

    @FXML
    private ListView<String> flightListView;
    @FXML
    private TextArea TextAreaofALLFlights;

    public void initialize(java.net.URL url, java.util.ResourceBundle resourceBundle) {
        GettingFlightNo();

    }

    @FXML
    void GettingFlightNo() {
        String username = Starting.getCurrentUser();
        InsertUsernameBox.setText(username);

        // Clear existing content in the ListView
        flightListView.getItems().clear();

        String url = "jdbc:sqlserver://test-sunnysqlserver.database.windows.net:1433;database=Sanrio_Airlines;user=stinkysnoopy@test-sunnysqlserver;password=AdminPass!!!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";

        try (Connection connection = DriverManager.getConnection(url)) {
            // Use PreparedStatement to prevent SQL injection
            String query = "SELECT FlightID FROM BookedFlights WHERE Username = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        // Retrieve flight details from the result set

                        String flightID = resultSet.getString("FlightID");

                        // Display flight details in the ListView
                        String listViewItem = String.format("%s", flightID);
                        flightListView.getItems().add(listViewItem);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Handle the exception appropriately
        }
    }
    @FXML
    void onDeleteFightButtonClicked(ActionEvent event) {
        // Get the selected item from the ListView
        String selectedFlight = flightListView.getSelectionModel().getSelectedItem();

        // Check if an item is selected
        if (selectedFlight != null) {
            // Remove the selected item from the ListView
            flightListView.getItems().remove(selectedFlight);

            // Extract the flight details from the selected item
            String[] flightDetails = selectedFlight.split("\\s+"); // Assuming whitespace separates details
            String flightID = flightDetails[0]; // Adjust index based on your flight details format

            // Call the method to delete the flight from the database
            if (deleteFlight(flightID)) {
                System.out.println("Flight deleted successfully");
                SearchFlight.decrementPassengers(flightID);
            } else {
                System.out.println("Flight deletion failed");
            }
        }
    }

    private boolean deleteFlight(String FlightID) {
        String url = "jdbc:sqlserver://test-sunnysqlserver.database.windows.net:1433;database=Sanrio_Airlines;user=stinkysnoopy@test-sunnysqlserver;password=AdminPass!!!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";

        try (Connection connection = DriverManager.getConnection(url)) {
            // Use PreparedStatement to prevent SQL injection
            String query = "DELETE FROM BookedFlights WHERE FlightID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, FlightID);

                // Execute the delete statement
                int rowsAffected = statement.executeUpdate();

                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Handle the exception appropriately
            return false;
        }

    }

    @FXML
    void onGetFlightInformationButtonClicked(ActionEvent event) {
        // Get the selected item from the ListView
        String selectedFlight = flightListView.getSelectionModel().getSelectedItem();

        // Check if an item is selected
        if (selectedFlight != null) {
            // Extract the flight details from the selected item
            String[] flightDetails = selectedFlight.split("\\s+"); // Assuming whitespace separates details
            String flightID = flightDetails[0]; // Adjust index based on your flight details format

            // Query the database for detailed flight information
            String detailedFlightInfo = getDetailedFlightInfo(flightID);

            // Display the detailed flight information in the FlightDetailsTextArea
            FlightDetailsTextArea.setText(detailedFlightInfo);
        }
    }

    private String getDetailedFlightInfo(String flightID) {
        String url = "jdbc:sqlserver://test-sunnysqlserver.database.windows.net:1433;database=Sanrio_Airlines;user=stinkysnoopy@test-sunnysqlserver;password=AdminPass!!!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";

        try (Connection connection = DriverManager.getConnection(url)) {
            // Use PreparedStatement to prevent SQL injection
            String query = "SELECT * FROM BookedFlights WHERE FlightID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, flightID);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        // Retrieve flight details from the result set
                        String departureDate = resultSet.getString("DepartureDate");
                        String departureTime = resultSet.getString("DepartureTime");
                        String arrivalTime = resultSet.getString("ArrivalTime");
                        String username = resultSet.getString("Username");

                        // Construct the detailed flight information
                        return String.format("Username: %s%nDeparture Date: %s%nDeparture Time: %s%nArrival Time: %s",
                                username, departureDate, departureTime, arrivalTime);
                    } else {
                        return "Flight information not found";
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Handle the exception appropriately
            return "Error retrieving flight information";
        }

    }

    @FXML
    void onMainMenuButtonClicked() {
        System.out.println("going back to the main menu");
        Starting.window.setScene(Starting.mainMenuAccessScene);
    }
}
