package sample.demo;
import javafx.fxml.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ArrayList;
import javafx.scene.control.*;
import SQL.*;
import userInformation.UserInformation;
import javafx.collections.*;
public class DepartFlightsController implements Initializable{

    @FXML
    private TextField departureDateTextField;
    @FXML private TextField departureAirportTextField;
    @FXML private TextField arrivalAirportTextField;
    @FXML private TextField tripTypeTextField;
    @FXML private TextField ticket1DepartureTimeTextField;
    @FXML private TextField ticket2DepartureTimeTextField;
    @FXML private TextField ticket3DepartureTimeTextField;
    @FXML private TextField ticket4DepartureTimeTextField;
    @FXML private TextField departFlightNo1Box;
    @FXML private TextField departFlightNo2Box;
    @FXML private TextField departFlightNo3Box;
    @FXML private TextField departFlightNo4Box;
    @FXML private TextField ticket1ArrivalTimeTextField;
    @FXML private TextField ticket2ArrivalTimeTextField;
    @FXML private TextField ticket3ArrivalTimeTextField;
    @FXML private TextField ticket4ArrivalTimeTextField;
    @FXML private  Button ticket1AddButton;
    @FXML private  Button ticket2AddButton;
    @FXML private  Button ticket3AddButton;
    @FXML private  Button ticket4AddButton;


    static private HashMap<Integer, ArrayList<String>> allFlightTickets;
    //public FlightTicketsController(HashMap<String, ArrayList<String>> allFlightTickets) {
    //    this.allFlightTickets = allFlightTickets;
    //}

    @Override
    public void initialize(java.net.URL url, java.util.ResourceBundle resourceBundle) {
        ArrayList<UserInformation> flightInformation = flightInformationVO.getCurrentFlightInfo();
        String fromCity = flightInformation.get(4).getInfo();
        fromCity = fromCity.substring(fromCity.length() - 3);
        String toCity = flightInformation.get(6).getInfo();
        toCity = toCity.substring(toCity.length() - 3);

        String departureDate = flightInformation.get(1).getInfo();
        //String returnDate = flightInformation.get(2).getInfo();

        String[] flightInfo = {fromCity, toCity, departureDate};
        allFlightTickets = bookFlightQueries.retrieveFlights(flightInfo);

        departureDateTextField.setText(departureDate);
        departureAirportTextField.setText(fromCity);
        arrivalAirportTextField.setText(toCity);
        tripTypeTextField.setText(flightInformation.get(0).getInfo());

        displayFlightInformation();
    }

    void displayFlightInformation() {
        int i = 0;
        TextField[] flightIDBoxes = {departFlightNo1Box,departFlightNo2Box,departFlightNo3Box,departFlightNo4Box};
        TextField[] departureTimeBoxes = {ticket1DepartureTimeTextField, ticket2DepartureTimeTextField,ticket3DepartureTimeTextField, ticket4DepartureTimeTextField};
        TextField[] arrivalTimeBoxes = {ticket1ArrivalTimeTextField, ticket2ArrivalTimeTextField, ticket3ArrivalTimeTextField, ticket4ArrivalTimeTextField};
        for (Integer key: allFlightTickets.keySet()) {
            ArrayList<String> details = allFlightTickets.get(key);
            flightIDBoxes[i].setText(String.format("%04d", key));
            departureTimeBoxes[i].setText(details.get(3));
            arrivalTimeBoxes[i].setText(details.get(4));
            i++;
            System.out.println(key + " " + allFlightTickets.get(key));
        }
    }
    @FXML
    void onMainMenuLinkClicked() {
        System.out.println("going back to main menu");
        Starting.window.setScene(Starting.mainMenuAccessScene);
    }

    @FXML
    void onDepartTicket1AddButtonClicked() {
        addInformationToDatabase(
                Starting.getCurrentUser(),
                departureDateTextField.getText(),
                ticket1DepartureTimeTextField.getText(),
                ticket1ArrivalTimeTextField.getText(),
                departFlightNo1Box.getText()
        );
        System.out.println("Adding 1st Depart Tickets to Database....");
        // Additional logic or scene transition if needed
    }


    @FXML
    void onDepartTicket2AddButtonClicked() {
        addInformationToDatabase(
                Starting.getCurrentUser(),
                departureDateTextField.getText(),
                ticket2DepartureTimeTextField.getText(),
                ticket2ArrivalTimeTextField.getText(),
                departFlightNo2Box.getText()
        );
        System.out.println("Adding 2nd Depart Tickets to Database....");
        // Additional logic or scene transition if needed
    }

    @FXML
    void onDepartTicket3AddButtonClicked() {
        addInformationToDatabase(
                Starting.getCurrentUser(),
                departureDateTextField.getText(),
                ticket3DepartureTimeTextField.getText(),
                ticket3ArrivalTimeTextField.getText(),
                departFlightNo3Box.getText()
        );
        System.out.println("Adding 3rd Depart Tickets to Database....");
        // Additional logic or scene transition if needed
    }

    @FXML
    void onDepartTicket4AddButtonClicked() {
            addInformationToDatabase(
                    Starting.getCurrentUser(),
                    departureDateTextField.getText(),
                    ticket4DepartureTimeTextField.getText(),
                    ticket4ArrivalTimeTextField.getText(),
                    departFlightNo4Box.getText()
            );
            System.out.println("Adding 4th Depart Tickets to Database....");
            // Additional logic or scene transition if needed
        }

    private void addInformationToDatabase(String Username, String DepartureDate, String DepartureTime, String ArrivalTime, String FlightID) {
        try {
            // Check if the user has already booked this flight
            if (hasUserBookedFlight(Username, FlightID)) {
                // Show a popup indicating that the user has already booked this flight
                showErrorPopup("Booking Error", "You have already booked this flight.");
                return;  // Do not proceed with the insertion
            }

            // Insert the new booking information
            PreparedStatement ps = Starting.conn.prepareStatement("INSERT INTO BookedFlights VALUES (?,?,?,?,?)");
            ps.setString(1, Username);
            ps.setString(2, DepartureDate);
            ps.setString(3, DepartureTime);
            ps.setString(4, ArrivalTime);
            ps.setString(5, FlightID);
            ps.execute();

            // Optionally, you can show a success popup or perform additional logic
            showSuccessPopup("Booking Successful", "You have successfully booked the flight.");

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the SQLException appropriately (e.g., show an error popup)
            showErrorPopup("Booking Error", "An error occurred while booking the flight. Please try again.");
        }
    }

    // Check if the user has already booked this flight
    private boolean hasUserBookedFlight(String username, String flightID) {
        try {
            PreparedStatement ps = Starting.conn.prepareStatement("SELECT * FROM BookedFlights WHERE Username = ? AND FlightID = ?");
            ps.setString(1, username);
            ps.setString(2, flightID);
            return ps.executeQuery().next();  // Returns true if a record is found
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the SQLException appropriately
            return false;

        }

    }

    private void showSuccessPopup(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showErrorPopup(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    }

