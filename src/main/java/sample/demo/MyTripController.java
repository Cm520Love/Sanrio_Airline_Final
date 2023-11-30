package sample.demo;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class MyTripController extends Application {

    @FXML
    private TextField MyTripDArrivalTimeBox;

    @FXML
    private TextField MyTripDDateBox;

    @FXML
    private TextField MyTripDFlightNoBox;

    @FXML
    private TextField MyTripDTimeBox;

    @FXML
    private TextField MyTripDTypeBox;

    @FXML
    private TextField MyTripDepartFromBox;

    @FXML
    private TextField MyTripDepartToBox;

    @FXML
    private ImageView MyTripImageView;

    @FXML
    private TextField MyTripRArrivalTimeBox;

    @FXML
    private TextField MyTripRDateBox;

    @FXML
    private TextField MyTripRFlightNoBox;

    @FXML
    private TextField MyTripRTimeBox;

    @FXML
    private TextField MyTripRTypeBox;

    @FXML
    private TextField MyTripReturnFromBox;

    @FXML
    private TextField MyTripReturnToBox;

    @FXML
    private Hyperlink SanrioAirlines;

    @FXML
    public void onAddButtonClicked() {
        // Depart Section for My Trip Page
        String MYTripDFlightNo = MyTripDFlightNoBox.getText();
        String MYTripDType = MyTripDTypeBox.getText();
        String MYTripDepartFrom = MyTripDepartFromBox.getText();
        String MYTripDepartTo = MyTripDepartToBox.getText();
        String MYTripDDate = MyTripDDateBox.getText();

        //Depart Section for Times
        String MYTripDTime = MyTripDTimeBox.getText();
        String MYTripDArrivalTime = MyTripDArrivalTimeBox.getText();

        //Return Section for My Trip Page
        String MYTripRFlightNo = MyTripRFlightNoBox.getText();
        String MYTripRType = MyTripRTypeBox.getText();
        String MYTripReturnFrom = MyTripReturnFromBox.getText();
        String MYTripReturnTo = MyTripReturnToBox.getText();
        String MYTripReturnDate = MyTripRDateBox.getText();

        //Return Section for Times
        String MYTripRTime = MyTripRTimeBox.getText();
        String MYTripRArrivalTime = MyTripRArrivalTimeBox.getText();

    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MyTrip.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setResizable(false);
        primaryStage.setTitle("Sanrio My Trip");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

