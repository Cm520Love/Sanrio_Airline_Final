package SQL;
import java.sql.*;
import java.util.HashMap;

import java.util.ArrayList;
import GUI.BookTicketsController;

public class SearchFlight {

    private final static String[] DEPARTURE_TIMES = {"9:00 AM", "12:00 PM", "3:00 PM", "6:00 PM"};
    private final static String[] ARRIVAL_TIMES = {"11:30 AM", "2:30 PM", "5:30 PM", "8:30 PM"};
    private final static int MAX_PASSENGERS = 4;

    static PreparedStatement pStatement;
    static ResultSet result;

    public static ArrayList<String[]> getAirports(String stateAbbreviation) {
        // method grabs all the airports associated with a state
        java.util.ArrayList<String[]> cityAirports = new java.util.ArrayList<>();
        try {
            String[] city;

            // grabbing the city name and airport abbreviation
            pStatement = Access.conn.prepareStatement("SELECT CityName, AirportAbbreviation FROM Airport WHERE StateAbbreviation = ?");
            pStatement.setString(1, stateAbbreviation);

            result = pStatement.executeQuery();

            while (result.next()) {
                // grabbing information for a single city
                city = new String[1];
                city[0] = result.getString("CityName") + ", " + result.getString("AirportAbbreviation");

                // adding the single city to the arraylist that contains all the city/airports for a state
                cityAirports.add(city);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            return cityAirports;
        }
    }

    public static HashMap<Integer, ArrayList<String>> checkFlights(String[] flightInfo) {
        // method checks if flights exist
        // no? create them
        // regardless, return the flights
        try {
            // grabbing the number of flights with criteria as 'num'
            pStatement = Access.conn.prepareStatement(
                    "SELECT COUNT(*) AS num FROM Flight WHERE DepartureAirport = ? AND ArrivalAirport = ? AND DepartureDate = ?"
            );

            pStatement.setString(1, flightInfo[0]);
            pStatement.setString(2, flightInfo[1]);
            pStatement.setString(3, flightInfo[2]);
            result = pStatement.executeQuery();
            result.next();

            // if there are no flights, create them
            if ((result.getInt("num")) == 0) {
                createFlights(flightInfo);
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            // regardless of whether there is flights, return them
            return getFlights(flightInfo);
        }


    }

    public static void createFlights(String[] airportInformation) {
        // method creates the flights if it does not already exist in the database
        // ensures that there will be flights available no matter which day they choose to fly
        try {
            // get the max flightID from flight table
            pStatement = Access.conn.prepareStatement("SELECT MAX(FlightID) AS num FROM Flight");
            result = pStatement.executeQuery();
            result.next();

            // increment the flightID by one because it is a new flight
            int flightID = result.getInt("num") + 1;

            // iterating 4 times to create 4 flights
            for (int i = 0; i < 4; i++) {
                pStatement = Access.conn.prepareStatement(
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
        // hashmap that contains available flights
        // key: flightID, type int
        // value: flight information, type ArrayList<String>
        HashMap<Integer, ArrayList<String>> availableFlights = new HashMap<>();
        ArrayList<String> singleFlight;
        int flightID;

        try {
            // query that grabs all flights with criteria
            pStatement = Access.conn.prepareStatement(
                    "SELECT * FROM Flight WHERE DepartureAirport = ? AND ArrivalAirport = ? AND DepartureDate = ?"
            );
            pStatement.setString(1, flightInfo[0]);
            pStatement.setString(2, flightInfo[1]);
            pStatement.setString(3, flightInfo[2]);
            result = pStatement.executeQuery();

            while (result.next()) {
                // grabbing flight information for a single flight
                singleFlight = new ArrayList<>();

                // column 1 is the flightID, for loop will start at column 2
                flightID = result.getInt(1);
                for (int i = 2; i <= 6; i++) {
                        singleFlight.add(result.getString(i));
                }
                singleFlight.add(String.valueOf(result.getInt(7)));

                // add another key:value pair into the hashmap
                availableFlights.put(flightID, singleFlight);
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return availableFlights;

    }

    public static boolean addFlight(String Username, String DepartureDate, String DepartureTime, String ArrivalTime, String FlightID) {
        try {
            // Check if the user has already booked this flight
            if (hasUserBookedFlight(Username, FlightID)) {
                // Show a popup indicating that the user has already booked this flight
                BookTicketsController.showErrorPopup("Booking Error", "You have already booked this flight.");
                return false;
            }
            if (ConflictedTimeFlights(
                    GUI.Starting.getCurrentUser(),
                    DepartureDate,
                    DepartureTime,
                    ArrivalTime
            )) {
                BookTicketsController.showErrorPopup("Booking Error", "Time and date conflict with another booked flight");
                return false;
            }
            if (isFull(FlightID)) {

                BookTicketsController.showErrorPopup("Booking Error", "Flight was full. Please book another time");
                return false;
            }

            // Insert the new booking information
            PreparedStatement ps = Access.conn.prepareStatement("INSERT INTO BookedFlights VALUES (?,?,?,?,?)");
            ps.setString(1, Username);
            ps.setString(2, DepartureDate);
            ps.setString(3, DepartureTime);
            ps.setString(4, ArrivalTime);
            ps.setString(5, FlightID);
            ps.execute();

            incrementPassengers(FlightID); //increment the number of passengers to the flight

            // show success pop up
            BookTicketsController.showSuccessPopup("Booking Successful", "You have successfully booked the flight.");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();

            // show error popup
            BookTicketsController.showErrorPopup("Booking Error", "An error occurred while booking the flight. Please try again.");
            return false;
        }
    }



    private static void incrementPassengers(String FlightID) {
        try {
            PreparedStatement ps = Access.conn.prepareStatement("SELECT Passengers FROM Flight WHERE FlightID = ?");
            PreparedStatement updatedStatement = Access.conn.prepareStatement("UPDATE Flight SET Passengers = ? WHERE FlightID = ?");
            ps.setInt(1, Integer.parseInt(FlightID));
            ResultSet rs = ps.executeQuery();
            rs.next();
            updatedStatement.setInt(1, rs.getInt(1) + 1); //add the passenger for each booked
            updatedStatement.setInt(2, Integer.parseInt(FlightID)); // incrementing passengers to specific flights
            updatedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static boolean ConflictedTimeFlights(String Username, String DepartureDate, String DepartureTime, String ArrivalTime) {
        try {
            PreparedStatement ps = Access.conn.prepareStatement("SELECT COUNT(*) AS Num FROM BookedFlights WHERE Username = ? AND DepartureDate = ? AND DepartureTime = ? AND ArrivalTime = ?");
            ps.setString(1, Username);
            ps.setString(2, DepartureDate);
            ps.setString(3, DepartureTime);
            ps.setString(4, ArrivalTime);

            ResultSet rs = ps.executeQuery();  // Returns true if a record is found
            rs.next();
            if ((rs.getInt("Num")) != 0) {
                // meaning user hasn't booked a flight with this date and time
                return true;
            } else {
                return false; //it means user have booked a flight with this time and day
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;

        }
    }

    //Has this user booked this flight already
    private static boolean hasUserBookedFlight(String username, String flightID) {
        try {
            PreparedStatement ps = Access.conn.prepareStatement("SELECT * FROM BookedFlights WHERE Username = ? AND FlightID = ?");
            ps.setString(1, username);
            ps.setString(2, flightID);
            return ps.executeQuery().next();  // Returns true if a record is found
        } catch (SQLException e) {
            e.printStackTrace();
            return false;

        }

    }
    private static boolean isFull(String flightID) {
        try {
            PreparedStatement ps = Access.conn.prepareStatement("SELECT Passengers FROM Flight WHERE FlightID = ?");
            ps.setString(1, flightID);
            ResultSet rs = ps.executeQuery();
            rs.next();
            if ((rs.getInt(1)) == MAX_PASSENGERS) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static void decrementPassengers(String FlightID){
        try{
            PreparedStatement ps = Access.conn.prepareStatement("SELECT Passengers FROM Flight WHERE FlightID = ?");
            PreparedStatement updatedStatement = Access.conn.prepareStatement("UPDATE Flight SET Passengers = ? WHERE FligHtID =?");
            ps.setInt(1, Integer.parseInt(FlightID));//ParseInt makes whatever the string you convert into numbers or integer to able to do calculations
            ResultSet rs = ps.executeQuery();
            rs.next();
            updatedStatement.setInt(1, rs.getInt(1)-1);
            updatedStatement.setInt(2, Integer.parseInt(FlightID));
            updatedStatement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
