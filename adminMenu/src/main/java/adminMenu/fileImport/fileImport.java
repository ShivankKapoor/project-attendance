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
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class fileImport {




    //database user ID and password strings
    String userId = ("admin");
    String password = ("Angry1123!");


    //sql connection
    String JDBCConnectionString = String.format("jdbc:mysql://csproject.c54ogsos2j17.us-east-2.rds.amazonaws.com:3306/seniorProject?user=%s&password=%s", userId, password);


    //lets us upload a txt file containing the class information and its students, so they can start using the system.
    public ArrayList<Records.course> importData(String filePath) {



        String courseId = null;
        String professorName = null;

        try (Scanner scanner = new Scanner(new File(filePath))) {
            // Skip header lines until the line containing "NetId"
            while (scanner.hasNextLine()) {
                String line1 = scanner.nextLine();
                String line2 = scanner.nextLine();
                String line3 = scanner.nextLine();

                String[] value = parseLine(line1, line2);

                courseId = value[1];
                professorName = value[0];

                if (line3.contains("NetId")) {
                    break;
                }
            }

            Connection conn = DriverManager.getConnection(JDBCConnectionString);
            PreparedStatement pStmt = conn.prepareStatement("INSERT IGNORE INTO `seniorProject`.`users` " +
                    "(`utdId`, `Name`, `netId`) VALUES (?, ?, ?);");

            PreparedStatement pStmt2 = conn.prepareStatement("INSERT IGNORE INTO `seniorProject`.`studentClass` " +
                    "(`courseId`, `utdId`) VALUES (?, ?);");

            PreparedStatement pStmt3 = conn.prepareStatement("INSERT IGNORE INTO `seniorProject`.`class` " +
                    "(`courseId`, `name`, `professor`) VALUES (?, ?, ?);");

            pStmt3.setString(1, courseId);
            pStmt3.setString(2, courseId);
            pStmt3.setString(3, professorName);

            // Process the tab-separated data
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split("\t");

                String netId = fields[0];
                int utdId = Integer.parseInt(fields[1]);
                String name = (fields[2] + " " + fields[4]);

                pStmt.setInt(1, utdId);
                pStmt.setString(2, name);
                pStmt.setString(3, netId);

                pStmt2.setString(1, fields[6]);
                pStmt2.setString(2, fields[1]);

                pStmt.addBatch();
                pStmt2.addBatch();
                System.out.println();
            }
                int[] returnValues = pStmt.executeBatch();
                int[] returnValues2 = pStmt2.executeBatch();
                pStmt3.execute();
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


    public static String[] parseLine(String line1, String line2) {
        String[] result = new String[2];

        Pattern instructorPattern = Pattern.compile("Instructors: (.+?) /");
        Matcher instructorMatcher = instructorPattern.matcher(line2);
        if (instructorMatcher.find()) {
            result[0] = instructorMatcher.group(1).trim();
        }

        Pattern courseIdPattern = Pattern.compile("- Class Roster - (.+?) -");
        Matcher courseIdMatcher = courseIdPattern.matcher(line1);
        if (courseIdMatcher.find()) {
            result[1] = courseIdMatcher.group(1).trim();
        }

        return result;
    }

}