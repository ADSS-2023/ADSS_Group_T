package GUI;

import DataLayer.HR_T_DAL.DB_init.Data_init;
import DataLayer.HR_T_DAL.DB_init.Data_init_HR;
import GUI.Generic.GenericButton;
import GUI.Generic.GenericFrame;
import UtilSuper.ServiceFactory;

import java.awt.*;

public class MainFrame extends GenericFrame {
    private GenericButton startNewButton;
    private GenericButton loadOldDataButton;
    private GenericButton lastSaveButton;
    private GenericButton exitButton;



    public MainFrame(ServiceFactory serviceFactory) {
        // Customize properties if needed
        super( serviceFactory);
        setTitle("Main");

        // Create the buttons
        startNewButton = new GenericButton("Start New");
        loadOldDataButton = new GenericButton("Load Old Data");
        lastSaveButton = new GenericButton("Continue from Last Save");
        exitButton = new GenericButton("Exit Program");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(startNewButton, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(loadOldDataButton, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(lastSaveButton, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(exitButton, gbc);

        // Add event listeners
        startNewButton.addActionListener(e -> {
            // Perform actions when the button is clicked
            System.out.println("Button start clicked");
            try {
                Data_init.initBasicData(this.serviceFactory.getDAO());
                Data_init_HR.initBasicData(this.serviceFactory.getDAO(),serviceFactory.getShiftService());
                LoginFrame loginFrame = new LoginFrame(serviceFactory);
                dispose();
            }
            catch (Exception exception){
                setErrorText(exception.getMessage());
            }
        });

        loadOldDataButton.addActionListener(e -> {
            // Perform actions when the button is clicked
            System.out.println("Button load old data clicked");
            try {
                Data_init.initOldData(this.serviceFactory.getDAO(),serviceFactory.getSupplierService(),serviceFactory.getDeliveryService());
                Data_init_HR.initOldData(this.serviceFactory.getDAO(), serviceFactory.getEmployeeService(), serviceFactory.getShiftService(), serviceFactory.getEmployeeController(), serviceFactory.getShiftController(), serviceFactory.getDalShiftService());
                LoginFrame loginFrame = new LoginFrame(serviceFactory);
                dispose();
            }
            catch (Exception exception){
                setErrorText(exception.getMessage());
            }
        });

        lastSaveButton.addActionListener(e -> {
            // Perform actions when the button is clicked
            System.out.println("Button continue from last save clicked");
            try {
                LoginFrame loginFrame = new LoginFrame(serviceFactory);
                dispose();
            }
            catch (Exception exception){
                setErrorText(exception.getMessage());
            }
        });





        setVisible(true);
    }
}
