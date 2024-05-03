package adminMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Console extends JFrame {

    public Console() {
        setTitle("UTD Attendance"); // Added title "UTD Attendance"
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Will end program when exited
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout()); // Layout system
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 20, 20, 20); // Padding

        // Load logo image
        ImageIcon logoIcon = null;
        try {
            BufferedImage img = ImageIO.read(new File("src/main/Logo.png"));
            // Resize the image
            int logoWidth = 150;
            int logoHeight = 150;
            Image scaledImg = img.getScaledInstance(logoWidth, logoHeight, Image.SCALE_SMOOTH);
            logoIcon = new ImageIcon(scaledImg);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Add logo image
        JLabel logoLabel = new JLabel(logoIcon);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 4;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(logoLabel, constraints);

        JLabel titleLabel = new JLabel("UTD Attendance"); // Title label
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Setting font size and style
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 4;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(titleLabel, constraints);

        JButton getCSVButton = new JButton("Get Class List"); // Button to get lists of attendance
        getCSVButton.setPreferredSize(new Dimension(180, 60));
        getCSVButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create CSVGetter object
                AttendanceGetter x = new AttendanceGetter();
                // Close the console window
                dispose();
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(getCSVButton, constraints);

        JButton excuseAbsenceButton = new JButton("Excuse Absence"); // Button to excuse a student
        excuseAbsenceButton.setPreferredSize(new Dimension(180, 60)); // Larger button size
        excuseAbsenceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excuser x = new excuser(); // Open the excuser window
                dispose(); // Close the main window
            }
        });
        constraints.gridx = 2;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        panel.add(excuseAbsenceButton, constraints);

        JButton seeWarningsButton = new JButton("See Warnings"); // Button to see if there were warning for students for
        // a class
        seeWarningsButton.setPreferredSize(new Dimension(180, 60)); // Larger button size
        seeWarningsButton.addActionListener(new ActionListener() { // Listener for warnings button
            @Override
            public void actionPerformed(ActionEvent e) {
                warnings x = new warnings(); // Open warnings window
                dispose(); // Close current window
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        panel.add(seeWarningsButton, constraints);

        JButton startAttendanceButton = new JButton("Start Attendance"); // Window to start taking attendance
        startAttendanceButton.setPreferredSize(new Dimension(180, 60)); // Larger button size
        startAttendanceButton.addActionListener(new ActionListener() { // Listener to start taking attendance
            @Override
            public void actionPerformed(ActionEvent e) {
                attendanceStarter x = new attendanceStarter(); // Open the attendance starter window
                dispose(); // Close the console window
            }
        });
        constraints.gridx = 2;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        panel.add(startAttendanceButton, constraints);

        JButton createCourseButton = new JButton("Create Course"); // Button to create a new course
        createCourseButton.setPreferredSize(new Dimension(180, 60)); // Large button size
        createCourseButton.addActionListener(new ActionListener() { // Listener to open the course creation window
            @Override
            public void actionPerformed(ActionEvent e) {
                new Importer(); // Open a new CreateCourse window
                dispose(); // Close the console window
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 4;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(createCourseButton, constraints);

        add(panel);
        pack(); // Adjusting frame size to fit components
        setLocationRelativeTo(null); // Centering the frame on the screen
        setVisible(true);
    }
}
