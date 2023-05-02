package BusinessLayer.HR;

import BusinessLayer.Transport.Driver;
import UtilSuper.PositionType;
import UtilSuper.UserType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class DriverController {
    private LinkedHashMap<Integer, Driver> drivers;

    private LinkedHashMap<LocalDate, Integer> driversRequirements; // date, amount
    private LinkedHashMap<LocalDate, HashMap<Driver, Boolean>> date2driversSubmission; // date, driver. isAssigned


    public DriverController() {
        drivers = new LinkedHashMap<Integer, Driver>();
    }

    public boolean assignDriver(LocalDate date, int id) {

    }

    public boolean assignAllDriver(LocalDate date) {

    }
    public boolean submitShift(LocalDate date, int id) {
        if (!drivers.containsKey(id)) {
            throw new IllegalArgumentException("Driver with id " + id + " does not exist.");
        }
        if (!drivers.get(id).isLegalDate(date)) {
            throw new IllegalArgumentException("The driver cannot work on " + date);
        }

        if (date2driversSubmission.containsKey(date) && date2driversSubmission.get(date).containsKey(drivers.get(id))) {
            throw new IllegalArgumentException("Driver with id " + id + " has already submitted for " + date);
        }

        if (!date2driversSubmission.containsKey(date)) {
            date2driversSubmission.put(date, new HashMap<>());
        }
        date2driversSubmission.get(date).put(drivers.get(id), false);
        return true;
    }





    public boolean addDriverRequirement(LocalDate localDate) {
        if (driversRequirements.containsKey(localDate)) {
            int currentRequirement = driversRequirements.get(localDate);
            driversRequirements.put(localDate, currentRequirement + 1);
        } else {
            driversRequirements.put(localDate, 1);
        }
        return true;
    }


    // TODO- make submit Shift to driver and assign shift

    /**
     * add a new driver to the drivers map
     *
     * @param id           - the id of the driver
     * @param employeeName - the name of the driver
     * @param bankAccount  - the bank account of the driver
     * @param qualifiedPositions - a list of positions the driver is qualified for
     * @param description  - a description of the driver
     * @param salary       - the salary of the driver
     * @param joiningDay   - the joining date of the driver
     * @param password     - the password of the driver
     * @param userType     - the user type of the driver
     * @param licenseType  - the license type of the driver
     * @param coolingLevel - the cooling level to which the driver is qualified
     * @return true if the driver added successfully , and false otherwise
     */
    public boolean addDriver(int id, String employeeName, String bankAccount, List<PositionType> qualifiedPositions,
                             String description, int salary, LocalDate joiningDay, String password, UserType userType,
                             Driver.LicenseType licenseType, Driver.CoolingLevel coolingLevel) {
        if (drivers.containsKey(id))
            throw new IllegalArgumentException("Driver already exists.");
        else {
            Driver driver = new Driver(id, employeeName, bankAccount, qualifiedPositions, description, salary,
                    joiningDay, password, userType, licenseType, coolingLevel);
            this.drivers.put(id, driver);
        }
        return true;
    }


    public LinkedHashMap<Integer, Driver> getAllDrivers() {
        return this.drivers;
    }
    public Driver getDriver(int id){
        if (!drivers.containsKey(id))
            throw new IllegalArgumentException("no such driver");
        else
            return drivers.get(id);
    }

    public LinkedHashMap<Integer, Driver> getDrivers() {
        return drivers;
    }



    /**
     * remove a driver from the drivers map
     *
     * @param id - thr id of the driver
     * @return true if the driver removed successfully , and false otherwise
     */
    public  boolean removeDriver(int id) {
        if (!drivers.containsKey(id))
            return false;
        drivers.remove(id);
        return true;
    }
}
