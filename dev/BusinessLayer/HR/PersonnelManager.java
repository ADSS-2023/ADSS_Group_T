package BusinessLayer.HR;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonnelManager extends  Employee {
    private List<Employee> employees;
    private HashMap<String, List<String>> shiftsDemand;

    public PersonnelManager() {
        this.employees = new ArrayList<Employee>();
        this.shiftsDemand = new HashMap<String, List<String>>();
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public List<Employee> getEmployees(){
        return employees;
    }

    public void addShiftDemand(String date, String shiftType) {
        shiftsDemand.computeIfAbsent(date, k -> new ArrayList<String>()).add(shiftType);
    }

    public HashMap<String, List<String>> getShiftsDemand(){
        return shiftsDemand;
    }

    public void setShiftsDemand(HashMap<String, List<String>> shiftsDemand){
        this.shiftsDemand = shiftsDemand;
    }

    public boolean isLegalPlacement(int numOfShiftWorkers){
        return employees.size() >= numOfShiftWorkers;
    }

    public List<Employee> getQualifiedEmployees(String date, String shiftType){
        List<Employee> qualifiedEmployees = new ArrayList<Employee>();
        for (Employee employee : employees) {
            if (employee.getQualifiedPositions().contains(shiftType)) {
                if (!isEmployeeAssigned(employee, date, shiftType) && !isEmployeeInShift(date, shiftType, employee)){
                    qualifiedEmployees.add(employee);
                }
            }
        }
        return qualifiedEmployees;
    }

    public boolean isEmployeeInShift(String date, String shiftType, Employee employee){
        List<Shift> shifts = employee.getShifts();
        for (Shift shift : shifts) {
            if (shift.getDate().equals(date) && shift.getShiftType().equals(shiftType)) {
                return true;
            }
        }
        return false;
    }

    public boolean isEmployeeAssigned(Employee employee, String date, String shiftType){
        List<Shift> shifts = getShiftsByDateAndShiftType(date, shiftType);
        for (Shift shift : shifts) {
            for (Employee assignedEmployee : shift.getEmployees()) {
                if (assignedEmployee == employee) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<Shift> getShiftsByDateAndShiftType
