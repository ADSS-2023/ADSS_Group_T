package BusinessLayer.HR;
import BusinessLayer.HR.User.PositionType;
import DataLayer.HR_T_DAL.DalService.DalShiftService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class Shift {

    private LocalDate date;

    private String branch;
    private boolean shiftType;

    private LinkedHashMap <String, Integer> employeeRequirements; // HashMap<PositionType, amount> - require employees per shift

    private LinkedHashMap<String, LinkedHashMap<Employee, Boolean>> submittedPositionByEmployees; // positionType, Empoyee, isAssigned

    private DalShiftService dalShiftService;
    private int shiftManagerId;



    public Shift(String branch, LocalDate date, boolean shiftType, LinkedHashMap <String, Integer>  shiftRequirements,
    LinkedHashMap<String, LinkedHashMap<Employee, Boolean>>  submittedPositionByEmployees, int shiftManagerId, DalShiftService dalShiftService ) {
        this.branch = branch;
        this.date = date;
        this.shiftType = shiftType;
        this.employeeRequirements = shiftRequirements;
        this.submittedPositionByEmployees = submittedPositionByEmployees;
        this.shiftManagerId = shiftManagerId;
        this.dalShiftService = dalShiftService;
    }





    public boolean getShiftType() {
        return shiftType;
    }

    public int getShiftManagerId() {
        return shiftManagerId;
    }

    public void setShiftManagerId(int shiftManagerId) {
        this.shiftManagerId = shiftManagerId;
    }


//dalShiftService.updateRequierement(branch, date.toString(), s, positionType, amount, amount - 1);

    public void addEmployeeRequirements(LinkedHashMap<String, Integer> requirements) throws SQLException {
        if (requirements == null || requirements.isEmpty()) {
            throw new IllegalArgumentException("Requirements cannot be null or empty");
        }

        for (Map.Entry<String, Integer> entry : requirements.entrySet()) {
            String position = entry.getKey();
            Integer toAddAmount = entry.getValue();
            String shiftTypeStr = shiftType ? "m" : "e";
            lazyLoadFindRequiermentsBtDateAndShiftType();

            if (employeeRequirements == null){
                employeeRequirements = new LinkedHashMap <String, Integer>();
            }
            if (employeeRequirements.containsKey(position)) {
                int oldAmount = employeeRequirements.get(position);
                dalShiftService.updateRequierement(branch, date.toString(), shiftTypeStr, position, oldAmount, oldAmount + toAddAmount);
            } else {
                employeeRequirements.put(position, toAddAmount);
                dalShiftService.addRequierement(branch, date.toString(), shiftTypeStr, position, toAddAmount);
            }
        }
    }





    public void  lazyLoadFindRequiermentsBtDateAndShiftType() throws SQLException {
        String s ;
        if(shiftType) s = "m";
        else s = "e";
        employeeRequirements = dalShiftService.findRequiermentsByDateAndShiftType(branch, date, s);
    }


    public void lazyLoadFindAllsubmittedPositionByEmployees() throws SQLException {
        String s ;
        if(shiftType) s = "m";
        else s = "e";
        submittedPositionByEmployees = dalShiftService.findAllSubmissionByBranchDateAndShiftType(branch, date, s);
    }


    public int isLegalShift() throws SQLException {
        lazyLoadFindRequiermentsBtDateAndShiftType();
        boolean hasManager = (shiftManagerId != -1);
        boolean allPositionsFilled = true;
        if (employeeRequirements == null)
            return -1;
        // Check if all position requirements are fulfilled
        for (Map.Entry<String, Integer> requirement : employeeRequirements.entrySet()) {
                int requiredAmount = requirement.getValue();
                if (requiredAmount > 0) {
                    allPositionsFilled = false;
                    break;
                }
            }
            if (hasManager && allPositionsFilled)
                return 1;
            else
                return 0;

        }





    public String showShiftStatus(DalShiftService dalShiftService) throws SQLException {
        lazyLoadFindRequiermentsBtDateAndShiftType();
        lazyLoadFindAllsubmittedPositionByEmployees();
        String st = "Shift state:\n\n";
        st += String.format("| %-15s | %-8s | %-8s | %-25s | %-25s |\n", "Position", "Assigned", "Required", "Submissions Not Assigned", "Employee IDs Not Assigned");
        st += "|-----------------|----------|----------|---------------------------|---------------------------|\n";
        boolean isLegalShift = true;
        StringBuilder missing = new StringBuilder();
        for (Map.Entry<String, Integer> entry : employeeRequirements.entrySet()) {
            String position = entry.getKey();
            int required = entry.getValue();
            int assigned = 0;
            List<Employee> assignedEmployees = new ArrayList<>();
            for (Map.Entry<String, LinkedHashMap<Employee, Boolean>> assignment : submittedPositionByEmployees.entrySet()) {
                String assignmentPosition = assignment.getKey();
                LinkedHashMap<Employee, Boolean> employeesToAssign = assignment.getValue();
                for (Map.Entry<Employee, Boolean> employeeAssignment : employeesToAssign.entrySet()) {
                    Employee employee = employeeAssignment.getKey();
                    boolean isAssigned = employeeAssignment.getValue();
                    if (isAssigned && position.equals(assignmentPosition)) {
                        assignedEmployees.add(employee);
                        assigned++;
                    }
                }
            }
            int submissionsNotAssigned = 0;
            String employeeIdsNotAssigned = "";
            if (submittedPositionByEmployees.containsKey(position)) {
                List<Employee> unassignedSubmissions = new ArrayList<>();
                HashMap<Employee, Boolean> employeesToAssign = submittedPositionByEmployees.get(position);
                for (Map.Entry<Employee, Boolean> employeeAssignment : employeesToAssign.entrySet()) {
                    Employee employee = employeeAssignment.getKey();
                    boolean isAssigned = employeeAssignment.getValue();
                    if (!isAssigned) {
                        unassignedSubmissions.add(employee);
                    }
                }
                submissionsNotAssigned = unassignedSubmissions.size();
                for (Employee employee : unassignedSubmissions) {
                    if (employee.getQualifiedPositions().contains(PositionType.shiftManager.name()))
                        employeeIdsNotAssigned += employee.getId() + "(SM)" + ",";
                    else
                        employeeIdsNotAssigned += employee.getId()  + ",";

                }
                if (employeeIdsNotAssigned.length() > 0) {
                    employeeIdsNotAssigned = employeeIdsNotAssigned.substring(0, employeeIdsNotAssigned.length() - 1); // remove last comma
                }
            }
            st += String.format("| %-15s | %-8d | %-8d | %-25d | %-25s |\n", position, assigned, required, submissionsNotAssigned, employeeIdsNotAssigned);
            if (required > assigned) {
                isLegalShift = false;
                missing.append(required).append(" employees are missing in the position of ").append(position).append("\n");
            }
        }
        if (this.shiftManagerId == -1) {
            isLegalShift = false;
            missing.append("Noticed!! no such shift manager assign- the shift must have a manager!\n");
        }
        if (isLegalShift) {
            st += "All the requirements of the employees in the shift are fulfilled";
        } else {
            st += missing.toString();
        }
        return st;
    }





    public Map<String, Object> showShiftStatusUI(DalShiftService dalShiftService) throws SQLException {
        lazyLoadFindRequiermentsBtDateAndShiftType();
        lazyLoadFindAllsubmittedPositionByEmployees();

        Map<String, Object> shiftStatusData = new LinkedHashMap<>();
        List<Map<String, Object>> positionDataList = new ArrayList<>();

        boolean isLegalShift = true;
        StringBuilder missingRequirements = new StringBuilder();

        for (Map.Entry<String, Integer> entry : employeeRequirements.entrySet()) {
            String position = entry.getKey();
            int required = entry.getValue();
            int assigned = 0;
            List<Map<String, Object>> assignedEmployees = new ArrayList<>();

            for (Map.Entry<String, LinkedHashMap<Employee, Boolean>> assignment : submittedPositionByEmployees.entrySet()) {
                String assignmentPosition = assignment.getKey();
                LinkedHashMap<Employee, Boolean> employeesToAssign = assignment.getValue();

                for (Map.Entry<Employee, Boolean> employeeAssignment : employeesToAssign.entrySet()) {
                    Employee employee = employeeAssignment.getKey();
                    boolean isAssigned = employeeAssignment.getValue();

                    if (isAssigned && position.equals(assignmentPosition)) {
                        Map<String, Object> assignedEmployeeData = new LinkedHashMap<>();
                        assignedEmployeeData.put("employeeId", employee.getId());
                        assignedEmployeeData.put("qualifiedPositions", employee.getQualifiedPositions());
                        assignedEmployees.add(assignedEmployeeData);
                        assigned++;
                    }
                }
            }

            int submissionsNotAssigned = 0;
            List<Map<String, Object>> unassignedSubmissions = new ArrayList<>();

            if (submittedPositionByEmployees.containsKey(position)) {
                LinkedHashMap<Employee, Boolean> employeesToAssign = submittedPositionByEmployees.get(position);

                for (Map.Entry<Employee, Boolean> employeeAssignment : employeesToAssign.entrySet()) {
                    Employee employee = employeeAssignment.getKey();
                    boolean isAssigned = employeeAssignment.getValue();

                    if (!isAssigned) {
                        Map<String, Object> unassignedEmployeeData = new LinkedHashMap<>();
                        unassignedEmployeeData.put("employeeId", employee.getId());
                        unassignedEmployeeData.put("qualifiedPositions", employee.getQualifiedPositions());
                        unassignedSubmissions.add(unassignedEmployeeData);
                        submissionsNotAssigned++;
                    }
                }
            }

            Map<String, Object> positionData = new LinkedHashMap<>();
            positionData.put("position", position);
            positionData.put("assigned", assigned);
            positionData.put("required", required);
            positionData.put("assignedEmployees", assignedEmployees);
            positionData.put("submissionsNotAssigned", submissionsNotAssigned);
            positionData.put("unassignedSubmissions", unassignedSubmissions);
            positionDataList.add(positionData);

            if (required > assigned) {
                isLegalShift = false;
                missingRequirements.append(position).append(", ");
            }
        }

        shiftStatusData.put("isLegalShift", isLegalShift);
        shiftStatusData.put("positions", positionDataList);

        if (!isLegalShift) {
            shiftStatusData.put("missingRequirements", missingRequirements.toString());
        }

        return shiftStatusData;
    }



    public String submitShiftForEmployee(Employee emp, List<String> qualifiedPositions) throws Exception {
        lazyLoadFindAllsubmittedPositionByEmployees();
        for (String pos : qualifiedPositions) {
            LinkedHashMap<Employee, Boolean> employeesByPosition = submittedPositionByEmployees.get(pos);
            if (employeesByPosition == null) {
                employeesByPosition = new LinkedHashMap<>();
                submittedPositionByEmployees.put(pos, employeesByPosition);
                dalShiftService.addEmployeeToShift(branch, date, shiftType, emp.getId(), pos, false);
            }
            else if (employeesByPosition.get(emp) == null){
                employeesByPosition.put(emp, false);
                dalShiftService.addEmployeeToShift(branch, date, shiftType, emp.getId(), pos, false);
                return "employee added successfully";
            }
        }
        return "Shift submitted successfully";
    }


    public void makeSureThereIsStorekeeperRequirement() throws Exception {
        String s;
        lazyLoadFindRequiermentsBtDateAndShiftType();
        if (!employeeRequirements.containsKey(PositionType.storekeeper.name()) || employeeRequirements.get(PositionType.storekeeper.name()) < 1) {
            employeeRequirements.put(PositionType.storekeeper.name(), 1);
            if(shiftType){s = "m";}
            else s = "e";
            dalShiftService.addRequierement(branch, date.toString(), s, PositionType.storekeeper.name(), 1);
        }
    }

    public boolean isThereAnyStoreKeeperReuirement() throws Exception {
        lazyLoadFindRequiermentsBtDateAndShiftType();
        return employeeRequirements != null && employeeRequirements.containsKey(PositionType.storekeeper.name()) && employeeRequirements.get(PositionType.storekeeper.name()) >= 1;
    }

    public boolean isThereAssignStoreKeeper() throws Exception {
        LinkedHashMap<Employee, Boolean> employeeSubmitToStoreKeeper = submittedPositionByEmployees.get(PositionType.storekeeper.name());
        if (employeeSubmitToStoreKeeper == null)
            return false;
        for (Employee employee : employeeSubmitToStoreKeeper.keySet()) {
            // Check if the position is assigned (true)
            if (employeeSubmitToStoreKeeper.get(employee))
                return true;
        }
        return false;
    }
    public String assignEmployeeForShift(String pos, Employee employee) throws Exception {
        lazyLoadFindAllsubmittedPositionByEmployees();
        lazyLoadFindRequiermentsBtDateAndShiftType();
        // Get the map of employees and their assigned status for the given position
        HashMap<Employee, Boolean> employees = submittedPositionByEmployees.get(pos);
        if (! (employees == null)) {
            // Check if the employee has submitted to this position before
            if (!employees.containsKey(employee)) {
                return employee.getEmployeeName() + " has not submitted to position " + pos + " yet.";
            }
            // Check if the employee is already assigned to this position
            Boolean isAssigned = employees.get(employee);
            if (isAssigned != null && isAssigned) {
                return "Employee already assigned to this position";
            }

            if (!employeeRequirements.containsKey(PositionType.valueOf(pos).name()))
                throw new NoSuchElementException("Invalid position");

            int amount = employeeRequirements.get(pos);
            if ( amount <1)
                throw new NoSuchElementException("No such requierment");

            else {
                // Assign the employee  to his list- make sure that he leagal assignes
                employee.assignShift(this.branch, this.date, this.shiftType, PositionType.valueOf(pos));

                // Assign the employee to the position by updating their assigned status in the map
                dalShiftService.updateEmployeeToShift(date, shiftType, employee.getId(), pos, true);
                employees.put(employee, true);

                //check if he qualified to be ShiftManager and ther noy yet a shift manger assign
                if (shiftManagerId == -1 && employee.getQualifiedPositions().contains(PositionType.shiftManager.name())){
                    dalShiftService.updateShift(date, shiftType, employee.getId(), this.branch);
                    shiftManagerId = employee.getId();
                }

                // remove the requierements
                deleteRequirement(pos, amount);
                return employee.getEmployeeName() + " has been assigned to position " + pos + " successfully.";
            }
        }
        else {
            // The position doesn't exist in the map, throw an exception
            throw new NoSuchElementException("Invalid position");
        }
    }


    public String assignAll() throws SQLException {
        StringBuilder result = new StringBuilder();
        lazyLoadFindAllsubmittedPositionByEmployees();
        lazyLoadFindRequiermentsBtDateAndShiftType();
        // Iterate over the position types
        for (String positionType : submittedPositionByEmployees.keySet()) {
            HashMap<Employee, Boolean> employeeMap = submittedPositionByEmployees.get(positionType);
            if (!employeeRequirements.containsKey(positionType))
                continue;
            int requirement = employeeRequirements.get(positionType);
            if (requirement == 0)
                continue;
            int assignedCount = 0;
            // Filter out employees who have already been assigned
            List<Employee> unassignedEmployees = new ArrayList<>();
            for (Employee employee : employeeMap.keySet()) {
                if (!employeeMap.get(employee)) {
                    unassignedEmployees.add(employee);
                } else {
                    assignedCount++;
                }
            }
            // Assign remaining employees to shifts
            if (requirement > 0 && unassignedEmployees.size() > 0) {
                for (int i = 0; requirement>0 && i < unassignedEmployees.size(); i++) {
                    Employee employee = unassignedEmployees.get(i);
                    try {
                        assignEmployeeForShift(positionType, employee);
                        requirement--;
                        //employee.assignShift(this.branch, this.date, this.shiftType, PositionType.valueOf(positionType));
                        //employeeMap.put(employee, true);
                        assignedCount++;
                    } catch (Exception exception) {
                        int k = 1;
                    }
                }
            }
            result.append(String.format("%d employees assigned for position type %s\n", assignedCount, positionType));
        }
        return result.toString();
    }

    public boolean deleteRequirement(String positionType, int amount) throws SQLException {
        String s;
        if (shiftType) {
            s = "m";
        } else {
            s = "e";
        }
        dalShiftService.updateRequierement(branch, date.toString(), s, positionType, amount, amount - 1);
        employeeRequirements.replace(positionType, amount, amount - 1);
        return true;

    }




    // just for tests!!


    public HashMap<String, Integer> getEmployeeRequirements() {
        return employeeRequirements;
    }
    public String getBranch() {
        return branch;
    }

    public boolean isShiftType() {
        return shiftType;
    }

    public LinkedHashMap<String, LinkedHashMap<Employee, Boolean>> getSubmittedPositionByEmployees() throws SQLException {
        lazyLoadFindAllsubmittedPositionByEmployees();
        return submittedPositionByEmployees;
    }
    public LocalDate getDate() {
        return date;
    }
}


