package BusinessLayer.HR;
import UtilSuper.PositionType;

import java.util.*;

public class Shift {
    private int shiftId;
    private String date;
    private int managerID;
    private boolean shiftType;
    private boolean isApproved;
    private HashMap<String, Integer> employeesRequirement; // HashMap<PositionType, amount> - require employees per shift
    private HashMap<String, List<Employee>> assignedEmployee; // HashMap<PositionType, EmployeesToFullfill> - the Employees that assign to the shifts by manager
    private HashMap<String, List<Employee>> submittedPositionByEmployees;



    public Shift(int shiftId, int managerID, String date,  boolean shiftType ) {
        this.shiftId = shiftId;
        this.date = date;
        this.shiftType = shiftType;
        employeesRequirement = createNewEmployeesRequirement();
        assignedEmployee = createNewFulfillPositionByEmployees();
        submittedPositionByEmployees = createNewSubmittedPositionByEmployees();
        isApproved = false;
        this.managerID = -1;
    }

    public Shift(int shiftId,  String date,  boolean shiftType) {
        this.shiftId = shiftId;
        this.date = date;
        this.shiftType = shiftType;
        employeesRequirement = createNewEmployeesRequirement();
        assignedEmployee = createNewFulfillPositionByEmployees();
        submittedPositionByEmployees= createNewSubmittedPositionByEmployees();
        this.managerID = -1;
        isApproved = false;
    }


    public String  shiftHistory() {
      String st = showShiftStatus();
      if (isApproved)
          return st + "\n The shift was approved. ";
      else
          return st + "\n The shift has not yet been confirmed. " ;
    }



    public HashMap<String, Integer> createNewEmployeesRequirement() {
        HashMap<String, Integer> employeeRequirements = new HashMap<>();
        for (PositionType positionType : PositionType.values()) {
            employeeRequirements.put(positionType.name(), 0); // set initial requirement to 0
        }
        return employeeRequirements;
    }

    public HashMap<String, List<Employee>> createNewFulfillPositionByEmployees() {
        HashMap<String, List<Employee>> fulfillPositionByEmployees = new HashMap<>();
        for (PositionType positionType : PositionType.values()) {
            fulfillPositionByEmployees.put(positionType.name(), new ArrayList<>()); // create an empty list for each position
        }
        return fulfillPositionByEmployees;
    }

    public HashMap<String, List<Employee>> createNewSubmittedPositionByEmployees() {
        HashMap<String, List<Employee>> submittedPositionByEmployees = new HashMap<>();
        for (PositionType positionType : PositionType.values()) {
            submittedPositionByEmployees.put(positionType.name(), new ArrayList<>()); // create an empty list for each position
        }
        return submittedPositionByEmployees;
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
        // Check if all position requirements are fulfilled
        for (Map.Entry<String, Integer> entry : employeesRequirement.entrySet()) {
            String position = entry.getKey();
            int requiredAmount = entry.getValue();
            int assignedAmount = assignedEmployee.get(position).size();
            if (assignedAmount != requiredAmount) {
                int num = requiredAmount - assignedAmount;
                missing += num + " employees are missing in the position of " + position + "\n";
            }
            // Check if the shift contains a manager
            if (managerID != -1)
                hasManager = true;
        }
        if (!hasManager)
            missing += "noticed- the shift must has a manager" + "\n";
        if (missing.isEmpty())
            return "0";
        return missing;
    }

    /*public String ShowShiftStatus() {
        String st = "Shift state :\n";
        st = st + ShowCurrentShiftState();
        st = st +"\n";
        st = st + showCurrentSubmitionNotAssigned();
        return  st;
    }*/

    public String ShowCurrentShiftState() {
        String st = "The current status of the shift: \n";
        for (Map.Entry<String, Integer> entry : employeesRequirement.entrySet()) {
            String position = entry.getKey();
            int required = entry.getValue();
            int assigned = 0;
            List<Employee> asssignedEmployees = assignedEmployee.get(position);
            if (asssignedEmployees != null)
                assigned = asssignedEmployees.size();
            st += position + ": " + assigned + "/" + required + "\n";
        }
        return st;
    }

