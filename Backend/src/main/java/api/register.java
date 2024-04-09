package api;
import record.Records;

import java.sql.*;
import java.time.LocalDateTime;
import io.github.cdimascio.dotenv.Dotenv;

public class register {

    Dotenv dotenv = Dotenv.configure().load();
    String userId = dotenv.get("DB_UserId");
    String password = dotenv.get("DB_Password");


    String JDBCConnectionString = String.format("jdbc:mysql://csproject.c54ogsos2j17.us-east-2.rds.amazonaws.com:3306/seniorProject?user=%s&password=%s", userId, password);

    public boolean addNewUser(Records.register register) {

        Connection conn = null;
        int count=-1;
        try {
            conn = DriverManager.getConnection(JDBCConnectionString);

            PreparedStatement addUserToRegisterTable = conn.prepareStatement("INSERT INTO `seniorProject`.`register` (`utdID`, `password`) VALUES (?, ?)");
            addUserToRegisterTable.setString(1, register.utdId());
            addUserToRegisterTable.setString(2, register.password());

            if (addUserToRegisterTable.executeUpdate() == 0) {
                System.out.println("Error No lines updated while updating password");
                //give error logging possibly because user is aldready registered
                return false;
            }

            PreparedStatement addUsertoUsersTable = conn.prepareStatement("INSERT INTO `seniorProject`.`users` (`utdId`, `Name`, `netId`) VALUES (?, ?, ?);");
            addUsertoUsersTable.setString(1, register.utdId());
            addUsertoUsersTable.setString(2, register.fullName());
            addUsertoUsersTable.setString(3, register.netId());
            if (addUsertoUsersTable.executeUpdate() == 0){
                System.out.println("Error No lines updated while adding the new user");
                return false;
            }

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return false;
        }

        return true;
    }

    public boolean login(Records.login login) {

        Connection conn = null;
        int count=-1;
        try {
            conn = DriverManager.getConnection(JDBCConnectionString);
            // SELECT count('idregister') FROM seniorProject.register
            //where utdId = 'test' and password = 'test';
            PreparedStatement checkIfValidUserIdandPassword = conn.prepareStatement("SELECT count('idregister') FROM seniorProject.register where utdId = (?) and password = (?);");
            checkIfValidUserIdandPassword.setString(1, login.utdId());
            checkIfValidUserIdandPassword.setString(2, login.password());

            if (checkIfValidUserIdandPassword.executeUpdate() == 0) {
                System.out.println("Incorrect password or userID");
                return false;
            }

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return false;
        }

        return true;
    }

}
