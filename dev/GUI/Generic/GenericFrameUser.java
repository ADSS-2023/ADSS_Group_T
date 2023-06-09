package GUI.Generic;

import GUI.LoginFrame;
import UtilSuper.ServiceFactory;

import javax.swing.*;
import java.awt.*;

public class GenericFrameUser extends GenericFrame {
    protected JPanel leftPanel;
    protected JPanel rightPanel;

    public GenericFrameUser(ServiceFactory serviceFactory) {
        super(serviceFactory);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(26, 73, 100));
        bottomPanel.setPreferredSize(new Dimension(1200, 50));

        GenericButton logoutButton = new GenericButton("Logout");
        bottomPanel.add(logoutButton, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);

        this.mainPanel.setLayout(new GridBagLayout()); // Use GridBagLayout instead of GridLayout
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.insets = new Insets(5, 5, 5, 5); // Add some padding

        // Create left panel (1/4 of the width)

        leftPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        leftPanel.setBackground(Color.WHITE);
        constraints.weightx = 0.25;
        constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 0;
        //dont able to resize the left panel
        constraints.gridheight = 2;
        mainPanel.add(leftPanel, constraints);

        // Create right panel (3/4 of the width)
        rightPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        rightPanel.setBackground(Color.LIGHT_GRAY);
        constraints.weightx = 0.75;
        constraints.gridx = 1;
        mainPanel.add(rightPanel, constraints);



        logoutButton.addActionListener(e -> {
            System.out.println("Button logout clicked");
            LoginFrame loginFrame = new LoginFrame(serviceFactory);
            dispose();
        });
    }
}
