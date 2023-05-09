package BusinessLayer.HR;
import BusinessLayer.HR.User.PositionType;

import java.time.LocalDate;
import java.util.*;

public class Shift {

    private LocalDate date;

    String branch;
    private boolean shiftType;

    private HashMap<String, Integer> employeeRequirements; // HashMap<PositionType, amount> - require employees per shift

    private HashMap<String, HashMap<Employee, Boolean>> submittedPositionByEmployees; // positionType, Empoyee, isAssigned


    private int shiftManagerId;



    public Shift(String branch, LocalDate date, boolean shiftType) {
        this.branch = branch;
        this.date = date;
        this.shiftType = shiftType;
        employeeRequirements = new LinkedHashMap<>();
        submittedPositionByEmployees = new LinkedHashMap<>();
        this.shiftManagerId = -1;
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

    public void addEmployeeRequirements(HashMap<String, Integer> requirements) {
        for (Map.Entry<String, Integer> entry : requirements.entrySet()) {
            String pos = entry.getKey();
            int amount = entry.getValue();
            if (employeeRequirements.containsKey(pos)) {
                employeeRequirements.put(pos, employeeRequirements.get(pos) + amount);
            } else {
                employeeRequirements.put(pos, amount);
            }
        }
    }


    public String isLegalShift() {
        boolean hasManager = false;
        String missing = "";
        // Check if the shift contains a manager
        if (shiftManagerId == -1)
            missing += "Noticed- the shift must have a manager!!!" + "\n";
        // Check if all position requirements are fulfilled
        for (Map.Entry<String, Integer> entry : employeeRequirements.entrySet()) {
            String position = entry.getKey();
            int requiredAmount = entry.getValue();
            int assignedAmount = 0;
            HashMap<Employee, Boolean> employeeStatus = submittedPositionByEmployees.get(position);
            if (employeeStatus != null) {
                for (Map.Entry<Employee, Boolean> employeeEntry : employeeStatus.entrySet()) {
                    if (employeeEntry.getValue()) {
                        assignedAmount++;
                    }
                }
            }
            if (assignedAmount != requiredAmount && !position.equals(PositionType.shiftManager.name())) {
                int num = requiredAmount - assignedAmount;
                missing += num + " employees are missing in the position of " + position + "\n";
            }
        }
        if (missing.isEmpty())
            return "All the requierments of the employees in the shift are fullfillled";
        return missing;
    }


    // TODO-check it
    public String showShiftStatus() {
        String st = "Shift state:\n\n";
        st += String.format("| %-15s | %-8s | %-8s | %-25s | %-25s |\n", "Position", "Assigned", "Required", "Submissions Not Assigned", "Employee IDs Not Assigned");
        st += "|-----------------|----------|----------|-------------------------|-----------------------------|\n";
        for (Map.Entry<String, Integer> entry : employeeRequirements.entrySet()) {
            String position = entry.getKey();
            int required = entry.getValue();
            int assigned = 0;
            List<Employee> assignedEmployees = new ArrayList<>();
            for (Map.Entry<String, HashMap<Employee, Boolean>> assignment : submittedPositionByEmployees.entrySet()) {
                String assignmentPosition = assignment.getKey();
                HashMap<Employee, Boolean> employeesToAssign = assignment.getValue();
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
        for (String pos : qualifiedPositions) {
            HashMap<Employee, Boolean> employeesByPosition = submittedPositionByEmployees.get(pos);
            if (employeesByPosition == null) {
                employeesByPosition = new LinkedHashMap<>();
                submittedPositionByEmployees.put(pos, employeesByPosition);
            }
            employeesByPosition.put(emp, false);
        }
        return "Shift submitted successfully";
    }


    public void makeSureThereIsStorekeeperRequirement() throws Exception {
        if (!employeeRequirements.containsKey(PositionType.storekeeper.name()) || employeeRequirements.get(PositionType.storekeeper.name()) < 1) {
            employeeRequirements.put(PositionType.storekeeper.name(), 1);
        }
    }


    public String assignEmployeeForShift(String pos, Employee employee) throws Exception {
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
            } else {
                // Assign the employee to to his list- make sure that he leagal assignes
                employee.assignShift(this.branch, this.date, this.shiftType, PositionType.valueOf(pos));

                // Assign the employee to the position by updating their assigned status in the map
                employees.put(employee, true);
                return employee.getEmployeeName() + " has been assigned to position " + pos + " successfully.";
            }
        } else {
            // The position doesn't exist in the map, throw an exception
            throw new NoSuchElementException("Invalid position");
        }
    }


    public String assignAll() {
        StringBuilder result = new StringBuilder();

        // Iterate over the position types
        for (String positionType : submittedPositionByEmployees.keySet()) {
            HashMap<Employee, Boolean> employeeMap = submittedPositionByEmployees.get(positionType);
            int requirement = employeeRequirements.get(positionType);
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
            int remainingCount = requirement - assignedCount;
            if (remainingCount > 0 && unassignedEmployees.size() > 0) {
                for (int i = 0; i < remainingCount && i < unassignedEmployees.size(); i++) {
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

    // just for tests!!
    public void setSubmittedPositionByEmployees(HashMap<String, HashMap<Employee, Boolean>> submittedPositionByEmployees) {
        this.submittedPositionByEmployees = submittedPositionByEmployees;
    }

    public HashMap<String, Integer> getEmployeeRequirements() {
        return employeeRequirements;
    }
    public String getBranch() {
        return branch;
    }

    public boolean isShiftType() {
        return shiftType;
    }

    public HashMap<String, Integer> getEmployeesRequirement() {
        return employeeRequirements;
    }

    public HashMap<String, HashMap<Employee, Boolean>> getSubmittedPositionByEmployees() {
        return submittedPositionByEmployees;
    }
    public LocalDate getDate() {
        return date;
    }
}


