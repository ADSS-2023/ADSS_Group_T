package BusinessLayer.HR;

import BusinessLayer.HR.User.PositionType;
import BusinessLayer.Transport.BranchController;
import DataLayer.HR_T_DAL.DalService.DalEmployeeService;
import DataLayer.HR_T_DAL.DalService.DalShiftService;
import ServiceLayer.Transport.BranchService;
import UtilSuper.Time;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class ShiftController {


    private final LinkedHashMap<String, HashMap<LocalDate, ArrayList<Shift>>> shifts; // branch, shiftDate, shiftType
    private DriverController driverController;
    private BranchController branchController;
    private LinkedHashMap<Integer, Employee> employeesMapper;
    private DalShiftService dalShiftService;
    private DalEmployeeService dalEmployeeService;
    private HashMap<LocalDate, String> notifications;
    private HashMap<LocalDate, Map<String, Object>> notificationsUI;




    public ShiftController(DriverController driverController, DalEmployeeService dalEmployeeService, DalShiftService dalShiftService,
                           BranchController branchController, LinkedHashMap<Integer, Employee> employeesMapper) {

        this.branchController = branchController;
        shifts = new LinkedHashMap<>();
        this.driverController = driverController;
        this.dalEmployeeService = dalEmployeeService;
        this.dalShiftService = dalShiftService;
        this.notifications = new HashMap<>();
        this.employeesMapper = employeesMapper;
    }




    public void skipDay(LocalDate date) throws SQLException {
        // Retrieve all shifts for the given date and group them by branch
        LinkedHashMap<String, HashMap<LocalDate, ArrayList<Shift>>> shiftsByDateInAllBranch = dalShiftService.findAllShiftsByDateInAllBranches(date);

        // Initialize a notification string to collect information about illegal shifts
        String notificationString = notificationBuilder(date, shiftsByDateInAllBranch);


        // Output the notification string
        notifications.put(date, notificationString);
        notificationsUI.put(date, buildNotificationUI(date, shiftsByDateInAllBranch));
        dalShiftService.addNotification(date, notificationString);
    }

    public String notificationBuilder(LocalDate date, LinkedHashMap<String, HashMap<LocalDate, ArrayList<Shift>>> shiftsByDateInAllBranch) throws SQLException {
        // Initialize a notification string to collect information about illegal shifts
        StringBuilder notificationBuilder = new StringBuilder();

        try {
            // Iterate over all branches
            for (String branch : shiftsByDateInAllBranch.keySet()) {
                try {
                    notificationBuilder.append("\n==============================\n");
                    notificationBuilder.append(String.format("Branch: %s\n", branch));

                    // Retrieve all shifts for the current branch and date
                    ArrayList<Shift> shiftsForDateInBranch = shiftsByDateInAllBranch.get(branch).get(date);

                    if (shiftsForDateInBranch != null) {
                        // Iterate over all shifts for the current branch and date
                        for (Shift shift : shiftsForDateInBranch) {
                            // Check if the current shift has a manager
                            if (shift.getShiftManagerId() == -1) {
                                notificationBuilder.append("Noticed no such manager shift has been assign yet - the shift must have a manager!!!\n");
                            } else {
                                notificationBuilder.append(String.format("Manager ID: %s\n", shift.getShiftManagerId()));
                            }
                            // Check if the current shift is legal
                            String legalStatus = "";
                            int isLlegal = shift.isLegalShift();
                            if (isLlegal == 1)
                                legalStatus = "legal";
                            else if (isLlegal == 0)
                                legalStatus = "illegal";
                            else if (isLlegal == -1)
                                legalStatus = "There is no such requirements to this shift";

                            notificationBuilder.append("Noticed - the shift is illegal!!!\n");

                            notificationBuilder.append("\n------------------------------\n");
                            notificationBuilder.append(String.format("Shift Date: %s\nShift Type: %s\nLegal Status: %s\n", shift.getDate(), shift.getShiftType(), legalStatus));
                            notificationBuilder.append("\nEmployee Requirements:\n");

                            if (shift.getEmployeeRequirements() != null) {
                                // Add a line for each position requirement
                                for (Map.Entry<String, Integer> requirement : shift.getEmployeeRequirements().entrySet()) {
                                    String position = requirement.getKey();
                                    int requiredAmount = requirement.getValue();
                                    notificationBuilder.append(String.format("%s: %d\n", position, requiredAmount));
                                }
                            }
                        }
                        //notification for driver requirements
                        notificationBuilder.append(driverController.getRequirementsByDate(date));

                    }
                } catch (Exception ex) {
                    // Catch the exception and continue the loop
                    // ex.printStackTrace();
                    continue;
                }
            }

        } catch (Exception exp) {
            //exp.printStackTrace();
        }
        return notificationBuilder.toString();
    }


    public Map<String, Object> buildNotificationUI(LocalDate date, LinkedHashMap<String, HashMap<LocalDate, ArrayList<Shift>>> shiftsByDateInAllBranch) throws SQLException {
        Map<String, Object> notificationData = new LinkedHashMap<>();

        try {
            List<Map<String, Object>> branchesData = new ArrayList<>();

            for (String branch : shiftsByDateInAllBranch.keySet()) {
                Map<String, Object> branchData = new LinkedHashMap<>();
                List<Map<String, Object>> shiftsData = new ArrayList<>();

                try {
                    branchData.put("branch", branch);

                    ArrayList<Shift> shiftsForDateInBranch = shiftsByDateInAllBranch.get(branch).get(date);

                    if (shiftsForDateInBranch != null) {
                        for (Shift shift : shiftsForDateInBranch) {
                            Map<String, Object> shiftData = new LinkedHashMap<>();

                            if (shift.getShiftManagerId() == -1) {
                                shiftData.put("managerId", "Not assigned");
                            } else {
                                shiftData.put("managerId", shift.getShiftManagerId());
                            }

                            int isLegal = shift.isLegalShift();
                            shiftData.put("legalStatus", getLegalStatus(isLegal));

                            shiftData.put("date", shift.getDate());
                            shiftData.put("shiftType", shift.getShiftType());
                            shiftData.put("employeeRequirements", shift.getEmployeeRequirements());

                            shiftsData.add(shiftData);
                        }
                        //notification for driver requirements
                        String driverRequirements = driverController.getRequirementsByDate(date);
                        branchData.put("driverRequirements", driverRequirements);
                    }
                } catch (Exception ex) {
                    continue;
                }

                branchData.put("shifts", shiftsData);
                branchesData.add(branchData);
            }

            notificationData.put("branches", branchesData);
        } catch (Exception exp) {
            //exp.printStackTrace();
        }

        return notificationData;
    }

    private String getLegalStatus(int isLegal) {
        if (isLegal == 1) {
            return "Legal";
        } else if (isLegal == 0) {
            return "Illegal";
        } else {
            return "No specific requirements";
        }
    }


    public HashMap<LocalDate, String> getNotifications(LocalDate fromDate, LocalDate toDate) throws SQLException {
        this.notifications = dalShiftService.getNotifications(fromDate.toString(), toDate.toString());
        return notifications;
    }

    public Map<String, Object> getNotificationsUI (LocalDate date) throws SQLException {
        return this.notificationsUI.get(date);
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

    public Map<String, Object> showShiftStatusUI(String branchId, LocalDate date, boolean shiftType) throws SQLException {
        LinkedHashMap<LocalDate, ArrayList<Shift>> branchShifts = lazyLoadFindShifsByBranch(branchId);
        Shift shift = shiftType ? branchShifts.get(date).get(0) : branchShifts.get(date).get(1);
        return shift.showShiftStatusUI(dalShiftService);
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
                for (String position : requirements.keySet()) {
                    dalShiftService.addRequierement(branch, Time.localDateToString(shiftDate), shiftT, position, requirements.get(position));
                }
                shift.addEmployeeRequirements(requirements);
            } else {
                throw new IllegalArgumentException("No shifts available for the given date");
            }
        } else {
            throw new IllegalArgumentException("Invalid branch ID");
        }
    }

    public void addDriverRequirement(LocalDate requiredDate, Driver.LicenseType licenseType, Driver.CoolingLevel coolingLevel) throws SQLException {
        driverController.addDriverRequirement(requiredDate, licenseType, coolingLevel);
    }

    public void addStoreKeeperRequirement(LocalDate requiredDate, String branch) throws Exception {
        if (branchController.getBranch(branch) == null)
            throw new Exception(String.format("Branch %s does not exist", branch));
        HashMap<LocalDate, ArrayList<Shift>> branchShifts = lazyLoadFindShifsByBranch(branch);
        if (branchShifts == null)
            throw new NoSuchFieldException("there is no such branch exist");
        ArrayList<Shift> branchShiftsByDate = branchShifts.get(requiredDate);
        if(branchShiftsByDate == null || branchShiftsByDate.size() < 2)
            throw new Exception("there is no 2 shifts in this date");
            //TODO: if there is no shifts in this date, create 2 new shifts
        String s;
        if(!(branchShiftsByDate.get(0).isThereAnyStoreKeeperReuirement() && branchShiftsByDate.get(1).isThereAnyStoreKeeperReuirement())) {
            for (Shift shift : branchShiftsByDate) {
                LinkedHashMap<String, Integer> storeKeeperRequirment = new LinkedHashMap<>();
                storeKeeperRequirment.put(PositionType.storekeeper.name(), 1);
                shift.addEmployeeRequirements(storeKeeperRequirment);
                if (shift.getShiftType()) {
                    s = "m";
                }
                else
                    s = "e";
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
                    if (!shift.isThereAssignStoreKeeper()) {
                        branchesWithoutStoreKeeper.add(branch);
                        break;
                    }
                }
            }
        }
        return branchesWithoutStoreKeeper;
    }

}
