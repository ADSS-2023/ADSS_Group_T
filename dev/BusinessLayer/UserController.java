package BusinessLayer;

import BusinessLayer.HR.Employee;
import BusinessLayer.HR.HRManager;
import BusinessLayer.Transport.Driver;
import BusinessLayer.Transport.TransportManager;
import UtilSuper.PositionType;
import UtilSuper.UserType;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class UserController {
    private HashMap<Integer,Employee> employeesMapper;
    //driverController
    private HRManager hrManager;

    private TransportManager transportManager;


    public UserController(HashMap<Integer, Employee> employeesMapper, HRManager hrManager, TransportManager transportManager) {
        this.employeesMapper = employeesMapper;
        this.hrManager = hrManager;
        this.transportManager = transportManager;
    }

    public User login (int id, String password) throws Exception{
        if (hrManager.getId() == id) {
            if (hrManager.getPassword().equals(password))
                return hrManager;
            else
                throw new IllegalArgumentException("wrong password");
        }

        else if (transportManager.getId() == id){
            if (transportManager.getPassword().equals(password))
                return transportManager;
            else
                throw new IllegalArgumentException("wrong password");
            }

        else {
            for (Employee employee : employeesMapper.values()) {
                if (employee.getId() == id) {
                    if (employee.getPassword().equals(password)) {
                        return employee;
                    } else {
                        throw new IllegalArgumentException("Wrong password");
                    }
                }
            }
            // TODO - search in driver Controller


        }
    }

}
