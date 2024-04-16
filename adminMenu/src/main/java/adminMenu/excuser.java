package adminMenu;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class excuser extends JFrame {
    private JComboBox<String> dayDropdown;
    private JComboBox<String> monthDropdown;
    private JComboBox<String> yearDropdown;
    private JTextField utdIdField;
    private JTextField courseIdField;

    public excuser() {
        setTitle("Excuser");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        String[] days = new String[31];
        for (int i = 1; i <= 31; i++) {
            days[i - 1] = String.valueOf(i);
        }
        dayDropdown = new JComboBox<>(days);
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(dayDropdown, constraints);

        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        monthDropdown = new JComboBox<>(months);
        constraints.gridx = 1;
        panel.add(monthDropdown, constraints);

        String[] years = new String[981];
        for (int i = 2020; i <= 3000; i++) {
            years[i - 2020] = String.valueOf(i);
        }
        yearDropdown = new JComboBox<>(years);
        constraints.gridx = 2;
        panel.add(yearDropdown, constraints);

        JLabel utdIdLabel = new JLabel("UTD ID:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.EAST;
        panel.add(utdIdLabel, constraints);

        utdIdField = new JTextField(10);
        constraints.gridx = 1;
        constraints.anchor = GridBagConstraints.WEST;
        panel.add(utdIdField, constraints);

        JLabel courseIdLabel = new JLabel("Course ID:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.EAST;
        panel.add(courseIdLabel, constraints);

        courseIdField = new JTextField(10);
        constraints.gridx = 1;
        constraints.anchor = GridBagConstraints.WEST;
        panel.add(courseIdField, constraints);

        JButton submitButton = new JButton("Excuse Absence");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedDay = (String) dayDropdown.getSelectedItem();
                String selectedMonth = (String) monthDropdown.getSelectedItem();
                String selectedYear = (String) yearDropdown.getSelectedItem();
                String selectedDate = selectedYear + "-" + (monthDropdown.getSelectedIndex() + 1) + "-" + selectedDay;
                String utdId = utdIdField.getText();
                String courseId = courseIdField.getText();
                JOptionPane.showMessageDialog(excuser.this, "Selected date: " + selectedDate + "\nUTD ID: " + utdId + "\nCourse ID: " + courseId, "Excuse Details", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(submitButton, constraints);

        JButton goBackButton = new JButton("Go Back");
        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Console();
                dispose();
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
