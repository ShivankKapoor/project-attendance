package api;
import record.Records;

import java.sql.*;
import java.time.LocalDateTime;
import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntry;



public class dbConnection {

    Dotenv dotenv = Dotenv.configure().load();
    String userId = dotenv.get("DB_UserId");
    String password = dotenv.get("DB_Password");


    String JDBCConnectionString = String.format("jdbc:mysql://csproject.c54ogsos2j17.us-east-2.rds.amazonaws.com:3306/seniorProject?user=%s&password=%s", userId, password);

    public ResultSet DataBase(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");

        Connection conn = null;

        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn =
                    DriverManager.getConnection(JDBCConnectionString);
//            jdbc:mysql://${MYSQL_HOST:127.0.0.1}:3306/seniorProject

            // Do something with the Connection

            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM seniorProject.person;");

            while(rs.next()) {
                Integer ID = rs.getInt("ID");
                String firstName = rs.getString("FIRST_NAME");
                String lastName = rs.getString("LAST_NAME");

                System.out.println("ID: " + ID + " First Name: " + firstName + " Last Name: " + lastName);
            }

//            if (stmt.execute("SELECT * FROM seniorProject.person;")) {
//                rs = stmt.getResultSet();
//            }


            System.out.println("response: " + rs);


        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return rs;
    }

    public int addCheckInEntrty(Records.Checkin checkIn) {

        System.out.println("userID: " + userId + " password: " + password);
        System.out.println("Connection string: " + JDBCConnectionString);


        Connection conn = null;

        Statement stmt = null;
        ResultSet rs = null;
        int failedSucceded;
        try {
            conn = DriverManager.getConnection(JDBCConnectionString);

            stmt = conn.createStatement();
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO `seniorProject`.`checkIn` (`courseId`, `utdId`, `netId`, `time`) VALUES ('0', '12345678', 'hvrtest', '0000-00-00')");
//`seniorProject`.`checkIn` (`courseId`, `utdId`, `netId`) VALUES ('0', '1234', 'hvr190000');
            LocalDateTime date = LocalDateTime.now();
//            pstmt.setString(1, checkIn.stdId());
//            pstmt.setString(2, String.valueOf(date));

            failedSucceded = pstmt.executeUpdate();

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            failedSucceded = 0;
        }

        return failedSucceded;
    }
}
