package SQL;
import java.sql.*;
import java.util.HashMap;
import userInformation.UserInformation;
import static sample.demo.Starting.conn;

import java.util.ArrayList;
public class bookFlightQueries {

    private final static String[] DEPARTURE_TIMES = {"9:00", "12:00", "3:00", "6:00"};
    private final static String[] ARRIVAL_TIMES = {"11:30", "2:30", "5:30", "8:30"};
    static PreparedStatement pStatement;
    static ResultSet result;

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

    public static HashMap<String, ArrayList<String>>  retrieveFlights(ArrayList<UserInformation> flightInformation) {
        String fromCity = flightInformation.get(4).getInfo();
        fromCity = fromCity.substring(fromCity.length() - 3);
        String toCity = flightInformation.get(6).getInfo();
        toCity = toCity.substring(toCity.length() - 3);
        String departureDate = flightInformation.get(1).getInfo();
        String returnDate = flightInformation.get(2).getInfo();

        HashMap<String, ArrayList<String>> allFlights = new HashMap<>();
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

        return allFlights;

    }
    public static void checkDepartureFlights(String[] airportInformation) {
        try {
            pStatement = conn.prepareStatement(
                "SELECT COUNT(*) AS numFlights FROM Flight WHERE DepartureAirport = ? AND ArrivalAirport = ? AND DepartureDate = ?"
            );
            pStatement.setString(1, airportInformation[0]);
            pStatement.setString(2, airportInformation[1]);
            pStatement.setString(3, airportInformation[2]);

            result = pStatement.executeQuery();
            result.next();
            if ((result.getInt("numFlights")) >= 1) {
                System.out.println("there are flights available!!!");
            } else {
                System.out.println("no flights available");
                System.out.println("creating flights now....");
                createFlights(airportInformation);
                System.out.println("flights have been created!");
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
  
    }

    public static void createFlights(String[] airportInformation) {
        try {
            pStatement = conn.prepareStatement("SELECT MAX(FlightID) as num FROM Flight");
            result = pStatement.executeQuery();
            result.next();
            int flightID = result.getInt("num") + 1;
            for (int i = 0; i < 4; i++) {
                pStatement = conn.prepareStatement(
                        "INSERT INTO Flight VALUES (?, ?, ? , ?, ?, ?, ?, 0);"
                );
                pStatement.setString(1, flightID++ + "");
                pStatement.setString(2, "DELTA");
                pStatement.setString(3, airportInformation[2]);
                pStatement.setString(4, airportInformation[0]);
                pStatement.setString(5, airportInformation[1]);
                pStatement.setString(6, DEPARTURE_TIMES[i]);
                pStatement.setString(7, ARRIVAL_TIMES[i]);
                pStatement.execute();
            }

        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }



    public static HashMap<String, ArrayList<String>> getFlights(String[] airportInformation) {
        HashMap<String, ArrayList<String>> allFlights = new HashMap<>();
        try {
            pStatement = conn.prepareStatement(
            "SELECT * FROM Flight WHERE DepartureAirport = ? AND ArrivalAirport = ? AND DepartureDate = ?"
            );
            pStatement.setString(1, airportInformation[0]);
            pStatement.setString(2, airportInformation[1]);
            pStatement.setString(3, airportInformation[2]);

            result = pStatement.executeQuery();


            ArrayList<String> singleFlight;
            while (result.next()) {
                singleFlight = new ArrayList<>();
                for (int i = 1; i <= 8; i++) {
                        singleFlight.add(result.getString(i).toString());
                }
                allFlights.put(singleFlight.get(0), singleFlight);
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return allFlights;
    }

}
