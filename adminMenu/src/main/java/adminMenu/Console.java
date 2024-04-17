//Written by Shivank Kapoor for Senior Design
//NetID: sxk190175
/*
 * This file is the main window of the entire program
 *  -Lets them navigate to the different pages
 */

package adminMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Console extends JFrame {

    public Console() {
        setTitle("Console");
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Will end program when exited
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout()); //Layout system
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        JButton getCSVButton = new JButton("Get Class List"); //Button to get CSVs that can be uploaded to elearing
        getCSVButton.addActionListener(new ActionListener() { //Listener if the Get class list button is clicked
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create CSVGetter object
                CSVGetter x = new CSVGetter();
                // Close the console window
                dispose();
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(getCSVButton, constraints);

        JButton excuseAbsenceButton = new JButton("Excuse Absence"); // Button to excuse a student
        excuseAbsenceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excuser x = new excuser(); //Open the excuser window
                dispose();// Close the main window
            }
        });
        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(excuseAbsenceButton, constraints);

        JButton seeWarningsButton = new JButton("See Warnings"); //Button to see if there were warning for students for a class
        seeWarningsButton.addActionListener(new ActionListener() { //Listener for warnings button
            @Override
            public void actionPerformed(ActionEvent e) {
                warnings x = new warnings(); //Open warnings window
                dispose(); //Close current window
            }
        });
        constraints.gridx = 2;
        constraints.gridy = 0;
        panel.add(seeWarningsButton, constraints);

        JButton startAttendanceButton = new JButton("Start Attendance"); //Window to start taking attendance
        startAttendanceButton.addActionListener(new ActionListener() { //Listener to start taking attendance
            @Override
            public void actionPerformed(ActionEvent e) {
                attendanceStarter x = new attendanceStarter(); // Open the attendance starter window
                dispose(); // Close the console window
            }
        });
        constraints.gridx = 3;
        constraints.gridy = 0;
        panel.add(startAttendanceButton, constraints);

        add(panel);
        pack(); // Adjusting frame size to fit components
        setLocationRelativeTo(null); // Centering the frame on the screen
        setVisible(true);
    }
}
