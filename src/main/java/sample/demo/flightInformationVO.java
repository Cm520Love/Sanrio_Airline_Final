package sample.demo;
import userInformation.UserInformation;
import userInformation.flightInformation.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
public class flightInformationVO {
    DepartDate departDate;
    ReturnDate returnDate;
    TripType tripType;
    FromState fromState;
    FromCity fromCity;
    ToState toState;
    ToCity toCity;

    private static HashMap<String, ArrayList<String>> departFlights;
    private static HashMap<String, ArrayList<String>> returnFlights;

    private static ArrayList<UserInformation> currentFlightInfo;

    ArrayList<userInformation.UserInformation> getFlightInformation() {
        ArrayList<userInformation.UserInformation> flightInformation = new ArrayList<>(Arrays.asList(
                tripType, departDate, returnDate , fromState, fromCity, toState, toCity
        ));
        return flightInformation;
    }

    public static java.util.HashMap<String, java.util.ArrayList<String>> getDepartFlights() {
        return departFlights;
    }

    public static void setDepartFlights(java.util.HashMap<String, java.util.ArrayList<String>> departFlights) {
        flightInformationVO.departFlights = departFlights;
    }

    public static java.util.HashMap<String, java.util.ArrayList<String>> getReturnFlights() {
        return returnFlights;
    }

    public static void setReturnFlights(java.util.HashMap<String, java.util.ArrayList<String>> returnFlights) {
        flightInformationVO.returnFlights = returnFlights;
    }

    public static java.util.ArrayList<userInformation.UserInformation> getCurrentFlightInfo() {
        return currentFlightInfo;
    }

    public static void setCurrentFlightInfo(java.util.ArrayList<userInformation.UserInformation> currentFlightInfo) {
        flightInformationVO.currentFlightInfo = currentFlightInfo;
    }
}
