package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;

public class MainMenuAccessController {

    @FXML
    private Label welcomeLabel;


    @FXML
    private Hyperlink myTripsHyperlink;

    @FXML
    private Hyperlink bookFlightHyperlink;

    @FXML
    private Hyperlink logoutHyperlink;

    @FXML
    private Hyperlink ProfileHyperlink;



    @FXML
    void onMyTripsHyperlinkClicked() {
        System.out.println("going to the trip summary page...");
        Starting.switchScenes("TripSummary");
    }


    @FXML
    void onLogoutHyperlinkClicked() {
        System.out.println("logging out...");
        Starting.switchScenes("MainMenuNoAccess");
    }

    @FXML
    void onBookFlightHyperlinkClicked() {
        System.out.println("going to search a flight...");
        Starting.switchScenes("SearchFlights");
    }
    @FXML
    void onProfileHyperlinkClicked() {
        System.out.println("going to the user profile...");
        Starting.switchScenes("UserProfile");
    }


}
