package BusinessLayer;

import BusinessLayer.HR.DriverController;
import BusinessLayer.HR.Employee;
import BusinessLayer.HR.EmployeeController;
import BusinessLayer.Transport.Driver;
import BusinessLayer.Transport.TransportManager;
import UtilSuper.PositionType;
import UtilSuper.UserType;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class UserController {
    private EmployeeController employeeController;
    //driverController
    //private HRManager hrManager;

    private User Hr;
    private DriverController driverController;
    private TransportManager transportManager;


    public UserController(EmployeeController employeeController, TransportManager transportManager, DriverController driverController, int HRid) {

    }

    //TODO-init the user controller
    public void initUserController(EmployeeController employeeController, TransportManager transportManager, DriverController driverController, User Hr) {
        this.Hr = Hr;
        this.employeeController =employeeController;
        this.driverController = driverController;
        this.transportManager = transportManager;
    }

    public User login (int id, String password) throws Exception{


        if (Hr.getId() == id){
            if (Hr.getPassword().equals(password))
                return Hr;
            else
                throw new IllegalArgumentException("wrong password");
        }

        if (transportManager.getId() == id){
            if (transportManager.getPassword().equals(password))
                return transportManager;
            else
                throw new IllegalArgumentException("wrong password");
            }

        else {
            HashMap<Integer, Employee> employeesMapper =  employeeController.getEmployeesMapper();
            for (Employee employee : employeesMapper.values()) {
                if (employee.getId() == id) {
                    if (employee.getPassword().equals(password)) {
                        return employee;
                    } else {
                        throw new IllegalArgumentException("Wrong password");
                    }
                }
            }
            HashMap<Integer, Driver> drivers =  driverController.getDrivers();
            for (Driver driver : drivers.values()) {
                if (driver.getId() == id) {
                    if (driver.getPassword().equals(password)) {
                        return driver;
                    } else {
                        throw new IllegalArgumentException("Wrong password");
                    }
                }
            }
        }
        throw new NoSuchFieldException("User Don't exist.");
    }
}
