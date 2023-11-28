package DatabaseInformation;
import Interfaces.*;
import java.util.Scanner;
import java.io.*;

import java.sql.*;

public class AirportTable implements InitializeDatabase{

    public AirportTable() {
    }

    public void initialize() throws Exception {
        Scanner input = new Scanner(new File("AirportInformation.csv"));
        // connect to db
        String url = "jdbc:sqlserver://test-sunnysqlserver.database.windows.net:1433;database=Sanrio_Airlines;user=stinkysnoopy@test-sunnysqlserver;password=AdminPass!!!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
        Connection conn = DriverManager.getConnection(url);
        System.out.println("Database is connected.");

        // prepare statement
        PreparedStatement pStatement = conn.prepareStatement("INSERT INTO Airport VALUES (?, ?, ?, ?)");

        // skips over the column labels
        input.nextLine();

        while (input.hasNext()) {
            String word = input.nextLine();
            String[] stuff = word.split(",");
            for (int index = 1, index2 = 0; index <= 4; index++, index2++) {
                pStatement.setString(index, stuff[index2]);
            }
            pStatement.execute();
        }
    }

    public static void main(String[] args) {
        try {
            new AirportTable().initialize();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
