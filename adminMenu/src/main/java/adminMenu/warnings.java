package adminMenu;

import adminMenu.dbConnection.course;
import record.Records;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class warnings extends JFrame {
    private JComboBox<String> classDropdown; // Will hold drop down for the classes
    private JTable table; // Table that will show what students have warnings
    private DefaultTableModel model; // Model for the table

    public warnings() {
        setTitle("Warnings");
        setDefaultCloseOperation(EXIT_ON_CLOSE); // End program when close
        setResizable(false);

        // Initialize the main panel and set its layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Get class objects and names
        course course = new course();
        Object[] classObjects = course.getAllClasses().toArray();

        String[] classes = new String[classObjects.length];
        for (int i = 0; i < classObjects.length; i++) {
            classes[i] = ((Records.course) classObjects[i]).name();
        }

        // Create a panel for the label and dropdown and center it
        JPanel centerPanel = new JPanel(); // No layout specified, so it's centered by default
        centerPanel.add(new JLabel("Course:")); // Label for the dropdown
        classDropdown = new JComboBox<>(classes); // Dropdown with class names
        centerPanel.add(classDropdown); // Add dropdown to the center panel

        // Add the centered panel to the top of the main panel
        mainPanel.add(centerPanel, BorderLayout.NORTH);

        // Initialize the table with a default message
        String[] columnNames = { "UTD ID", "Warning" }; // Table column headers
        model = new DefaultTableModel(columnNames, 0); // No rows initially
        table = new JTable(model); // Create table with the model
        JScrollPane scrollPane = new JScrollPane(table); // Scrollable table
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Listener for class dropdown changes
        ActionListener classChanger = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = (String) classDropdown.getSelectedItem();
                model.setRowCount(0); // Clear existing rows

                for (Object classObject : classObjects) {
                    if (Objects.equals(((Records.course) classObject).name(), selected)) {
                        String courseID = ((Records.course) classObject).course();

                        // Get the list of students with 3 consecutive absences
                        Object[] badIds = course.getStudentsWhoMissed3ClassesConsequtivly(courseID).toArray();

                        // Populate the table with new data
                        for (Object badId : badIds) {
                            Object[] rowData = { badId, "Missed 3 classes in a row" };
                            model.addRow(rowData); // Add row to the model
                        }
                    }
                }
            }
        };

        classDropdown.addActionListener(classChanger); // Attach listener to the dropdown
        classChanger.actionPerformed(new ActionEvent(classDropdown, ActionEvent.ACTION_PERFORMED, ""));

        // Button to go back to the main console
        JButton goBackButton = new JButton("Go Back");
        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Console();
                dispose();
            }
        });

        mainPanel.add(goBackButton, BorderLayout.SOUTH); // Add button to the main panel

        // Add the main panel to the frame and set frame properties
        add(mainPanel);
        pack(); // Adjust frame to fit content
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true); // Display the frame
    }
}