    public String showCurrentSubmitionNotAssigned() {
        String st = "\n Submission to shift - needs to be assigned: \n";
        for (Map.Entry<String, List<Employee>> entry : submittedPositionByEmployees.entrySet()) {
            String position = entry.getKey();
            List<Employee> employees = entry.getValue();
            if (employees != null) { // Check if any position submitted
                st = st + employees.size() + " Shifts submitted to: " + position + "\n";
                for (Employee employee : employees){
                    st = st + "Name" + employee.getEmployeeName() + "    ID: " +  employee.getId() + "\n"; // Add employee name and ID to string
                }
            }
        }
        return st;
    }

    public String showShiftStatus() {
        String st = "Shift state:\n\n";
        st += String.format("| %-15s | %-8s | %-8s | %-25s | %-25s |\n", "Position", "Assigned", "Required", "Submissions Not Assigned", "Employee IDs Not Assigned");
        st += "|-----------------|----------|----------|-------------------------|-----------------------------|\n";
        for (Map.Entry<String, Integer> entry : employeesRequirement.entrySet()) {
            String position = entry.getKey();
            int required = entry.getValue();
            int assigned = 0;
            List<Employee> assignedEmployees = assignedEmployee.get(position);
            if (assignedEmployees != null) {
                assigned = assignedEmployees.size();
            }
            int submissionsNotAssigned = 0;
            List<Employee> unassignedSubmissions = submittedPositionByEmployees.get(position);
            String employeeIdsNotAssigned = "";
            if (unassignedSubmissions != null) {
                submissionsNotAssigned = unassignedSubmissions.size();
                for (Employee employee : unassignedSubmissions) {
                    employeeIdsNotAssigned += employee.getId() + ",";
                }
                if (employeeIdsNotAssigned.length() > 0)
                    employeeIdsNotAssigned = employeeIdsNotAssigned.substring(0, employeeIdsNotAssigned.length() - 1); // remove last comma
            }
            st += String.format("| %-15s | %-8d | %-8d | %-25d | %-25s |\n", position, assigned, required, submissionsNotAssigned, employeeIdsNotAssigned);
        }
        return st;
    }




    public void addNewSubmittedPositionByEmpoyee(Employee emp, boolean isTemp, List<String> qualigiedPosition) {
            for (String pos : qualigiedPosition){
                submittedPositionByEmployees.get(pos).add(emp);
            }
        }



    public String assignEmployeeForShift(String pos, Employee emp) {
        if (isContainInassignedEmployee(pos, emp)) {
            return emp.getEmployeeName() + " is already assigned to position " + pos;
        } else if (!isContainInSumittedPositionByEmployee(pos, emp)) {
            return emp.getEmployeeName() + " did not submit for position " + pos;
        } else {
            assignedEmployee.computeIfAbsent(pos, k -> new ArrayList<>()).add(emp);
            submittedPositionByEmployees.get(pos).remove(emp);
            emp.addSAssignShifts(date, shiftType, pos);
            return emp.getEmployeeName() + " has been assigned to position " + pos + " successfully.";
        }
    }



    public String assignAll() {
        StringBuilder assignedEmployees = new StringBuilder(); // to store assigned employees

        for (Map.Entry<String, Integer> entry : employeesRequirement.entrySet()) {
            String position = entry.getKey();
            int required = entry.getValue();
            List<Employee> assigned = assignedEmployee.get(position);

            if (assigned == null && required > 0 || assigned.size() < required) {
                List<Employee> submitted = submittedPositionByEmployees.get(position);

                if (submitted != null) {
                    int i = 0;

                    // assign as many employees as needed
                    while (assigned.size() < required && i < submitted.size()) {
                        Employee emp = submitted.get(i);
                        try {
                            assignedEmployees.append(assignEmployeeForShift(position, emp) + "\n");
                        } catch (IllegalArgumentException e) {
                            // handle exception by printing error message and moving to the next employee
                        }
                        i++;
                    }
                }
            }
        }
        return "Employee Assigned: \n" + assignedEmployees.toString() + "\n" + showShiftStatus(); // return assigned employees as a string
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

    public HashMap<String, Integer> getEmployeesRequirement() {
        return employeesRequirement;
    }

    public HashMap<String, List<Employee>> getAssignedEmployee() {
        return assignedEmployee;
    }

    public HashMap<String, List<Employee>> getSubmittedPositionByEmployees() {
        return submittedPositionByEmployees;
    }
}
