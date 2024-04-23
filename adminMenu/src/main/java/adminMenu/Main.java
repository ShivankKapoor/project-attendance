package adminMenu;

import adminMenu.dbConnection.course;
import adminMenu.dbConnection.password;
import record.Records;

import java.util.ArrayList;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
//        Console x = new Console();

        password password = new password();
        
        course course = new course();
        
        Records.professorCheckin professorCheckin = new Records.professorCheckin("0", "test", 5);

        ArrayList<Records.course> classValues = course.getAllClasses();


    }
}