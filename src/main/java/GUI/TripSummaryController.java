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

        // Check if the flight is selected
        if (selectedFlight != null) {
            // Remove the selected item from the ListView
            flightListView.getItems().remove(selectedFlight);

            // Extract the flight details from the selected item
            String flightID = selectedFlight;

            // Call the deleteUserFlight method from SQL package, and Current User class to delete the flight from the database
            //flightID is the argument and delete the flightID from the database
            if (SQL.CurrentUser.deleteUserFlight(flightID)) {
                System.out.println("Flight deleted successfully");
                //call this method to delete the passenger -1 when a flight was deleted, so the passenger stays the right number of spots left
                SearchFlight.decrementPassengers(flightID);
            } else {
                System.out.println("Flight deletion failed");
            }
        }
    }

    @FXML
    void onGetFlightInformationButtonClicked(ActionEvent event) {
        // Get the selected item from the flightListView that we named
        //the selectedItem is stored in the variable selectedFlight
        String selectedFlight = flightListView.getSelectionModel().getSelectedItem();

        // Check if an item is selected
        if (selectedFlight != null) {
            //extracts the flightID from the selected item
            String flightID = selectedFlight;

            // Query the database for detailed flight information
            //Getting the userFlightDetails based on the flightID
            // Display the detailed flight information in the FlightDetailsTextArea
            FlightDetailsTextArea.setText(SQL.CurrentUser.getUserFlightDetails(flightID));
        }
    }

    @FXML
    void onMainMenuButtonClicked() {
        System.out.println("going back to the main menu");
        Starting.window.setScene(Starting.mainMenuAccessScene);
    }
}
