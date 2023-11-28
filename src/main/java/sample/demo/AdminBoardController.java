package sample.demo;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class AdminBoardController {

    @FXML
    private Button AdminUpdateCustButton;

    @FXML
    private Button ChangeDepartTicketsButton;

    @FXML
    private Button ChangeReturnTicketsButton;

    @FXML
    private TextField CustAddressBox;

    @FXML
    private TextField CustAnswerBox;

    @FXML
    private TextField CustDArrivalBox;

    @FXML
    private TextField CustDFromBox;

    @FXML
    private TextField CustDTimeBox;

    @FXML
    private TextField CustDToBox;

    @FXML
    private TextField CustEmailBox;

    @FXML
    private TextField CustFirstNameBox;

    @FXML
    private TextField CustFlightNoDepartBox;

    @FXML
    private TextField CustFlightNoReturnBox;

    @FXML
    private Button CustIDSearchButton;

    @FXML
    private TextField CustLastNameBox;

    @FXML
    private TextField CustQuestionBox;

    @FXML
    private TextField CustRArrivalBox;

    @FXML
    private TextField CustRFromBox;

    @FXML
    private TextField CustRTimeBox;

    @FXML
    private TextField CustRToBox;

    @FXML
    private TextField CustSSNBox;

    @FXML
    private TextField CustStateBox;

    @FXML
    private TextField CustTotalBox;

    @FXML
    private ChoiceBox<?> CustTypeChoiceBox;

    @FXML
    private TextField CustZipBox;

    @FXML
    private TextField CustomerPasswordBox;

    @FXML
    private TextField EnterCustIDBox;


    @FXML
    public void onCustIDSearchButtonClicked() {

        String CustIDSearch = EnterCustIDBox.getText();
        String CustPassword = CustomerPasswordBox.getText();
        String CustFirstname = CustFirstNameBox.getText();
        String CustLastname = CustLastNameBox.getText();
        String CustAddress = CustAddressBox.getText();
        String CustState = CustStateBox.getText();
        String CustZip = CustZipBox.getText();
        String CustQuestion = CustQuestionBox.getText();
        String CustAnswer = CustAnswerBox.getText();

        //Depart
        String CustDFlightNo = CustFlightNoDepartBox.getText();
        String CustDFrom = CustDFromBox.getText();
        String CustDTo = CustDToBox.getText();
        String CustDDTime = CustDTimeBox.getText();
        String CustDArrival = CustDArrivalBox.getText();

        //Return
        String CustRFlightNo = CustFlightNoReturnBox.getText();
        String CustRFrom = CustRFromBox.getText();
        String CustRTo = CustRToBox.getText();
        String CustRTime = CustRTimeBox.getText();
        String CustRArrival = CustRArrivalBox.getText();
        String CustTotalCost = CustTotalBox.getText();
    }

    @FXML
    public void onAdminUpdateCustButtonClicked() {


    }

}