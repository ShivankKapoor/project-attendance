//Written by Shivank Kapoor for Senior Design
//NetID: SXK190175

/*
 * This file is the runner for the entire program. Is starts the console and other database stuff.
 */

package adminMenu;

import adminMenu.dbConnection.password;
import record.Records;

public class Main {
    public static void main(String[] args) {
        Console x = new Console(); // Create new console object
        password password = new password();
        Records.professorCheckin professorCheckin = new Records.professorCheckin("0", "test", 5);
    }
}