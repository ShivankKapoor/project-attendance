package adminMenu;

import adminMenu.dbConnection.course;
import record.Records;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;

public class AttendanceGetter extends JFrame {
    private JComboBox<String> dayDropdown;
    private JComboBox<String> monthDropdown;
    private JComboBox<String> yearDropdown;
    private JComboBox<String> classDropdown;
    private Object[] classObjects;

    public AttendanceGetter() {
        setTitle("Attendance Getter");
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Will end program if close button is clicked
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout()); // Layout system
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel dateLabel = new JLabel("Date:"); // Adding labels
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.EAST;
        panel.add(dateLabel, constraints);

        String[] days = new String[31];
        for (int i = 1; i <= 31; i++) {
            days[i - 1] = String.valueOf(i); // all days for the drop-down
        }
        dayDropdown = new JComboBox<>(days); // the day drop down
        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(dayDropdown, constraints);

        String[] months = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
                "October", "November", "December" }; // All months
        monthDropdown = new JComboBox<>(months); // drop down for months
        constraints.gridx = 2;
        constraints.gridy = 0;
        panel.add(monthDropdown, constraints);

        String[] years = new String[981];
        for (int i = 2020; i <= 3000; i++) {
            years[i - 2020] = String.valueOf(i); // all the years
        }
        yearDropdown = new JComboBox<>(years); // year drop down
        constraints.gridx = 3;
        constraints.gridy = 0;
        panel.add(yearDropdown, constraints);

        JLabel classLabel = new JLabel("Class:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.EAST;
        panel.add(classLabel, constraints);

        course courseObj = new course();
        classObjects = courseObj.getAllClasses().toArray(); // get an array of all class objects registered with the system

        String[] classes = new String[classObjects.length];
        for (int i = 0; i < classObjects.length; i++) {
            classes[i] = ((Records.course) classObjects[i]).name(); //will get all the classes names that are registered with the system
        }
        classDropdown = new JComboBox<>(classes); // drop down  for the class names
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 3;
        panel.add(classDropdown, constraints);

        JButton submitButton = new JButton("Get Attendance"); // Will return the list when clicked
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { // Will get executed when sumbit button is clicked
                String selectedDay = String.format("%02d", Integer.parseInt((String) dayDropdown.getSelectedItem())); // get the selected day
                String selectedMonth = String.format("%02d", monthDropdown.getSelectedIndex() + 1); // get the selected month
                String selectedYear = (String) yearDropdown.getSelectedItem(); // get the selected year
                String selectedDate = selectedYear + "-" + selectedMonth + "-" + selectedDay; // format all dates for api

                String selectedClass = (String) classDropdown.getSelectedItem(); // get the class name what was selected in drop down
                String courseID = "";

                for (Object classObject : classObjects) {
                    if (Objects.equals(((Records.course) classObject).name(), selectedClass)) {
                        courseID = ((Records.course) classObject).course(); // get the id for the selected class
                    }
                }

                List<Records.daysPresent> attendanceData = courseObj.getStudentsAttendanceBetween2GivenDaysInclusive(
                        courseID, selectedDate, selectedDate); // get the selected classes attendance list

                String[] columnNames = { "UTD ID", "Was Present", "Name" };
                Object[][] tableData = new Object[attendanceData.size()][3];

                for (int i = 0; i < attendanceData.size(); i++) { // format data for table
                    Records.daysPresent record = attendanceData.get(i);
                    tableData[i][0] = record.utdId();
                    tableData[i][1] = record.daysPresent();
                    tableData[i][2] = record.name();
                }

                JTable table = new JTable(tableData, columnNames);
                JScrollPane scrollPane = new JScrollPane(table);

                JOptionPane.showMessageDialog(
                        AttendanceGetter.this,
                        scrollPane,
                        "Attendance Data",
                        JOptionPane.INFORMATION_MESSAGE
                ); // show table that has attendance list in a pop up
            }
        });

        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridwidth = 3;
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
        constraints.gridwidth = 3;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(goBackButton, constraints);

        add(panel);
        pack(); // Adjusting frame size to fit components
        setSize(400, 250); // Setting a larger size
        setLocationRelativeTo(null); // Centering the frame on the screen
        setVisible(true);
    }
}
