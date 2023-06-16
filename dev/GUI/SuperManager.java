package GUI;

import BusinessLayer.HR.Employee;
import DataLayer.HR_T_DAL.DB_init.Data_init;
import DataLayer.HR_T_DAL.DB_init.Data_init_HR;
import GUI.Generic.GenericButton;
import GUI.Generic.GenericFrame;
import UtilSuper.ServiceFactory;

import java.awt.*;

public class SuperManager extends GenericFrame {
    private GenericButton registerAsHRM;
    private GenericButton registerAsTransportM;
    private GenericButton registerAsEmployee;
    private GenericButton registerAsDriver;
    public SuperManager(ServiceFactory serviceFactory) {
        super(serviceFactory);
        setTitle("Super-Manager");
        // Create the buttons
        registerAsHRM = new GenericButton("register As HR Manager");
        registerAsTransportM = new GenericButton("register As Transport Manager");
        registerAsEmployee = new GenericButton("register As Employee ");
        registerAsDriver = new GenericButton("register As Driver ");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(registerAsHRM, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(registerAsTransportM, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(registerAsEmployee, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(registerAsDriver, gbc);

        registerAsHRM.addActionListener(e -> {
            // Perform actions when the button is clicked
            System.out.println("Button start clicked");
            try {
                HRManagerFrame hrManagerFrame = new HRManagerFrame(serviceFactory);
                dispose();
            }
            catch (Exception exception){
                setErrorText(exception.getMessage());
            }
        });
        registerAsTransportM.addActionListener(e -> {
            // Perform actions when the button is clicked
            System.out.println("Button start clicked");
            try {
                TransportManagerFrame transportManagerFrame = new TransportManagerFrame(serviceFactory);
                dispose();
            }
            catch (Exception exception){
                setErrorText(exception.getMessage());
            }
        });
        registerAsEmployee.addActionListener(e -> {
            // Perform actions when the button is clicked
            System.out.println("Button start clicked");
            try {
                EmployeeMenueFrame employeeMenueFrame = new EmployeeMenueFrame(serviceFactory,1111);
                dispose();
            }
            catch (Exception exception){
                setErrorText(exception.getMessage());
            }
        });
        registerAsDriver.addActionListener(e -> {
            // Perform actions when the button is clicked
            System.out.println("Button start clicked");
            try {
                DriverMenuframe driverMenuframe = new DriverMenuframe(serviceFactory,1111);
            }
            catch (Exception exception){
                setErrorText(exception.getMessage());
            }
        });
    }
}
