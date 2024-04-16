package adminMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class attendanceStarter extends JFrame {

    public attendanceStarter() {
        setTitle("Attendance Starter");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        // Course ID Label
        JLabel courseIDLabel = new JLabel("Course ID:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(courseIDLabel, constraints);

        // Course ID Text Field
        JTextField courseIDField = new JTextField(15);
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(courseIDField, constraints);

        // Password Field
        JLabel passwordLabel = new JLabel("Password:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(passwordLabel, constraints);

        JPasswordField passwordField = new JPasswordField(15);
        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(passwordField, constraints);

        // Minutes Dropdown
        JLabel minutesLabel = new JLabel("Minutes:");
        constraints.gridx = 0;
        constraints.gridy = 4;
        panel.add(minutesLabel, constraints);

        Integer[] minutes = new Integer[15];
        for (int i = 0; i < 15; i++) {
            minutes[i] = (i + 1) * 5;
        }
        JComboBox<Integer> minutesDropDown = new JComboBox<>(minutes);
        constraints.gridx = 0;
        constraints.gridy = 5;
        panel.add(minutesDropDown, constraints);

        // Submit Button
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String courseID = courseIDField.getText();
                String password = new String(passwordField.getPassword());
                Integer minutes = (Integer) minutesDropDown.getSelectedItem();

                
                JOptionPane.showMessageDialog(attendanceStarter.this, "Course ID: " + courseID + "\nPassword: " + password + "\nMinutes: " + minutes, "Submission Details", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 6;
        panel.add(submitButton, constraints);

        // Go Back Button
        JButton goBackButton = new JButton("Go Back");
        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Console x = new Console();
                dispose();
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 7;
        panel.add(goBackButton, constraints);

        add(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
