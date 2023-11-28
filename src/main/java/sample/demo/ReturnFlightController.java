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
    private Button ReturnAddButton001;

    @FXML
    private Button ReturnAddButton002;

    @FXML
    private Button ReturnAddButton003;

    @FXML
    private Button ReturnAddButton004;

    @FXML
    private TextField ReturnArrivalTime001;

    @FXML
    private TextField ReturnArrivalTime0011;

    @FXML
    private TextField ReturnArrivalTime00111;

    @FXML
    private TextField ReturnArrivalTime001111;

    @FXML
    private Label ReturnFlightTitle;

    @FXML
    private TextField ReturnTicketDate;

    @FXML
    private TextField ReturnTicketFrom;

    @FXML
    private ImageView ReturnTicketImageView;

    @FXML
    private TextField ReturnTicketNo001;

    @FXML
    private TextField ReturnTicketNo0011;

    @FXML
    private TextField ReturnTicketNo00111;

    @FXML
    private TextField ReturnTicketNo001111;

    @FXML
    private TextField ReturnTicketTo;

    @FXML
    private TextField ReturnTicketType;

    @FXML
    private TextField ReturnTime001;

    @FXML
    private TextField ReturnTime0011;

    @FXML
    private TextField ReturnTime00111;

    @FXML
    private TextField ReturnTime001111;

    @FXML
    private Button SanrioAirlineButton;

    @FXML
    void onReturnAddButton001Clicked(ActionEvent event) {

    }

    @FXML
    void onReturnAddButton002Clicked(ActionEvent event) {

    }

    @FXML
    void onReturnAddButton003Clicked(ActionEvent event) {

    }

    @FXML
    void onReturnAddButton004Clicked(ActionEvent event) {

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

