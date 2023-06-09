package PresentationLayer;

import DataLayer.HR_T_DAL.DB_init.Data_init;
import DataLayer.HR_T_DAL.DB_init.Data_init_HR;
import ServiceLayer.HR.EmployeeService;
import ServiceLayer.HR.ShiftService;
import ServiceLayer.Transport.BranchService;
import ServiceLayer.Transport.DeliveryService;
import ServiceLayer.Transport.LogisticCenterService;
import ServiceLayer.Transport.SupplierService;
import ServiceLayer.UserService;
import UtilSuper.ServiceFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class MainPresentationUI extends JFrame {
    private ServiceFactory serviceFactory;
    private ShiftService shiftService;
    private EmployeeService employeeService;
    private LogisticCenterService logisticCenterService;
    private DeliveryService deliveryService;
    private UserService userService;
    private BranchService branchService;
    private SupplierService supplierService;
    private TransportManagerPresentation transportManagerPresentation;
    private HRManagerPresentation hrManagerPresentation;
    private EmployeePresentation employeePresentation;
    private DriverPresentation driverPresentation;
    private JButton startButton;
    private JButton loadDataButton;
    private JButton continueButton;
    private JButton exitButton;


    public MainPresentationUI() {
        initializeComponents();
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(startButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(loadDataButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(continueButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(exitButton, gbc);

        setTitle("Main Presentation");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Set the frame to full screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(240, 240, 240));
        pack(); // Pack the components tightly
        setVisible(true);

        try {
            this.serviceFactory = new ServiceFactory();
        } catch (Exception exception) {
            System.out.println(exception.toString());
        }
        this.shiftService = serviceFactory.getShiftService();
        this.employeeService = serviceFactory.getEmployeeService();
        this.logisticCenterService = serviceFactory.getLogisticCenterService();
        this.deliveryService = serviceFactory.getDeliveryService();
        this.userService = serviceFactory.getUserService();
        this.branchService = serviceFactory.getBranchService();
        this.supplierService = serviceFactory.getSupplierService();
        transportManagerPresentation = new TransportManagerPresentation(logisticCenterService, deliveryService, supplierService, branchService);
        hrManagerPresentation = new HRManagerPresentation(shiftService, employeeService, branchService);
        employeePresentation = new EmployeePresentation(employeeService, branchService);
        driverPresentation = new DriverPresentation(employeeService);
        serviceFactory.callbackEnterWeight(this.transportManagerPresentation::enterWeightFunction);
        serviceFactory.callbackEnterOverWeight(this.transportManagerPresentation::enterOverWeightAction);
    }

    private void initializeComponents() {
        Font buttonFont = new Font("Arial", Font.PLAIN, 24);
        Dimension buttonSize = new Dimension(300, 60);

        startButton = new JButton("Start New Program");
        startButton.setFont(buttonFont);
        startButton.setPreferredSize(buttonSize);

        loadDataButton = new JButton("Load Old Data");
        loadDataButton.setFont(buttonFont);
        loadDataButton.setPreferredSize(buttonSize);

        continueButton = new JButton("Continue from Last Save");
        continueButton.setFont(buttonFont);
        continueButton.setPreferredSize(buttonSize);

        exitButton = new JButton("Exit Program");
        exitButton.setFont(buttonFont);
        exitButton.setPreferredSize(buttonSize);

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startNewProgram();
            }
        });

        loadDataButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadOldData();
            }
        });

        continueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                continueFromLastSave();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private void startNewProgram() {
        try {
            // Perform actions for starting a new program
            Data_init.initBasicData(this.serviceFactory.getDAO());
            Data_init_HR.initBasicData(this.serviceFactory.getDAO(), this.shiftService);
            loginWindow();
        } catch (Exception exception) {
            System.out.println(exception.toString());
        }
        loginWindow();
    }

    private void loadOldData() {
        try {
            // Perform actions for loading old data
            Data_init.initOldData(this.serviceFactory.getDAO(), supplierService, deliveryService);
            Data_init_HR.initOldData(this.serviceFactory.getDAO(), employeeService, shiftService, serviceFactory.getEmployeeController(), serviceFactory.getShiftController(), serviceFactory.getDalShiftService());
            loginWindow();
        } catch (Exception exception) {
            System.out.println(exception.toString());
        }

    }

    private void continueFromLastSave() {
        // Perform actions for continuing from last save
        loginWindow();
    }

    private void loginWindow() {
        // create login
        // show login and close the current window immediately
        this.setVisible(false);
        LoginPresentationUI loginPresentationUI = new LoginPresentationUI(deliveryService, userService, employeePresentation, transportManagerPresentation, hrManagerPresentation, driverPresentation);
        loginPresentationUI.showLoginWindow();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainPresentationUI();
            }
        });
    }
}
