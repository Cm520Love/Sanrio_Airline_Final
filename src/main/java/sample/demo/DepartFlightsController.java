package sample.demo;
import javafx.fxml.*;
import java.util.HashMap;
import java.util.ArrayList;
import javafx.scene.control.*;
import SQL.bookFlightQueries;
public class DepartFlightsController {

    @FXML
    private TextField departureDateTextField;
    @FXML private TextField departureAirportTextField;
    @FXML private TextField arrivalAirportTextField;
    @FXML private TextField tripTypeTextField;
    @FXML private TextField ticket1DepartureTimeTextField;
    @FXML private TextField ticket1ArrivalTextField;
    @FXML private Button ticket1AddButton;

    static private HashMap<String, ArrayList<String>> allFlightTickets;
    static private String tripType;
    //public FlightTicketsController(HashMap<String, ArrayList<String>> allFlightTickets) {
    //    this.allFlightTickets = allFlightTickets;
    //}

    public void retrieveFlightTickets() {
        DepartFlightsController.allFlightTickets = bookFlightQueries.retrieveFlights(BookFlightController.flightInformation);
        setFlightInformation();


    }

    public void setFlightInformation() {
        for (String key: allFlightTickets.keySet()) {
            String from = (allFlightTickets.get(key)).get(3);
            String to = (allFlightTickets.get(key)).get(4);
            System.out.println(key + ": " + allFlightTickets.get(key));
        }
    }
}
