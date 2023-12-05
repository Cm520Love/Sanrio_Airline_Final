package SQL;

import java.sql.*;
import java.util.ArrayList;


public class NewUser {


    public static void addNewUser(ArrayList<Account.Information> information) {
        // method to insert the new user and relevant information into the database
        try {
            PreparedStatement pStatement = Access.conn.prepareStatement("INSERT INTO Customer VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            // iterating through the user information and setting it in the prepared statement
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
        // method checks to see if a certain value is new in the database
        boolean isNewValue = false;
        try {
            // type represents one of the columns
            // typically it's either SSN or Username
            PreparedStatement pStatement = Access.conn.prepareStatement(
                    "SELECT COUNT(*) AS number FROM Customer WHERE type = ?".replace("type", type));
            pStatement.setString(1, info);

            ResultSet result = pStatement.executeQuery();
            result.next();

            // if the query returns 0, it means that value is new
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
