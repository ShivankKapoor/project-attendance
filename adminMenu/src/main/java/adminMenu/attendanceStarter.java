//Written by Shivank Kapoor for Senior Design
//NetID: sxk190175
/*
 * Allows the admin to start taking attendance
 */

package adminMenu;

import adminMenu.dbConnection.course;
import record.Records;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class attendanceStarter extends JFrame {

    public attendanceStarter() {
        setTitle("Attendance Starter");
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Will end program if exited
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints(); // Layout system
        constraints.insets = new Insets(10, 10, 10, 10);

        // Course ID Label
        JLabel courseIDLabel = new JLabel("Course ID:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(courseIDLabel, constraints);

        // Class Dropdown
        course course = new course(); // create a new object that lets us get all classes
        Object[] classObjects=course.getAllClasses().toArray(); // get all these classes as objects

        String[] classes = new String[classObjects.length];
        for (int i = 0; i < classObjects.length; i++) {
            classes[i] = ((Records.course) classObjects[i]).name(); // get all of those objects class names
        }

        JComboBox<String> courseIDField =  new JComboBox<>(classes);
        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(courseIDField, constraints);

        // Password Label
        JLabel passwordLabel = new JLabel("Password:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(passwordLabel, constraints);

        // Password Field
        JPasswordField passwordField = new JPasswordField(20);
        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(passwordField, constraints);

        // Minutes Label
        JLabel minutesLabel = new JLabel("Minutes:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(minutesLabel, constraints);

        // Minutes Dropdown
        Integer[] minutes = new Integer[15]; // add the minutes
        for (int i = 0; i < 15; i++) {
            minutes[i] = (i + 1) * 5;
        }
        JComboBox<Integer> minutesDropDown = new JComboBox<>(minutes);
        constraints.gridx = 1;
        constraints.gridy = 2;
        panel.add(minutesDropDown, constraints);

        // Submit Button
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { // Will send password and minutes to the backend
                String courseID = (String) courseIDField.getSelectedItem();
                String password = new String(passwordField.getPassword());
                Integer minutes = (Integer) minutesDropDown.getSelectedItem();
                Records.professorCheckin professorCheckin = new Records.professorCheckin(courseID, password, minutes); // Sends
                                                                                                                       // it
               course.startProfessorCheckIn(professorCheckin);
                // TODO: Add logic to handle submission
                JOptionPane.showMessageDialog(attendanceStarter.this,
                        "Course ID: " + courseID + "\nPassword: " + password + "\nMinutes: " + minutes,
                        "Submission Details", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        panel.add(submitButton, constraints);

        // Go Back Button
        JButton goBackButton = new JButton("Go Back");
        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Console();
                dispose();
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        panel.add(goBackButton, constraints);

        add(panel);
        pack();
        setLocationRelativeTo(null); // open in the middle of the screen
        setVisible(true);
    }
}
