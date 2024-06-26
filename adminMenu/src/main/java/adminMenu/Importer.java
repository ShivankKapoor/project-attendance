package adminMenu;

import adminMenu.fileImport.fileImport;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Importer extends JFrame {
    private JTextField courseNameField;
    private JLabel selectedFileLabel;
    private File selectedFile;

    public Importer() {
        setTitle("Course Importer");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a panel to hold the components
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;


        // File chooser button
        JButton selectFileButton = new JButton("Select File");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.0; // Prevents expansion
        gbc.anchor = GridBagConstraints.CENTER; // Centering
        panel.add(selectFileButton, gbc);

        selectFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser(); // Instantiate new JFileChooser
                FileNameExtensionFilter csvFilter = new FileNameExtensionFilter("Txt Files", "txt");
                fileChooser.setFileFilter(csvFilter);
                int result = fileChooser.showOpenDialog(null); // Using 'null' to open in the current frame context
                if (result == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile(); // Store the selected file
                    selectedFileLabel.setText("Selected: " + selectedFile.getPath()); // Update the label
                } else {
                    selectedFileLabel.setText("No file selected"); // Reset if no file is selected
                }
            }
        });

        selectedFileLabel = new JLabel("No file selected");
        gbc.gridx = 1;
        gbc.gridy = 1; // On the same row as 'selectFileButton'
        gbc.weightx = 1.0; // Allowing expansion for label
        panel.add(selectedFileLabel, gbc);

        // Submit button
        JButton submitButton = new JButton("Submit");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // Centered over two columns
        gbc.weightx = 0.0; // No expansion
        gbc.anchor = GridBagConstraints.CENTER; // Centered
        panel.add(submitButton, gbc);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { // will run on sumbit
                if (selectedFile == null) {
                    JOptionPane.showMessageDialog(null, "Please select a TXT file.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                fileImport fi = new fileImport();
                fi.importData(selectedFile.getPath()); // sends path to backend file handler
                JOptionPane.showMessageDialog(null, "Class Created", "Success", JOptionPane.CLOSED_OPTION);
            }
        });

        // Go back button
        JButton goBackButton = new JButton("Go Back");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2; // Centered over two columns
        gbc.anchor = GridBagConstraints.CENTER; // Centered
        panel.add(goBackButton, gbc);

        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Assuming there's a Console class to go back to
                new Console();
                dispose();
            }
        });

        add(panel);
        setVisible(true);
    }
}
