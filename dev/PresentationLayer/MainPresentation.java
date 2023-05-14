package PresentationLayer;

import BusinessLayer.HR.DriverController;
import DataLayer.HR_T_DAL.DB_init.Data_init;
import DataLayer.HR_T_DAL.DB_init.Data_init_HR;
import DataLayer.HR_T_DAL.DalService.DalEmployeeService;
import ServiceLayer.HR.EmployeeService;
import ServiceLayer.HR.ShiftService;
import ServiceLayer.Transport.BranchService;
import ServiceLayer.Transport.DeliveryService;
import ServiceLayer.Transport.LogisticCenterService;
import ServiceLayer.Transport.SupplierService;
import ServiceLayer.UserService;
import UtilSuper.Response;
import UtilSuper.ResponseSerializer;
import UtilSuper.ServiceFactory;

import java.sql.SQLException;
import java.util.Scanner;

public class MainPresentation {

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


    public MainPresentation() {
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
        employeePresentation = new EmployeePresentation(employeeService,shiftService);
        driverPresentation = new DriverPresentation(employeeService);
        serviceFactory.callbackEnterWeight(this.transportManagerPresentation::enterWeightFunction);
        serviceFactory.callbackEnterOverWeight(this.transportManagerPresentation::enterOverWeightAction);
    }


    public void start() throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("------ START -------");
        System.out.println("Please choose an option:");
        System.out.println("1. start new program");
        System.out.println("2. load old data");
        System.out.println("3. continue from last save");
        System.out.println("4.exit program");
        int choice = scanner.nextInt();
        scanner.nextLine();
        if (choice == 1){
            Data_init.initBasicData(this.serviceFactory.getDAO());

        }
        if (choice == 2) {
            Data_init.initOldData(this.serviceFactory.getDAO(),supplierService,deliveryService);
            Data_init_HR.initOldData(this.serviceFactory.getDAO(), employeeService, shiftService, serviceFactory.getEmployeeController(), serviceFactory.getShiftController(), serviceFactory.getDalShiftService());
        }
        if (choice == 4) {
            return;
        }
         loginWindow();
    }

    /**
     * the main window of the system
     */
    public void loginWindow() {
        while(true){
            Scanner scanner = new Scanner(System.in);
            System.out.println(" ");
            System.out.println("------ login window -------");
            Response response = ResponseSerializer.deserializeFromJson(deliveryService.getCurrDate());
            if (response.isError()) {
                System.out.println("cant show current day");
            } else {
                System.out.println("Current date: " + response.getReturnValue());
            }
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
                case "TransportManager": transportManagerPresentation.start();
                case "HRManager" : hrManagerPresentation.start();
                case "driver" :  driverPresentation.start();
                break;
            }
        }
    }

}