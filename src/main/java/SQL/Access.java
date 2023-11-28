package SQL;
import java.sql.*;
import sample.demo.Starting.*;

import static sample.demo.Starting.conn;

public class Access {
    static PreparedStatement pStatement;
    static ResultSet result;
    public static boolean getAccess(String username, String password) {
        boolean accessGranted = false;
        try {
            pStatement = conn.prepareStatement(
            "SELECT * FROM Customer WHERE Username = ? AND Password = ?");
            pStatement.setString(1, username);
            pStatement.setString(2, password);

            result = pStatement.executeQuery();

            if (result.next()) {
                accessGranted = true;
            }
        } catch (SQLException ex) {
            System.out.println("Error occurred.");
        } finally {
            System.out.println(accessGranted);
            return accessGranted;
        }

    }

    public static boolean isNewValue(String info, String type, String table) {
        boolean isNewValue = false;
        try {
            pStatement = conn.prepareStatement(
                   "SELECT COUNT(*) AS number FROM table WHERE type = ?".replace("type", type).replace("table", table));
            pStatement.setString(1, info);
            result = pStatement.executeQuery();
            result.next();
            if ((result.getInt("number")) == 0) {
                isNewValue = true;
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return isNewValue;
    }
}