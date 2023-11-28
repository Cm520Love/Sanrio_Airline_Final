package SQL;

import java.sql.*;

import static sample.demo.Starting.conn;

public class registerUser {

    static PreparedStatement pStatement;
    static ResultSet result;
    public static void addNewUser(java.util.ArrayList<userInformation.UserInformation> userInformation) {
        try {
            pStatement = conn.prepareStatement("INSERT INTO type VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)".replace(
                                                "type", userInformation.get(0).getClass().getSimpleName()));

            for (int index = 1; index < userInformation.size(); index++) {
                pStatement.setString(index, (userInformation.get(index)).getInfo());
            }

            pStatement.execute();
            System.out.println("added customer successfully");
            //VO.getAllUsers().put(userInformation.get(6).getInfo(), userInformation);

        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
}
