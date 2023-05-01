package PresentationLayer;

import Initialization.Truck_init;
import ServiceLayer.HR.EmployeeService;
import Initialization.HR_Initialization;
import ServiceLayer.HR.ShiftService;
import ServiceLayer.Transport.*;
import ServiceLayer.UserService;
import UtilSuper.ServiceFactory;

import java.util.Scanner;

public class MainPresentation {



    ServiceFactory serviceFactory;

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
    private TruckService truckService;

    public MainPresentation() {
        ServiceFactory serviceFactory = new ServiceFactory();
        this.shiftService = serviceFactory.getShiftService();
        this.employeeService = serviceFactory.getEmployeeService();
        this.logisticCenterService = serviceFactory.getLogisticCenterService();
        this.deliveryService = serviceFactory.getDeliveryService();
        this.userService = serviceFactory.getUserService();
        this.branchService = serviceFactory.getBranchService();
        this.supplierService = serviceFactory.getSupplierService();
        this.truckService = serviceFactory.getTruckService();

        transportManagerPresentation = new TransportManagerPresentation(logisticCenterService,deliveryService,supplierService,branchService,truckService);


    }


    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("------ START -------");
        System.out.println("Please choose an option:");
        System.out.println("1. start new program");
        System.out.println("2. load old data");
        int choice = scanner.nextInt();
        scanner.nextLine();
        if (choice == 1)
            loginWindow();
        if (choice == 2) {
            HR_Initialization.init_data(shiftService,employeeService);
            Truck_init.init(truckService);
            loginWindow();
        }
    }

    /**
     * the main window of the system
     */
    public void loginWindow() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(" ");
        System.out.println("------ login window -------");
        System.out.println("Current date: " + deliveryService.getCurrDate());
        while (true) {
            String result = null;
            boolean loginSuccess = false;
            while (!loginSuccess) {
                System.out.print("Enter your ID: ");
                int id = scanner.nextInt();
                System.out.print("Enter your password: ");
                String password = scanner.next();
                result = userService.login(id, password);
                if (result.equals("error")) {
                    System.out.println("Incorrect ID or password. Please try again.");
                } else {
                    loginSuccess = true;
                }
            }

            switch (result) {
                case "employee": employeePresentation.start();
                case "delivery manager": transportManagerPresentation.start();
                default:
            }
        }
    }
}