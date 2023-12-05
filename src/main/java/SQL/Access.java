package SQL;
import java.sql.*;



public class Access {
    final static String url = "jdbc:sqlserver://test-sunnysqlserver.database.windows.net:1433;database=Sanrio_Airlines;user=stinkysnoopy@test-sunnysqlserver;password=AdminPass!!!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";

    static Connection conn;

    // method to get access into the database
    public static void getAccess() {
        try {
            conn = java.sql.DriverManager.getConnection(url);
            System.out.println("Database connected.");
        }
        catch (Exception ex) {
            System.out.println("Error occurred during connection to the database.");
        }
    }


}