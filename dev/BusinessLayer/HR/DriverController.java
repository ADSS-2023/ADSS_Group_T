package BusinessLayer.HR;

import DataLayer.HR_T_DAL.DalService.DalDriverService;
import UtilSuper.Pair;
import BusinessLayer.HR.User.PositionType;
import BusinessLayer.HR.User.UserType;
import BusinessLayer.HR.Driver.CoolingLevel;
import BusinessLayer.HR.Driver.LicenseType;
import UtilSuper.Time;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

public class DriverController {
    private LinkedHashMap<Integer, Driver> drivers;
    private LinkedHashMap<LocalDate, LinkedHashMap<Pair<Driver.LicenseType, Driver.CoolingLevel> , Integer>> driversRequirements; // date, amount
    private LinkedHashMap<LocalDate, LinkedHashMap<Driver, Boolean>> date2driversSubmission; // date, driver. isAssigned

    private DalDriverService dalDriverService;


    public DriverController(DalDriverService dalDriverService) {
        drivers = new LinkedHashMap<Integer, Driver>();
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



    public String getRequirementsByDate(LocalDate date) throws SQLException {
        LinkedHashMap<Pair<Driver.LicenseType, Driver.CoolingLevel>, Integer> requirements = lazyLoadAllRequierementsForDate(date);

        StringBuilder sb = new StringBuilder();
        sb.append("Requirements for ").append(date.toString()).append(":\n");
        sb.append("----------------------------------\n");
        int i = 1;
        for (Map.Entry<Pair<Driver.LicenseType, Driver.CoolingLevel>, Integer> entry : requirements.entrySet()) {
            Pair<Driver.LicenseType, Driver.CoolingLevel> requirement = entry.getKey();
            Integer amount = entry.getValue();
            sb.append(i++).append(". License type: ").append(requirement.getFirst().toString())
                    .append(", Cooling level: ").append(requirement.getSecond().toString())
                    .append(", Amount: ").append(amount).append("\n");
        }
        return sb.toString();
    }






    public void assignDriver(LocalDate date, int id, int numRequirement) throws SQLException {
        // get the driver with the given id
        Driver driver = lazyLoadDriver(id);
        if (driver == null) {
            throw new IllegalArgumentException("Invalid driver id");
        }

        // get the submission status for the given date and driver
        LinkedHashMap<Driver, Boolean> driverSubmissionToAssign = lazyLoadDriverSubmitionByDateAndId(date, id);
        if (driverSubmissionToAssign == null || driverSubmissionToAssign.get(driver) == null)
            throw new IllegalArgumentException("Driver has not submitted availability for this date");

        Boolean submissionStatus = driverSubmissionToAssign.get(driver);

        if (submissionStatus.booleanValue()) {
            throw new IllegalArgumentException("Driver is already assigned to a shift on this day.");
        }

        // Requirement : A driver cannot work more than six times a week.
        LocalDate startOfWeek = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDate endOfWeek = startOfWeek.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
        LinkedHashMap<LocalDate, LinkedHashMap<Driver, Boolean>> driverSubmissionsInWeek = dalDriverService.findAllDriverSubmissionsBetweenDates(startOfWeek, endOfWeek, id);
        int numShiftsThisWeek = 0;
        for (HashMap<Driver, Boolean> driverSubmissions : driverSubmissionsInWeek.values()) {
            Boolean submission = driverSubmissions.get(driver);
            if (submission != null && submission) {
                numShiftsThisWeek++;
            }
        }
        if (numShiftsThisWeek >= 6) {
            throw new IllegalArgumentException("Driver has already worked six shifts this week");
        }

        // Check that it meets the requirements
        LinkedHashMap<Pair<Driver.LicenseType, Driver.CoolingLevel>, Integer> requirements =  lazyLoadAllRequierementsForDate(date);
        int i=1;
        for (Pair<Driver.LicenseType, Driver.CoolingLevel> pair : requirements.keySet()) {
            if (i == numRequirement) { // check if we've reached the desired index
                if (!isDriverMeetingTheRequirements(driver, pair))
                    throw new IllegalArgumentException("Driver is not meeting the requirements");
                if (requirements.get(date) <= 0)
                    throw new IllegalArgumentException("there is no such requirements");
            }
            else{
                //deleteRequieremnt
                deleteRequirement(pair, date);
                // mark the driver as assigned for the given date
                driverSubmissionToAssign.put(driver, true);
                dalDriverService.assignDriver(id, date);
                }
            i++;
            }
        }


    public boolean deleteRequirement(Pair<Driver.LicenseType, Driver.CoolingLevel> requirement, LocalDate date) throws SQLException {
        LinkedHashMap<Pair<Driver.LicenseType, Driver.CoolingLevel>, Integer> requirements = driversRequirements.get(date);
        if (requirements.containsKey(requirement)) {
            int amount = requirements.get(requirement);
            //delete from cache
            if (amount > 1)
                requirements.put(requirement, amount - 1);
            else
                 requirements.remove(requirement);

            //delete from the dalService
             if (amount == 1)
                 dalDriverService.deleterequieremnt(date, requirement.getFirst().name(), requirement.getSecond().name());
             else
                 dalDriverService.updateAmountOfRequierement(date, requirement.getFirst().name(), requirement.getSecond().name(), amount-1);
            return true;
        } else {
            return false;
        }
    }



    public boolean isDriverMeetingTheRequirements(Driver driver, Pair<Driver.LicenseType, Driver.CoolingLevel> requirement) {
        if (requirement == null )
            throw new IllegalArgumentException("No such requeirement exist");
        if (driver == null)
            throw new IllegalArgumentException("No such driver");
        return driver.getLicenseLevel().ordinal() >= requirement.getFirst().ordinal() && driver.getCoolingLevel().ordinal() >= requirement.getSecond().ordinal();
    }

    public LinkedHashMap<Driver, Boolean> lazyLoadAllDriverSubmitionByDate(LocalDate date) throws SQLException {
        LinkedHashMap<Driver, Boolean> allDriversInDate = dalDriverService.findAllSubmissionByDate(date);
        date2driversSubmission.put(date, allDriversInDate);
        return  allDriversInDate;
    }

    public LinkedHashMap<Pair<Driver.LicenseType, Driver.CoolingLevel> , Integer> lazyLoadAllRequierementsForDate(LocalDate date) throws SQLException {
        LinkedHashMap<Pair<Driver.LicenseType, Driver.CoolingLevel> , Integer> requirementsForDate = dalDriverService.findAllRequirementsByDate(date);
        driversRequirements.put(date, requirementsForDate);
        return  requirementsForDate;
    }



    public int getMinRequirementsLicenseLevelByDate(LocalDate date) throws SQLException {
        LinkedHashMap<Pair<Driver.LicenseType, Driver.CoolingLevel>, Integer> requirements = lazyLoadAllRequierementsForDate(date);
        int minLicenseLevel = Integer.MAX_VALUE;
        for (Map.Entry<Pair<Driver.LicenseType, Driver.CoolingLevel>, Integer> entry : requirements.entrySet()) {
            int licenseLevel = entry.getKey().getFirst().ordinal();
            if (licenseLevel < minLicenseLevel) {
                minLicenseLevel = licenseLevel;
            }
        }
        return minLicenseLevel;
    }


    public int getMinRequirementsCoolingLevelByDate(LocalDate date) throws SQLException {
        LinkedHashMap<Pair<Driver.LicenseType, Driver.CoolingLevel>, Integer> requirements = lazyLoadAllRequierementsForDate(date);
        int minCoolingLevel = Integer.MAX_VALUE;
        for (Map.Entry<Pair<Driver.LicenseType, Driver.CoolingLevel>, Integer> entry : requirements.entrySet()) {
            int coolingLevel = entry.getKey().getSecond().ordinal();
            if (coolingLevel < minCoolingLevel) {
                minCoolingLevel = coolingLevel;
            }
        }
        return minCoolingLevel;
    }


    public LinkedHashMap<Driver, Boolean> lazyLoadDriverSubmitionByDateAndId(LocalDate date, int id) throws SQLException {
        LinkedHashMap<Driver, Boolean> driverSubmissionByDate  = dalDriverService.findSubmissionByIdAndDate(id, date);
        date2driversSubmission.put(date, driverSubmissionByDate);
        return  driverSubmissionByDate;
    }

    public String submitShift(LocalDate date, int id, CoolingLevel coolingLevel, LicenseType licenseType) throws SQLException {
        LinkedHashMap<Driver, Boolean> driverSubmissionByDate = lazyLoadDriverSubmitionByDateAndId(date, id);
        Driver driver = lazyLoadDriver(id);

        if (driver == null)
            throw new IllegalArgumentException("Driver with id " + id + " does not exist.");

        if (driverSubmissionByDate != null) {
            throw new IllegalArgumentException("Driver with id " + id + " has already submitted for " + date);
        }
        // Check if the driver has the required license type
        if (driver.getLicenseLevel().ordinal() >= getMinRequirementsLicenseLevelByDate(date)) {
            throw new IllegalArgumentException("Driver with id " + id + " does not have the required license type.");
        }

        // Check if the driver has the required cooling level
        if (drivers.get(id).getCoolingLevel().ordinal() >= getMinRequirementsCoolingLevelByDate(date)) {
            throw new IllegalArgumentException("Driver with id " + id + " does not have the required cooling level.");
        }



        if (!date2driversSubmission.containsKey(date)) {
            date2driversSubmission.put(date, new LinkedHashMap<>());
        }
        date2driversSubmission.get(date).put(driver, false);
        dalDriverService.addSubmissionForDriver(id, date);
        return "Driver with id " + id + " submit successfully to " + date.toString();
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
                             String licenseType, int coolingLevel) throws SQLException {
        Driver driver = lazyLoadDriver(id);
        if (driver != null)
            throw new IllegalArgumentException("Driver already exists.");
        else {
            driver = new Driver(id, employeeName, bankAccount, qualifiedPositions, description, salary,
                    Time.stringToLocalDate(joiningDay), password, userType, getByString(licenseType), get(coolingLevel));
            dalDriverService.addDriver(driver);
            this.drivers.put(id, driver);
        }
        return true;
    }

    public static LicenseType getByString (String licenseType ) {
        if (licenseType.equals("C1"))
            return LicenseType.C1;
        if (licenseType.equals("C"))
            return LicenseType.C;
        if (licenseType.equals("E"))
            return LicenseType.E;
        else
            return LicenseType.C1;
    }

    public static CoolingLevel get(int coolingLevel) {
        if (coolingLevel == 1)
            return CoolingLevel.non;
        if (coolingLevel == 2)
            return CoolingLevel.fridge;
        if (coolingLevel == 3)
            return CoolingLevel.freezer;
        else
            return CoolingLevel.non;
    }



    public ArrayList<Driver> getDriversAssignedByDate(LocalDate tomorrow) throws SQLException {
        LinkedHashMap<Driver, Boolean> driverStatusMap = lazyLoadAllDriverSubmitionByDate(tomorrow);
        ArrayList<Driver> drivers = new ArrayList<>();
        if (driverStatusMap != null) {
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



    public void addDriverRequirement(LocalDate requiredDate, Driver.LicenseType licenseType, Driver.CoolingLevel coolingLevel) throws SQLException {
        Pair<Driver.LicenseType, Driver.CoolingLevel> requirement = new Pair<>(licenseType, coolingLevel);
        LinkedHashMap<Pair<Driver.LicenseType, Driver.CoolingLevel>, Integer> requirements = lazyLoadAllRequierementsForDate(requiredDate);

        int amount;
        // Check if requirement already exists, increment the count
        if (requirements.containsKey(requirement)) {
            amount = requirements.get(requirement);
            requirements.put(requirement, amount + 1);
            dalDriverService.updateAmountOfRequierement(requiredDate, licenseType.name(), coolingLevel.name(), amount);
        } else {
            // Requirement doesn't exist, add a new entry
            amount = 1;
            requirements.put(requirement, amount);
            dalDriverService.addSRequirement(requiredDate, licenseType.name(), coolingLevel.name(), amount);
        }

    }

    //Noam Gilad Write
    public ArrayList<Driver> getAssignedDrivers() {
        ArrayList<Driver> assignedDrivers = new ArrayList<>();

        for (LinkedHashMap<Driver, Boolean> driversSubmission : date2driversSubmission.values()) {
            for (Map.Entry<Driver, Boolean> driverSubmission : driversSubmission.entrySet()) {
                if (driverSubmission.getValue()) {
                    assignedDrivers.add(driverSubmission.getKey());
                }
            }
        }
        return assignedDrivers;
    }






}

