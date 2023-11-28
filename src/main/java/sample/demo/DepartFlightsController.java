package sample.demo;
import javafx.fxml.*;
import java.util.HashMap;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.scene.control.*;
public class DepartFlightsController implements Initializable {

    @FXML
    private Button DepartTicket1AddButton;

    @FXML
    private Button DepartTicket2AddButton;

    @FXML
    private Button DepartTicket3AddButton;

    @FXML
    private Button DepartTicket4AddButton;

    @FXML
    private TextField DepartTicketNo001;

    @FXML
    private TextField DepartTicketNo002;

    @FXML
    private TextField DepartTicketNo003;

    @FXML
    private TextField DepartTicketNo004;

    @FXML
    private TextField ReturnTicketDate;

    @FXML
    private TextField ReturnTicketFrom;

    @FXML
    private ImageView ReturnTicketImageView;

    @FXML
    private TextField ReturnTicketTo;

    @FXML
    private TextField ReturnTicketType;

    @FXML
    private Button SanrioAirlineButton;

    @FXML
    private TextField ticket1DepartArrivalTimeTextfield;

    @FXML
    private TextField ticket1DepartTimeTextfield;

    @FXML
    private TextField ticket2DepartArrivalTimeTextfield;

    @FXML
    private TextField ticket2DepartTimeTextfield;

    @FXML
    private TextField ticket3DepartArrivalTimeTextfield;

    @FXML
    private TextField ticket3DepartTimeTextfield;

    @FXML
    private TextField ticket4DepartArrivalTimeTextfield;

    @FXML
    private TextField ticket4DepartTimeTextfield;

    @FXML
    void onDepartTicket1AddButtonClicked(ActionEvent event) {

    }

    @FXML
    void onDepartTicket2AddButtonClicked(ActionEvent event) {

    }

    @FXML
    void onDepartTicket3AddButtonClicked(ActionEvent event) {

    }

    @FXML
    void onDepartTicket4AddButtonClicked(ActionEvent event) {

    }

    @FXML
    void onSanrioAirlineButtonClicked(ActionEvent event) {

    }

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
