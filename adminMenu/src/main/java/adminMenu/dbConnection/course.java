package adminMenu.dbConnection;

import io.github.cdimascio.dotenv.Dotenv;
import record.Records;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

//package adminMenu.dbConnection;


import java.sql.*;
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
    public boolean getAllClassesForStudentId(int utdId) {

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
            return false;
        }

        return true;
    }


}

