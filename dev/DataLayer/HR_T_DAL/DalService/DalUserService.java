package DataLayer.HR_T_DAL.DalService;

import BusinessLayer.HR.Employee;
import BusinessLayer.HR.User.User;
import DataLayer.HR_T_DAL.DAOs.EmployeeDAO;
import DataLayer.HR_T_DAL.DAOs.ShiftDAO;
import DataLayer.HR_T_DAL.DAOs.UserDAO;
import DataLayer.HR_T_DAL.DTOs.EmployeeDTO;
import DataLayer.HR_T_DAL.DTOs.UserDTO;

import java.sql.Connection;
import java.sql.SQLException;

public class DalUserService {

    private Connection connection ;
    private UserDAO userDAO;
    public DalUserService(Connection connection) {
        this.connection = connection;
        this.userDAO = new UserDAO(connection);
    }

    public void addUserFromDriver (UserDTO userDTO) throws SQLException {
        userDAO.insert(userDTO);
    }
    public void addNewEmployee(Employee employee) throws SQLException {
        UserDTO userDTO = new UserDTO(employee);
        userDAO.insert(userDTO);
    }
}
