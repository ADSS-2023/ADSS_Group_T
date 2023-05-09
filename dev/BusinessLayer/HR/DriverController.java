package BusinessLayer.HR;

import DataLayer.HR_T_DAL.DalService.DalDriverService;
import UtilSuper.Pair;
import BusinessLayer.HR.User.PositionType;
import BusinessLayer.HR.User.UserType;
import BusinessLayer.HR.Driver.CoolingLevel;
import BusinessLayer.HR.Driver.LicenseType;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

public class DriverController {
    private LinkedHashMap<Integer, Driver> drivers;
    private LinkedHashMap<LocalDate, ArrayList<Pair<Driver.LicenseType, Driver.CoolingLevel>>> driversRequirements; // date, amount
    private LinkedHashMap<LocalDate, HashMap<Driver, Boolean>> date2driversSubmission; // date, driver. isAssigned

    private DalDriverService dalDriverService;


// TODO- modify it if needed
    public DriverController() {
        drivers = new LinkedHashMap<Integer, Driver>();
    }


    // TODO - do not forge to init
    public void initDriverController(DalDriverService dalDriverService) {
        this.dalDriverService = dalDriverService;
    }


public Driver lazyLoadDriver (int id) throws SQLException {
    Driver driver = drivers.get(id);
    if (driver == null){
        driver = dalDriverService.findDriverById(id);
        if (driver != null)
            drivers.put(id, driver);
    }
    return  driver;
}

    public HashMap<Driver, Boolean> lazyLoadDriverSubmition(LocalDate date, int id) throws SQLException {
        // get the submission status for the given date and driver
        HashMap<Driver, Boolean> driverSubmission = date2driversSubmission.get(date);
        if (driverSubmission == null) {
            driverSubmission = dalDriverService.findSubmissionByIdAndDate(id, date);
            if (driverSubmission != null)
                date2driversSubmission.put(date, driverSubmission);
            else
                throw new IllegalArgumentException("Driver has not submitted availability for this date");
        }
        return driverSubmission;
    }
    public void assignDriver(LocalDate date, int id) throws SQLException {
        // get the driver with the given id
        Driver driver = lazyLoadDriver(id);
        if (driver == null)
            throw new IllegalArgumentException("Invalid driver id");


        // get the submission status for the given date and driver
        HashMap<Driver, Boolean> driverSubmissionToAssign = lazyLoadDriverSubmition(date, id);

        if (driverSubmissionToAssign.get(driver) == null) {
            throw new IllegalArgumentException("Driver has not submitted availability for this date");
        }
        if (driverSubmissionToAssign.get(driver).booleanValue()) {
            throw new IllegalArgumentException("Driver is already assigned to a shift on this day.");
        }

        // Requirement : A driver cannot work more than six times a week.
        LocalDate startOfWeek = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDate endOfWeek = startOfWeek.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
        for (int i = 0; i <= 6; i++) {
            lazyLoadDriverSubmition(startOfWeek.plusDays(i), id);
        }
        int numShiftsThisWeek = 0;
        for (LocalDate d = startOfWeek; !d.isAfter(endOfWeek); d = d.plusDays(1)) {
            if (d.equals(date)) {
                continue;  // skip the current date
            }
            HashMap<Driver, Boolean> submissions = lazyLoadDriverSubmition(d, id);
            if (submissions != null && submissions.get(driver) != null && submissions.get(driver)) {
                numShiftsThisWeek++;
            }
        }
        if (numShiftsThisWeek >= 6) {
            throw new IllegalArgumentException("Driver has already worked six shifts this week");
        }

        // mark the driver as assigned for the given date
        driverSubmissionToAssign.replace(driver, false, true);
        dalDriverService.assignDriver(id, date);
    }




    public void assignAllDriver(LocalDate date) throws SQLException {
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
                             Driver.LicenseType licenseType, Driver.CoolingLevel coolingLevel) throws SQLException {
        if (drivers.containsKey(id))
            throw new IllegalArgumentException("Driver already exists.");
        else {
            Driver driver = new Driver(id, employeeName, bankAccount, qualifiedPositions, description, salary,
                    joiningDay, password, userType, licenseType, coolingLevel);
            dalDriverService.addDriver(driver);
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
