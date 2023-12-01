package sample.demo;
import javafx.fxml.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ArrayList;
import javafx.scene.control.*;
import SQL.*;
import userInformation.UserInformation;

public class DepartFlightsController implements Initializable {

    @FXML
    private TextField departureDateTextField;
    @FXML
    private TextField departureAirportTextField;
    @FXML
    private TextField arrivalAirportTextField;
    @FXML
    private TextField tripTypeTextField;
    @FXML
    private TextField ticket1DepartureTimeTextField;
    @FXML
    private TextField ticket2DepartureTimeTextField;
    @FXML
    private TextField ticket3DepartureTimeTextField;
    @FXML
    private TextField ticket4DepartureTimeTextField;
    @FXML
    private TextField departFlightNo1Box;
    @FXML
    private TextField departFlightNo2Box;
    @FXML
    private TextField departFlightNo3Box;
    @FXML
    private TextField departFlightNo4Box;
    @FXML
    private TextField ticket1ArrivalTimeTextField;
    @FXML
    private TextField ticket2ArrivalTimeTextField;
    @FXML
    private TextField ticket3ArrivalTimeTextField;
    @FXML
    private TextField ticket4ArrivalTimeTextField;
    @FXML
    private Button ticket1AddButton;
    @FXML
    private Button ticket2AddButton;
    @FXML
    private Button ticket3AddButton;
    @FXML
    private Button ticket4AddButton;


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
        TextField[] flightIDBoxes = {departFlightNo1Box, departFlightNo2Box, departFlightNo3Box, departFlightNo4Box};
        TextField[] departureTimeBoxes = {ticket1DepartureTimeTextField, ticket2DepartureTimeTextField, ticket3DepartureTimeTextField, ticket4DepartureTimeTextField};
        TextField[] arrivalTimeBoxes = {ticket1ArrivalTimeTextField, ticket2ArrivalTimeTextField, ticket3ArrivalTimeTextField, ticket4ArrivalTimeTextField};
        for (Integer key : allFlightTickets.keySet()) {
            ArrayList<String> details = allFlightTickets.get(key);
            flightIDBoxes[i].setText(key+"");
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
            if (ConflictedTimeFlights(
                    Starting.getCurrentUser(),
                    departureDateTextField.getText(),
                    DepartureTime,
                    ArrivalTime
            )) {
                showErrorPopup("Booking Error", "Time and date conflict with another booked flight");
                return;
            }
            if (isFull(FlightID)) {

                showErrorPopup("Booking Error", "Flight was full. Please book another time");
                return;
            }
            // Insert the new booking information
            PreparedStatement ps = Starting.conn.prepareStatement("INSERT INTO BookedFlights VALUES (?,?,?,?,?)");
            ps.setString(1, Username);
            ps.setString(2, DepartureDate);
            ps.setString(3, DepartureTime);
            ps.setString(4, ArrivalTime);
            ps.setString(5, FlightID);
            ps.execute();
            incrementPassengers(FlightID);

            if(flightInformationVO.getCurrentFlightInfo().get(0).getInfo() == "Round") {
                System.out.println("going to the return flight page...");
                Starting.window.setScene(Starting.returnFlightScene);

            }
            else{
                System.out.println("going to the Mytrip...");
                Starting.window.setScene(Starting.myTripScene);
            }

            // Optionally, you can show a success popup or perform additional logic
            showSuccessPopup("Booking Successful", "You have successfully booked the flight.");

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the SQLException appropriately (e.g., show an error popup)
            showErrorPopup("Booking Error", "An error occurred while booking the flight. Please try again.");
        }
    }

    private boolean isFull(String flightID) {
        try {
            PreparedStatement ps = Starting.conn.prepareStatement("SELECT Passengers FROM Flight WHERE FlightID = ?");
            ps.setString(1, flightID);
            ResultSet rs = ps.executeQuery();
            rs.next();
            if ((rs.getInt(1)) == bookFlightQueries.getMaxPassengers()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the SQLException appropriately

        }
        return false;
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
    ////////////////////////////////////////////////////

    //This is for checking if the customer has a date and time conflicts
    private boolean ConflictedTimeFlights(String Username, String DepartureDate, String DepartureTime, String ArrivalTime) {
        try {
            PreparedStatement ps = Starting.conn.prepareStatement("SELECT COUNT(*) AS Num FROM BookedFlights WHERE Username = ? AND DepartureDate = ? AND DepartureTime = ? AND ArrivalTime =?");
            ps.setString(1, Username);
            ps.setString(2, DepartureDate);
            ps.setString(3, DepartureTime);
            ps.setString(4, ArrivalTime);

            ResultSet rs = ps.executeQuery();  // Returns true if a record is found
            rs.next();
            if ((rs.getInt("Num")) != 0) {
                // meaning user hasn't booked a flight with this date and time
                return true;
            } else {
                return false; //it means user have booked a flight with this time and day
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the SQLException appropriately
            return false;

        }
    }

    private void showErrorPopup2(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void incrementPassengers(String FlightID) {
        try {
            PreparedStatement ps = Starting.conn.prepareStatement("SELECT Passengers FROM Flight WHERE FlightID = ?");
            PreparedStatement updatedStatement = Starting.conn.prepareStatement("UPDATE Flight SET Passengers = ? WHERE FlightID = ?");
            ps.setInt(1, Integer.parseInt(FlightID));
            ResultSet rs = ps.executeQuery();  // Returns true if a record is found
            rs.next();
            updatedStatement.setInt(1, rs.getInt(1) + 1); //add the passenger for each booked
            updatedStatement.setInt(2, Integer.parseInt(FlightID)); // this is for specific flightID so it won't add passengers to all flightID
            updatedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}