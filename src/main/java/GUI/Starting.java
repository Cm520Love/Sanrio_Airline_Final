package GUI;
import javafx.stage.*;
import javafx.scene.*;
import javafx.application.*;
import javafx.fxml.FXMLLoader;

public class Starting extends Application {

    private static Stage window;
    private static FXMLLoader loginPage;
    private static FXMLLoader mainMenuNoAccessPage;
    private static FXMLLoader mainMenuAccessPage;
    private static FXMLLoader signUpPage;
    private static FXMLLoader searchFlightsPage;
    private static FXMLLoader bookTicketsPage;


    private static FXMLLoader profilePage;
    private static FXMLLoader forgotPasswordPage;

    static FXMLLoader tripSummaryPage;

    static Scene mainMenuNoAccessScene;
    static Scene loginScene;
    static Scene splashScreenScene;
    private static Scene signUpScene;
    static Scene mainMenuAccessScene;
    static Scene searchFlightsScene;
    static Scene departTicketsScene;
    static Scene adminboardScene;
    static Scene profileScene;
    static Scene forgotPasswordScene;
    static Scene returnFlightScene;
    static Scene bookTicketsScene;

    static Scene tripSummaryScene;

    public static void main(String[] args) { launch(args); }

    private static String currentUser;
    @Override
    public void start(Stage primaryStage) {
        try {
            SQL.Access.getAccess();

            // setting Stage window as primaryStage and not allowing user to resize it
            window = primaryStage;
            window.setResizable(false);
            window.setTitle("Sanrio Airlines");

            // loading splashscreen
            Parent root = FXMLLoader.load(getClass().getResource("splashScreen.fxml"));

            // creating splash screen
            splashScreenScene = new Scene(root);


            // showing the splash screen on startup
            window.setScene(splashScreenScene);
            window.show();

            // transition to the next scene: Main Menu
            SplashScreenController.transition();

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }



    public static void setCurrentUser(String username) {
        currentUser = username;
    }
    public static String getCurrentUser() {
        return currentUser;
    }


    public static void switchScenes(String sceneName) {
        // method used to switch scenes
        try {
            // application will switch scenes using a switch case
            // input will determine which scene to switch to
            switch(sceneName) {
                case "Login":
                    loginPage = new FXMLLoader(Starting.class.getResource("loginPage.fxml"));
                    loginScene = new Scene(loginPage.load());
                    window.setScene(loginScene);
                    break;
                case "TripSummary":
                    tripSummaryPage = new FXMLLoader(Starting.class.getResource("TripSummary.fxml"));
                    tripSummaryScene = new Scene(tripSummaryPage.load());
                    window.setScene(tripSummaryScene);
                    break;
                case "SignUp":
                    signUpPage = new FXMLLoader(Starting.class.getResource("SignUp.fxml"));
                    signUpScene = new Scene(signUpPage.load());
                    window.setScene(signUpScene);
                    break;
                case "ForgotPassword":
                    forgotPasswordPage = new FXMLLoader(Starting.class.getResource("PasswordRecovery.fxml"));
                    forgotPasswordScene = new Scene(forgotPasswordPage.load());
                    window.setScene(forgotPasswordScene);
                    break;
                case "SearchFlights":
                    searchFlightsPage = new FXMLLoader(Starting.class.getResource("SearchFlight.fxml"));
                    searchFlightsScene = new Scene(searchFlightsPage.load());
                    window.setScene(searchFlightsScene);
                    break;
                case "MainMenuAccess":
                    mainMenuAccessPage = new FXMLLoader(Starting.class.getResource("mainMenuAccess.fxml"));
                    mainMenuAccessScene = new Scene(mainMenuAccessPage.load());
                    window.setScene(mainMenuAccessScene);
                    break;
                case "MainMenuNoAccess":
                    mainMenuNoAccessPage = new FXMLLoader(Starting.class.getResource("mainMenuNoAccess.fxml"));
                    mainMenuNoAccessScene = new Scene(mainMenuNoAccessPage.load());
                    window.setScene(mainMenuNoAccessScene);
                    break;
                case "BookTickets":
                    bookTicketsPage = new FXMLLoader(Starting.class.getResource("BookTickets.fxml"));
                    bookTicketsScene = new Scene(Starting.bookTicketsPage.load());
                    window.setScene(bookTicketsScene);
                    break;
                case "UserProfile":
                    profilePage = new FXMLLoader(Starting.class.getResource("Profile.fxml"));
                    profileScene = new Scene(Starting.profilePage.load());
                    window.setScene(profileScene);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}