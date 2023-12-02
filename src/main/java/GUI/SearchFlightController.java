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
        fromStateComboBox.getItems().addAll(State.getStateAbbreviations());
        fromStateComboBox.getSelectionModel().selectedItemProperty().addListener(
                (properties, oldValue, newValue) -> {
                    fromCityComboBox.getItems().clear();
                    toStateComboBox.getItems().clear();
                    toStateComboBox.getItems().addAll(State.getStateAbbreviations());
                    toStateComboBox.getItems().remove(newValue);
                    final ArrayList<String[]> cityAirports = SQL.SearchFlight.getAirports(newValue);
                    for (String[] city: cityAirports) {
                        fromCityComboBox.getItems().addAll(city);
                    }
                });

        toStateComboBox.getSelectionModel().selectedItemProperty().addListener(
                (properties, oldValue, newValue) -> {
                    toCityComboBox.getItems().clear();
                    final ArrayList<String[]> cityAirports = SQL.SearchFlight.getAirports(newValue);
                    for (String[] city: cityAirports) {
                        toCityComboBox.getItems().addAll(city);
                    }
                }
        );

    }

    private void setTripTypeListener() {
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
        final java.time.LocalDate[] selectedDepartDate = {departDatePicker.getValue()};
        departDatePicker.valueProperty().addListener(
                (properties, oldValue, newValue) -> {
                    selectedDepartDate[0] = newValue;
                    returnDatePicker.getEditor().clear();
                });

        departDatePicker.setDayCellFactory(args -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now()) < 0);
            }
        });

        returnDatePicker.setDayCellFactory(args -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(selectedDepartDate[0]) < 0);
            }
        });
    }

    @FXML
    void onMyTripsHyperlinkClicked() {
        System.out.println("Heading to Your Trips");
        Starting.window.setScene(GUI.Starting.tripsummaryScene);
    }
    @FXML
    void onLogoutHyperlinkClicked() {
        System.out.println("logging out and heading to main menu");
        Starting.window.setScene(Starting.mainMenuNoAccessScene);
        Starting.setCurrentUser("");
    }

    @FXML
    private void onMainMenuHyperlinkClicked() {
        System.out.println("heading back to the main menu");
        Starting.window.setScene(Starting.mainMenuAccessScene);
    }


    @FXML
    private void onSearchFlightsButtonClicked() {
        try {
            clearEntryErrors();
            flightInformation = testFlightInformation();
            if (canContinueSearchFlights(flightInformation)) {
                System.out.println("Searching for flights with your criteria...");
                VOFlightInformation.setCurrentFlightInformation(flightInformation);
                Starting.bookTicketsPage = new FXMLLoader(getClass().getResource("BookTickets.fxml"));
                Starting.bookTicketsScene = new Scene(Starting.bookTicketsPage.load());
                Starting.window.setScene(Starting.bookTicketsScene);
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
        VOFlightInformation newFlight = new VOFlightInformation();

        newFlight.tripType = new TripType(tripTypeChoiceBox.getValue());
        newFlight.departDate = new DepartDate(departDatePicker.getValue());
        newFlight.returnDate = new ReturnDate(returnDatePicker.getValue(), newFlight.tripType);
        newFlight.fromState = new FromState(fromStateComboBox.getValue());
        newFlight.fromCity = new FromCity(fromCityComboBox.getValue());
        newFlight.toState = new ToState(toStateComboBox.getValue());
        newFlight.toCity = new ToCity(toCityComboBox.getValue());
        return newFlight.getFlightInformation();
    }
    private boolean canContinueSearchFlights(ArrayList<Account.Information> flightInformation) {
        boolean continueSearchFlights = true;

        for (Account.Information info: flightInformation) {
            if (info.isValid() ) {
                continue;
            }
            continueSearchFlights = false;

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
