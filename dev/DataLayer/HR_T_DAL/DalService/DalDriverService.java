package DataLayer.HR_T_DAL.DalService;

import BusinessLayer.HR.Driver;
import BusinessLayer.HR.Employee;
import BusinessLayer.HR.User.User;
import BusinessLayer.HR.User.UserType;
import DataLayer.HR_T_DAL.DAOs.DriverDAO;
import DataLayer.HR_T_DAL.DTOs.DriverDTO;
import DataLayer.HR_T_DAL.DTOs.UserDTO;
import UtilSuper.Pair;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class DalDriverService {
    private Connection connection;
    private DalUserService dalUserService;

    private DriverDAO driverDAO;
    public DalDriverService(Connection connection) {
        this.connection = connection;
        this.driverDAO = new DriverDAO(connection);
    }

    public Driver findDriverById(int driverId) throws SQLException {
        DriverDTO driverdto = driverDAO.find(driverId,"driverId","Driver",DriverDTO.class);
        Driver driver = new Driver(driverdto ,dalUserService.findUserById(driverId) );
        return driver;
    }


    public LinkedHashMap<Driver, Boolean> findSubmissionByIdAndDate(int driverId, LocalDate date) throws SQLException { // the boolean is if assigned or not
         Pair result = driverDAO.getDriverAndIfIsAssigned(driverId,date.toString());
         LinkedHashMap<Driver, Boolean> ret = new LinkedHashMap<>();
         if(result.getFirst() != null && result.getSecond() != null ) {
             DriverDTO dd = (DriverDTO) result.getFirst();
             Driver driver = new Driver(dd, dalUserService.findUserById(driverId));
             ret.put(driver,true);
             return ret;
         }
         else return null;
    }

//TODO - israel

    // TODO - israel
    public LinkedHashMap<Driver, Boolean>  findAllSubmissionByDate(LocalDate date) throws SQLException {
        return null;
    }

    // TODO - israel
    public LinkedHashMap<Pair<Driver.LicenseType, Driver.CoolingLevel> , Integer>  findAllRequirementsByDate(LocalDate date) throws SQLException {
        return null;
    }

    // TODO - israel
    public ArrayList<Pair<Driver.LicenseType, Driver.CoolingLevel>> findRequirementsByDate(LocalDate date) throws SQLException {
        return null;
    }


    // TODO - israel
    public LinkedHashMap<LocalDate, LinkedHashMap<Driver, Boolean>> findAllDriverSubmissionsBetweenDates(LocalDate startDate, LocalDate finishDate, int driverId) throws SQLException { // the boolean is if assigned or not
        return null;
    }

    // TODO - israel
    public boolean deleterequieremnt(LocalDate date, String licenseType,  String coolingLevel ) throws SQLException { // delete the num's requierment - the index that it show in the order of findRequirementsByDate
        return false;
    }

    // TODO - israel
    public boolean updateAmountOfRequierement(LocalDate date, String licenseType,  String coolingLevel, int updateNumber ) throws SQLException { // delete the num's requierment - the index that it show in the order of findRequirementsByDate
        return false;
    }
    // TODO - israel
    public LinkedHashMap<Driver, Boolean> assignDriver(int driverId, LocalDate date) throws SQLException { // the boolean is if assigned or not
        return null;
    }

    // TODO - israel
    public void addSubmissionForDriver(int driverId, LocalDate date) throws SQLException { // the boolean is if assigned or not

    }

    public void addSRequirement( LocalDate date, String licenseType, String coolingLevel, int amount ) throws SQLException { // the boolean is if assigned or not

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
