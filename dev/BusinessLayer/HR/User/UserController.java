package BusinessLayer.HR.User;

import BusinessLayer.HR.DriverController;
import BusinessLayer.HR.Employee;
import BusinessLayer.HR.EmployeeController;
import BusinessLayer.HR.Driver;
import DataLayer.HR_T_DAL.DalService.DalUserService;

import java.util.HashMap;

public class UserController {
    private EmployeeController employeeController;
    //driverController
    //private HRManager hrManager;

    private User Hr;
    private DriverController driverController;
    private TransportManager transportManager;
    private DalUserService dalUserService;


    public UserController(EmployeeController employeeController, TransportManager transportManager, DriverController driverController, int HRid) {

    }

    //TODO-init the user controller
    public void initUserController(EmployeeController employeeController, TransportManager transportManager, DriverController driverController, User Hr, DalUserService dalUserService) {
        this.Hr = Hr;
        this.employeeController = employeeController;
        this.driverController = driverController;
        this.transportManager = transportManager;
        this.dalUserService = dalUserService;
    }

    public UserType login(int id, String password) throws Exception {
        String userType;
        if (Hr.getId() == id) {
            if (Hr.getPassword().equals(password))
                return Hr.userType;
            else
                throw new IllegalArgumentException("wrong password");
        }

        if (transportManager.getId() == id) {
            if (transportManager.getPassword().equals(password))
                return transportManager.userType;
            else
                throw new IllegalArgumentException("wrong password");
        } else {
            User user = dalUserService.findUserById(id);
            if (user == null)
                throw new NoSuchFieldException("User Don't exist.");
            if (user.password != password)
                throw new IllegalArgumentException("Wrong password");
            return user.userType;
        }
    }
}









}
