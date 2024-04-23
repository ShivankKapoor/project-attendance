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

    public boolean getAllClassesForStudentId(int utdId) {

        Connection conn = null;
        boolean isInClass = false;
        ArrayList<String> classesValues = null;

        try {
            conn = DriverManager.getConnection(JDBCConnectionString);

            PreparedStatement pstmt = conn.prepareStatement("select sc.utdId, c.courseId, c.name, c.classId from " +
                    "seniorProject.studentClass as sc left join seniorProject.class as c on c.courseId where sc.utdId = ?;");

            LocalDateTime date = LocalDateTime.now();
            pstmt.setInt(1, utdId);

            pstmt.executeQuery();

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

