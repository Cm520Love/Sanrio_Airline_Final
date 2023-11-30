package SQL;
import java.sql.*;

public class Customers {
    private String FirstName, LastName, Address, State, ZipCode;
    private String Username, Password, SSN;
    private String Email, SecurityQuestion, SecurityAnswer;

    public Customers() {

    }

    //Constructor that passes all arguments
    public Customers(String FirstName, String LastName, String Address, String State, String ZipCode, String Username, String Password, String SSN, String Email, String SecurityQuestion, String SecurityAnswer) {
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Address = Address;
        this.State = State;
        this.ZipCode = ZipCode;
        this.Username = Username;
        this.Password = Password;
        this.SSN = SSN;
        this.Email = Email;
        this.SecurityQuestion = SecurityQuestion;
        this.SecurityAnswer = SecurityAnswer;
    }

    public static void main(String[] args) throws Exception {
        Customers c1 = new Customers();
        c1.selectDB("Kimberly123");  //select data
        c1.display();

        /*************
         c1.insertDB("Kimberly","Wang", "1234 South Cobb Dr", "GA", "30000","Kimberly123","Kimberly2233","111-12-2233","kimberly123@gmail.com", "What will you name your pet?","Snoopy"); //insert data
         c1.display();
         **************/

        /*******
         c1.deleteDB("Kimberly123"); //delete data
         c1.display();
         *********/


        //In order to update remember to select the username first:
        //UpdateDB COdes Following:
        /*************
         c1.selectDB("Kimberly123");
         c1.setState("Fl");
         c1.updateDB();
         c1.display();
         *********************/
    }

    public void display() {
        System.out.println("FirstName" + FirstName + " LastName: " + LastName
                + "Address: " + Address + "State: " + State + "ZipCode: " + ZipCode
                + "Username: " + Username + "Password: " + Password + "SSN: " + SSN
                + "Email: " + Email + "SecurityQuestion: " + SecurityQuestion + "SecurityAnswer : " + SecurityAnswer);
    }

    /*********************************************************
     *The SelectDB() Method will get one Customer from the DB
     ********************************************************/
    public void selectDB(String Username) {
        try {
            //Getting data from the Customer table
            System.out.println("Selecting data ...");
            ResultSet resultset;
            System.out.println("Starting to connect to the database");
            String url = "jdbc:sqlserver://test-sunnysqlserver.database.windows.net:1433;database=Sanrio_Airlines;user=stinkysnoopy@test-sunnysqlserver;password=AdminPass!!!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
            Connection conn = DriverManager.getConnection(url);
            System.out.println("Database connected.");

            Statement stmt = conn.createStatement();
            ResultSet rs;
            PreparedStatement pStatement = conn.prepareStatement("SELECT * FROM Customer WHERE Username = ?");
            pStatement.setString(1, Username);
            rs = pStatement.executeQuery();
            rs.next();

            //Process ResultSet
            while (rs.next()) {
                this.FirstName = rs.getString(1);
                this.LastName = rs.getString(2);
                this.Address = rs.getString(3);
                this.State = rs.getString(4);
                this.ZipCode = rs.getString(5);
                this.Username = rs.getString(6);
                this.Password = rs.getString(7);
                this.SSN = rs.getString(8);
                this.Email = rs.getString(9);
                this.SecurityQuestion = rs.getString(10);
                this.SecurityAnswer = rs.getString(11);
            }

            conn.close();
        } catch (SQLException se) {
            System.out.println(se);

        }
    } //end selectDB()

    /*********************************************************
     *The InsertDB() will insert one Customer to the DB
     ********************************************************/

    public boolean insertDB(String FirstName, String LastName, String Address, String State, String ZipCode, String Username, String Password, String SSN, String Email, String SecurityQuestion, String SecurityAnswer) {
        //Assign values
        setFirstName(FirstName);
        setLastName(LastName);
        setAddress(Address);
        setState(State);
        setZipCode(ZipCode);
        setUsername(Username);
        setPassword(Password);
        setSSN(SSN);
        setEmail(Email);
        setSecurityQuestion(SecurityQuestion);
        setSecurityAnswer(SecurityAnswer);

        try {
            System.out.println("Inserting data ...");
            ResultSet resultset = null;
            System.out.println("Starting to connect to the database");
            String url = "jdbc:sqlserver://test-sunnysqlserver.database.windows.net:1433;database=Sanrio_Airlines;user=stinkysnoopy@test-sunnysqlserver;password=AdminPass!!!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
            Connection conn = DriverManager.getConnection(url);
            System.out.println("Database connected.");

            Statement stmt = conn.createStatement();
            ResultSet rs;
            String sql02 = ("INSERT INTO CUSTOMER VALUES ('" + getFirstName() + "', " +
                    "'" + getLastName() + "', " +
                    "'" + getAddress() + "', " +
                    "'" + getState() + "', " +
                    "'" + getZipCode() + "', " +
                    "'" + getUsername() + "', " +
                    "'" + getPassword() + "', " +
                    "'" + getSSN() + "', " +
                    "'" + getEmail() + "', " +
                    "'" + getSecurityQuestion() + "', " +
                    "'" + getSecurityAnswer() + "' " + ")");

            //print the statement
            System.out.println(sql02);
            //check for duplicates
            int n1 = stmt.executeUpdate(sql02);
            conn.close();

            if (n1 == 1) {
                System.out.println("INSERT Successful!!!");
                return true;
            } else {
                System.out.println("INSERT FAILED***********");
                return false;
            }

        } catch (Exception e1) {
            System.out.println(e1);
            return false;
        }
    }
    /***************************************************
     *The DeleteDB() will delete one Customer from the DB
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
            String sql03 = "Delete from Customer Where Username = '"+ Username +"'";
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
            String sql04 = "Update Customer set FirstName = '"+getFirstName()+"',"+
                    "LastName = '"+getLastName()+"',"+
                    "Address ='"+getAddress()+"',"+
                    "State = '"+getState()+"',"+
                    "ZipCode = '"+getZipCode()+"',"+
                    "Password = '"+getPassword()+"',"+
                    "SSN = '"+getSSN()+"',"+
                    "Email = '"+getEmail()+"',"+
                    "SecurityQuestion = '"+getSecurityQuestion()+"',"+
                    "SecurityAnswer = '"+getSecurityAnswer()+"'" +
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
    public String getFirstName(){return FirstName;}
    public String getLastName() {return LastName;}
    public String getAddress() {return Address;}
    public String getState() {return State;}
    public String getZipCode() {return ZipCode;}

    public String getPassword() {return Password;}
    public String getSSN() {return SSN;}
    public String getEmail() {return Email;}
    public String getSecurityQuestion() {return SecurityQuestion;}
    public String getSecurityAnswer() {return SecurityAnswer;}

    //Setters
    public void setUsername (String Username) {this.Username = Username;}
    public void setFirstName (String FirstName) {this.FirstName = FirstName;}
    public void setLastName(String LastName) {this.LastName = LastName;}
    public void setAddress(String Address) {this.Address = Address;}
    public void setState(String State) {this.State = State;}
    public void setZipCode(String ZipCode) {this.ZipCode = ZipCode;}
    public void setPassword(String Password) {this.Password = Password;}
    public void setSSN(String SSN) {this.SSN = SSN;}
    public void setEmail(String Email) {this.Email = Email;}
    public void setSecurityQuestion(String SecurityQuestion) {this.SecurityQuestion = SecurityQuestion;}
    public void setSecurityAnswer(String SecurityAnswer) {this.SecurityAnswer = SecurityAnswer;}

}






