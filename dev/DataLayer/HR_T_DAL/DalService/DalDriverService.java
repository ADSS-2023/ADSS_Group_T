package DataLayer.HR_T_DAL.DalService;

import BusinessLayer.HR.Driver;
import BusinessLayer.HR.Employee;
import BusinessLayer.HR.User.User;
import BusinessLayer.HR.User.UserType;
import DataLayer.HR_T_DAL.DAOs.DriverDAO;
import DataLayer.HR_T_DAL.DTOs.DriverDTO;
import DataLayer.HR_T_DAL.DTOs.UserDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class DalDriverService {
    private Connection connection;
    private DalUserService dalUserService;

    private DriverDAO driverDAO;
    public DalDriverService(Connection connection) {
        this.connection = connection;
        this.driverDAO = new DriverDAO(connection);
    }

    // TODO - israel
    public Driver findDriverById(int driverId) throws SQLException {
        return null;
    }

    // TODO - israel
    public HashMap<Driver, Boolean> findSubmissionByIdAndDate(int driverId, LocalDate date) throws SQLException { // the boolean is if assigned or not
        return null;
    }

    public HashMap<Driver, Boolean> assignDriver(int driverId, LocalDate date) throws SQLException { // the boolean is if assigned or not
        return null;
    }

    public void addDriver (Driver driver) throws SQLException {
        DriverDTO driverDTO = new DriverDTO(driver.getId(), driver.getLicenseLevel().toString(),driver.getCoolingLevel().toString());
        User user = new User(driver.getId(),driver.getEmployeeName(), driver.getBankAccount(), driver.getDescription(),driver.getSalary()
                ,driver.getJoiningDay(),driver.getPassword(), UserType.driver);
        UserDTO userDTO = new UserDTO(user);
        dalUserService.addUserFromDriver(userDTO);
        driverDAO.insert(driverDTO);
    }

    public List<DriverDTO> getDriversByLicenseType(Driver.LicenseType licenseType) throws SQLException {
        return driverDAO.getDriversByLicenseType(licenseType);
    }

}
