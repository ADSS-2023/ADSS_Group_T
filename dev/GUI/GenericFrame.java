package GUI;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class GenericFrame extends JFrame {
    private JLabel errorLabel;
    private JLabel dateTimeLabel;
    protected GridBagConstraints gbc;
    protected GridBagLayout gbl;
    protected GridLayout gridLayout;
    protected JPanel mainPanel;

    public GenericFrame() {

        // Create the error label
        errorLabel = new JLabel();
        //set the font size
        errorLabel.setFont(new Font("Arial", Font.BOLD, 16));
        //set the color of the error label text to white
        errorLabel.setForeground(Color.white);

        // Create the date time label
        dateTimeLabel = new JLabel();
        //set the font size
        dateTimeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        //set the color of the date time label text to white
        dateTimeLabel.setForeground(Color.white);

        // Create the top panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(26, 73, 100));
        //toppanelsize
        topPanel.setPreferredSize(new Dimension(1200, 50));

        mainPanel = new JPanel(new BorderLayout());
        // Create the main panel layout
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(new Color(193, 226, 228));

        // Add the date time label at the right of the top bar
        topPanel.add(dateTimeLabel, BorderLayout.EAST);
        topPanel.add(errorLabel, BorderLayout.WEST);

        add(topPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);

        updateDateTime();
        setErrorText("");


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setVisible(true);
    }



    public void setErrorText(String errorText) {
        //the first word of the error message is always "Error: " in white color
        //the rest of the error message is in red color
        errorLabel.setText("<html><font color='white'>Error: </font><font color='red'>" + errorText + "</font></html>");

    }

    private void updateDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateTime = LocalDateTime.now().format(formatter);
        dateTimeLabel.setText("Date/Time: " + dateTime);
    }
}
