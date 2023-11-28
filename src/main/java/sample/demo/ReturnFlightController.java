package sample.demo;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ReturnFlightController extends Application {

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
    private TextField ReturnTicketDate;

    @FXML
    private TextField ReturnTicketFrom;

    @FXML
    private ImageView ReturnTicketImageView;

    @FXML
    private TextField ReturnTicketNo001;

    @FXML
    private TextField ReturnTicketNo002;

    @FXML
    private TextField ReturnTicketNo003;

    @FXML
    private TextField ReturnTicketNo004;

    @FXML
    private TextField ReturnTicketTo;

    @FXML
    private TextField ReturnTicketType;

    @FXML
    private Button SanrioAirlineButton;

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
    void onReturnTicket1AddButtonClicked(ActionEvent event) {

    }

    @FXML
    void onReturnTicket2AddButtonClicked(ActionEvent event) {

    }

    @FXML
    void onReturnTicket3AddButtonClicked(ActionEvent event) {

    }

    @FXML
    void onReturnTicket4AddButtonClicked(ActionEvent event) {

    }

    @FXML
    void onSanrioAirlineButtonClicked(ActionEvent event) {
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ReturnFlight.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setResizable(false);
        primaryStage.setTitle("Sanrio Airline Return Tickets");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}


