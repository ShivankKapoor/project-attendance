package adminMenu;

import adminMenu.dbConnection.password;
import record.Records;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Console x = new Console();

        password password = new password();

        Records.professorCheckin professorCheckin = new Records.professorCheckin("0", "test", 5);

    }
}