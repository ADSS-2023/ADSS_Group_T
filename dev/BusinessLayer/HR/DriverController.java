package BusinessLayer.HR;

import BusinessLayer.Transport.Driver;
import UtilSuper.Pair;
import UtilSuper.PositionType;
import UtilSuper.UserType;
import BusinessLayer.Transport.Driver.CoolingLevel;
import BusinessLayer.Transport.Driver.LicenseType;
import java.security.KeyPair;
import java.time.LocalDate;
import java.util.*;

public class DriverController {
    private LinkedHashMap<Integer, Driver> drivers;
    private LinkedHashMap<LocalDate, ArrayList<Pair<Driver.LicenseType, Driver.CoolingLevel>>> driversRequirements; // date, amount
    private LinkedHashMap<LocalDate, HashMap<Driver, Boolean>> date2driversSubmission; // date, driver. isAssigned


// TODO- modify it if needed
    public DriverController() {
        drivers = new LinkedHashMap<Integer, Driver>();
    }

    public String areRequirementsFulfilled(LocalDate date) {
        int requiredDrivers = driversRequirements.getOrDefault(date, 0);
        int assignedDrivers = 0;
        if (date2driversSubmission.containsKey(date)) {
            for (Boolean isAssigned : date2driversSubmission.get(date).values()) {
                if (isAssigned) {
                    assignedDrivers++;
                }
            }
        }
        if (assignedDrivers < requiredDrivers) {
            return "There are not enough drivers assigned for the date " + date + ". Required: " + requiredDrivers + ", Assigned: " + assignedDrivers;
        } else {
            return "All driver requirements are fulfilled for the date " + date + ". Required: " + requiredDrivers + ", Assigned: " + assignedDrivers;
        }
    }



    public void assignDriver(LocalDate date, int id) {
        if(this.date2driversSubmission.get(date).containsKey(drivers.get(id)))
            this.date2driversSubmission.get(date).replace(drivers.get(id),false,true);
    }

    public void assignAllDriver(LocalDate date) {
        HashMap<Driver, Boolean> driversForDate = date2driversSubmission.get(date);
        for (Driver driver: driversForDate.keySet()) {
                this.assignDriver(date,driver.getId());
        }
    }




    public boolean submitShift(LocalDate date, int id) {
        if (!drivers.containsKey(id)) {
            throw new IllegalArgumentException("Driver with id " + id + " does not exist.");
        }
//        if (!drivers.get(id).isLegalDate(date)) {
//            throw new IllegalArgumentException("The driver cannot work on " + date);
//        }

        if (date2driversSubmission.containsKey(date) && date2driversSubmission.get(date).containsKey(drivers.get(id))) {
            throw new IllegalArgumentException("Driver with id " + id + " has already submitted for " + date);
        }

        if (!date2driversSubmission.containsKey(date)) {
            date2driversSubmission.put(date, new HashMap<>());
        }
        date2driversSubmission.get(date).put(drivers.get(id), false);
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

    public ArrayList<Driver> getDriversByDate(LocalDate tomorrow) {
        ArrayList<Driver> drivers = new ArrayList<>();
        if (date2driversSubmission.containsKey(tomorrow)) {
            HashMap<Driver, Boolean> driverStatusMap = date2driversSubmission.get(tomorrow);
            for (Map.Entry<Driver, Boolean> entry : driverStatusMap.entrySet()) {
                Driver driver = entry.getKey();
                Boolean isAssigned = entry.getValue();
                if (!isAssigned) {
                    drivers.add(driver);
                }
            }
        }
        return drivers;
    }

    public void addDirverRequirement(LocalDate requiredDate, Driver.LicenseType licenseType, Driver.CoolingLevel coolingLevel) {
        Pair<Driver.LicenseType, Driver.CoolingLevel> pair = new Pair(licenseType,coolingLevel);
        if(this.driversRequirements.containsKey(requiredDate))
            driversRequirements.get(requiredDate).add(pair);
        else{
            ArrayList<Pair<Driver.LicenseType, Driver.CoolingLevel>> newList = new ArrayList< Pair<LicenseType,CoolingLevel>>();
            newList.add(pair);
            this.driversRequirements.put(requiredDate, newList);
        }
    }
    public ArrayList<Pair<Driver.LicenseType, Driver.CoolingLevel>> getDirverRequirement(LocalDate requiredDate) {
        if(this.driversRequirements.containsKey(requiredDate))
            return driversRequirements.get(requiredDate);
        else
            return null;
    }

}
