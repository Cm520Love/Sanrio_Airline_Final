package SQL;
import java.sql.*;

public class BookedFlights {
    private String Username, DepartureDate, DepartureTime, ArrivalTime,FlightID;

    public BookedFlights() {

    }

    //Constructor that passes all arguments
    public BookedFlights(String Username, String DepartureDate, String DepartureTime, String ArrivalTime, String FlightID) {
        this.Username = Username;
        this.DepartureDate = DepartureDate;
        this.DepartureTime = DepartureTime;
        this.ArrivalTime = ArrivalTime;
        this.FlightID = FlightID;
    }

    public static void main(String[] args) throws Exception {
        BookedFlights B1 = new BookedFlights();

        //B1.selectDB("Kimberly123");  //select data
        //B1.display();

        /*************
         B1.insertDB("sunny123","11-12-23", "9:00 AM", "10:00 AM", "0001"); //insert data
         B1.display();
         **************/
        B1.insertDB("sunny123","11-12-23", "9:00 AM", "10:00 AM", "0001"); //insert data
        B1.display();

        /*******
         B1.deleteDB("Kimberly123"); //delete data
         B1.display();
         *********/


        //In order to update remember to select the username first:
        //UpdateDB COdes Following:
        /*************
         B1.selectDB("Kimberly123");
         B1.setState("Fl");
         B1.updateDB();
         B1.display();
         *********************/
    }

    public void display() {
        System.out.println("Username" + Username + " DepartureDate: " + DepartureDate
                + "DepartureTime: " + DepartureTime + "ArrivalTime: " + ArrivalTime + "FlightID: " + FlightID);
    }

    /*********************************************************
     *The SelectDB() Method will get flights from the DB
     ********************************************************/
    public void selectDB(String Username) {
        try {
            //Getting data from the Customer table
            System.out.println("Selecting data ...");
            ResultSet resultset = null;
            System.out.println("Starting to connect to the database");
            String url = "jdbc:sqlserver://test-sunnysqlserver.database.windows.net:1433;database=Sanrio_Airlines;user=stinkysnoopy@test-sunnysqlserver;password=AdminPass!!!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
            Connection conn = DriverManager.getConnection(url);
            System.out.println("Database connected.");

            Statement stmt = conn.createStatement();
            ResultSet rs;
            String sql = ("Select * From BookedFlights Where Username = '" + Username + "'");
            rs = stmt.executeQuery(sql);


            //Process ResultSet
            while (rs.next()) {
                this.Username = rs.getString(1);
                this.DepartureDate = rs.getString(2);
                this.DepartureTime = rs.getString(3);
                this.ArrivalTime = rs.getString(4);
                this.FlightID = rs.getString(5);
            }

            conn.close();
        } catch (SQLException se) {
            System.out.println(se);

        }
    } //end selectDB()

    /*********************************************************
     *The InsertDB() will insert flights to the DB
     ********************************************************/

    // Add this method to your BookedFlights class
    public boolean insertDB(String username, String departureDate, String departureTime, String arrivalTime, String flightNo) {
        String url = "jdbc:sqlserver://test-sunnysqlserver.database.windows.net:1433;database=Sanrio_Airlines;user=stinkysnoopy@test-sunnysqlserver;password=AdminPass!!!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";

        try (Connection conn = DriverManager.getConnection(url)) {
            // Prepare the INSERT statement
            String sql = "INSERT INTO BookedFlights (Username, DepartureDate, DepartureTime, ArrivalTime, FlightID) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                // Set values for the parameters in the prepared statement
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, departureDate);
                preparedStatement.setString(3, departureTime);
                preparedStatement.setString(4, arrivalTime);
                preparedStatement.setString(5, flightNo);

                // Execute the INSERT statement
                int rowsAffected = preparedStatement.executeUpdate();

                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /***************************************************
     *The DeleteDB() will delete one flight from the DB
     ****************************************************/
    public void deleteDB(String Username) {
        try {
            System.out.println("Deleting data ...");
            ResultSet resultset = null;
            System.out.println("Starting to connect to the database");
            String url = "jdbc:sqlserver://test-sunnysqlserver.database.windows.net:1433;database=Sanrio_Airlines;user=stinkysnoopy@test-sunnysqlserver;password=AdminPass!!!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
            Connection conn = DriverManager.getConnection(url);
            System.out.println("Database connected.");
            Statement stmt = conn.createStatement();
            ResultSet rs;
            String sql03 = "Delete from BookedFlights Where FlightID = '"+ FlightID +"'";
            //Print sql statement
            System.out.println(sql03);
            //check for duplicates
            int n1 = stmt.executeUpdate(sql03);
            if (n1==1)
                System.out.println("DELETE Successfull!!!");
            else
                System.out.println("DELETE FAILED***********");
            conn.close();
        }
        catch(Exception e2){
            System.out.println(e2);
        }

    }

    public boolean updateDB() {

        try{
            System.out.println("Updating data ...");
            ResultSet resultset = null;
            System.out.println("Starting to connect to the database");
            String url = "jdbc:sqlserver://test-sunnysqlserver.database.windows.net:1433;database=Sanrio_Airlines;user=stinkysnoopy@test-sunnysqlserver;password=AdminPass!!!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
            Connection conn = DriverManager.getConnection(url);
            System.out.println("Database connected.");
            Statement stmt = conn.createStatement();
            ResultSet rs;
            String sql04 = "Update BookedFlights set FlightID = '"+getFlightID()+"',"+
                    "DepartureDate = '"+getDepartureDate()+"',"+
                    "DepartureTime ='"+getDepartureTime()+"',"+
                    "ArrivalTime = '"+getArrivalTime()+"',"+
                    "Where Username ='"+getUsername()+"'";

            System.out.println(sql04);
            int n = stmt.executeUpdate(sql04);
            if(n==1)
                System.out.println("UPDATE Successful!!!");
            else
                System.out.println("UPDATE Failed************");
            conn.close();
        }
        catch(Exception e4){
            System.out.println(e4);
        }

        return true;
    }//End of UpdateDB()

    //GETTERS
    public String getUsername(){return Username;}
    public String getDepartureDate(){return DepartureDate;}
    public String getDepartureTime() {return DepartureTime;}
    public String getArrivalTime() {return ArrivalTime;}
    public String getFlightID() {return FlightID;}

    //Setters
    public void setUsername (String Username) {this.Username = Username;}
    public void setDepartureDate (String DepartureDate) {this.DepartureDate = DepartureDate;}
    public void setDepartureTime(String DepartureTime) {this.DepartureTime = DepartureTime;}
    public void setArrivalTime(String ArrivalTime) {this.ArrivalTime = ArrivalTime;}
    public void setFlightID(String FlightID) {this.FlightID = FlightID;}

}







