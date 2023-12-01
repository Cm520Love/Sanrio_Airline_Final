package sample.demo;

import SQL.bookFlightQueries;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import userInformation.UserInformation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class ReturnFlightController implements Initializable {

    @FXML
    private Label ReturnFlightTitle;

    @FXML
    private Button ReturnTicket1AddButton;

    @FXML
    private Button ReturnTicket2AddButton;

    @FXML
    private Button ReturnTicket3AddButton;

    @FXML
    private Button ReturnTicket4AddButton;

    @FXML
    private TextField ReturnTicketDateBox;

    @FXML
    private TextField ReturnTicketFromBox;

    @FXML
    private ImageView ReturnTicketImageView;

    @FXML
    private TextField ReturnTicketToBox;

    @FXML
    private TextField ReturnTicketTypeBox;

    @FXML
    private Button SanrioAirlineButton;

    @FXML
    private TextField returnFlightNo1Box;

    @FXML
    private TextField returnFlightNo2Box;

    @FXML
    private TextField returnFlightNo3Box;

    @FXML
    private TextField returnFlightNo4Box;

    @FXML
    private TextField ticket1ReturnArrivalTimeTextfield;

    @FXML
    private TextField ticket1ReturnTimeTextfield;

    @FXML
    private TextField ticket2ReturnArrivalTimeTextfield;

    @FXML
    private TextField ticket2ReturnTimeTextfield;

    @FXML
    private TextField ticket3ReturnArrivalTimeTextfield;

    @FXML
    private TextField ticket3ReturnTimeTextfield;

    @FXML
    private TextField ticket4ReturnArrivalTimeTextfield;

    @FXML
    private TextField ticket4ReturnTimeTextfield;

    @FXML
    void onSanrioAirlineButtonClicked(ActionEvent event) {
        System.out.println("Going to the main menu .....");
        Starting.window.setScene(Starting.mainMenuAccessScene);
    }

    static private HashMap<Integer, ArrayList<String>> allFlightTickets;

    @Override
    public void initialize(java.net.URL url, java.util.ResourceBundle resourceBundle) {
        ArrayList<UserInformation> flightInformation = flightInformationVO.getCurrentFlightInfo();
        String fromCity = flightInformation.get(4).getInfo();
        fromCity = fromCity.substring(fromCity.length() - 3);
        String toCity = flightInformation.get(6).getInfo();
        toCity = toCity.substring(toCity.length() - 3);

        String returnDate = flightInformation.get(2).getInfo();

        String[] returnflightInfo = {fromCity, toCity, returnDate};
        allFlightTickets = bookFlightQueries.retrieveFlights(returnflightInfo);

        ReturnTicketDateBox.setText(returnDate);
        ReturnTicketFromBox.setText(fromCity);
        ReturnTicketToBox.setText(toCity);
        ReturnTicketTypeBox.setText(flightInformation.get(0).getInfo());

        displayReturnFlightInformation();
    }

    void displayReturnFlightInformation() {
        int i = 0;
        TextField[] flightIDBoxes = {returnFlightNo1Box, returnFlightNo2Box, returnFlightNo3Box, returnFlightNo4Box};
        TextField[] returnTimeBoxes = {ticket1ReturnTimeTextfield, ticket2ReturnTimeTextfield, ticket3ReturnTimeTextfield, ticket4ReturnTimeTextfield};
        TextField[] arrivalTimeBoxes = {ticket1ReturnArrivalTimeTextfield, ticket2ReturnArrivalTimeTextfield, ticket3ReturnArrivalTimeTextfield, ticket4ReturnArrivalTimeTextfield};
        for (Integer key : allFlightTickets.keySet()) {
            ArrayList<String> details = allFlightTickets.get(key);
            flightIDBoxes[i].setText(key+"");
            returnTimeBoxes[i].setText(details.get(3));
            arrivalTimeBoxes[i].setText(details.get(4));
            i++;
            System.out.println(key + " " + allFlightTickets.get(key));
        }
    }


    @FXML
    void onReturnTicket1AddButtonClicked(ActionEvent event) {
        addReturnInformationToDatabase(
                Starting.getCurrentUser(),
                ReturnTicketDateBox.getText(),
                ticket1ReturnTimeTextfield.getText(),
                ticket1ReturnArrivalTimeTextfield.getText(),
                returnFlightNo1Box.getText()
        );
        System.out.println("Adding 1st Return Tickets to Database....");
        // Additional logic or scene transition if needed

    }


    @FXML
    void onReturnTicket2AddButtonClicked(ActionEvent event) {
        addReturnInformationToDatabase(
                Starting.getCurrentUser(),
                ReturnTicketDateBox.getText(),
                ticket2ReturnTimeTextfield.getText(),
                ticket2ReturnArrivalTimeTextfield.getText(),
                returnFlightNo2Box.getText()
        );
        System.out.println("Adding 2nd Return Tickets to Database....");
        // Additional logic or scene transition if needed

    }


    @FXML
    void onReturnTicket3AddButtonClicked(ActionEvent event) {
        addReturnInformationToDatabase(
                Starting.getCurrentUser(),
                ReturnTicketDateBox.getText(),
                ticket3ReturnTimeTextfield.getText(),
                ticket3ReturnArrivalTimeTextfield.getText(),
                returnFlightNo3Box.getText()
        );
        System.out.println("Adding 3rd Return Tickets to Database....");
        // Additional logic or scene transition if needed

    }

    @FXML
    void onReturnTicket4AddButtonClicked(ActionEvent event) {
        addReturnInformationToDatabase(
                Starting.getCurrentUser(),
                ReturnTicketDateBox.getText(),
                ticket4ReturnTimeTextfield.getText(),
                ticket4ReturnArrivalTimeTextfield.getText(),
                returnFlightNo4Box.getText()
        );
        System.out.println("Adding 4th Return Tickets to Database....");
        // Additional logic or scene transition if needed

    }

    private void addReturnInformationToDatabase(String Username, String DepartureDate, String DepartureTime, String ArrivalTime, String FlightID) {
        try {
            // Check if the user has already booked this flight
            if (hasUserBookedFlight(Username, FlightID)) {
                // Show a popup indicating that the user has already booked this flight
                showErrorPopup("Booking Error", "You have already booked this return flight.");
                return;  // Do not proceed with the insertion
            }
            if (ConflictedTimeFlights(
                    Starting.getCurrentUser(),
                    ReturnTicketDateBox.getText(),
                    DepartureTime,
                    ArrivalTime
            )) {
                showErrorPopup("Booking Error", "Time and date conflict with another booked flight");
                return;
            }
            if (isFull(FlightID)) {

                showErrorPopup("Booking Error", "Flight was full. Please book another time");
                return;
            }
            // Insert the new booking information
            PreparedStatement ps = Starting.conn.prepareStatement("INSERT INTO BookedFlights VALUES (?,?,?,?,?)");
            ps.setString(1, Username);
            ps.setString(2, DepartureDate);
            ps.setString(3, DepartureTime);
            ps.setString(4, ArrivalTime);
            ps.setString(5, FlightID);
            ps.execute();
            incrementPassengers(FlightID);

            if(flightInformationVO.getCurrentFlightInfo().get(0).getInfo() == "Round") {
                System.out.println("going to the My Trip page...");
                Starting.window.setScene(Starting.myTripScene);

            }
            else{
                System.out.println("going to the main menu access scene for right now if the type is One-Way...");
                Starting.window.setScene(Starting.mainMenuAccessScene);
            }

            // Optionally, you can show a success popup or perform additional logic
            showSuccessPopup("Booking Successful", "You have successfully booked the flight.");

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the SQLException appropriately (e.g., show an error popup)
            showErrorPopup("Booking Error", "An error occurred while booking the flight. Please try again.");
        }
    }

    private boolean isFull(String flightID) {
        try {
            PreparedStatement ps = Starting.conn.prepareStatement("SELECT Passengers FROM Flight WHERE FlightID = ?");
            ps.setString(1, flightID);
            ResultSet rs = ps.executeQuery();
            rs.next();
            if ((rs.getInt(1)) == bookFlightQueries.getMaxPassengers()) {
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

    // Check if the user has already booked this flight
    private boolean hasUserBookedFlight(String username, String flightID) {
        try {
            PreparedStatement ps = Starting.conn.prepareStatement("SELECT * FROM BookedFlights WHERE Username = ? AND FlightID = ?");
            ps.setString(1, username);
            ps.setString(2, flightID);
            return ps.executeQuery().next();  // Returns true if a record is found
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the SQLException appropriately
            return false;

        }

    }

    private void showSuccessPopup(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showErrorPopup(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    ////////////////////////////////////////////////////

    //This is for checking if the customer has a date and time conflicts
    private boolean ConflictedTimeFlights(String Username, String DepartureDate, String DepartureTime, String ArrivalTime) {
        try {
            PreparedStatement ps = Starting.conn.prepareStatement("SELECT COUNT(*) AS Num FROM BookedFlights WHERE Username = ? AND DepartureDate = ? AND DepartureTime = ? AND ArrivalTime =?");
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

    private void showErrorPopup2(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void incrementPassengers(String FlightID) {
        try {
            PreparedStatement ps = Starting.conn.prepareStatement("SELECT Passengers FROM Flight WHERE FlightID = ?");
            PreparedStatement updatedStatement = Starting.conn.prepareStatement("UPDATE Flight SET Passengers = ? WHERE FlightID = ?");
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



