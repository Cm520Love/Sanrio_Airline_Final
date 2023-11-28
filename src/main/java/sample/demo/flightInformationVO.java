package sample.demo;
import userInformation.flightInformation.*;
import java.util.ArrayList;
import java.util.Arrays;
public class flightInformationVO {
    DepartDate departDate;
    ReturnDate returnDate;
    TripType tripType;
    FromState fromState;
    FromCity fromCity;
    ToState toState;
    ToCity toCity;

    ArrayList<userInformation.UserInformation> getFlightInformation() {
        ArrayList<userInformation.UserInformation> flightInformation = new ArrayList<>(Arrays.asList(
                tripType, departDate, returnDate , fromState, fromCity, toState, toCity
        ));
        return flightInformation;
    }

}
