package sample.demo;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.event.ActionEvent;
import SQL.bookFlightQueries;
import javafx.fxml.Initializable;
import userInformation.UserInformation;
import userInformation.userProfile.State;
import java.util.ArrayList;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.time.LocalDate;

import userInformation.flightInformation.*;
public class BookFlightController implements Initializable{

    static ArrayList<userInformation.UserInformation> flightInformation;

    @FXML
    private Hyperlink myTripsHyperlink;

    @FXML
    private Hyperlink logoutHyperlink;

    @FXML
    private Hyperlink mainMenuHyperlink;

    @FXML
    private ComboBox<String> fromStateComboBox;

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
    @FXML private Label loadingFlightsLabel;

    @Override
    public void initialize(java.net.URL url, java.util.ResourceBundle resourceBundle) {
        setFromStateListener();
        tripTypeChoiceBox.getItems().addAll("Round", "One-Way");
        tripTypeChoiceBox.setValue("Round");
        setTripTypeListener();

        departDatePicker.setValue(LocalDate.now());
        setDatePickerListener();
        loadingFlightsLabel.setVisible(false);
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



    private void setFromStateListener() {
        fromStateComboBox.getItems().addAll(State.getStateAbbreviations());
        fromStateComboBox.getSelectionModel().selectedItemProperty().addListener(
                (properties, oldValue, newValue) -> {
                    final ArrayList<String> adjustedStates = State.getStateAbbreviations();
                    fromCityComboBox.getItems().clear();
                    toStateComboBox.getItems().clear();
                    adjustedStates.remove(newValue);
                    toStateComboBox.getItems().addAll(adjustedStates);
                    final ArrayList<String[]> cityAirports = bookFlightQueries.getAirports(newValue);
                    for (String[] city: cityAirports) {
                        fromCityComboBox.getItems().addAll(city);
                    }
                });

        toStateComboBox.getSelectionModel().selectedItemProperty().addListener(
                (properties, oldValue, newValue) -> {
                    toCityComboBox.getItems().clear();
                    final ArrayList<String[]> cityAirports = bookFlightQueries.getAirports(newValue);
                    for (String[] city: cityAirports) {
                        toCityComboBox.getItems().addAll(city);
                    }
                }
        );

    }

    @FXML
    void onMyTripsHyperlinkClicked(ActionEvent event) {

    }

    private boolean displayEntryErrors(ArrayList<UserInformation> flightInformation) {
        boolean continueSearchFlights = true;

        for (UserInformation info: flightInformation) {
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


    @FXML
    void onLogoutHyperlinkClicked(ActionEvent event) {
        System.out.println("logging out and heading to main menu");
        Starting.window.setScene(Starting.mainMenuNoAccessScene);
    }



    @FXML
    private void onMainMenuHyperlinkClicked(ActionEvent event) {
        System.out.println("heading back to the main menu");
        Starting.window.setScene(Starting.mainMenuAccessScene);
    }

    @FXML
    private void onSearchFlightsButtonClicked() {
        clearEntryErrors();
        flightInformation = new ArrayList<>();
        flightInformation = testFlightInformation();
        if (displayEntryErrors(flightInformation)) {
            System.out.println("Searching for flights with your criteria...");
            loadingFlightsLabel.setVisible(true);
            Starting.window.setScene(Starting.flightTicketsScene);
        }

    }

    private ArrayList<UserInformation> testFlightInformation() {
        flightInformationVO newFlight = new flightInformationVO();

        newFlight.tripType = new TripType(tripTypeChoiceBox.getValue());
        newFlight.departDate = new DepartDate(departDatePicker.getValue());
        newFlight.returnDate = new ReturnDate(returnDatePicker.getValue(), newFlight.tripType);
        newFlight.fromState = new FromState(fromStateComboBox.getValue());
        newFlight.fromCity = new FromCity(fromCityComboBox.getValue());
        newFlight.toState = new ToState(toStateComboBox.getValue());
        newFlight.toCity = new ToCity(toCityComboBox.getValue());
        return newFlight.getFlightInformation();
    }

    private void clearEntryErrors() {
        returnError.setText("");
        fromStateError.setText("");
        fromCityError.setText("");
        toStateError.setText("");
        toCityError.setText("");
    }

}
