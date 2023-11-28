/* dont use this code
package DatabaseInformation;
import Interfaces.*;
import userInformation.userProfile.State;
import java.util.Scanner;
import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
public class FlightTable implements InitializeDatabase {

    static Connection conn;
    @Override
    public void initialize() throws Exception {
        Scanner input = new Scanner(new File("testAirportInformation.txt"));
        String url = "jdbc:sqlserver://test-sunnysqlserver.database.windows.net:1433;database=Sanrio_Airlines;user=stinkysnoopy@test-sunnysqlserver;password=AdminPass!!!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
        conn = java.sql.DriverManager.getConnection(url);
        System.out.println("Database connected.");

        String[] airline = {"KITTY", "POCHA", "CINNA", "POM", "KERO"};
        String[] departureTimes = {"9:00", "12:00", "3:00", "6:00"};
        String[] arrivalTimes = {"12:00", "3:00", "6:00", "9:00"};
        ArrayList<String> states = State.getStateAbbreviations();

        LocalDate today = LocalDate.now();
        LocalDate end = LocalDate.now().plusYears(1);

        PreparedStatement flightPreparedStatement;
        PreparedStatement airportPreparedStatement;

        int flightIDCounter = 1;
        int randomAirlineCounter;
        String flightID;
        java.util.ArrayList<String> departureAirports;
        java.util.ArrayList<String> arrivalAirports;


        PreparedStatement insertFlightStatement;
        while (!today.toString().equals(end.toString())) {
            for (String state: states) {
                departureAirports = getDepartureAirports(state, true);
                arrivalAirports = getDepartureAirports(state, false);
                for (int i = 0; i < departureAirports.size(); i++) {
                    for (int j = 0; j < arrivalAirports.size(); j++) {
                        for (int k = 0; k < departureTimes.length; k++) {
                            insertFlightStatement = conn.prepareStatement("INSERT INTO Flight VALUES (?, ?, ?, ?, ?, ?, ?,0)");
                            flightID = String.format("%05d", flightIDCounter++);
                            randomAirlineCounter = (int)(Math.random() * 4);

                            insertFlightStatement.setString(1, flightID);
                            insertFlightStatement.setString(2, airline[randomAirlineCounter]);
                            insertFlightStatement.setString(3, today.toString());
                            insertFlightStatement.setString(4, departureAirports.get(i));
                            insertFlightStatement.setString(5, arrivalAirports.get(j));
                            insertFlightStatement.setString(6, departureTimes[k]);
                            insertFlightStatement.setString(7, arrivalTimes[k]);
                            insertFlightStatement.execute();
                        }
                    }
                }
            }
            today = today.plusDays(1);
        }

        conn.close();
    }

    public static java.util.ArrayList<String> getDepartureAirports(String state, boolean isDepart) throws Exception {
        PreparedStatement airportPreparedStatement;
        if (isDepart) {
            airportPreparedStatement = conn.prepareStatement("SELECT AirportAbbreviation FROM Airport WHERE StateAbbreviation = ?");
        } else {
            airportPreparedStatement = conn.prepareStatement("SELECT AirportAbbreviation FROM Airport WHERE StateAbbreviation != ?");
        }

        airportPreparedStatement.setString(1, state);
        ResultSet result = airportPreparedStatement.executeQuery();

        java.util.ArrayList<String> AirportsArray = new java.util.ArrayList<String>();

        while (result.next()) {
            AirportsArray.add(result.getString("AirportAbbreviation"));
        }

        return AirportsArray;

    }

    public static void main(String[] args) {
        try {
            new FlightTable().initialize();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
*/