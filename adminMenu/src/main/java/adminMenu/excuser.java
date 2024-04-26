//Written by Shivank Kapoor for Senior Design
//NetID: SXK190175

/*
 * This file will let admins excuse a student for missing a class
 */

package adminMenu;
import adminMenu.dbConnection.course;
import record.Records;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class excuser extends JFrame {
    private JComboBox<String> dayDropdown; // Will hold the day field for the date of excusable absence
    private JComboBox<String> monthDropdown; // Will hold the month field for the date of excusable absence
    private JComboBox<String> yearDropdown; // Will hold the year field for the date of excusable absence
    private JTextField utdIdField; // Will hold the utdID of the student who needs to be excused
    private JComboBox<String>  courseIdField; // Will hold the class that student needs to be excused from

    public excuser() {
        setTitle("Excuser");
        setDefaultCloseOperation(EXIT_ON_CLOSE); //End program if window is closed
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout()); // Layout system
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        String[] days = new String[31];
        for (int i = 1; i <= 31; i++) { //Creates the days
            days[i - 1] = String.valueOf(i);
        }
        dayDropdown = new JComboBox<>(days); //Drop down for days
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(dayDropdown, constraints);

        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"}; // The months
        monthDropdown = new JComboBox<>(months);
        constraints.gridx = 1;
        panel.add(monthDropdown, constraints);

        String[] years = new String[981]; // The years
        for (int i = 2020; i <= 3000; i++) {
            years[i - 2020] = String.valueOf(i);
        }
        yearDropdown = new JComboBox<>(years); // Drop down for year
        constraints.gridx = 2;
        panel.add(yearDropdown, constraints);

        JLabel utdIdLabel = new JLabel("UTD ID:"); //The label for UTD ID
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.EAST;
        panel.add(utdIdLabel, constraints);

        utdIdField = new JTextField(10); // The input field for UTD ID
        constraints.gridx = 1;
        constraints.anchor = GridBagConstraints.WEST;
        panel.add(utdIdField, constraints);

        JLabel courseIdLabel = new JLabel("Course ID:"); // Label for Course ID
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.EAST;
        panel.add(courseIdLabel, constraints);


        course course = new course(); // create a new object that lets us get all classes
        Object[] classObjects=course.getAllClasses().toArray(); // get all these classes as objects

        String[] classes = new String[classObjects.length];
        for (int i = 0; i < classObjects.length; i++) {
            classes[i] = ((Records.course) classObjects[i]).name(); // get all of those objects class names
        }


        courseIdField = new JComboBox<>(classes); //The input for course ID (WILL BE CHANGE FOR DROP DOWN)
        constraints.gridx = 1;
        constraints.anchor = GridBagConstraints.WEST;
        panel.add(courseIdField, constraints);

        JButton submitButton = new JButton("Excuse Absence"); // Button will act like the submit button for the entire page
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { //For now when submitted it will show what data was entered will connect to API in future
                String selectedDay = (String) dayDropdown.getSelectedItem();
                String selectedMonth = (String) monthDropdown.getSelectedItem();
                String selectedYear = (String) yearDropdown.getSelectedItem();
                String selectedDate = selectedYear + "-" + (monthDropdown.getSelectedIndex() + 1) + "-" + selectedDay;
                String utdId = utdIdField.getText();
                String courseId = (String) courseIdField.getSelectedItem();
                JOptionPane.showMessageDialog(excuser.this, "Selected date: " + selectedDate + "\nUTD ID: " + utdId + "\nCourse ID: " + courseId, "Excuse Details", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(submitButton, constraints);

        JButton goBackButton = new JButton("Go Back"); // Button will take u back to main screen
        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Console(); //Open console window again
                dispose(); //Close current excuse window
            }
        });
        constraints.gridx = 1;
        constraints.gridy = 4;
        panel.add(goBackButton, constraints);

        add(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
