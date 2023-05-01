package UtilSuper;

import BusinessLayer.HR.EmployeeController;
import BusinessLayer.HR.ShiftController;
import BusinessLayer.Transport.DeliveryController;
import BusinessLayer.Transport.LogisticCenterController;
import BusinessLayer.UserController;
import ServiceLayer.HR.EmployeeService;
import ServiceLayer.HR.ShiftService;
import ServiceLayer.Transport.DeliveryService;
import ServiceLayer.Transport.LogisticCenterService;
import ServiceLayer.UserService;

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

    public ServiceFactory() {
        shiftController = new ShiftController();
        shiftService = new ShiftService(shiftController);
        employeeController = new EmployeeController();
        employeeService = new EmployeeService(employeeController);
        logisticCenterController = new LogisticCenterController();
        logisticCenterService = new LogisticCenterService(logisticCenterController);
        deliveryController = new DeliveryController();
        deliveryService = new DeliveryService(deliveryController);
        this.userService = new UserService();
        this.userController = new UserController();

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
}
