package DataLayer.HR_T_DAL.DalService;

import BusinessLayer.HR.Driver;
import BusinessLayer.HR.User.User;
import BusinessLayer.HR.User.UserType;
import DataLayer.HR_T_DAL.DAOs.DriverDAO;
import DataLayer.HR_T_DAL.DTOs.DriverDTO;
import DataLayer.HR_T_DAL.DTOs.UserDTO;

import java.sql.Connection;
import java.sql.SQLException;

public class DalDriverService {
    private Connection connection;
    private DalUserService dalUserService;

    private DriverDAO driverDAO;
    public DalDriverService(Connection connection) {
        this.connection = connection;
        this.driverDAO = new DriverDAO(connection);
    }

    public void addDriver (Driver driver) throws SQLException {
        DriverDTO driverDTO = new DriverDTO(driver.getId(), driver.getLicenseLevel().toString(),driver.getCoolingLevel().toString());
        User user = new User(driver.getId(),driver.getEmployeeName(), driver.getBankAccount(), driver.getDescription(),driver.getSalary()
                ,driver.getJoiningDay(),driver.getPassword(), UserType.driver);
        UserDTO userDTO = new UserDTO(user);
        dalUserService.addUserFromDriver(userDTO);
        driverDAO.insert(driverDTO);
    }

}
