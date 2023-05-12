package DataLayer.HR_T_DAL.DalService;

import BusinessLayer.HR.Driver;
import BusinessLayer.HR.User.User;
import BusinessLayer.HR.User.UserType;
import DataLayer.HR_T_DAL.DAOs.DriverDAO;
import DataLayer.HR_T_DAL.DTOs.CounterDTO;
import DataLayer.HR_T_DAL.DTOs.DriverDTO;
import DataLayer.HR_T_DAL.DTOs.DriverRequirementDTO;
import DataLayer.HR_T_DAL.DTOs.UserDTO;
import DataLayer.Util.DAO;
import UtilSuper.Pair;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import java.util.*;


public class DalDriverService {
    private Connection connection;
    private DalUserService dalUserService;

    private DAO dao;

    private DriverDAO driverDAO;

    public DalDriverService(Connection connection, DalUserService dalUserService) {
        this.connection = connection;
        this.driverDAO = new DriverDAO(connection);
        this.dalUserService = dalUserService;
        this.dao = new DAO(connection);
    }

    public Driver findDriverById(int driverId) throws SQLException {
        DriverDTO driverdto = driverDAO.find(driverId, "driverId", "Driver", DriverDTO.class);
        if (driverdto != null) {
            Driver driver = new Driver(driverdto, dalUserService.findUserById(driverId));
            return driver;
        }
        return null;
    }


    public LinkedHashMap<Driver, Boolean> findSubmissionByIdAndDate(int driverId, LocalDate date) throws SQLException { // the boolean is if assigned or not
        Pair result = driverDAO.getDriverAndIfIsAssigned(driverId, date.toString());
        LinkedHashMap<Driver, Boolean> ret = new LinkedHashMap<>();
        if (result.getFirst() != null && result.getSecond() != null) {
            DriverDTO dd = (DriverDTO) result.getFirst();
            Driver driver = new Driver(dd, dalUserService.findUserById(driverId));
            ret.put(driver, true);
            return ret;
        } else return null;
    }


    // TODO - israel
    public LinkedList<Driver> findAllSubmissionByDate(LocalDate date) throws SQLException {
        LinkedList<DriverDTO> listDriverDTO = driverDAO.findAllSubmissionByDate(date.toString());
        LinkedList<Driver> listDriver = new LinkedList<Driver>();
        for (DriverDTO d : listDriverDTO) {
            if (d != null) {
                User u = dalUserService.findUserById(d.getDriverId());
                if (u != null) {
                    Driver driver = new Driver(d, u);
                    listDriver.add(driver);
                }
            }
        }
        return listDriver;
    }







    public LinkedHashMap<Pair<Driver.LicenseType, Driver.CoolingLevel>, Integer> findAllRequirementsByDate(LocalDate date) throws SQLException {
        LinkedHashMap<Pair<Driver.LicenseType, Driver.CoolingLevel>, Integer> requirements = new LinkedHashMap<>();
        ArrayList<DriverRequirementDTO> driverRequirements = driverDAO.findAll("DriverRequirements", date.toString(), DriverRequirementDTO.class );

        for (DriverRequirementDTO requirement : driverRequirements) {
            Driver.LicenseType licenseType = Driver.LicenseType.valueOf(requirement.getLicenseType());
            Driver.CoolingLevel coolingLevel = Driver.CoolingLevel.valueOf(requirement.getCoolingLevel());
            int amount = requirement.getAmount();

            Pair<Driver.LicenseType, Driver.CoolingLevel> key = new Pair<>(licenseType, coolingLevel);

            requirements.put(key, amount);
        }

        return requirements;
    }




    // TODO - israel
    public LinkedHashMap<LocalDate, LinkedHashMap<Driver, Boolean>> findAllDriverSubmissionsBetweenDates(LocalDate startDate, LocalDate finishDate, int driverId) throws SQLException { // the boolean is if assigned or not
        return null;
    }
    public boolean deleterequieremnt(LocalDate date, String licenseType, String coolingLevel) throws SQLException {
        LinkedHashMap<String, Object> pk = new LinkedHashMap<>();
        pk.put("date", date);
        pk.put("licenseType", licenseType);
        pk.put("coolingLevel", coolingLevel);

        DriverRequirementDTO requirement = dao.find(pk, "requirement", DriverRequirementDTO.class);
        if (requirement != null) {
            dao.delete(requirement);
            return true;
        } else {
            return false;
        }
    }


    public boolean updateAmountOfRequirement(LocalDate date, String licenseType, String coolingLevel, int oldNumber, int updateNumber) throws SQLException {
        DriverRequirementDTO oldDTO = new DriverRequirementDTO(date.toString(), licenseType, coolingLevel, oldNumber);
        DriverRequirementDTO newDTO = new DriverRequirementDTO(date.toString(), licenseType, coolingLevel, updateNumber);
        dao.update(oldDTO, newDTO);
        return true;
    }

    // TODO - israel
    public LinkedHashMap<Driver, Boolean> assignDriver(int driverId, LocalDate date) throws SQLException { // the boolean is if assigned or not
        return null;
    }

    // TODO - israel
    public void addSubmissionForDriver(int driverId, LocalDate date) throws SQLException { // the boolean is if assigned or not
    }

    public void addSRequirement(LocalDate date, String licenseType, String coolingLevel, int amount) throws SQLException {
        DriverRequirementDTO dto = new DriverRequirementDTO(date.toString(), licenseType, coolingLevel, amount);
        dao.insert(dto);
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
