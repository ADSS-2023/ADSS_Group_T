package DataLayer.HR_T_DAL.DAOs;

import BusinessLayer.HR.Driver;
import DataLayer.HR_T_DAL.DTOs.DriverDTO;
import DataLayer.Util.DAO;

import java.util.List;

public class DriverDAO extends DAO {

    public DriverDAO() {
    }

    public DriverDTO getDriverById(int id) {
        // Retrieve a driver's details from the database by their ID
        return null;
    }

    public List<DriverDTO> getAllDrivers() {
        // Retrieve all drivers from the database
        return null;
    }

    public List<DriverDTO> getDriversByLicenseType(Driver.LicenseType licenseType) {
        // Retrieve all drivers from the database with a specific license type
        return null;
    }



}