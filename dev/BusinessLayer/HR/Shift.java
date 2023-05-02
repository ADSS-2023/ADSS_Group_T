package BusinessLayer.HR;
import UtilSuper.PositionType;

import java.time.LocalDate;
import java.util.*;

public class Shift {

    private LocalDate date;

    private boolean shiftType;

    private HashMap<String, Integer> employeesRequirement; // HashMap<PositionType, amount> - require employees per shift

    private HashMap<String, HashMap<Employee, Boolean>> submittedPositionByEmployees; // positionType, Empoyee, isAssigned



    private int shiftManagerId;





    public Shift(LocalDate date,  boolean shiftType ) {
        this.date = date;
        this.shiftType = shiftType;
        employeesRequirement = new LinkedHashMap<>();
        submittedPositionByEmployees = new LinkedHashMap<>();
        this.shiftManagerId = -1;
    }


    public void addEmployeeRequirements(HashMap<String, Integer> requirements) {
        for (Map.Entry<String, Integer> entry : requirements.entrySet()) {
            String pos = entry.getKey();
            int amount = entry.getValue();
            if (employeesRequirement.containsKey(pos)) {
                employeesRequirement.put(pos, employeesRequirement.get(pos) + amount);
            } else {
                employeesRequirement.put(pos, amount);
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
        for (Map.Entry<String, Integer> entry : employeesRequirement.entrySet()) {
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
            if (assignedAmount != requiredAmount) {
                int num = requiredAmount - assignedAmount;
                missing += num + " employees are missing in the position of " + position + "\n";
            }
        }
        if (missing.isEmpty())
            return "All the requierments of the shift are fullfillled";
        return missing;
    }



    // TODO-check it
    public String showShiftStatus() {
        String st = "Shift state:\n\n";
        st += String.format("| %-15s | %-8s | %-8s | %-25s | %-25s |\n", "Position", "Assigned", "Required", "Submissions Not Assigned", "Employee IDs Not Assigned");
        st += "|-----------------|----------|----------|-------------------------|-----------------------------|\n";
        for (Map.Entry<String, Integer> entry : employeesRequirement.entrySet()) {
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



    public void makeSureThereIsStorekeeperRequirement() {
      if (!employeesRequirement.containsKey(PositionType.storekeeper.name()) || employeesRequirement.get(PositionType.storekeeper.name()) < 1){
          employeesRequirement.put(PositionType.storekeeper.name(), 1);
    }





    public String assignEmployeeForShift(String pos, Employee emp) throws Exception {
        // Get the map of employees and their assigned status for the given position
        HashMap<Employee, Boolean> employees = submittedPositionByEmployees.get(pos);
        if (employees != null) {
            // Check if the employee has submitted to this position before
            if (!employees.containsKey(emp)) {
                return emp.getEmployeeName() + " has not submitted to position " + pos + " yet.";
            }
            // Check if the employee is already assigned to this position
            Boolean isAssigned = employees.get(emp);
            if (isAssigned != null && isAssigned) {
                return "Employee already assigned to this position";
            } else {
                //TODO- make ths constraint assigned in employee, also had to check if he has alreadty assign to shifth at this day and has already assigne to 6 shifts(in employee)

                // Assign the employee to the position by updating their assigned status in the map
                employees.put(emp, true);
                return emp.getEmployeeName() + " has been assigned to position " + pos + " successfully.";
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
            int requirement = employeesRequirement.get(positionType);
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
                    //TODO- make ths constraint assigned in employee, also had to check if he has alreadty assign to shifth at this day and has already assigne to 6 shifts(in employee)
                    employeeMap.put(employee, true);
                    assignedCount++;
                    if (positionType.equals("shiftManager") && shiftManagerId==-1){
                        shiftManagerId = employee.getId();
                    }
                }
            }

            result.append(String.format("%d employees assigned for position type %s\n", assignedCount, positionType));
        }

        return result.toString();
    }






    public boolean isContainInSumittedPositionByEmployee(String pos, Employee emp) {
        if (submittedPositionByEmployees.containsKey(pos)) {
            return submittedPositionByEmployees.get(pos).contains(emp);
        } else {
            return false;
        }
    }

    public boolean isContainInassignedEmployee(String pos, Employee emp) {
        if (assignedEmployee.containsKey(pos)) {
            return assignedEmployee.get(pos).contains(emp);
        } else {
            return false;
        }
    }

    public String getDate() {
        return date;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }


