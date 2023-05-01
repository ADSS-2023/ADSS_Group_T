package BusinessLayer.HR;

import UtilSuper.PositionType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;


public class EmployeeController {
    private HashMap<Integer,Employee> employeesMapper;
    public ShiftController shiftController;


    public EmployeeController(){
        employeesMapper = new HashMap<Integer,Employee>();
    }
    public void addNewEmployee(String employeeName, String bankAccount, List<PositionType> qualifiedPositions, String joiningDay, int employeeId, String password){
        Employee newEmployee = new Employee(employeeName, bankAccount, qualifiedPositions, joiningDay, employeeId, password);
        //TODO: check if the ID is already exist, if so throw exception
        employeesMapper.putIfAbsent(employeeId, newEmployee);
    }

public void initEmployeeConroller (ShiftController shiftController){
        this.shiftController = shiftController;
}


    public void addRestrictionToall(String date, boolean isMorning) {
        for (Employee employee : employeesMapper.values()) {
            if (employee.getShiftsRestriction().containsKey(date)) {
                if (!employee.getShiftsRestriction().get(date).contains(isMorning)) {
                    employee.getShiftsRestriction().get(date).add(isMorning);
                }
            } else {
                List<Boolean> shiftTypes = new ArrayList<>();
                shiftTypes.add(isMorning);
                employee.getShiftsRestriction().put(date, shiftTypes);
            }
        }
    }

    public String getListOfSubmittedConstraints(int Id) {
        return employeesMapper.get(Id).getListOfSubmittedConstraints();
    }

    public String getListOfAssignedShifts(int Id) {
        return employeesMapper.get(Id).getListOfAssignedShifts();
    }

    public void addQualification(int Id,  String position) {
       employeesMapper.get(Id).addQualification(position);
    }


    public HashMap<Integer, Employee> getEmployeesMapper(){
        return employeesMapper;
    }

    public Employee getEmployee(int id){
        return employeesMapper.get(id);
    }

    public boolean login(int employeeId, String password) {
        Employee employee = employeesMapper.get(employeeId);
        if (employee == null) {
            throw new IllegalArgumentException("Employee ID does not exist");
        } else if (!employee.getPassword().equals(password)) {
            throw new IllegalArgumentException("Wrong password");
        } else if (employee.isManager()) {
            return true;
        } else {
            return false;
        }
    }

    public void setEmployeeAsShiftManager(int id) {
        employeesMapper.get(id).setShiftManager(true);
    }
    public void deleteEmployee(int emploeeyId){
        if (! employeesMapper.containsKey(emploeeyId))
            throw new NoSuchElementException("employee Id not exist");
        else
            employeesMapper.remove(emploeeyId);
    }
}
