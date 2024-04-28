package adminMenu.fileImport;

import io.github.cdimascio.dotenv.Dotenv;
import record.Records;

import java.io.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

//import java.io.BufferedReader;
//import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner; // Import the Scanner class to read text files


public class fileImport {


    Dotenv dotenv = Dotenv.configure().load();

    //database user ID and password strings
    String userId = dotenv.get("DB_UserId");
    String password = dotenv.get("DB_Password");


    //sql connection
    String JDBCConnectionString = String.format("jdbc:mysql://csproject.c54ogsos2j17.us-east-2.rds.amazonaws.com:3306/seniorProject?user=%s&password=%s", userId, password);

    public ArrayList<Records.course> importData() {

        System.out.println("this is a test");

        // read file
        String filePath = "C:\\Users\\Admin\\Downloads\\Sample-CS1000-Coursebook.txt";

        try (Scanner scanner = new Scanner(new File(filePath))) {
            // Skip header lines until the line containing "NetId"
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains("NetId")) {
                    break;
                }
            }

            Connection conn = DriverManager.getConnection(JDBCConnectionString);
            PreparedStatement pStmt = conn.prepareStatement("INSERT IGNORE INTO `seniorProject`.`users` " +
                    "(`utdId`, `Name`, `netId`) VALUES (?, ?, ?);");

            // Process the tab-separated data
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split("\t");

                    // This is where the inserts go

//                Records.newUser newUser = new Records.newUser(Integer.parseInt(fields[1]), fields[2], fields[0]);

                String netId = fields[0];
                int utdId = Integer.parseInt(fields[1]);
                String name = (fields[2] + " " + fields[4]);

                pStmt.setInt(1, utdId); //This might be pStmt.SetInt(0, fileid) depending on teh type of fileid)
                pStmt.setString(2, name);
                pStmt.setString(3, netId);

                pStmt.addBatch();
//                }
                System.out.println(); // Print a new line after each row
            }
                int[] returnValues = pStmt.executeBatch();
                System.out.println(Arrays.toString(returnValues));

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Connection conn = null;
        boolean isInClass = false;
        ArrayList<Records.course> classValues = null;

        return classValues;
    }

}