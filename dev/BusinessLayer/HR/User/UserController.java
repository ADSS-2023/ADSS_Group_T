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

    private User Tr;
    private DriverController driverController;
    private TransportManager transportManager;
    private DalUserService dalUserService;


    //TODO-init the user controller
    public UserController(EmployeeController employeeController,User Tr ,DriverController driverController, User Hr,DalUserService dalUserService) {
        this.Hr = Hr;
        this.Tr = Tr;
        this.employeeController = employeeController;
        this.driverController = driverController;
        this.dalUserService = dalUserService;
    }

    public UserType login(int id, String password) throws Exception {
        if (Hr.getId() == id) {
            if (Hr.getPassword().equals(password))
                return Hr.userType;
            else
                throw new IllegalArgumentException("error");
        }
        if (Tr.getId() == id) {
            if (Tr.getPassword().equals(password))
                return Tr.userType;
            else
                throw new IllegalArgumentException("error");
        } else {
            User user = dalUserService.findUserById(id);
            if (user == null)
                throw new NoSuchFieldException("error");
            if (!user.password.toString().equals(password))
                throw new IllegalArgumentException("error");
            return user.userType;
        }
    }
}










