package sample.demo;
import javafx.stage.*;
import javafx.scene.*;
import javafx.application.*;
import javafx.fxml.FXMLLoader;

public class Starting extends Application {

    static Stage window;
    private FXMLLoader loginPage;
    private FXMLLoader mainMenuNoAccessPage;
    private FXMLLoader mainMenuAccessPage;
    private FXMLLoader signUpPage;
    private FXMLLoader bookFlightPage;
    private FXMLLoader flightTicketsPage;

    private FXMLLoader adminboardPage;

    static Scene mainMenuNoAccessScene;
    static Scene loginScene;
    static Scene splashScreenScene;
    static Scene signUpScene;
    static Scene mainMenuAccessScene;
    static Scene bookFlightScene;
    static Scene flightTicketsScene;
    static Scene adminboardScene;
    public static java.sql.Connection conn;
    public static void main(String[] args) { launch(args); }

    private static String currentUser;
    @Override
    public void start(Stage primaryStage) {
        try {
            // establishing connection to database
            String url = "jdbc:sqlserver://test-sunnysqlserver.database.windows.net:1433;database=Sanrio_Airlines;user=stinkysnoopy@test-sunnysqlserver;password=AdminPass!!!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
            conn = java.sql.DriverManager.getConnection(url);
            System.out.println("Database connected.");

            // setting Stage window as primaryStage and not allowing user to resize it
            window = primaryStage;
            window.setResizable(false);
            window.setTitle("Sanrio Airlines");

            // loading all the pages
            Parent root = FXMLLoader.load(getClass().getResource("splashScreen.fxml"));
            loginPage = new FXMLLoader(getClass().getResource("loginPage.fxml"));
            mainMenuNoAccessPage = new FXMLLoader(getClass().getResource("mainMenuNoAccess.fxml"));
            mainMenuAccessPage = new FXMLLoader(getClass().getResource("mainMenuAccess.fxml"));
            signUpPage = new FXMLLoader(getClass().getResource("SignUp.fxml"));
            bookFlightPage = new FXMLLoader(getClass().getResource("bookFlight.fxml"));
            flightTicketsPage = new FXMLLoader(getClass().getResource("FlightTickets.fxml"));
            adminboardPage = new FXMLLoader(getClass().getResource("AdminBoard.fxml"));

            // creating scenes for all the pages
            splashScreenScene = new Scene(root);
            mainMenuNoAccessScene = new Scene(mainMenuNoAccessPage.load());
            mainMenuAccessScene = new Scene(mainMenuAccessPage.load());
            loginScene = new Scene(loginPage.load());
            signUpScene = new Scene(signUpPage.load());
            bookFlightScene = new Scene(bookFlightPage.load());
            flightTicketsScene = new Scene(flightTicketsPage.load());
            adminboardScene = new Scene(adminboardPage.load());


            // showing the splash screen on startup
            window.setScene(splashScreenScene);
            window.show();

            // transition to the next scene: Main Menu
            SplashScreenController.transition();

        } catch (java.sql.SQLException e) {
            System.out.println("Error occurred during connection to database");
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

}