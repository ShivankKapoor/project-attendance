package adminMenu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class warnings extends JFrame {
    private JComboBox<String> classDropdown;
    private JTable table;

    public warnings() {
        setTitle("Warnings");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Class Dropdown
        String[] classes = {"Class 1", "Class 2", "Class 3"};
        classDropdown = new JComboBox<>(classes);
        panel.add(classDropdown, BorderLayout.NORTH);

        // Table
        String[] columnNames = {"Student ID", "Warning"};
        Object[][] data = {{"Test Student 1", "Warning message here"}};
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Go Back Button
        JButton goBackButton = new JButton("Go Back");
        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the Console window
                new Console();
                // Close the warnings window
                dispose();
            }
        });
        panel.add(goBackButton, BorderLayout.SOUTH);

        add(panel);
        pack(); // Adjusting frame size to fit components
        setLocationRelativeTo(null); // Centering the frame on the screen
        setVisible(true);
    }
}
