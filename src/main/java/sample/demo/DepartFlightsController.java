package sample.demo;
import javafx.fxml.*;
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

}
