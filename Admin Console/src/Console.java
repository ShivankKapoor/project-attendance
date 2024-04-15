import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Console extends JFrame {

    public Console() {
        setTitle("Console");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        JButton getCSVButton = new JButton("Get CSVs");
        getCSVButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create CSVGetter object
                CSVGetter x = new CSVGetter();
                // Close the console window
                dispose();
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(getCSVButton, constraints);

        JButton excuseAbsenceButton = new JButton("Excuse Absence");
        excuseAbsenceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              excuser x = new excuser();
              dispose();
            }
        });
        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(excuseAbsenceButton, constraints);

        JButton seeWarningsButton = new JButton("See Warnings");
        seeWarningsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                warnings x = new warnings();
                dispose();
            }
        });
        constraints.gridx = 2;
        constraints.gridy = 0;
        panel.add(seeWarningsButton, constraints);

        add(panel);
        pack(); // Adjusting frame size to fit components
        setLocationRelativeTo(null); // Centering the frame on the screen
        setVisible(true);
    }
}