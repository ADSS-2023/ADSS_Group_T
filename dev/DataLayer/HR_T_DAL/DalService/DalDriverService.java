package DataLayer.HR_T_DAL.DalService;

import BusinessLayer.HR.Driver;
import BusinessLayer.HR.User.User;
import BusinessLayer.HR.User.UserType;
import DataLayer.HR_T_DAL.DAOs.DriverDAO;
import DataLayer.HR_T_DAL.DTOs.*;
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


//    public LinkedHashMap<Driver, Boolean> findSubmissionByIdAndDate(int driverId, LocalDate date) throws SQLException { // the boolean is if assigned or not
//        Pair result = driverDAO.getDriverAndIfIsAssigned(driverId, date.toString());
//        LinkedHashMap<Driver, Boolean> ret = new LinkedHashMap<>();
//        if (result.getFirst() != null && result.getSecond() != null) {
//            DriverDTO dd = (DriverDTO) result.getFirst();
//            Driver driver = new Driver(dd, dalUserService.findUserById(driverId));
//            ret.put(driver, true);
//            return ret;
//        } else return null;
//    }

    public LinkedHashMap<Driver, Boolean> findSubmissionByIdAndDate(int driverId, LocalDate date) throws SQLException { // the boolean is if assigned or not
        LinkedHashMap<String, Object> pk = new LinkedHashMap<>();
        pk.put("shiftDate", date.toString());
        pk.put("driverId",driverId);
        DateToDriverDTO dateToDriverDTO = driverDAO.find(pk, "DateToDriver", DateToDriverDTO.class);
        if (dateToDriverDTO == null  )
            return null;
        DriverDTO driverDTO = driverDAO.find(driverId, "driverId", "Driver", DriverDTO.class);
        UserDTO userDTO = driverDAO.find(driverId, "id", "User", UserDTO.class);
        if (driverDTO == null || userDTO == null)
            return null;
        Driver driver = new Driver(driverDTO, userDTO);
        LinkedHashMap<Driver, Boolean> submissionByDriverIdAnDate = new LinkedHashMap<>();
        String isAssignedString = dateToDriverDTO.getIsAssigned();
        boolean isAssignedboolean = false;
        if (isAssignedString.equals("true"))
            isAssignedboolean = true;
        submissionByDriverIdAnDate.put(driver, isAssignedboolean);
        return submissionByDriverIdAnDate;
    }

    public LinkedHashMap<Driver, Boolean> findAllSubmissionByDate(LocalDate date) throws SQLException { // the boolean is if assigned or not
        LinkedHashMap<String, Object> conditions = new LinkedHashMap<>();
        conditions.put("shiftDate", date);
        ArrayList<DateToDriverDTO> driverSubmissionsByDateDTOs = driverDAO.findAll("DateToDriver", conditions, DateToDriverDTO.class);
        if (driverSubmissionsByDateDTOs == null)
            return null;
        LinkedHashMap<Driver, Boolean> submissionsByDriverIdDate = new LinkedHashMap<>();
        // Loop through all the submissions
        for (DateToDriverDTO submission : driverSubmissionsByDateDTOs) {
                // Add the driver to the driverMap with the value set to true if the submission is approved, otherwise set false
                boolean isAssigned = false;
                if (submission.getIsAssigned() != null && submission.getIsAssigned().equals("true"))
                    isAssigned = true;
                Driver driver = findDriverById(submission.getDriverId());
                submissionsByDriverIdDate.put(driver, isAssigned);
            }
        return submissionsByDriverIdDate;
        }




    public LinkedHashMap<Pair<Driver.LicenseType, Driver.CoolingLevel>, Integer> findAllRequirementsByDate(LocalDate date) throws SQLException {
        LinkedHashMap<Pair<Driver.LicenseType, Driver.CoolingLevel>, Integer> requirements = new LinkedHashMap<>();
        ArrayList<DriverRequirementDTO> driverRequirements = driverDAO.findAll("DriverRequirements", "date",  date.toString(), DriverRequirementDTO.class );

        for (DriverRequirementDTO requirement : driverRequirements) {
            Driver.LicenseType licenseType = Driver.LicenseType.valueOf(requirement.getLicenseType());
            Driver.CoolingLevel coolingLevel = Driver.CoolingLevel.valueOf(requirement.getCoolingLevel());
            int amount = requirement.getAmount();

            Pair<Driver.LicenseType, Driver.CoolingLevel> key = new Pair<>(licenseType, coolingLevel);

            requirements.put(key, amount);
        }

        return requirements;
    }




    public LinkedHashMap<LocalDate, LinkedHashMap<Driver, Boolean>> findAllDriverSubmissionsBetweenDates(LocalDate startDate, LocalDate endDate, int driverId) throws SQLException {
        LinkedHashMap<LocalDate, LinkedHashMap<Driver, Boolean>> result = new LinkedHashMap<>();
        LinkedHashMap<String, Object> conditions = new LinkedHashMap<>();
        conditions.put("driverId", driverId);
        conditions.put("shiftDate", startDate.toString() + " TO " + endDate.toString());
        ArrayList<DateToDriverDTO> driverSubmissionsBetweenDays = driverDAO.findAll("DateToDriver", conditions, DateToDriverDTO.class);
        Driver driver = findDriverById(driverId);
        if ( driver != null ){
            // Loop through all the dates between startDate and finishDate
            for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
                // Create a new LinkedHashMap to store the drivers for the current date
                LinkedHashMap<Driver, Boolean> driverMap = new LinkedHashMap<>();

                // Loop through all the submissions for the current date
                for (DateToDriverDTO submission : driverSubmissionsBetweenDays) {
                    // Check if the submission's date is the same as the current date
                    if (submission.getShiftDate().equals(date)) {
                        // Find the driver for the submission
                        // Add the driver to the driverMap with the value set to true if the submission is approved
                        boolean isAssigned = false;
                        if (submission.getIsAssigned().equals("true"))
                            isAssigned = true;
                        driverMap.put(driver, isAssigned);
                    }
                }
                // Add the driverMap to the result LinkedHashMap with the key set to the current date
                result.put(date, driverMap);
            }
        }
        return result;
    }


    public boolean deleterequieremnt(LocalDate date, String licenseType, String coolingLevel) throws SQLException {
        LinkedHashMap<String, Object> pk = new LinkedHashMap<>();
        pk.put("date", date);
        pk.put("licenseType", licenseType);
        pk.put("coolingLevel", coolingLevel);

        DriverRequirementDTO requirement = dao.find(pk, "DriverRequirements", DriverRequirementDTO.class);
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

    public LinkedHashMap<Driver, Boolean> assignDriver(int driverId, LocalDate date) throws SQLException {
        // Create a new DateToDriverDTO with the given driverId and date, and isAssigned set to true
        DateToDriverDTO newDto = new DateToDriverDTO(date.toString(), driverId, "true");

        // Get the old DateToDriverDTO from the database with the same driverId and date
        LinkedHashMap<String, Object> pk = new LinkedHashMap<>();
        pk.put("driverId", driverId);
        pk.put("shiftDate", date.toString());
        DateToDriverDTO oldDto = driverDAO.find(pk, "DateToDriver", DateToDriverDTO.class);

        // Update the row in the database with the new DTO
        driverDAO.update(oldDto, newDto);

        // Return a LinkedHashMap with the assigned Driver and a Boolean value of true
        LinkedHashMap<Driver, Boolean> result = new LinkedHashMap<>();
        DriverDTO driverDTO = driverDAO.find(driverId, "driverId", "Driver", DriverDTO.class);
        UserDTO userDTO = driverDAO.find(driverDTO.getDriverId(), "id", "User", UserDTO.class);

        UserType userType = UserType.valueOf(userDTO.getUserType());

        Driver driver = new Driver(driverDTO.getDriverId(), userDTO.getUserName(), userDTO.getBankAccount(), userDTO.getDescription(), userDTO.getSalary(), LocalDate.parse(userDTO.getJoiningDay()), userDTO.getPassword(), userType, Driver.LicenseType.valueOf(driverDTO.getLicenseType()), Driver.CoolingLevel.valueOf(driverDTO.getCoolingLevel()));
        result.put(driver, true);
        return result;
    }



    public void addSubmissionForDriver(int driverId, LocalDate date) throws SQLException {
        // Create a new DateToDriverDTO with the given driverId and date, and isAssigned set to false
        DateToDriverDTO newDto = new DateToDriverDTO(date.toString(), driverId, "false");

        // Insert the new DTO into the database
        driverDAO.insert(newDto);
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

//    public List<DriverDTO> getDriversByLicenseType(Driver.LicenseType licenseType) throws SQLException {
//        return driverDAO.getDriversByLicenseType(licenseType);
//    }

}
