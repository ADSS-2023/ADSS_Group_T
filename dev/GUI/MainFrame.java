package GUI;

import DataLayer.HR_T_DAL.DB_init.Data_init;
import DataLayer.HR_T_DAL.DB_init.Data_init_HR;
import PresentationLayer.DriverPresentation;
import PresentationLayer.EmployeePresentation;
import PresentationLayer.HRManagerPresentation;
import PresentationLayer.TransportManagerPresentation;
import ServiceLayer.HR.EmployeeService;
import ServiceLayer.HR.ShiftService;
import ServiceLayer.Transport.BranchService;
import ServiceLayer.Transport.DeliveryService;
import ServiceLayer.Transport.LogisticCenterService;
import ServiceLayer.Transport.SupplierService;
import ServiceLayer.UserService;
import UtilSuper.ServiceFactory;

import java.awt.*;

public class MainFrame extends GenericFrame {
    private GenericButton startNewButton;
    private GenericButton loadOldDataButton;
    private GenericButton lastSaveButton;
    private GenericButton exitButton;

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

    public MainFrame() {
        // Customize properties if needed
        super();
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
                Data_init_HR.initBasicData(this.serviceFactory.getDAO(),this.shiftService);
                this.setVisible(false);
                LoginFrame loginFrame = new LoginFrame(this.deliveryService, this.userService);


            }
            catch (Exception exception){
                setErrorText(exception.getMessage());
            }
        });

        try {
            this.serviceFactory = new ServiceFactory();
        }
        catch (Exception exception){
            System.out.println(exception.toString());
        }
        this.shiftService = serviceFactory.getShiftService();
        this.employeeService = serviceFactory.getEmployeeService();
        this.logisticCenterService = serviceFactory.getLogisticCenterService();
        this.deliveryService = serviceFactory.getDeliveryService();
        this.userService = serviceFactory.getUserService();
        this.branchService = serviceFactory.getBranchService();
        this.supplierService = serviceFactory.getSupplierService();
        transportManagerPresentation = new TransportManagerPresentation(logisticCenterService,deliveryService,supplierService,branchService);
        hrManagerPresentation = new HRManagerPresentation(shiftService,employeeService,branchService);
        employeePresentation = new EmployeePresentation(employeeService,branchService);
        driverPresentation = new DriverPresentation(employeeService);
        serviceFactory.callbackEnterWeight(this.transportManagerPresentation::enterWeightFunction);
        serviceFactory.callbackEnterOverWeight(this.transportManagerPresentation::enterOverWeightAction);
        setVisible(true);
    }
}
