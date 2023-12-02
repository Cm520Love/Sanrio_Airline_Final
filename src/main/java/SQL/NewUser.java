package SQL;

import java.sql.*;
import java.util.ArrayList;


public class NewUser {

    static PreparedStatement pStatement;
    static ResultSet result;
    public static void addNewUser(ArrayList<Account.Information> information) {
        try {
            pStatement = Access.conn.prepareStatement("INSERT INTO Customer VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            for (int index = 0; index < information.size(); index++) {
                pStatement.setString(index+1, (information.get(index)).getInfo());
            }

            pStatement.execute();
            System.out.println("added customer successfully");

        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public static boolean isNewValue(String info, String type) {
        boolean isNewValue = false;
        try {
            pStatement = Access.conn.prepareStatement(
                    "SELECT COUNT(*) AS number FROM Customer WHERE type = ?".replace("type", type));
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
