package BusinessLayer.HR;

import java.util.*;

public class Shift {
    private int shiftId;
    private String date;
    private int managerID;
    private String shiftType;
    private HashMap<String, Integer> employeesRequirement; // HashMap<PositionType, amount> - require employees per shift
    private HashMap<String, List<Employee>> fulfillPositionByEmployees; // HashMap<PositionType, EmployeesToFullfill> - the Employees that assign to the shifts by manager
    private HashMap<String, List<Employee>> submittedPositionByEmployees;



    public Shift(int shiftId, int managerID, String date,  String shiftType) {
        this.shiftId = shiftId;
        this.date = date;
        this.shiftType = shiftType;
        this.managerID = managerID;
        employeesRequirement = createNewEmployeesRequirement();
        fulfillPositionByEmployees = createNewFulfillPositionByEmployees();
        submittedPositionByEmployees = createNewSubmittedPositionByEmployees();
    }

    public HashMap<String, Integer> createNewEmployeesRequirement() {
        HashMap<String, Integer> employeeRequirements = new HashMap<>();
        for (Position.PositionType positionType : Position.PositionType.values()) {
            Position position = new Position(positionType);
            employeeRequirements.put(positionType.name(), 0); // set initial requirement to 0
        }
        return employeeRequirements;
    }

    public HashMap<String, List<Employee>> createNewFulfillPositionByEmployees() {
        HashMap<String, List<Employee>> fulfillPositionByEmployees = new HashMap<>();
        for (Position.PositionType positionType : Position.PositionType.values()) {
            fulfillPositionByEmployees.put(positionType.name(), new Vector<>()); // create an empty list for each position
        }
        return fulfillPositionByEmployees;
    }

    public HashMap<String, List<Employee>> createNewSubmittedPositionByEmployees() {
        HashMap<String, List<Employee>> submittedPositionByEmployees = new HashMap<>();
        for (Position.PositionType positionType : Position.PositionType.values()) {
            Position position = new Position(positionType);
            submittedPositionByEmployees.put(positionType.name(), new Vector<>()); // create an empty list for each position
        }
        return submittedPositionByEmployees;
    }

    public void addEmployeeRequirement(String pos, int amount) {
        if (employeesRequirement.containsKey(pos)) {
            employeesRequirement.put(pos, employeesRequirement.get(pos) + amount);
        } else {// is needed???????
            employeesRequirement.put(pos, amount);
        }
    }


    public void submittedPosition(String pos, Employee emp) {
        if (!fulfillPositionByEmployees.containsKey(pos)) {
            throw  new IllegalArgumentException("there is no such position requierment");
        }
        else{
            submittedPositionByEmployees.get(pos).add(emp);
        }
    }

    public void assignEmployee(Position pos, Employee emp) {
        if (!fulfillPositionByEmployees.containsKey(pos)) {
            throw  new IllegalArgumentException("there is no position exist");
        }
        else{
            fulfillPositionByEmployees.get(pos).add(emp);
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

    public String getShiftType() {
        return shiftType;
    }

    public HashMap<String, Integer> getEmployeesRequirement() {
        return employeesRequirement;
    }

    public HashMap<String, List<Employee>> getFulfillPositionByEmployees() {
        return fulfillPositionByEmployees;
    }

    public HashMap<String, List<Employee>> getSubmittedPositionByEmployees() {
        return submittedPositionByEmployees;
    }
}
