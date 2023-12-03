package SQL;


import java.sql.*;
import java.util.ArrayList;
import GUI.Starting.*;


public class CurrentUser {
    static java.sql.PreparedStatement pStatement;
    static ResultSet result;

    public static boolean login(String username, String password) {
        boolean accessGranted = false;
        try {
            pStatement = Access.conn.prepareStatement(
                    "SELECT * FROM Customer WHERE Username = ? AND Password = ?");
            pStatement.setString(1, username);
            pStatement.setString(2, password);

            result = pStatement.executeQuery();

            if (result.next()) {
                accessGranted = true;
            }
        } catch (java.sql.SQLException ex) {
            System.out.println("Error occurred.");
        } finally {
            System.out.println(accessGranted);
            return accessGranted;
        }

    }

    public static ArrayList<String> getUserFlights() {
        ArrayList<String> allUserFlights = new ArrayList<>();

        try {
            PreparedStatement ps = Access.conn.prepareStatement("SELECT FlightID FROM BookedFlights WHERE Username = ?");
            ps.setString(1, GUI.Starting.getCurrentUser());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                allUserFlights.add(rs.getInt("FlightID") + "");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allUserFlights;
    }

    public static boolean deleteUserFlight(String FlightID) {
        int rowsAffected = 0;
        try {
            PreparedStatement ps = Access.conn.prepareStatement("DELETE FROM BookedFlights WHERE FlightID = ?");
            ps.setInt(1, Integer.parseInt(FlightID));
            rowsAffected = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected > 0;

    }

    public static String getUserFlightDetails(String FlightID) {
        String departureDate = "";
        String departureTime = "";
        String arrivalTime = "";
        String departureAirport = "";
        String arrivalAirport = "";
        String username = "";
        try {
            PreparedStatement ps = Access.conn.prepareStatement("SELECT * FROM BookedFlights WHERE FlightID = ?");
            PreparedStatement ps2 = Access.conn.prepareStatement("SELECT DepartureAirport, ArrivalAirport FROM Flight WHERE FlightID = ?");
            ps.setInt(1, Integer.parseInt(FlightID));
            ps2.setInt(1, Integer.parseInt(FlightID));
            ResultSet rs = ps.executeQuery();
            ResultSet rs2 = ps2.executeQuery();

            if (rs.next() && rs2.next()) {
                departureDate = rs.getString("DepartureDate");
                departureTime = rs.getString("DepartureTime");
                departureAirport = rs2.getString("DepartureAirport");
                arrivalAirport = rs2.getString("ArrivalAirport");
                arrivalTime = rs.getString("ArrivalTime");
                username = rs.getString("Username");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return String.format("Username: %s%n Departure Airport: %s%n Arrival Airport: %s%n Departure Date: %s%nDeparture Time: %s%nArrival Time: %s",
                username, departureAirport, arrivalAirport, departureDate, departureTime, arrivalTime);
    }
}
