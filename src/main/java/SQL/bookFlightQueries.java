package SQL;
import java.sql.*;
import java.util.HashMap;

import sample.demo.Starting;
import userInformation.UserInformation;
import static sample.demo.Starting.conn;

import java.util.ArrayList;
import sample.demo.flightInformationVO;
public class bookFlightQueries {

    private final static String[] DEPARTURE_TIMES = {"9:00", "12:00", "3:00", "6:00"};
    private final static String[] ARRIVAL_TIMES = {"11:30", "2:30", "5:30", "8:30"};
    static PreparedStatement pStatement;
    static ResultSet result;

    private final static int MAX_PASSENGERS = 4;

    public static int getMaxPassengers() {
        return MAX_PASSENGERS;
    }
    public static ArrayList<String[]> getAirports(String stateAbbreviation) {
        java.util.ArrayList<String[]> cityAirports = new java.util.ArrayList<>();
        try {
            String[] city;

            pStatement = conn.prepareStatement("SELECT CityName, AirportAbbreviation FROM Airport WHERE StateAbbreviation = ?");
            pStatement.setString(1, stateAbbreviation);

            result = pStatement.executeQuery();

            while (result.next()) {
                city = new String[1];
                city[0] = result.getString("CityName") + ", " + result.getString("AirportAbbreviation");
                cityAirports.add(city);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            return cityAirports;
        }
    }
    // another method that checks whether its a return or one way
    // if return call the check departure flights and check return flights
    // if one way, only call the departure flights

    public static HashMap<Integer, ArrayList<String>>  retrieveFlights(String[] flightInfo) {
        /*




        switch (flightInformation.get(0).getInfo()) {
            case "One-Way":
                System.out.println("checking one way flights");
                checkDepartureFlights(new String[] {fromCity, toCity, departureDate});
                allFlights = getFlights(new String[] {fromCity, toCity, departureDate});
                break;
            case "Round":
                System.out.println("checking round trip flights");
                checkDepartureFlights(new String[] {fromCity, toCity, departureDate});
                allFlights = getFlights(new String[] {fromCity, toCity, departureDate});
                checkDepartureFlights(new String[] {toCity, fromCity, returnDate});
                allFlights.putAll(getFlights(new String[] {toCity, fromCity, returnDate}));

        }
        */

        try {
            pStatement = conn.prepareStatement(
                    "SELECT COUNT(*) AS num FROM Flight WHERE DepartureAirport = ? AND ArrivalAirport = ? AND DepartureDate = ?"
            );
            pStatement.setString(1, flightInfo[0]);
            pStatement.setString(2, flightInfo[1]);
            pStatement.setString(3, flightInfo[2]);


            result = pStatement.executeQuery();
            result.next();
            if ((result.getInt("num")) == 0) {
                createFlights(flightInfo);
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            return getFlights(flightInfo);
        }


    }

    public static void createFlights(String[] airportInformation) {
        try {
            pStatement = conn.prepareStatement("SELECT MAX(FlightID) AS num FROM Flight");
            result = pStatement.executeQuery();
            result.next();
            int flightID = result.getInt("num") + 1;
            for (int i = 0; i < 4; i++) {
                pStatement = conn.prepareStatement(
                        "INSERT INTO Flight VALUES (?, ? , ?, ?, ?, ?, 0);"
                );
                pStatement.setInt(1, flightID++);
                pStatement.setString(2, airportInformation[2]);
                pStatement.setString(3, airportInformation[0]);
                pStatement.setString(4, airportInformation[1]);
                pStatement.setString(5, DEPARTURE_TIMES[i]);
                pStatement.setString(6, ARRIVAL_TIMES[i]);
                pStatement.execute();
            }

        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }



    public static HashMap<Integer, ArrayList<String>> getFlights(String[] flightInfo) {
        HashMap<Integer, ArrayList<String>> availableFlights = new HashMap<>();
        ArrayList<String> singleFlight;
        int flightID;
        try {
            pStatement = conn.prepareStatement(
                    "SELECT * FROM Flight WHERE DepartureAirport = ? AND ArrivalAirport = ? AND DepartureDate = ?"
            );
            pStatement.setString(1, flightInfo[0]);
            pStatement.setString(2, flightInfo[1]);
            pStatement.setString(3, flightInfo[2]);

            result = pStatement.executeQuery();
            while (result.next()) {
                singleFlight = new ArrayList<>();
                flightID = result.getInt(1);
                for (int i = 2; i <= 6; i++) {
                        singleFlight.add(result.getString(i).toString());
                }
                singleFlight.add(String.valueOf(result.getInt(7)));
                availableFlights.put(flightID, singleFlight);
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return availableFlights;

    }
}
