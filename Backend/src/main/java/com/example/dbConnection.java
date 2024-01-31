package com.example;
import record.Records;

import java.sql.*;
import java.time.LocalDateTime;

public class dbConnection {

    public ResultSet DataBase(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");

        Connection conn = null;

        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn =
                    DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/testschema?" +
                            "user=root&password=Football1123!");
//            jdbc:mysql://${MYSQL_HOST:127.0.0.1}:3306/testschema

            // Do something with the Connection

            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM testschema.person;");

            while(rs.next()) {
                Integer ID = rs.getInt("ID");
                String firstName = rs.getString("FIRST_NAME");
                String lastName = rs.getString("LAST_NAME");

                System.out.println("ID: " + ID + " First Name: " + firstName + " Last Name: " + lastName);
            }

//            if (stmt.execute("SELECT * FROM testschema.person;")) {
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

        Connection conn = null;

        Statement stmt = null;
        ResultSet rs = null;
        int failedSucceded;

        try {
            conn =
                    DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/testschema?" +
                            "user=root&password=Football1123!");

            stmt = conn.createStatement();
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO `testschema`.`cs6969` (`stdID`, `date`) VALUES (?, ?)");

            LocalDateTime date = LocalDateTime.now();
            pstmt.setString(1, checkIn.stdId());
            pstmt.setString(2, String.valueOf(date));

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
