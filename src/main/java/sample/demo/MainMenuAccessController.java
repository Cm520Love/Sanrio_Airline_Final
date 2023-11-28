package sample.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import sample.demo.Starting.*;
import java.util.ArrayList;

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
    void onMyTripsHyperlinkClicked() {

    }


    @FXML
    void onLogoutHyperlinkClicked() {
        System.out.println("logging out...");
        Starting.window.setScene(Starting.mainMenuNoAccessScene);
    }

    @FXML
    void onBookFlightHyperlinkClicked() {
        System.out.println("going to book a flight...");
        Starting.window.setScene(Starting.bookFlightScene);
    }


}
