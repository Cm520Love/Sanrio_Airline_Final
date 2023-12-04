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

    boolean returnAdded;

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
        allFlightTickets = SQL.SearchFlight.retrieveFlights(flightInfo);

        departureAirportTextField.setText(flightInfo[0]);
        arrivalAirportTextField.setText(flightInfo[1]);
        departureDateTextField.setText(flightInfo[2]);
        tripTypeTextField.setText(flightInformation.get(0).getInfo());

        returnAdded = false;
        displayFlightInformation();
    }

    void getReturnTickets() {
        String temp = flightInfo[0];
        flightInfo[0] = flightInfo[1];
        flightInfo[1] = temp;
        flightInfo[2] = flightInformation.get(2).getInfo();
        allFlightTickets = SQL.SearchFlight.retrieveFlights(flightInfo);


        bookFlightTitle.setText("Sanrio Return Flight Tickets");
        departureAirportTextField.setText(flightInfo[0]);
        arrivalAirportTextField.setText(flightInfo[1]);
        departureDateTextField.setText(flightInfo[2]);
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
        if (SearchFlight.addFlight(
                Starting.getCurrentUser(),
                departureDateTextField.getText(),
                ticket1DepartureTimeTextField.getText(),
                ticket1ArrivalTimeTextField.getText(),
                departFlightNo1Box.getText()
        )) {
            returnAdded = !returnAdded;
            System.out.println("Adding 1st Depart Tickets to Database....");
            finishBooking();
        }

        // Additional logic or scene transition if needed

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
            returnAdded = !returnAdded;
            System.out.println("Adding 2nd Depart Tickets to Database....");
            finishBooking();
        }

        // Additional logic or scene transition if needed
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
            returnAdded = !returnAdded;
            System.out.println("Adding 3rd Depart Tickets to Database....");
            finishBooking();
        }

        // Additional logic or scene transition if needed

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
            returnAdded = !returnAdded;
            System.out.println("Adding 4th Depart Tickets to Database....");
            finishBooking();
        }

        // Additional logic or scene transition if needed
    }

    private void finishBooking() {
        try {
            if (returnAdded && tripTypeTextField.getText().equals("Round")) {
                getReturnTickets();
            } else {
                returnAdded = false;
                Starting.tripSummaryPage = new FXMLLoader(getClass().getResource("TripSummary.fxml"));
                Starting.tripsummaryScene = new javafx.scene.Scene(Starting.tripSummaryPage.load());
                Starting.window.setScene(Starting.tripsummaryScene);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static void showSuccessPopup(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void showErrorPopup(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }





}