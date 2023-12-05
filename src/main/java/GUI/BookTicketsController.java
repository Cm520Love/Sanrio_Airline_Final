package GUI;
import javafx.fxml.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ArrayList;
import javafx.scene.control.*;
import SQL.SearchFlight;

public class BookTicketsController implements Initializable {

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
    @FXML private Label bookFlightTitle;

    private static HashMap<Integer, ArrayList<String>> allFlightTickets;
    private static String flightInfo[];

    private static ArrayList<Account.Information> flightInformation;

    boolean departAdded;

    @Override
    public void initialize(java.net.URL url, java.util.ResourceBundle resourceBundle) {
        flightInformation = VOFlightInformation.getCurrentFlightInformation();

        // gets the airport abbreviations
        String fromCity = flightInformation.get(4).getInfo();
        fromCity = fromCity.substring(fromCity.length() - 3);
        String toCity = flightInformation.get(6).getInfo();
        toCity = toCity.substring(toCity.length() - 3);

        // gets the departure date
        String departureDate = flightInformation.get(1).getInfo();

        // putting airport abbreviations + departure date into a list
        flightInfo = new String[3];
        flightInfo[0] = fromCity;
        flightInfo[1] = toCity;
        flightInfo[2] = departureDate;

        getDepartureTickets();
    }

    void getDepartureTickets() {
        // getting all the flights and storing it in a hashmap
        // key: flightID, value: flight information
        allFlightTickets = SQL.SearchFlight.checkFlights(flightInfo);

        // setting the information in their respective text fields
        departureAirportTextField.setText(flightInfo[0]);
        arrivalAirportTextField.setText(flightInfo[1]);
        departureDateTextField.setText(flightInfo[2]);
        tripTypeTextField.setText(flightInformation.get(0).getInfo());

        departAdded = false;

        // display the different flight options to the user
        displayFlightInformation();
    }

    void getReturnTickets() {
        //swap the from and to when booking flight tickets
        String temp = flightInfo[0];
        flightInfo[0] = flightInfo[1];
        flightInfo[1] = temp;
        flightInfo[2] = flightInformation.get(2).getInfo();
        allFlightTickets = SQL.SearchFlight.checkFlights(flightInfo);

        //set the title to return flights page
        bookFlightTitle.setText("Sanrio Return Flight Tickets");
        //flight information for return tickets
        departureAirportTextField.setText(flightInfo[0]);
        arrivalAirportTextField.setText(flightInfo[1]);
        departureDateTextField.setText(flightInfo[2]);
        displayFlightInformation();
    }

    void displayFlightInformation() {
        int i = 0;
        // storing text fields into a list
        TextField[] flightIDBoxes = {departFlightNo1Box, departFlightNo2Box, departFlightNo3Box, departFlightNo4Box};
        TextField[] departureTimeBoxes = {ticket1DepartureTimeTextField, ticket2DepartureTimeTextField, ticket3DepartureTimeTextField, ticket4DepartureTimeTextField};
        TextField[] arrivalTimeBoxes = {ticket1ArrivalTimeTextField, ticket2ArrivalTimeTextField, ticket3ArrivalTimeTextField, ticket4ArrivalTimeTextField};

        // iterating through the hashmap
        for (Integer key : allFlightTickets.keySet()) {
            // getting individual flight details and storing it in an arraylist
            ArrayList<String> details = allFlightTickets.get(key);

            // setting the flightID number
            flightIDBoxes[i].setText(key+"");

            // grabbing arrival/departure time from the individual flight and displaying it in the text field
            departureTimeBoxes[i].setText(details.get(3));
            arrivalTimeBoxes[i].setText(details.get(4));
            i++;

        }
    }

    @FXML
    void onMainMenuLinkClicked() {
        System.out.println("going back to main menu");
        Starting.switchScenes("MainMenuAccess");
    }

    @FXML
    void onDepartTicket1AddButtonClicked() {
        //add the booked flights from the boxes to the database
        if (SearchFlight.addFlight(
                Starting.getCurrentUser(), //get the current username when the customer log in
                departureDateTextField.getText(), //get text from the boxes
                ticket1DepartureTimeTextField.getText(),
                ticket1ArrivalTimeTextField.getText(),
                departFlightNo1Box.getText()
        )) {
            //checks if return flights was added successfully
            departAdded = !departAdded;
            System.out.println("Adding 1st Depart Tickets to Database....");
            finishBooking();
        }

    }
    @FXML
    void onDepartTicket2AddButtonClicked() {
        if (SearchFlight.addFlight(
                Starting.getCurrentUser(),
                departureDateTextField.getText(),
                ticket2DepartureTimeTextField.getText(),
                ticket2ArrivalTimeTextField.getText(),
                departFlightNo2Box.getText()
        )) {
            departAdded = !departAdded;
            System.out.println("Adding 2nd Depart Tickets to Database....");
            finishBooking();
        }
    }

    @FXML
    void onDepartTicket3AddButtonClicked() {

        if (SearchFlight.addFlight(
                Starting.getCurrentUser(),
                departureDateTextField.getText(),
                ticket3DepartureTimeTextField.getText(),
                ticket3ArrivalTimeTextField.getText(),
                departFlightNo3Box.getText()
        )) {
            departAdded = !departAdded;
            System.out.println("Adding 3rd Depart Tickets to Database....");
            finishBooking();
        }

    }
    @FXML
    void onDepartTicket4AddButtonClicked() {
        if (SearchFlight.addFlight(
                Starting.getCurrentUser(),
                departureDateTextField.getText(),
                ticket4DepartureTimeTextField.getText(),
                ticket4ArrivalTimeTextField.getText(),
                departFlightNo4Box.getText()
        )) {
            departAdded = !departAdded;
            System.out.println("Adding 4th Depart Tickets to Database....");
            finishBooking();
        }
    }

    private void finishBooking() {
        try {
            //check if depart flight is added and if the flight type equals to Round then :
            if (departAdded && tripTypeTextField.getText().equals("Round")) {
                //if true, make the user book their return ticket
                getReturnTickets();
            } else {
                //if false, don't add the return tickets, is a One-Way OR
                // it means an error occurred when they were booking their departure fligh
                departAdded = false;
                //to get the flights after the customer have booked
                //reloading the trip summary page to contain the user's new flights
                Starting.switchScenes("TripSummary");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //show the popup if it's successful
    public static void showSuccessPopup(String title, String content) {
        //this is the popup code, name the popup title, and then the text you want it to show
        //Creating an instance of Alert with AlertType.INFORMATION
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        //name the pop title
        alert.setTitle(title);
        //make it no header
        alert.setHeaderText(null);
        //content text
        alert.setContentText(content);
        //display the alert and wait until it was interacted or started
        alert.showAndWait();
    }
    //show the pop alert when there is an error
    public static void showErrorPopup(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}