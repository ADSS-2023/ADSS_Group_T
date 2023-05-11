package BusinessLayer.HR;
import BusinessLayer.HR.User.PositionType;
import DataLayer.HR_T_DAL.DalService.DalEmployeeService;
import DataLayer.HR_T_DAL.DalService.DalShiftService;
import UtilSuper.Pair;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class Shift {

    private LocalDate date;

    String branch;
    private boolean shiftType;

    private LinkedHashMap <String, Integer> employeeRequirements; // HashMap<PositionType, amount> - require employees per shift

    private LinkedHashMap<String, LinkedHashMap<Employee, Boolean>> submittedPositionByEmployees; // positionType, Empoyee, isAssigned

    private DalShiftService dalShiftService;
    private int shiftManagerId;



    public Shift(String branch, LocalDate date, boolean shiftType) {
        this.branch = branch;
        this.date = date;
        this.shiftType = shiftType;
        employeeRequirements = new LinkedHashMap<>();
        submittedPositionByEmployees = new LinkedHashMap<>();
        this.shiftManagerId = -1;
    }


    // Todo- init
    public void initShift(DalShiftService dalShiftService) {
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




    public void addEmployeeRequirements(LinkedHashMap<String, Integer> requirements) throws SQLException {
        String s;
        for (Map.Entry<String, Integer> entry : requirements.entrySet()) {
            int amount = entry.getValue();
            String pos = entry.getKey();
            if (employeeRequirements.containsKey(pos)){
                if(shiftType){s = "morning";}
                else s = "evening";
                amount = amount + employeeRequirements.get(pos);
                dalShiftService.updateRequierement(branch, date.toString(),s, pos, amount);
            }
            else
                employeeRequirements.put(pos, amount);
        }
    }

    public void  lazyLoadFindRequiermentsBtDateAndShiftType() throws SQLException {
        employeeRequirements = dalShiftService.findRequiermentsBtDateAndShiftType(branch, date, shiftType);
    }


    public void  lazyLoadFindAllSubmissionByDateShiftType() throws SQLException {
        employeeRequirements = dalShiftService.findAllSubmissionByDateAndShiftType(branch, date, shiftType);
    }

    public String isLegalShift() throws SQLException {
        lazyLoadFindRequiermentsBtDateAndShiftType();
        boolean hasManager = false;
        String missing = "";
        // Check if the shift contains a manager
        if (shiftManagerId == -1)
            missing += "Noticed- the shift must have a manager!!!" + "\n";
        // Check if all position requirements are fulfilled
        for (Map.Entry<String, Integer> requirement: employeeRequirements.entrySet()) {
            String position = requirement.getKey();
            int requiredAmount = requirement.getValue();
            if (requiredAmount>0)
            missing += requiredAmount + " employees are missing in the position of " + position + "\n";
            }
        if (missing.isEmpty())
            return "All the requierments of the employees in the shift are fullfillled";
        return missing;
    }



    public String showShiftStatus() throws SQLException {
        lazyLoadFindRequiermentsBtDateAndShiftType();
        lazyLoadFindAllSubmissionByDateShiftType();
        String st = "Shift state:\n\n";
        st += String.format("| %-15s | %-8s | %-8s | %-25s | %-25s |\n", "Position", "Assigned", "Required", "Submissions Not Assigned", "Employee IDs Not Assigned");
        st += "|-----------------|----------|----------|-------------------------|-----------------------------|\n";
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
                    employeeIdsNotAssigned += employee.getId() + ",";
                }
                if (employeeIdsNotAssigned.length() > 0) {
                    employeeIdsNotAssigned = employeeIdsNotAssigned.substring(0, employeeIdsNotAssigned.length() - 1); // remove last comma
                }
            }
            st += String.format("| %-15s | %-8d | %-8d | %-25d | %-25s |\n", position, assigned, required, submissionsNotAssigned, employeeIdsNotAssigned);
        }
        st += isLegalShift();
        return st;
    }


    public String submitShiftForEmployee(Employee emp, List<String> qualifiedPositions) throws Exception {
        lazyLoadFindAllSubmissionByDateShiftType();
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
            }
        }
        return "Shift submitted successfully";
    }


    public void makeSureThereIsStorekeeperRequirement() throws Exception {
        String s;
        lazyLoadFindRequiermentsBtDateAndShiftType();
        if (!employeeRequirements.containsKey(PositionType.storekeeper.name()) || employeeRequirements.get(PositionType.storekeeper.name()) < 1) {
            employeeRequirements.put(PositionType.storekeeper.name(), 1);
            if(shiftType){s = "morning";}
            else s = "evening";
            dalShiftService.addRequierement(branch, date.toString(), s, PositionType.storekeeper.name(), 1);
        }
    }

    public boolean isThereAnyStoreKeeperReuirement() throws Exception {
        lazyLoadFindRequiermentsBtDateAndShiftType();
        if (!employeeRequirements.containsKey(PositionType.storekeeper.name()) || employeeRequirements.get(PositionType.storekeeper.name()) < 1)
            return false;
        return true;
    }


    public String assignEmployeeForShift(String pos, Employee employee) throws Exception {
        lazyLoadFindAllSubmissionByDateShiftType();
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

            if (!employeeRequirements.containsKey(PositionType.valueOf(pos)))
                throw new NoSuchElementException("Invalid position");

            int amount = employeeRequirements.get(pos);
            if ( amount <1)
                throw new NoSuchElementException("No such requierment");

            else {
                // Assign the employee  to his list- make sure that he leagal assignes
                employee.assignShift(this.branch, this.date, this.shiftType, PositionType.valueOf(pos));

                // Assign the employee to the position by updating their assigned status in the map
                employees.put(employee, true);
                dalShiftService.updateEmployeeToShift(date, shiftType, employee.getId(), pos, true);


                // remove the requierements
                deleteRequirement(pos);
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
        lazyLoadFindAllSubmissionByDateShiftType();
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
                for (int i = 0; i < requirement && i < unassignedEmployees.size(); i++) {
                    Employee employee = unassignedEmployees.get(i);
                    try {
                        employee.assignShift(this.branch, this.date, this.shiftType, PositionType.valueOf(positionType));
                        employeeMap.put(employee, true);
                        assignedCount++;
                        if (positionType.equals("shiftManager") && shiftManagerId == -1) {
                            shiftManagerId = employee.getId();
                        }
                    } catch (Exception exception) {
                        //continue- this employee cannot assign
                    }
                }
            }
            result.append(String.format("%d employees assigned for position type %s\n", assignedCount, positionType));
        }
        return result.toString();
    }

    public boolean deleteRequirement(String positionType) throws SQLException {
        String s;
        if (employeeRequirements.containsKey(positionType)) {
            int amount = employeeRequirements.get(positionType);
            //delete from cache
            if (amount > 1){
                employeeRequirements.put(positionType, amount - 1);
                if(shiftType){s = "morning";}
                else s = "evening";
                dalShiftService.updateRequierement(branch, date.toString(), s, positionType, amount-1);
            }
            else{
                if(shiftType){s = "morning";}
                else s = "evening";
                employeeRequirements.remove(positionType);
                dalShiftService.deleteRequierement(branch, date.toString(), s , positionType);
            }
            return true;
        }
        else {
            return false;
        }
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
        lazyLoadFindAllSubmissionByDateShiftType();
        return submittedPositionByEmployees;
    }
    public LocalDate getDate() {
        return date;
    }
}


