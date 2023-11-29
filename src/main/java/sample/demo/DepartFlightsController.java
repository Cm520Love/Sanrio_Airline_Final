package sample.demo;
import javafx.fxml.*;
import java.util.HashMap;
import java.util.ArrayList;
import javafx.scene.control.*;
import SQL.*;
public class DepartFlightsController {

    @FXML
    private static TextField departureDateTextField;
    @FXML private static TextField departureAirportTextField;
    @FXML private static TextField arrivalAirportTextField;
    @FXML private static TextField tripTypeTextField;
    @FXML private static TextField ticket1DepartureTimeTextField;
    @FXML private static TextField ticket1ArrivalTimeTextField;
    @FXML private static Button ticket1AddButton;

    static private HashMap<String, ArrayList<String>> allFlightTickets;
    //public FlightTicketsController(HashMap<String, ArrayList<String>> allFlightTickets) {
    //    this.allFlightTickets = allFlightTickets;
    //}


    public static void retrieveFlightTickets() {
        allFlightTickets = bookFlightQueries.retrieveFlights(sample.demo.BookFlightController.flightInformation);

        for (String key: allFlightTickets.keySet()) {
            System.out.println(key + ": " + allFlightTickets.get(key));
        }

        displayFlightInformation();

    }

    public static void displayFlightInformation() {
        //ticket1DepartureTimeTextField.setText(allFlightTickets.get(1).get(5));
        //ticket1ArrivalTimeTextField.setText(allFlightTickets.get(1).get(6));
    }
}
