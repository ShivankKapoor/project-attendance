package api;
import record.Records;

import java.sql.*;
import java.time.LocalDateTime;
import io.github.cdimascio.dotenv.Dotenv;

public class dbConnection {

    Dotenv dotenv = Dotenv.configure().load();

    //database user ID and password strings
    String userId = dotenv.get("DB_UserId");
    String password = dotenv.get("DB_Password");


    //sql connection
    String JDBCConnectionString = String.format("jdbc:mysql://csproject.c54ogsos2j17.us-east-2.rds.amazonaws.com:3306/seniorProject?user=%s&password=%s", userId, password);

    //function to check in to the database
    public ResultSet DataBase(String[] args) {
        System.out.printf("Hello and welcome!");

        Connection conn;

        Statement stmt;
        ResultSet rs = null;

        try {
            //connecting to database
            conn = DriverManager.getConnection(JDBCConnectionString);

            //creating statement to execute
            stmt = conn.createStatement();
            //executing the statement and getting result in rs (response)
            rs = stmt.executeQuery("SELECT * FROM seniorProject.person;");

            //getting ID, First name, and last name
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

    // function forChecking in student entry
    public boolean addCheckInEntrty(Records.Checkin checkIn) {

        Connection conn = null;
        boolean isInClass=false;
        try {
            conn = DriverManager.getConnection(JDBCConnectionString);

            //count holds the return value if the function isUserInClass which returns whether the student exists in the class or not
            isInClass= isUserInClass(checkIn.courseId(), checkIn.utdId(), checkIn.netId());

            //if return value is false, then the student is not in class and the student checkin is not recorded
            if(!isInClass){
                System.out.println("you are not enrolled for the class, you cannot sign in");
                //returning the value failedSucceeded=0 so that we know that the checkin failed
                return false;
            }

            //preparing statement to execute to enter the values of the student for the course and date
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO `seniorProject`.`checkIn` (`classId`, `utdId`, `passcode`, `time`) VALUES (?, ?, ?, ?)");
            LocalDateTime date = LocalDateTime.now();
            pstmt.setString(1, checkIn.classId());
            pstmt.setInt(2, checkIn.utdId());
            pstmt.setString(3, checkIn.passcode());
            pstmt.setString(4, String.valueOf(date));

            pstmt.executeUpdate();

        } catch (SQLException ex) {
            //to catch any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return false;
        }

        return true;
    }

    //function to check if the user is in the class
    public boolean isUserInClass(String courseId, int utdId, String netId ){

        Connection conn = null;

        //count to keep track of the number of records with the student UTDID in the class
        int count=-1;
        //string to get the student's net ID from the database. This will be later compared with the net ID of the student that enetred the details
        String netIdSQL="";
        //Boolean to keep track of the existence of the student in the class
        boolean isInClass=false;


        try{
            conn = DriverManager.getConnection(JDBCConnectionString);

            //checking if the UTDID exists in the class
            PreparedStatement getCountOfUtdIdInClass= conn.prepareStatement( "Select count(`utdId`) From `seniorProject`.`studentClass` where `courseId`=? and `utdId`=?;");
            getCountOfUtdIdInClass.setString(1, courseId);
            getCountOfUtdIdInClass.setInt(2, utdId);
            //saving the return value for count of students in class with the same UTDID in rs
            ResultSet isUtdIdInCourse= getCountOfUtdIdInClass.executeQuery();

            //comparing the netID from the entry and checking to see if the netID in the database and the quiz match with teh UTDID
            PreparedStatement getNetIdAndUtdId= conn.prepareStatement("SELECT `netId` FROM `seniorProject`.`users` where utdId=?;");
            getNetIdAndUtdId.setInt(1, utdId);
            //saving the value of the netID for the UTDID From the database in rs2
            ResultSet isNetIdandUtdIdSameUser= getNetIdAndUtdId.executeQuery();

            //getting value to see if student exists in class. if count=1, student is in class, if count<1 then the student is not
            if(isUtdIdInCourse.next()){
                count = isUtdIdInCourse.getInt(1);
            }
            //getting the value of the netID from the database
            if(isNetIdandUtdIdSameUser.next()){
                netIdSQL = isNetIdandUtdIdSameUser.getString(1);
            }

            //comparing the values of netID and count to make sure all details match, if they dont, then student does not exist in class
            if(count>0 && netId.equalsIgnoreCase(netIdSQL)){
                isInClass=true;
            }

        }
        catch(SQLException ex) {
            //catch for any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());

        }

        //returns True if student is in the class, or false if the student is not
        return isInClass;
    }
}

//SELECT count(*) as attendance FROM seniorProject.checkIn where utdId=12332 and courseId=0;
