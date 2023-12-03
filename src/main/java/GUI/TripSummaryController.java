package GUI;

import SQL.SearchFlight;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import SQL.*;
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
        String username = Starting.getCurrentUser();
        InsertUsernameBox.setText(username);
        flightListView.getItems().addAll(SQL.CurrentUser.getUserFlights());

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
            //String[] flightDetails = selectedFlight.split("\\s+"); // Assuming whitespace separates details
            String flightID = selectedFlight; // Adjust index based on your flight details format

            // Call the method to delete the flight from the database
            if (SQL.CurrentUser.deleteUserFlight(flightID)) {
                System.out.println("Flight deleted successfully");
                SearchFlight.decrementPassengers(flightID);
            } else {
                System.out.println("Flight deletion failed");
            }
        }
    }


    @FXML
    void onGetFlightInformationButtonClicked(ActionEvent event) {
        // Get the selected item from the ListView
        String selectedFlight = flightListView.getSelectionModel().getSelectedItem();

        // Check if an item is selected
        if (selectedFlight != null) {
            // Extract the flight details from the selected item
            //String[] flightDetails = selectedFlight.split("\\s+"); // Assuming whitespace separates details
            String flightID = selectedFlight; // Adjust index based on your flight details format

            // Query the database for detailed flight information
            String detailedFlightInfo = SQL.CurrentUser.getUserFlightDetails(flightID);

            // Display the detailed flight information in the FlightDetailsTextArea
            FlightDetailsTextArea.setText(detailedFlightInfo);
        }
    }

    @FXML
    void onMainMenuButtonClicked() {
        System.out.println("going back to the main menu");
        Starting.window.setScene(Starting.mainMenuAccessScene);
    }
}
