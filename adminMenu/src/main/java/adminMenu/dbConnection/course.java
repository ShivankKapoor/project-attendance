package adminMenu.dbConnection;

import io.github.cdimascio.dotenv.Dotenv;
import record.Records;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

//package adminMenu.dbConnection;


import java.sql.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class course {

    Dotenv dotenv = Dotenv.configure().load();

    //database user ID and password strings
    String userId = dotenv.get("DB_UserId");
    String password = dotenv.get("DB_Password");


    //sql connection
    String JDBCConnectionString = String.format("jdbc:mysql://csproject.c54ogsos2j17.us-east-2.rds.amazonaws.com:3306/seniorProject?user=%s&password=%s", userId, password);

    public ArrayList<Records.course> getAllClasses() {

        Connection conn = null;
        boolean isInClass = false;
        ArrayList<Records.course> classValues = null;
        try {
            conn = DriverManager.getConnection(JDBCConnectionString);

            Statement getAllClasses = conn.createStatement();
            LocalDateTime date = LocalDateTime.now();

            ResultSet rs = getAllClasses.executeQuery("SELECT courseId, name, professor FROM seniorProject.class;");

            System.out.println("RS: " + rs);

            classValues = new ArrayList<>();
            while (rs.next()) {
                String courseid = rs.getString("courseId");
                String name = rs.getString("name");
                String professor = rs.getString("professor");

                Records.course course = new Records.course(courseid, name, professor);

                classValues.add(course);
            }

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());

        }

        return classValues;
    }

    //TODO Hiran finish this up and check this please
    public ArrayList<Records.courseInfo> getAllClassesForStudentId(int utdId) {

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


    //For starting attendance in admin menu
    public void startProfessorCheckIn(Records.professorCheckin professorCheckin) {

        Connection conn = null;
        boolean isInClass = false;
        ArrayList<Records.courseInfo> classesValues = null;

        try {
            conn = DriverManager.getConnection(JDBCConnectionString);

            LocalDate currentDate = LocalDate.now();

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = currentDate.format(dateFormatter);
            LocalTime currentTime = LocalTime.now();
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String formattedTime = currentTime.format(timeFormatter);

            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO `seniorProject`.`classProfessorCheckIn` " +
                    "(`courseId`, `startDate`, `startTime`, `buffer`, `passwordOfTheDay`) VALUES (?,?,?,?,?);\n");

            LocalDateTime date = LocalDateTime.now();
            pstmt.setString(1, professorCheckin.courseId());
            pstmt.setString(2, formattedDate);
            pstmt.setString(3, formattedTime);
            pstmt.setInt(4, (professorCheckin.timeBuffer() * 100));
            pstmt.setString(5, professorCheckin.password());

            boolean rs = pstmt.execute();

        } catch (SQLException ex) {
            //to catch any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    //For Warnings
    public ArrayList<Integer> getStudentsWhoMissed3ClassesConsequtivly(String courseId) {

        Connection conn = null;
        boolean isInClass = false;
        ArrayList<Integer> classesValues = null;

        try {
            conn = DriverManager.getConnection(JDBCConnectionString);

            PreparedStatement pstmt = conn.prepareStatement("SELECT sc.utdId " +
                    "FROM seniorProject.studentClass sc " +
                    "LEFT JOIN ( " +
                    "    SELECT DISTINCT utdId " +
                    "    FROM seniorProject.checkIn " +
                    "    WHERE courseId = ? " +
                    "      AND time >= ( " +
                    "          SELECT startDate " +
                    "          FROM ( " +
                    "              SELECT DISTINCT startDate " +
                    "              FROM seniorProject.classProfessorCheckIn " +
                    "              ORDER BY startDate DESC " +
                    "              LIMIT 3 " +
                    "          ) AS latestDates " +
                    "          ORDER BY startDate ASC " +
                    "          LIMIT 1 " +
                    "      ) " +
                    ") ci ON sc.utdId = ci.utdId " +
                    "WHERE sc.courseId = ? " +
                    "AND ci.utdId IS NULL;");

            LocalDateTime date = LocalDateTime.now();
            pstmt.setString(1, courseId);
            pstmt.setString(2, courseId);

            ResultSet rs = pstmt.executeQuery();

            classesValues = new ArrayList<>();

            while (rs.next()) {
                int classId = rs.getInt("utdId");
                classesValues.add(classId);
            }

        } catch (SQLException ex) {
            //to catch any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return classesValues;
    }


    // This is used to get the dates that a professor wants a report for
    public ArrayList<String> getDatesList(String courseId) {

        Connection conn = null;
        boolean isInClass = false;
        ArrayList<String> classesValues = null;

        try {
            conn = DriverManager.getConnection(JDBCConnectionString);

            PreparedStatement pstmt = conn.prepareStatement("SELECT startDate FROM seniorProject.classProfessorCheckIn " +
                    "where seniorProject.classProfessorCheckIn.courseId = ?;");

            pstmt.setString(1, courseId);

            ResultSet rs = pstmt.executeQuery();

            classesValues = new ArrayList<>();

            while (rs.next()) {
                String classId = rs.getString("startDate");
                classesValues.add(classId);
            }

        } catch (SQLException ex) {
            //to catch any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return classesValues;
    }



    //To get CSV
    public ArrayList<Records.daysPresent> getStudentsAttendanceBetween2GivenDaysInclusive(String courseId, String day1, String day2) {

        Connection conn = null;
        boolean isInClass = false;
        ArrayList<Records.daysPresent> daysPresentsForEachStudentBetween2Days = null;

        try {
            conn = DriverManager.getConnection(JDBCConnectionString);

            PreparedStatement pstmt = conn.prepareStatement("SELECT u.name, sc.utdId, COALESCE(COUNT(DISTINCT DATE(ci.time)), 0) AS days_present " +
                    "FROM seniorProject.studentClass sc " +
                    "LEFT JOIN seniorProject.checkIn ci ON sc.utdId = ci.utdId AND ci.time BETWEEN ? AND ? " +
                    "LEFT JOIN seniorProject.users u ON sc.utdId = u.utdId " +
                    "WHERE sc.courseId = ? GROUP BY u.name, sc.utdId;");

            LocalDateTime date = LocalDateTime.now();
            pstmt.setString(3, courseId);
            pstmt.setString(1, day1);
            pstmt.setString(2, day2);

            ResultSet rs = pstmt.executeQuery();

            daysPresentsForEachStudentBetween2Days = new ArrayList<>();

            while (rs.next()) {
                int utdId = rs.getInt("utdId");
                int daysPresent = rs.getInt("days_present");
                String name = rs.getString("name");

                Records.daysPresent courseInfo = new Records.daysPresent(utdId, daysPresent, name);

                daysPresentsForEachStudentBetween2Days.add(courseInfo);
            }

        } catch (SQLException ex) {
            //to catch any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return daysPresentsForEachStudentBetween2Days;
    }


}

