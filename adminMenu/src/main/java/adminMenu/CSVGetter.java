//Written by Shivank Kapoor for Senior Design
//NetID: SXK190175

/*
 * This file is the runner for the entire program. Is starts the console and other database stuff.
 */

package adminMenu;

import adminMenu.dbConnection.course;
import record.Records;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CSVGetter extends JFrame {
    private JComboBox<String> dayDropdown;
    private JComboBox<String> monthDropdown;
    private JComboBox<String> yearDropdown;
    private JComboBox<String> classDropdown;

    public CSVGetter() {
        setTitle("CSV Getter");
        setDefaultCloseOperation(EXIT_ON_CLOSE);// Will end program if close button is clicked
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout()); // Layout system
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        // Day Dropdown
        String[] days = new String[31];
        for (int i = 1; i <= 31; i++) {
            days[i - 1] = String.valueOf(i);
        }
        dayDropdown = new JComboBox<>(days);
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(dayDropdown, constraints);

        // Month Dropdown
        String[] months = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
                "October", "November", "December" };
        monthDropdown = new JComboBox<>(months);
        constraints.gridx = 1;
        panel.add(monthDropdown, constraints);

        // Year Dropdown
        String[] years = new String[981]; // 2020 to 3000
        for (int i = 2020; i <= 3000; i++) {
            years[i - 2020] = String.valueOf(i);
        }
        yearDropdown = new JComboBox<>(years);
        constraints.gridx = 2;
        panel.add(yearDropdown, constraints);

        // Class Dropdown
        course course = new course(); // create a new object that lets us get all classes
        Object[] classObjects=course.getAllClasses().toArray(); // get all these classes as objects

        String[] classes = new String[classObjects.length];
        for (int i = 0; i < classObjects.length; i++) {
            classes[i] = ((Records.course) classObjects[i]).name(); // get all of those objects class names
        }
        classDropdown = new JComboBox<>(classes);
        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(classDropdown, constraints);

        JButton submitButton = new JButton("Get Attendance"); // Will return the CSV when clicked
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedDay = (String) dayDropdown.getSelectedItem();
                String selectedMonth = (String) monthDropdown.getSelectedItem();
                String selectedYear = (String) yearDropdown.getSelectedItem();
                String selectedClass = (String) classDropdown.getSelectedItem();
                String selectedDate = selectedYear + "-" + (monthDropdown.getSelectedIndex() + 1) + "-" + selectedDay;
                JOptionPane.showMessageDialog(CSVGetter.this,
                        "Selected date: " + selectedDate + "\nSelected class: " + selectedClass, "Selections",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(submitButton, constraints);

        JButton goBackButton = new JButton("Go Back"); // Will go back to main console whe clicked
        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the Console window
                new Console();
                // Close the CSVGetter window
                dispose();
            }
        });
        constraints.gridx = 1;
        constraints.gridy = 3;
        panel.add(goBackButton, constraints);

        // Adding labels
        JLabel dateLabel = new JLabel("Date:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.EAST;
        panel.add(dateLabel, constraints);

        JLabel classLabel = new JLabel("Class:");
        constraints.gridy = 1;
        panel.add(classLabel, constraints);

        add(panel);
        pack(); // Adjusting frame size to fit components
        setSize(400, 250); // Setting a larger size
        setLocationRelativeTo(null); // Centering the frame on the screen
        setVisible(true);
    }
}