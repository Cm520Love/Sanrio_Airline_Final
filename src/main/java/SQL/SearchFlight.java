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
        java.util.ArrayList<String[]> cityAirports = new java.util.ArrayList<>();
        try {
            String[] city;

            pStatement = Access.conn.prepareStatement("SELECT CityName, AirportAbbreviation FROM Airport WHERE StateAbbreviation = ?");
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

    public static HashMap<Integer, ArrayList<String>> retrieveFlights(String[] flightInfo) {
        try {
            pStatement = Access.conn.prepareStatement(
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
            pStatement = Access.conn.prepareStatement("SELECT MAX(FlightID) AS num FROM Flight");
            result = pStatement.executeQuery();
            result.next();
            int flightID = result.getInt("num") + 1;
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
        HashMap<Integer, ArrayList<String>> availableFlights = new HashMap<>();
        ArrayList<String> singleFlight;
        int flightID;

        try {
            pStatement = Access.conn.prepareStatement(
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

    public static boolean addInformationToDatabase(String Username, String DepartureDate, String DepartureTime, String ArrivalTime, String FlightID) {
        try {
            // Check if the user has already booked this flight
            if (hasUserBookedFlight(Username, FlightID)) {
                // Show a popup indicating that the user has already booked this flight
                BookTicketsController.showErrorPopup("Booking Error", "You have already booked this flight.");
                return false;  // Do not proceed with the insertion
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
            incrementPassengers(FlightID);

            // Optionally, you can show a success popup or perform additional logic
            BookTicketsController.showSuccessPopup("Booking Successful", "You have successfully booked the flight.");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the SQLException appropriately (e.g., show an error popup)
            BookTicketsController.showErrorPopup("Booking Error", "An error occurred while booking the flight. Please try again.");
            return false;
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
            // Handle the SQLException appropriately
            return false;

        }
    }

    private static boolean hasUserBookedFlight(String username, String flightID) {
        try {
            PreparedStatement ps = Access.conn.prepareStatement("SELECT * FROM BookedFlights WHERE Username = ? AND FlightID = ?");
            ps.setString(1, username);
            ps.setString(2, flightID);
            return ps.executeQuery().next();  // Returns true if a record is found
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the SQLException appropriately
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
            // Handle the SQLException appropriately

        }
        return false;
    }
    private static void incrementPassengers(String FlightID) {
        try {
            PreparedStatement ps = Access.conn.prepareStatement("SELECT Passengers FROM Flight WHERE FlightID = ?");
            PreparedStatement updatedStatement = Access.conn.prepareStatement("UPDATE Flight SET Passengers = ? WHERE FlightID = ?");
            ps.setInt(1, Integer.parseInt(FlightID));
            ResultSet rs = ps.executeQuery();  // Returns true if a record is found
            rs.next();
            updatedStatement.setInt(1, rs.getInt(1) + 1); //add the passenger for each booked
            updatedStatement.setInt(2, Integer.parseInt(FlightID)); // this is for specific flightID so it won't add passengers to all flightID
            updatedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
