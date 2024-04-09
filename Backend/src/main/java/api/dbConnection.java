package api;
import record.Records;

import java.sql.*;
import java.time.LocalDateTime;
import io.github.cdimascio.dotenv.Dotenv;

public class dbConnection {

    Dotenv dotenv = Dotenv.configure().load();
    String userId = dotenv.get("DB_UserId");
    String password = dotenv.get("DB_Password");


    String JDBCConnectionString = String.format("jdbc:mysql://csproject.c54ogsos2j17.us-east-2.rds.amazonaws.com:3306/seniorProject?user=%s&password=%s", userId, password);

    public ResultSet DataBase(String[] args) {
        System.out.printf("Hello and welcome!");

        Connection conn = null;

        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn =
                    DriverManager.getConnection(JDBCConnectionString);

            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM seniorProject.person;");

            while(rs.next()) {
                Integer ID = rs.getInt("ID");
                String firstName = rs.getString("FIRST_NAME");
                String lastName = rs.getString("LAST_NAME");

                System.out.println("ID: " + ID + " First Name: " + firstName + " Last Name: " + lastName);
            }


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

        System.out.println("userID: " + userId + " password: " + "***********");
        System.out.println("Connection string: " + JDBCConnectionString);


        Connection conn = null;
        int count=-1;
        int failedSucceded;
        try {
            conn = DriverManager.getConnection(JDBCConnectionString);


            System.out.println("this is the courseId="+ checkIn.courseId());
            System.out.println("this is the utdId="+ checkIn.utdId());

            count= isUserInClass(checkIn.courseId(), checkIn.utdId());
            //System.out.println("this is in the main part:"+ count);
            if(count<1){
                System.out.println("you are not enrolled for the class, you cannot sign in");
                failedSucceded=0;
                return failedSucceded;
            }

            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO `seniorProject`.`checkIn` (`courseId`, `utdId`, `netId`, `time`) VALUES (?, ?, ?, ?)");
            LocalDateTime date = LocalDateTime.now();
            pstmt.setString(1, checkIn.courseId());
            pstmt.setInt(2, checkIn.utdId());
            pstmt.setString(3, checkIn.netId());
            pstmt.setString(4, String.valueOf(date));

            failedSucceded = pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            failedSucceded = 0;
        }

        return failedSucceded;
    }

    public int isUserInClass(String courseId, int utdId ){


        System.out.println("userID: " + userId + " password: " + password);
        System.out.println("Connection string: " + JDBCConnectionString);
        Connection conn = null;
        int count=-1;

        String stmt= "Select count(`utdId`) From `seniorProject`.`studentClass` where `courseId`="+courseId +" and `utdId`=" +utdId+";";
        int succeeded;
        try{
            conn = DriverManager.getConnection(JDBCConnectionString);
            Statement stmt3 = conn.createStatement();
            ResultSet rs= stmt3.executeQuery(stmt);
            if(rs.next()){
                count = rs.getInt(1);
            }
            //count=rs.getInt(1);
            System.out.println("total count is studentClass:"+ count);

        }
        catch(SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());

        }

        return count;
    }
}
