package BusinessLayer.HR;

import BusinessLayer.Transport.Driver;

import java.util.LinkedHashMap;

public class DriverController {
    private LinkedHashMap<Integer, Driver> drivers;
    public DriverController() {
        drivers = new LinkedHashMap<Integer, Driver>();
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

    /**
     * add a new driver to the drivers map
     *
     * @param id           - the id of the driver
     * @param name         - the name of the driver
     * @param licenseType  - the license type of the driver
     * @param coolingLevel - the cooling level to which the driver is qualified
     * @return true if the driver added successfully , and false otherwise
     */
    public  boolean addDriver(int id, String name, int licenseType, int coolingLevel) {
        if (drivers.containsKey(id))
            throw new IllegalArgumentException("driver already exist");
        else {
            Driver driver = new Driver(id, name, licenseType, coolingLevel);
            this.drivers.put(id, driver);
        }
    return true;
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
