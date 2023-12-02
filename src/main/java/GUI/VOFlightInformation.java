package GUI;
import Account.FlightInformation.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
public class VOFlightInformation {
    DepartDate departDate;
    ReturnDate returnDate;
    TripType tripType;
    FromState fromState;
    FromCity fromCity;
    ToState toState;
    ToCity toCity;


    private static ArrayList<Account.Information> currentFlightInformation;

    ArrayList<Account.Information> getFlightInformation() {
        ArrayList<Account.Information> flightInformation = new ArrayList<>(Arrays.asList(
                tripType, departDate, returnDate , fromState, fromCity, toState, toCity
        ));
        return flightInformation;
    }

    public static java.util.ArrayList<Account.Information> getCurrentFlightInformation() {
        return currentFlightInformation;
    }

    public static void setCurrentFlightInformation(java.util.ArrayList<Account.Information> currentFlightInfo) {
        VOFlightInformation.currentFlightInformation = currentFlightInfo;
    }
}
