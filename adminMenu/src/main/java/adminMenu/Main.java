//Written by Shivank Kapoor for Senior Design
//NetID: SXK190175

/*
 * This file is the runner for the entire program. Is starts the console and other database stuff.
 */

package adminMenu;

import adminMenu.dbConnection.course;
//import adminMenu.dbConnection.password;
import adminMenu.fileImport.fileImport;
import record.Records;
import java.util.ArrayList;
public class Main {
    public static void main(String[] args) {
//        Console x = new Console(); // Create new console object
        //password password = new password();
        course course = new course();
         Records.professorCheckin professorCheckin = new Records.professorCheckin("0", "test", 5);
//        ArrayList<Records.course> classValues = course.getAllClasses();

        fileImport fileImport = new fileImport();

        fileImport.importData("test");

    }
}