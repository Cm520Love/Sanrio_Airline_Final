package SQL;


import java.sql.*;
import java.util.ArrayList;


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

    //Grabbing all of the flights associate to the user for the ListView Box
    public static ArrayList<String> getUserFlights() {
        //create an arraylist that contains strings
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
        int rowsDeleted = 0; //starts with 0
        try {
            PreparedStatement ps = Access.conn.prepareStatement("DELETE FROM BookedFlights WHERE FlightID = ?");
            ps.setInt(1, Integer.parseInt(FlightID));
            rowsDeleted = ps.executeUpdate(); //give back the rows deleted

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsDeleted > 0;//if the rows is greater than 0, means its deleted

    }

    public static String getUserFlightDetails(String FlightID) {
        String departureDate = "";
        String departureTime = "";
        String arrivalTime = "";
        String departureAirport = "";
        String arrivalAirport = "";
        String username = "";
        try {
            //grabbing all from booked flights
            PreparedStatement ps = Access.conn.prepareStatement("SELECT * FROM BookedFlights WHERE FlightID = ?");
            //want the textarea to show the departure airport and arrival airport
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
        //how I want it to layout
        //%s is formatting the string, have to have it to let the systme know what argument is passed
        //%n is going to the next line
        return String.format("Username: %s%n Departure Airport: %s%n Arrival Airport: %s%n Departure Date: %s%nDeparture Time: %s%nArrival Time: %s",
                username, departureAirport, arrivalAirport, departureDate, departureTime, arrivalTime); //variables associated it
    }

    public static String[] getAllUserInformation(String username) {
        String[] userInformation = new String[11];
        try {
            PreparedStatement ps = Access.conn.prepareStatement("SELECT * FROM Customer WHERE Username = ?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            rs.next();

            //we iterating through the result set and getting the information and store it in the array
            for (int index = 1; index <= 11; index++) {
                userInformation[index - 1] = rs.getString(index);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userInformation;
    }

    public static boolean accountExists(String username, String firstName, String lastName) {
        //see whether the account exists or not, false means the accounts does not exists
        boolean exists = false;
        try {
            //preparing the select statement
            PreparedStatement ps = Access.conn.prepareStatement("SELECT SecurityQuestion, SecurityAnswer, Password FROM Customer WHERE Username = ? AND FirstName = ? AND LastName = ?");
            ps.setString(1, username);
            ps.setString(2, firstName);
            ps.setString(3, lastName);
            ResultSet rs = ps.executeQuery();
            exists = rs.next();

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return exists;

    }

    public static String getSecurityQuestion(String username) {
        String question = "";
        try {
            PreparedStatement ps = Access.conn.prepareStatement("SELECT SecurityQuestion FROM Customer WHERE Username = ? ");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            rs.next();
            question = rs.getString(1);

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return question;
    }

    public static String getSecurityAnswer(String username) {
        String answer = "";
        try {
            PreparedStatement ps = Access.conn.prepareStatement("SELECT SecurityAnswer FROM Customer WHERE Username = ?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            rs.next();
            answer = rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return answer;
    }

    public static String getUserPassword(String username) {
        String password = "";
        try {
            PreparedStatement ps = Access.conn.prepareStatement("SELECT Password FROM Customer WHERE Username = ?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            rs.next();
            password = rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return password;
    }
}

