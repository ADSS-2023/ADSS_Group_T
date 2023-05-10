package UtilSuper;

import BusinessLayer.HR.DriverController;
import BusinessLayer.HR.EmployeeController;
import BusinessLayer.HR.ShiftController;
import BusinessLayer.HR.User.User;
import BusinessLayer.HR.User.UserType;
import BusinessLayer.Transport.*;
import BusinessLayer.HR.User.UserController;
import DataLayer.HR_T_DAL.DAOs.TruckDAO;
import DataLayer.HR_T_DAL.DB_init.Data_init;
import DataLayer.HR_T_DAL.DB_init.Data_init_HR;
import DataLayer.HR_T_DAL.DalService.DalDeliveryService;
import DataLayer.HR_T_DAL.DalService.DalLogisticCenterService;
import DataLayer.HR_T_DAL.DalService.DalUserService;
import DataLayer.Util.DAO;
import ServiceLayer.HR.EmployeeService;
import ServiceLayer.HR.ShiftService;
import ServiceLayer.Transport.*;
import ServiceLayer.UserService;
import org.junit.Before;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ServiceFactory {
    private ShiftController shiftController;
    private ShiftService shiftService;
    private EmployeeController employeeController;
    private EmployeeService employeeService;
    private LogisticCenterController logisticCenterController;
    private LogisticCenterService logisticCenterService;
    private DeliveryController deliveryController;
    private DeliveryService deliveryService;
    private UserService userService;
    private UserController userController;
    private BranchService branchService;
    private BranchController branchController;
    private SupplierService supplierService;
    private SupplierController supplierController;
    private Connection connection;
    private DalLogisticCenterService dalLogisticCenterService;
    private DalDeliveryService dalDeliveryService;
    private DAO dao;



    private DriverController driverController;

    public ServiceFactory() throws Exception {

        String testDBUrl = "jdbc:sqlite:dev/DataLayer/HR_Transport_DB.db";
        connection = DriverManager.getConnection(testDBUrl);

        this.dao = new DAO(connection);
        Data_init.initBasicData(dao);
        Data_init_HR.initBasicData(dao);




        dalLogisticCenterService = new DalLogisticCenterService(connection);
        dalDeliveryService = new DalDeliveryService(connection,dalLogisticCenterService);


        shiftController = new ShiftController();
        shiftService = new ShiftService(shiftController);
        employeeController = new EmployeeController();
        driverController = new DriverController();
        employeeService = new EmployeeService(employeeController,driverController);


        logisticCenterController = new LogisticCenterController(dalLogisticCenterService);
        logisticCenterService = new LogisticCenterService(logisticCenterController);
        dalDeliveryService = new DalDeliveryService(connection,dalLogisticCenterService);

        //TODO //
        User HRuser = new User(1,"HRManeger","123456","cool",1000,null,"1", UserType.HRManager);
        User TRuser = new User(2,"TrManeger","123456","cool",1000,null,"2", UserType.TransportManager);
        userController = new UserController(employeeController,TRuser,driverController,HRuser);
        userService = new UserService(userController);
        branchController = new BranchController(dalDeliveryService);
        branchService = new BranchService(branchController);
        supplierController = new SupplierController(dalDeliveryService);
        supplierService = new SupplierService(supplierController);
        deliveryController = new DeliveryController(logisticCenterController,supplierController,branchController,driverController,shiftController,dalDeliveryService);
        deliveryService = new DeliveryService(deliveryController);

    }

    public DAO getDAO() {
        return this.dao;
    }

    public void callbackEnterWeight(EnterWeightInterface enterWeightInterface){
        deliveryService.setEnterWeightInterface(enterWeightInterface);
    }
    public void callbackEnterOverWeight(EnterOverWeightInterface enterOverWeightInterface){
        deliveryService.setEnterOverWeightInterface(enterOverWeightInterface);
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
