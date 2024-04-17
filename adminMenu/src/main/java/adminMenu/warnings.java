//Written by Shivank Kapoor for Senior Design
//NetID: sxk190175
/*
 * This file will show the admin what students have violated class rules.
 *  -If they have signed if from wrong IP
 *  -If they have missed class more than 3 times in a row
 */

package adminMenu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class warnings extends JFrame {
    private JComboBox<String> classDropdown; // Will hold drop down for the classes
    private JTable table; // Table that will show what students have warnings on em

    public warnings() {
        setTitle("Warnings");
        setDefaultCloseOperation(EXIT_ON_CLOSE); // End program when close
        setResizable(false);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] classes = { "Class 1", "Class 2", "Class 3" }; // This is just a placeholder for the classes
        classDropdown = new JComboBox<>(classes);
        panel.add(classDropdown, BorderLayout.NORTH);

        String[] columnNames = { "Student ID", "Warning" };// Header for table
        Object[][] data = { { "Test Student 1", "Warning message here" } }; // Placeholder
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        table = new JTable(model); // Table will hold the placeholder
        JScrollPane scrollPane = new JScrollPane(table); // Can scroll on table
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton goBackButton = new JButton("Go Back"); // Button to go back to home screen
        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Console(); // Reopen main windows
                dispose(); // Close the warnings window
            }
        });
        panel.add(goBackButton, BorderLayout.SOUTH);

        add(panel); // Add panel to main jfram
        pack();
        setLocationRelativeTo(null); // Spawn Frame in center of primary monitor
        setVisible(true); // Can be seen
    }
}
