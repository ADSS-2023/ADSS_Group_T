package BusinessLayer.HR;

import BusinessLayer.Transport.Driver;
import UtilSuper.PositionType;
import UtilSuper.UserType;

import java.util.LinkedHashMap;
import java.util.List;

public class DriverController {
    private LinkedHashMap<Integer, Driver> drivers;
    public DriverController() {
        drivers = new LinkedHashMap<Integer, Driver>();
    }


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
                             String description, int salary, String joiningDay, String password, UserType userType,
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
