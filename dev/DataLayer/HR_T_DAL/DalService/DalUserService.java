package DataLayer.HR_T_DAL.DalService;

import DataLayer.HR_T_DAL.DAOs.ShiftDAO;
import DataLayer.HR_T_DAL.DAOs.UserDAO;
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
}
