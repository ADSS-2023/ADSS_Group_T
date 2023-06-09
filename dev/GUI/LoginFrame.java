package GUI;

import BusinessLayer.Transport.Delivery;
import ServiceLayer.Transport.DeliveryService;
import ServiceLayer.UserService;
import UtilSuper.ServiceFactory;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends GenericFrame {
    private DeliveryService deliveryService;
    private UserService userService;

    public LoginFrame(DeliveryService deliveryService, UserService userService) {
        super();
        setTitle("Login");
        this.deliveryService = deliveryService;
        this.userService = userService;

        GenericLabel usernameLabel = new GenericLabel("Username:");
        GenericLabel passwordLabel = new GenericLabel("Password:");
        GenericTextField usernameField = new GenericTextField();

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBackground(Color.WHITE);
        passwordField.setOpaque(true);
        passwordField.setHorizontalAlignment(SwingConstants.CENTER);
        passwordField.setFont(new Font("Arial", Font.BOLD, 16));
        passwordField.setPreferredSize(new Dimension(150, 30));


        GenericButton loginButton = new GenericButton("Login");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(usernameLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Set the gridwidth to 2 for a wider text box
        mainPanel.add(usernameField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1; // Reset the gridwidth to 1 for the label
        mainPanel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2; // Set the gridwidth to 2 for a wider text box
        mainPanel.add(passwordField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3; // Set the gridwidth to 3 for a wider button
        mainPanel.add(loginButton, gbc);

        // Add action listeners
        loginButton.addActionListener(e -> {
            System.out.println("Button start clicked");
            int username = Integer.parseInt(usernameField.getText());
            String password = passwordField.getText();
            String result = userService.login(username, password);
            if(result.equals("employee")) {
                //TODO: add employee presentation
            }
            else if(result.equals("driver")) {
                //TODO: add driver presentation
            }
            else if(result.equals("transport manager")) {
                //TODO: add transport manager presentation
            }
            else if(result.equals("hr manager")) {
                //TODO: add hr manager presentation
            }
            else
                setErrorText("invalid username or password");
            // Perform actions when the button is clicked

        });


    }
}
