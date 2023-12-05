package DatabaseInformation;
import Interfaces.*;
import java.util.Scanner;
import java.io.*;

import java.sql.*;

public class AirportTable implements InitializeDatabase{

    public void initialize() throws Exception {
        // get the information in csv file
        Scanner input = new Scanner(new File("AirportInformation.csv"));

        // connect to db
        String url = "jdbc:sqlserver://test-sunnysqlserver.database.windows.net:1433;database=Sanrio_Airlines;user=stinkysnoopy@test-sunnysqlserver;password=AdminPass!!!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
        Connection conn = DriverManager.getConnection(url);
        System.out.println("Database is connected.");

        // prepare statement
        PreparedStatement pStatement = conn.prepareStatement("INSERT INTO Airport VALUES (?, ?, ?, ?)");

        // skips over the column labels
        input.nextLine();

        // while the file still has information
        while (input.hasNext()) {
            // get each line, split it using ,
            String line = input.nextLine();
            String[] stuff = line.split(",");

            // put each part in the array into the prepared statement
            for (int index = 1, index2 = 0; index <= 4; index++, index2++) {
                pStatement.setString(index, stuff[index2]);
            }

            // place that single row of airport information into the db
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
