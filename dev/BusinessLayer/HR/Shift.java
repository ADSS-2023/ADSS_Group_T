package BusinessLayer.HR;
import UtilSuper.PositionType;

import java.util.*;

public class Shift {
    private int shiftId;
    private String date;
    private int managerID;
    private boolean shiftType;
    private HashMap<String, Integer> employeesRequirement; // HashMap<PositionType, amount> - require employees per shift
    private HashMap<String, List<Employee>> assignedEmployee; // HashMap<PositionType, EmployeesToFullfill> - the Employees that assign to the shifts by manager
    private HashMap<String, List<Employee>> submittedPositionByEmployeesUpdated;
    private HashMap<String, List<Employee>> submittedPositionByEmployeesAll;


    public Shift(int shiftId, int managerID, String date,  boolean shiftType) {
        this.shiftId = shiftId;
        this.date = date;
        this.shiftType = shiftType;
        this.managerID = managerID;
        employeesRequirement = createNewEmployeesRequirement();
        assignedEmployee = createNewFulfillPositionByEmployees();
        submittedPositionByEmployeesUpdated = createNewSubmittedPositionByEmployees();
        submittedPositionByEmployeesAll = createNewSubmittedPositionByEmployees();
    }

    public Shift(int shiftId,  String date,  boolean shiftType) {
        this.shiftId = shiftId;
        this.date = date;
        this.shiftType = shiftType;
        this.managerID = managerID;
        employeesRequirement = createNewEmployeesRequirement();
        assignedEmployee = createNewFulfillPositionByEmployees();
        submittedPositionByEmployeesUpdated = createNewSubmittedPositionByEmployees();
        submittedPositionByEmployeesAll = createNewSubmittedPositionByEmployees();
        managerID = -1;
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
            if (position.equals("shift_manager") && assignedAmount > 0)
                hasManager = true;
        }
        if (!hasManager)
            missing += "noticed- the shift must has a manager" + "\n";
        if (missing.isEmpty())
            return "0";
        return missing;
    }

    public String shiftState() {
        String st = "Shift state :\n";
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





    public void submittedPosition(String pos, Employee emp) {
        if (!assignedEmployee.containsKey(pos)) {
            throw  new IllegalArgumentException("there is no such position requierment");
        }
        else{
            submittedPositionByEmployeesUpdated.get(pos).add(emp);
            s
        }
    }


    public void assignEmployee(PositionType pos, Employee emp) {
        if (!assignedEmployee.containsKey(pos)) {
            throw  new IllegalArgumentException("there is no position exist");
        }
        else{
            assignedEmployee.get(pos).add(emp);
        }
    }

    public int getShiftId() {
        return shiftId;
    }

    public String getDate() {
        return date;
    }

    public int getManagerID() {
        return managerID;
    }

    public boolean getShiftType() {
        return shiftType;
    }

    public HashMap<String, Integer> getEmployeesRequirement() {
        return employeesRequirement;
    }

    public HashMap<String, List<Employee>> getFulfillPositionByEmployees() {
        return assignedEmployee;
    }

    public HashMap<String, List<Employee>> getSubmittedPositionByEmployees() {
        return submittedPositionByEmployees;
    }
}
