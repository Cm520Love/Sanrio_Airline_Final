package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.fxml.Initializable;
import Account.UserInformation.State;
import java.util.ArrayList;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.time.LocalDate;
import javafx.fxml.*;
import javafx.scene.*;

import Account.FlightInformation.*;

public class SearchFlightController implements Initializable{

    static ArrayList<Account.Information> flightInformation;


    @FXML private ComboBox<String> fromStateComboBox;
    @FXML private ComboBox<String> fromCityComboBox;
    @FXML private ComboBox<String> toStateComboBox;
    @FXML private ComboBox<String> toCityComboBox;
    @FXML private ChoiceBox<String> tripTypeChoiceBox;
    @FXML private Pane returnDatePane;
    @FXML private DatePicker departDatePicker;
    @FXML private DatePicker returnDatePicker;
    @FXML private Label returnError;
    @FXML private Label fromStateError;
    @FXML private Label fromCityError;
    @FXML private Label toStateError;
    @FXML private Label toCityError;

    @Override
    public void initialize(java.net.URL url, java.util.ResourceBundle resourceBundle) {

        setStateListener();

        tripTypeChoiceBox.getItems().addAll("Round", "One-Way");
        tripTypeChoiceBox.setValue("Round");
        setTripTypeListener();

        departDatePicker.setValue(LocalDate.now());
        setDatePickerListener();

    }
    private void setStateListener() {
        // adding all the available states a user can select from
        fromStateComboBox.getItems().addAll(State.getStateAbbreviations());

        fromStateComboBox.getSelectionModel().selectedItemProperty().addListener(
                (properties, oldValue, newValue) -> {
                    // when user selects a new state, clear the departure cities and arrival states
                    fromCityComboBox.getItems().clear();
                    toStateComboBox.getItems().clear();

                    // add all the available states to the arrival states, BUT remove the state that the user
                    // selected for their departure state
                    toStateComboBox.getItems().addAll(State.getStateAbbreviations());
                    toStateComboBox.getItems().remove(newValue);

                    // grab all the associated airports + cities and place it in the departure city combobox
                    final ArrayList<String[]> cityAirports = SQL.SearchFlight.getAirports(newValue);
                    for (String[] city: cityAirports) {
                        fromCityComboBox.getItems().addAll(city);
                    }
                });

        toStateComboBox.getSelectionModel().selectedItemProperty().addListener(
                (properties, oldValue, newValue) -> {
                    // clear the arrival cities every time the user changes the arrival state
                    toCityComboBox.getItems().clear();

                    // add all the arrival cities
                    final ArrayList<String[]> cityAirports = SQL.SearchFlight.getAirports(newValue);
                    for (String[] city: cityAirports) {
                        toCityComboBox.getItems().addAll(city);
                    }
                }
        );

    }

    private void setTripTypeListener() {
        // method that displays or removes the option for the user to select a return date
        // depends on whether the user selected round or one-way trip
        tripTypeChoiceBox.getSelectionModel().selectedItemProperty().addListener(
                (properties, oldValue, newValue) -> {
                    switch (newValue) {
                        case "Round":
                            returnDatePane.setVisible(true);
                            returnError.setText("");
                            returnDatePicker.setValue(null);
                            break;
                        case "One-Way":
                            returnDatePane.setVisible(false);
                    }
                });
    }

    private void setDatePickerListener() {
        // get the departure date and store it in a list
        // allows you to change the contents despite the variable being "final"
        final LocalDate[] selectedDepartDate = {departDatePicker.getValue()};
        departDatePicker.valueProperty().addListener(
                (properties, oldValue, newValue) -> {
                    // place the selected date in the list
                    selectedDepartDate[0] = newValue;
                    // every time the user selects a new departure date, clear the date in the return date
                    returnDatePicker.getEditor().clear();
                });

        departDatePicker.setDayCellFactory(args -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                // method will disable the user from selecting departure dates before today's date or whichever date they pick
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now()) < 0);
            }
        });

        returnDatePicker.setDayCellFactory(args -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                // method will disable the user from selecting dates prior to the selected departure date
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(selectedDepartDate[0]) < 0);
            }
        });
    }

    @FXML
    void onMyTripsHyperlinkClicked() {
        System.out.println("Heading to Your Trips");
        Starting.switchScenes("TripSummary");

    }
    @FXML
    void onLogoutHyperlinkClicked() {
        System.out.println("logging out and heading to main menu");
        Starting.switchScenes("MainMenuNoAccess");
        Starting.setCurrentUser("");
    }

    @FXML
    private void onMainMenuHyperlinkClicked() {
        System.out.println("heading back to the main menu");
        Starting.switchScenes("MainMenuAccess");
    }


    @FXML
    private void onSearchFlightsButtonClicked() {
        try {
            clearEntryErrors();
            flightInformation = testFlightInformation();
            // once all the required entry fields are filled, continue with searching a flight for the user
            if (canContinueSearchFlights(flightInformation)) {
                System.out.println("Searching for flights with your criteria...");
                VOFlightInformation.setCurrentFlightInformation(flightInformation);
                Starting.switchScenes("BookTickets");

            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    private void clearEntryErrors() {
        returnError.setText("");
        fromStateError.setText("");
        fromCityError.setText("");
        toStateError.setText("");
        toCityError.setText("");
    }

    private ArrayList<Account.Information> testFlightInformation() {
        // creating a flight information value object
        VOFlightInformation newFlight = new VOFlightInformation();

        newFlight.tripType = new TripType(tripTypeChoiceBox.getValue());
        newFlight.departDate = new DepartDate(departDatePicker.getValue());
        newFlight.returnDate = new ReturnDate(returnDatePicker.getValue(), newFlight.tripType);
        newFlight.fromState = new FromState(fromStateComboBox.getValue());
        newFlight.fromCity = new FromCity(fromCityComboBox.getValue());
        newFlight.toState = new ToState(toStateComboBox.getValue());
        newFlight.toCity = new ToCity(toCityComboBox.getValue());

        // return the arraylist of flight criteria information
        return newFlight.getFlightInformation();
    }
    private boolean canContinueSearchFlights(ArrayList<Account.Information> flightInformation) {
        boolean continueSearchFlights = true;

        // iterate through the flight criteria information
        for (Account.Information info: flightInformation) {
            // if any of the information is not valid for any reason
            // the user cannot continue to book flights
            if (info.isValid() ) {
                continue;
            }
            continueSearchFlights = false;

            // display what the user needs to correct
            switch (info.getClass().getSimpleName()) {
                case "ReturnDate":
                    returnError.setText(info.getErrorMsg());
                    break;
                case "FromState":
                    fromStateError.setText(info.getErrorMsg());
                    break;
                case "FromCity":
                    fromCityError.setText(info.getErrorMsg());
                    break;
                case "ToState":
                    toStateError.setText(info.getErrorMsg());
                    break;
                case "ToCity":
                    toCityError.setText(info.getErrorMsg());
                    break;

            }
        }

        return continueSearchFlights;
    }


}
