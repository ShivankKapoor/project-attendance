package adminMenu.dbConnection;
import record.Records;


import java.sql.*;
import java.time.LocalDateTime;
import io.github.cdimascio.dotenv.Dotenv;
public class password {

    Dotenv dotenv = Dotenv.configure().load();

    //database user ID and password strings
    String userId = dotenv.get("DB_UserId");
    String password = dotenv.get("DB_Password");


    //sql connection
    String JDBCConnectionString = String.format("jdbc:mysql://csproject.c54ogsos2j17.us-east-2.rds.amazonaws.com:3306/seniorProject?user=%s&password=%s", userId, password);

    public boolean addDailyPassword(Records.professorCheckin professorCheckin) {

        Connection conn = null;
        boolean isInClass=false;
        try {
            conn = DriverManager.getConnection(JDBCConnectionString);

            //preparing statement to execute to enter the values of the student for the course and date


            PreparedStatement pstmt = conn.prepareStatement("UPDATE `seniorProject`.`class` SET `timeMargin` = ?, `password` = ? WHERE (`classId` = ?);");
            LocalDateTime date = LocalDateTime.now();
            pstmt.setInt(1, (professorCheckin.timeBuffer() * 100));
            pstmt.setString(2, professorCheckin.password());
            pstmt.setString(3, professorCheckin.classId());

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


}
