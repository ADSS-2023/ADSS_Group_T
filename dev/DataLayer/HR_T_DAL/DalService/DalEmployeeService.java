package DataLayer.HR_T_DAL.DalService;

import BusinessLayer.HR.Employee;
import DataLayer.HR_T_DAL.DAOs.EmployeeDAO;
import DataLayer.HR_T_DAL.DAOs.TruckDAO;
import DataLayer.HR_T_DAL.DTOs.EmployeeDTO;

import java.sql.Connection;

public class DalEmployeeService {

    private EmployeeDAO employeeDAO;
    private Connection connection;

    private DalUserService dalUserService;

    public DalEmployeeService(Connection connection) {
        this.connection = connection;
        this.employeeDAO = new EmployeeDAO(connection);
    }


}
