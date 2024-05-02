package adminMenu;

import adminMenu.dbConnection.course;
import record.Records;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

// This file will let admins excuse a student for missing a class
public class excuser extends JFrame {
    private JComboBox<String> dayDropdown; // Will hold the day field for the date of excusable absence
    private JComboBox<String> monthDropdown; // Will hold the month field for the date of excusable absence
    private JComboBox<String> yearDropdown; // Will hold the year field for the date of excusable absence
    private JComboBox<String> courseIdField; // Will hold the class that student needs to be excused from
    private JComboBox<String> utdIdDropdown; // Will hold the UTD ID dropdown

    public excuser() {
        setTitle("Excuser");
        setDefaultCloseOperation(EXIT_ON_CLOSE); // End program if window is closed
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout()); // Layout system
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        String[] days = new String[31]; // List of days
        for (int i = 1; i <= 31; i++) {
            days[i - 1] = String.valueOf(i);
        }
        dayDropdown = new JComboBox<>(days); // Drop down for days
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(dayDropdown, constraints);

        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"}; // The months
        monthDropdown = new JComboBox<>(months);
        constraints.gridx = 1;
        panel.add(monthDropdown, constraints);

        String[] years = new String[981]; // List of years
        for (int i = 2020; i <= 3000; i++) {
            years[i - 2020] = String.valueOf(i);
        }
        yearDropdown = new JComboBox<>(years); // Drop down for year
        constraints.gridx = 2;
        panel.add(yearDropdown, constraints);

        JLabel courseIdLabel = new JLabel("Course ID:"); // Label for Course ID
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.EAST;
        panel.add(courseIdLabel, constraints);

        course course = new course(); // Object to get all classes
        Object[] classObjects = course.getAllClasses().toArray(); // Get all these classes as objects

        String[] classes = new String[classObjects.length]; // Create array for class names
        for (int i = 0; i < classObjects.length; i++) {
            classes[i] = ((Records.course) classObjects[i]).name(); // Get all class names
        }

        courseIdField = new JComboBox<>(classes); // The input for course ID
        constraints.gridx = 1;
        constraints.anchor = GridBagConstraints.WEST;
        panel.add(courseIdField, constraints);

        JLabel utdIdLabel = new JLabel("UTD ID:"); // Label for UTD ID
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.EAST;
        panel.add(utdIdLabel, constraints);

        utdIdDropdown = new JComboBox<>(); // Initialize empty dropdown
        constraints.gridx = 1;
        constraints.anchor = GridBagConstraints.WEST;
        panel.add(utdIdDropdown, constraints);


        ActionListener classChanger=(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCourseId = "";
                String selectedCourseName = (String) courseIdField.getSelectedItem();

                for (Object classObject : classObjects) {
                    Records.course courseObject = (Records.course) classObject;
                    if (courseObject.name().equals(selectedCourseName)) {
                        selectedCourseId = courseObject.course();
                        break;
                    }
                }

                ArrayList<String> newUtdIds = course.getStudentsForCourseId(selectedCourseId);
                utdIdDropdown.setModel(new DefaultComboBoxModel<>(newUtdIds.toArray(new String[0])));
            }
        });
        courseIdField.addActionListener(classChanger);
        classChanger.actionPerformed(new ActionEvent(courseIdField, ActionEvent.ACTION_PERFORMED, ""));
        JButton submitButton = new JButton("Excuse Absence"); // Button to submit the excusal
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedDay = (String) dayDropdown.getSelectedItem();
                String selectedMonth = (String) monthDropdown.getSelectedItem();
                String selectedYear = (String) yearDropdown.getSelectedItem();
                String selectedDate = selectedYear + "-" + (monthDropdown.getSelectedIndex() + 1) + "-" + selectedDay; // Format the date
                String utdId = (String) utdIdDropdown.getSelectedItem();
                String courseId = (String) courseIdField.getSelectedItem();

                JOptionPane.showMessageDialog(
                        excuser.this,
                        "Selected date: " + selectedDate + "\nUTD ID: " + utdId + "\nCourse ID: " + courseId,
                        "Excuse Details",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(submitButton, constraints);

        JButton goBackButton = new JButton("Go Back"); // Button to go back to the main screen
        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Console(); // Open the console window again
                dispose(); // Close the current excuse window
            }
        });
        constraints.gridx = 1;
        constraints.gridy = 4;
        panel.add(goBackButton, constraints);

        add(panel);
        pack();
        setLocationRelativeTo(null); // Center the window on the screen
        setVisible(true); // Show the window
    }
}