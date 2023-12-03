package SQL;


import java.sql.*;


public class CurrentUser {
    static java.sql.PreparedStatement pStatement;
    static ResultSet result;

    public static boolean login(String username, String password) {
        boolean accessGranted = false;
        try {
            pStatement = Access.conn.prepareStatement(
                    "SELECT * FROM Customer WHERE Username = ? AND Password = ?");
            pStatement.setString(1, username);
            pStatement.setString(2, password);

            result = pStatement.executeQuery();

            if (result.next()) {
                accessGranted = true;
            }
        } catch (java.sql.SQLException ex) {
            System.out.println("Error occurred.");
        } finally {
            System.out.println(accessGranted);
            return accessGranted;
        }

    }
}
