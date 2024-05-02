/* team 55 CS4485
This file is used for most of the endpoints that are related to the student check in.
*/
package api;
import record.Records;
import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;



public class dbConnection {

    //database user ID and password strings
    String userId = "";
    String password = "";

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
            while (rs.next()) {
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


    /* function for Checking in student entry
    When the student enters the utdID and the password it gets added into the checkIn table * */
    public boolean addCheckInEntrty(Records.Checkin checkIn) {

        Connection conn = null;
        boolean isInClass = false;
        int numOfAttendance = 0;
        String timing = "";
        try {
            conn = DriverManager.getConnection(JDBCConnectionString);

            //count holds the return value if the function isUserInClass which returns whether the student exists in the class or not
            isInClass = isUserInClass(checkIn.courseId(), checkIn.utdId());
            boolean isValid =  checkIfValidValues(checkIn.courseId(), checkIn.password());

            //if return value is false, then the student is not in class and the student checkin is not recorded
            if (!isInClass) {
                System.out.println("you are not enrolled for the class, you cannot sign in");
                //returning the value failedSucceeded=0 so that we know that the checkin failed
                return false;
            }

            if (!isValid) {
                return false;
            }

            numOfAttendance = numberAttendance(checkIn.courseId(), checkIn.utdId());

            //preparing statement to execute to enter the values of the student for the course and date
            //INSERT INTO `seniorProject`.`checkIn` (`courseId`, `utdId`, `netId`, `time`) VALUES ('0', '123', 'test', '2');
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO `seniorProject`.`checkIn` (`courseId`, `utdId`, `time`) VALUES (?, ?, ?);");
            LocalDateTime date = LocalDateTime.now();
            pstmt.setString(1, checkIn.courseId());
            pstmt.setInt(2, checkIn.utdId());
            pstmt.setString(3, String.valueOf(date));

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


    /*function to check if the user is in the class
    * this is done by checking the table studentClass which has the record of students in the class
    * The utdId and the course number are compared and if teh student exists, it lets them checkIn*/
    public boolean isUserInClass(String courseId, int utdId) {

        Connection conn = null;

        //count to keep track of the number of records with the student UTDID in the class
        int count = -1;
        //string to get the student's net ID from the database. This will be later compared with the net ID of the student that enetred the details
        String netIdSQL = "";
        //Boolean to keep track of the existence of the student in the class
        boolean isInClass = false;


        try {
            conn = DriverManager.getConnection(JDBCConnectionString);

            //checking if the UTDID exists in the class
            PreparedStatement getCountOfUtdIdInClass = conn.prepareStatement("Select count(`utdId`) From `seniorProject`.`studentClass` where `courseId`=? and `utdId`=?;");
            getCountOfUtdIdInClass.setString(1, courseId);
            getCountOfUtdIdInClass.setInt(2, utdId);
            //saving the return value for count of students in class with the same UTDID in rs
            ResultSet isUtdIdInCourse = getCountOfUtdIdInClass.executeQuery();

            //comparing the netID from the entry and checking to see if the netID in the database and the quiz match with teh UTDID


            //getting value to see if student exists in class. if count=1, student is in class, if count<1 then the student is not
            if (isUtdIdInCourse.next()) {
                count = isUtdIdInCourse.getInt(1);
            }
            //getting the value of the netID from the database


            //comparing the values of netID and count to make sure all details match, if they dont, then student does not exist in class
            if (count > 0 ) {
                isInClass = true;
            }

        } catch (SQLException ex) {
            //catch for any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());

        }

        //returns True if student is in the class, or false if the student is not
        return isInClass;
    }


    /*Function is used to record the total number of days a student has attended class
    *this is done by checking the checkin table and getting the count of the number of student logins
    * */
    public int numberAttendance(String courseId, int utdId) {

        Connection conn = null;
        int count = -1;

        try {
            conn = DriverManager.getConnection(JDBCConnectionString);

            //getting count from the class with the utdid and courseid
            PreparedStatement getCountAttendance = conn.prepareStatement("SELECT count(*) FROM `seniorProject`.`checkIn` where  `courseId`=? and `utdId`=?;");
            getCountAttendance.setString(1, courseId);
            getCountAttendance.setInt(2, utdId);

            //saving the return value for number of attended days for the class
            ResultSet isCourseAttendance = getCountAttendance.executeQuery();

            if (isCourseAttendance.next()) {
                count = isCourseAttendance.getInt(1);
            }
        } catch (SQLException ex) {
            //catch for any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());

        }

        System.out.println("attendance:" + count);
        return count;

    }


    /*function checks for the validity of the checkin
    * It checks if the student has the correct password, checkIn within the time window and on the correct day*/
    public boolean checkIfValidValues(String courseId, String password) {

        Connection conn = null;
        String timing = "";

        try {
            conn = DriverManager.getConnection(JDBCConnectionString);

            //getting the local date and formatting it
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = currentDate.format(dateFormatter);

            //getting the local time and formatting it
            LocalTime currentTime = LocalTime.now();
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String formattedTime = currentTime.format(timeFormatter);

            //The SQL statement to get the startDate, the startTime the time window and the password
            //SELECT startDate, startTime, passwordOfTheDay FROM seniorProject.classProfessorCheckIn where startDate = '2024-04-27' and courseId = '0' ORDER BY idclassProfessorCheckIn desc LIMIT 1;
            PreparedStatement getStartTiming = conn.prepareStatement("SELECT startDate, startTime, buffer,  passwordOfTheDay " +
                    "FROM seniorProject.classProfessorCheckIn where startDate = ? and courseId = ? " +
                    "ORDER BY idclassProfessorCheckIn desc LIMIT 1;");
            getStartTiming.setString(1, formattedDate);
            getStartTiming.setString(2, courseId);
            ResultSet ClassStartTime = getStartTiming.executeQuery();

            String startDate = null;
            String startTime = null;
            String bufferTime = null;
            String passwordOfTheDay = null;

            //getting the values for the class start date, start Time, time window and the password
            if (ClassStartTime.next()) {
                startDate = ClassStartTime.getString("startDate");
                startTime = ClassStartTime.getString("startTime");
                bufferTime = ClassStartTime.getString("buffer");
                passwordOfTheDay = ClassStartTime.getString("passwordOfTheDay");
            }

            //checking to see if the passwords match
            if (!Objects.equals(passwordOfTheDay, password)) {
                return false;
            }

            //checking to seeif the start days match
            if (!Objects.equals(startDate, formattedDate)) {
                return false;
            }

            //checking to see if the checkin is within the time window
            if (!isInTimeWindow(startTime, bufferTime)) {
                return false;
            }

        } catch (SQLException ex) {

            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());

        }

        return true;

    }


    /*function to check in if the student has checked in within the time window
    * This is done by passing the start time and the time window*/
    public boolean isInTimeWindow(String baseTime, String bufferTimeString) {
        // Parse SQL time
        LocalTime sqlTime = LocalTime.parse(baseTime);

        // Parse buffer time
        String[] bufferParts = bufferTimeString.split(":");
        int hours = Integer.parseInt(bufferParts[0]);
        int minutes = Integer.parseInt(bufferParts[1]);
        int seconds = Integer.parseInt(bufferParts[2]);

        // Convert buffer time to ISO-8601 duration format
        String isoDuration = "PT" + hours + "H" + minutes + "M" + seconds + "S";

        // Parse the ISO duration
        Duration bufferDuration = Duration.parse(isoDuration);

        // Calculate the time range
        LocalTime upperBound = sqlTime.plus(bufferDuration);

        // Get current time with timezone
        LocalTime currentTime = LocalTime.now(ZoneId.systemDefault());

        return currentTime.isAfter(sqlTime) && currentTime.isBefore(upperBound);
    }

    public ArrayList<Records.courseInfo> getAllClassesForStudent(int utdId) {

        Connection conn = null;
        boolean isInClass = false;
        ArrayList<Records.courseInfo> classesValues = null;

        try {
            conn = DriverManager.getConnection(JDBCConnectionString);

            PreparedStatement pstmt = conn.prepareStatement("select c.classId, c.courseId, c.name from seniorProject.studentClass as sc " +
                    "inner join seniorProject.class as c on c.courseId = sc.courseId where sc.utdId = ?;");

            LocalDateTime date = LocalDateTime.now();
            pstmt.setInt(1, utdId);

            ResultSet rs = pstmt.executeQuery();

            classesValues = new ArrayList<>();

            while (rs.next()) {
                String classId = rs.getString("classId");
                String courseId = rs.getString("courseId");
                String name = rs.getString("name");

                Records.courseInfo courseInfo = new Records.courseInfo(classId, courseId, name);

                classesValues.add(courseInfo);
            }

        } catch (SQLException ex) {
            //to catch any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return classesValues;
    }

    public ArrayList<Records.timings> getTimings(String courseId) {

        Connection conn = null;
        boolean isInClass = false;
        ArrayList<Records.timings> classesValues = null;

        try {
            conn = DriverManager.getConnection(JDBCConnectionString);

            PreparedStatement pstmt = conn.prepareStatement("SELECT startDate, startTime, buffer FROM seniorProject.classProfessorCheckIn" +
                    "where courseId = ? order by idclassProfessorCheckIn desc limit 1;");

            LocalDateTime date = LocalDateTime.now();
            pstmt.setString(1, courseId);

            ResultSet rs = pstmt.executeQuery();

            classesValues = new ArrayList<>();

            while (rs.next()) {
                String startDate = rs.getString("startDate");
                String startTime = rs.getString("startTime");
                String timeBuffer = rs.getString("buffer");

                Records.timings timings = new Records.timings(startDate, startTime, timeBuffer);

                classesValues.add(timings);
            }

        } catch (SQLException ex) {
            //to catch any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return classesValues;
    }

}

