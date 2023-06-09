package PresentationLayer;

import PresentationLayer.DriverPresentation;
import PresentationLayer.EmployeePresentation;
import PresentationLayer.HRManagerPresentation;
import PresentationLayer.TransportManagerPresentation;
import ServiceLayer.Transport.DeliveryService;
import ServiceLayer.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPresentationUI {
    private DeliveryService deliveryService;
    private UserService userService;
    private EmployeePresentation employeePresentation;
    private TransportManagerPresentation transportManagerPresentation;
    private HRManagerPresentation hrManagerPresentation;
    private DriverPresentation driverPresentation;

    public LoginPresentationUI(DeliveryService deliveryService, UserService userService,
                               EmployeePresentation employeePresentation,
                               TransportManagerPresentation transportManagerPresentation,
                               HRManagerPresentation hrManagerPresentation,
                               DriverPresentation driverPresentation) {
        this.deliveryService = deliveryService;
        this.userService = userService;
        this.employeePresentation = employeePresentation;
        this.transportManagerPresentation = transportManagerPresentation;
        this.hrManagerPresentation = hrManagerPresentation;
        this.driverPresentation = driverPresentation;
    }

    public void showLoginWindow() {
        JFrame frame = new JFrame("Login Window");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Full screen
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.getContentPane().setBackground(new Color(240, 240, 240));
        frame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(1000, 1000, 1000, 1000);

        JLabel idLabel = new JLabel("ID:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(idLabel, gbc);

        JTextField idField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        frame.add(idField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        frame.add(passwordField, gbc);

        JButton loginButton = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(loginButton, gbc);

        Font buttonFont = new Font("Arial", Font.PLAIN, 18);
        Dimension buttonSize = new Dimension(150, 30);
        loginButton.setFont(buttonFont);
        loginButton.setPreferredSize(buttonSize);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(idField.getText());
                String password = new String(passwordField.getPassword());

                // Perform login authentication
                String result = userService.login(id, password);

                // Perform appropriate actions based on the result
                switch (result) {
                    case "employee":
                        employeePresentation.start(id);
                        break;
                    case "TransportManager":
                        // transportManagerPresentation.start();
                        break;
                    case "HRManager":
                        hrManagerPresentation.start();
                        break;
                    case "driver":
                        driverPresentation.start();
                        break;
                    default:
                        JOptionPane.showMessageDialog(frame, "Incorrect ID or password. Please try again.");
                        break;
                }
            }
        });

        frame.pack();
        frame.setVisible(true);
    }
}
