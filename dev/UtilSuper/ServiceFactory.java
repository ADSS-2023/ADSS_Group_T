package UtilSuper;

import BusinessLayer.HR.DriverController;
import BusinessLayer.HR.EmployeeController;
import BusinessLayer.HR.ShiftController;
import BusinessLayer.Transport.*;
import BusinessLayer.HR.User.UserController;
import DataLayer.HR_T_DAL.DAOs.BranchDAO;
import DataLayer.HR_T_DAL.DAOs.DeliveryDAO;
import DataLayer.HR_T_DAL.DAOs.LogisticCenterDAO;
import DataLayer.HR_T_DAL.DAOs.SupplierDAO;
import ServiceLayer.HR.EmployeeService;
import ServiceLayer.HR.ShiftService;
import ServiceLayer.Transport.*;
import ServiceLayer.UserService;

public class ServiceFactory {
    private ShiftController shiftController;
    private ShiftService shiftService;
    private EmployeeController employeeController;
    private EmployeeService employeeService;
    private LogisticCenterController logisticCenterController;
    private LogisticCenterService logisticCenterService;
    private LogisticCenterDAO logisticDAO;
    private DeliveryController deliveryController;
    private DeliveryService deliveryService;
    private DeliveryDAO deliveryDAO;
    private UserService userService;
    private UserController userController;
    private BranchService branchService;
    private BranchController branchController;
    private BranchDAO branchDAO;
    private SupplierService supplierService;
    private SupplierController supplierController;
    private SupplierDAO supplierDAO;


    private DriverController driverController;

    public ServiceFactory() {
        shiftController = new ShiftController();
        shiftService = new ShiftService(shiftController);

        employeeController = new EmployeeController();
        employeeService = new EmployeeService(employeeController);

        logisticDAO = new LogisticCenterDAO();
        logisticCenterController = new LogisticCenterController(logisticDAO);
        logisticCenterService = new LogisticCenterService(logisticCenterController);

        //TODO //userController = new UserController();
        userService = new UserService(userController);

        branchDAO = new BranchDAO();
        branchController = new BranchController(branchDAO);
        branchService = new BranchService(branchController);

        supplierDAO = new SupplierDAO();
        supplierController = new SupplierController(supplierDAO);
        supplierService = new SupplierService(supplierController);

        deliveryController = new DeliveryController(logisticCenterController,supplierController,branchController,driverController,shiftController,deliveryDAO);
        deliveryService = new DeliveryService(deliveryController);



    }
    public void callbackEnterWeight(EnterWeightInterface enterWeightInterface){
        deliveryService.setEnterWeightInterface(enterWeightInterface);
    }

    public ShiftController getShiftController() {
        return shiftController;
    }

    public ShiftService getShiftService() {
        return shiftService;
    }

    public EmployeeController getEmployeeController() {
        return employeeController;
    }

    public EmployeeService getEmployeeService() {
        return employeeService;
    }

    public LogisticCenterController getLogisticCenterController() {
        return logisticCenterController;
    }

    public LogisticCenterService getLogisticCenterService() {
        return logisticCenterService;
    }

    public DeliveryController getDeliveryController() {
        return deliveryController;
    }

    public DeliveryService getDeliveryService() {
        return deliveryService;
    }

    public UserService getUserService() {
        return userService;
    }

    public UserController getUserController() {
        return userController;
    }

    public BranchService getBranchService() {return branchService;}

    public SupplierService getSupplierService() {return supplierService;}

}
