package BusinessLayer.HR;

import BusinessLayer.HR.User.PositionType;
import BusinessLayer.Transport.BranchController;
import DataLayer.HR_T_DAL.DalService.DalEmployeeService;
import DataLayer.HR_T_DAL.DalService.DalShiftService;
import ServiceLayer.Transport.BranchService;
import UtilSuper.Time;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ShiftController {
    private final LinkedHashMap<String, HashMap<LocalDate, ArrayList<Shift>>> shifts; // branch, shiftDate, shiftType
    private DriverController driverController;
    private BranchController branchController;
    private LinkedHashMap<Integer, Employee> employeesMapper;
    private DalShiftService dalShiftService;
    private DalEmployeeService dalEmployeeService;
    private HashMap<LocalDate, String> notifications;


    public ShiftController() {
        shifts = new LinkedHashMap<>();
    }

    public ShiftController(DriverController driverController, DalEmployeeService dalEmployeeService, DalShiftService dalShiftService,
                           LinkedHashMap<Integer, Employee> employeesMapper, BranchController branchController) {

        this.branchController = branchController;
        shifts = new LinkedHashMap<>();
        this.driverController = driverController;
        this.dalEmployeeService = dalEmployeeService;
        this.dalShiftService = dalShiftService;
        this.employeesMapper = employeesMapper;
        this.notifications = new HashMap<>();
    }

    public HashMap<LocalDate, String> getNotifications() {
        return notifications;
    }

    public void skipDay(LocalDate date) throws SQLException {
        // Retrieve all shifts for the given date and group them by branch
        LinkedHashMap<String, HashMap<LocalDate, ArrayList<Shift>>> shiftsByDateInAllBranch = dalShiftService.findAllShiftsByDateInAllBranches(date);

        // Initialize a notification string to collect information about illegal shifts
        StringBuilder notificationBuilder = new StringBuilder();

        // Iterate over all branches
        for (String branch : shiftsByDateInAllBranch.keySet()) {
            notificationBuilder.append("\n==============================\n");
            notificationBuilder.append(String.format("Branch: %s\n", branch));

            // Retrieve all shifts for the current branch and date
            ArrayList<Shift> shiftsForDateInBranch = shiftsByDateInAllBranch.get(branch).get(date);

            // Iterate over all shifts for the current branch and date
            for (Shift shift : shiftsForDateInBranch) {
                // Check if the current shift has a manager
                if (shift.getShiftManagerId() == -1) {
                    notificationBuilder.append("Noticed - the shift must have a manager!!!\n");
                } else {
                    notificationBuilder.append(String.format("Manager ID: %s\n", shift.getShiftManagerId()));
                }
                // Check if the current shift is legal
                String legalStatus = shift.isLegalShift() ? "LEGAL" : "ILLEGAL";
                notificationBuilder.append("\n------------------------------\n");
                notificationBuilder.append(String.format("Shift Date: %s\nShift Type: %s\nLegal Status: %s\n", shift.getDate(), shift.getShiftType(), legalStatus));
                notificationBuilder.append("\nEmployee Requirements:\n");

                // Add a line for each position requirement
                for (Map.Entry<String, Integer> requirement : shift.getEmployeeRequirements().entrySet()) {
                    String position = requirement.getKey();
                    int requiredAmount = requirement.getValue();
                    notificationBuilder.append(String.format("%s: %d\n", position, requiredAmount));
                }
            }
        }

        // Output the notification string
        notifications.put(date, notificationBuilder.toString());
        dalShiftService.addNotification(date, notificationBuilder.toString());
    }

    public HashMap<LocalDate, String> getNotifications(LocalDate fromDate, LocalDate toDate) throws SQLException {
        this.notifications = dalShiftService.getNotifications(fromDate.toString(), toDate.toString());
        dalShiftService.getNotifications(fromDate.toString(), toDate.toString());
        return notifications;
    }

    public LinkedHashMap<LocalDate, ArrayList<Shift>> lazyLoadFindShifsByBranch(String branch) throws SQLException {
        LinkedHashMap<LocalDate, ArrayList<Shift>> branchShifts = dalShiftService.findAllShiftsByBranch(branch);
        shifts.put(branch, branchShifts);
        return branchShifts;
    }

    public String showShiftStatus(String branchId, LocalDate date, boolean shiftType) throws SQLException {
        LinkedHashMap<LocalDate, ArrayList<Shift>> branchShifts = lazyLoadFindShifsByBranch(branchId);
        Shift shift = shiftType ? branchShifts.get(date).get(0) : branchShifts.get(date).get(1);
        return shift.showShiftStatus(dalShiftService);
    }

    public Employee lazyLoadFindEmployeeByid(int id) throws SQLException {
        Employee employee = employeesMapper.get(id);
        if (employee == null)
            employee = dalEmployeeService.findEmployeeById(id);
        return employee;
    }

    public String submitShiftForEmployee(String branch, int id, LocalDate date, boolean shiftType) throws Exception {
        Employee employee = lazyLoadFindEmployeeByid(id);
        HashMap<LocalDate, ArrayList<Shift>> branchShifts = lazyLoadFindShifsByBranch(branch);

        if (employee != null && branchShifts != null) {
            employee.addSubmittedShift(branch, date, shiftType);
            Shift shift = shiftType ? branchShifts.get(date).get(0) : branchShifts.get(date).get(1);
            return shift.submitShiftForEmployee(employee, employee.getQualifiedPositions());
        }
        return "submit shift has failed";
    }

    public String assignEmployeeForShift(String branch, int id, LocalDate date, boolean shiftType, String positionType) throws Exception {
        if (branchController.getBranch(branch) == null)
            throw new Exception(String.format("Branch %s does not exist", branch));
        HashMap<LocalDate, ArrayList<Shift>> branchShifts = lazyLoadFindShifsByBranch(branch);
        Employee employee = lazyLoadFindEmployeeByid(id);
        if (branchShifts != null && employee != null) {
            Shift shift = shiftType ? branchShifts.get(date).get(0) : branchShifts.get(date).get(1);
            return shift.assignEmployeeForShift(positionType, employee);
        }
        return "assign failed";
    }

    public String assignAll(String branch, LocalDate date, boolean shiftType) throws Exception {
        if (branchController.getBranch(branch) == null)
            throw new Exception(String.format("Branch %s does not exist", branch));
        HashMap<LocalDate, ArrayList<Shift>> branchShifts = lazyLoadFindShifsByBranch(branch);
        if (branchShifts != null) {
            Shift shift = shiftType ? branchShifts.get(date).get(0) : branchShifts.get(date).get(1);
            shift.assignAll();
            return shift.assignAll();
        }
        return "assign failed";
    }

    public void addRequirements(String branch, LocalDate shiftDate, boolean shiftType, LinkedHashMap<String, Integer> requirements) throws Exception {
        if (branchController.getBranch(branch) == null)
            throw new Exception(String.format("Branch %s does not exist", branch));
        HashMap<LocalDate, ArrayList<Shift>> branchShifts = lazyLoadFindShifsByBranch(branch);
        String shiftT = "e";
        if (branchShifts != null) {
            if (branchShifts.containsKey(shiftDate)) {
                ArrayList<Shift> shiftList = branchShifts.get(shiftDate);
                Shift shift = shiftList.get(shiftType ? 0 : 1);
                if (shiftType) shiftT = "m";
                for (String positionT : requirements.keySet()) {
                    dalShiftService.addRequierement(branch, Time.localDateToString(shiftDate), shiftT, positionT, requirements.get(positionT));
                }
                shift.addEmployeeRequirements(requirements);
            } else {
                throw new IllegalArgumentException("No shifts available for the given date");
            }
        } else {
            throw new IllegalArgumentException("Invalid branch ID");
        }
    }

    public void addDirverRequirement(LocalDate requiredDate, Driver.LicenseType licenseType, Driver.CoolingLevel coolingLevel) throws SQLException {

        driverController.addDriverRequirement(requiredDate, licenseType, coolingLevel);
    }

    public void addStoreKeeperRequirement(LocalDate requiredDate, String branch) throws Exception {
        if (branchController.getBranch(branch) == null)
            throw new Exception(String.format("Branch %s does not exist", branch));
        HashMap<LocalDate, ArrayList<Shift>> branchShifts = lazyLoadFindShifsByBranch(branch);
        if (branchShifts == null)
            throw new NoSuchFieldException("there is no such branch exist");
        ArrayList<Shift> branchShiftsByDate = branchShifts.get(requiredDate);
        String s;
        if (branchShiftsByDate != null) {
            for (Shift shift : branchShiftsByDate) {
                LinkedHashMap<String, Integer> storeKeeperRequirment = new LinkedHashMap<>();
                storeKeeperRequirment.put(PositionType.storekeeper.name(), 1);
                shift.addEmployeeRequirements(storeKeeperRequirment);
                if (shift.getShiftType()) {
                    s = "morning";
                } else s = "evening";
                dalShiftService.addRequierement(branch, requiredDate.toString(), s, PositionType.storekeeper.toString(), 1);
            }
        }
    }

    public ArrayList<String> getBranchesWithoutStoreKeeper(LocalDate date) throws Exception {
        ArrayList<String> branchesWithoutStoreKeeper = new ArrayList<>();
        // Iterate over all the shifts for the specified date and all the branches
        LinkedHashMap<String, HashMap<LocalDate, ArrayList<Shift>>> shiftsByDateInAllBranch = dalShiftService.findAllShiftsByDateInAllBranches(date);
        if (shiftsByDateInAllBranch != null) {
            for (String branch : shiftsByDateInAllBranch.keySet()) {
                // Get the shifts for the specified date and branch
                ArrayList<Shift> shiftsForDateInBranch = shiftsByDateInAllBranch.get(branch).get(date);
                // Check if there is a storekeeper assigned to any of the shifts for the specified date and branch
                for (Shift shift : shiftsForDateInBranch) {
                    if (!shift.isThereAnyStoreKeeperReuirement()) {
                        branchesWithoutStoreKeeper.add(branch);
                        break;
                    }
                }
            }
        }
        return branchesWithoutStoreKeeper;
    }

}
