package sample.demo;
import javafx.fxml.*;
import java.util.HashMap;
import java.util.ArrayList;
import javafx.scene.control.*
public class DepartFlightsController implements Initializable {

    @FXML
    private TextField departureDateTextField;
    @FXML private TextField departureAirportTextField;
    @FXML private TextField arrivalAirportTextField;
    @FXML private TextField tripTypeTextField;
    @FXML private TextField ticket1DepartureTimeTextField;
    @FXML private TextField ticket1ArrivalTextField;
    @FXML private Button ticket1AddButton;

    static private HashMap<String, ArrayList<String>> allFlightTickets;
    //public FlightTicketsController(HashMap<String, ArrayList<String>> allFlightTickets) {
    //    this.allFlightTickets = allFlightTickets;
    //}
    @Override
    public void initialize(java.net.URL url, java.util.ResourceBundle resourceBundle) {

    }

    public static void displayFlightTickets(HashMap<String, ArrayList<String>> allFlightTickets) {
        DepartFlightsController.allFlightTickets = allFlightTickets;

        for (String key: allFlightTickets.keySet()) {
            System.out.println(key + ": " + allFlightTickets.get(key));
        }

    }
}
