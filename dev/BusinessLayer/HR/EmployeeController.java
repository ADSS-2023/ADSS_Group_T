package BusinessLayer.HR;

import UtilSuper.PositionType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;


public class EmployeeController {
    private HashMap<Integer,Employee> employeesMapper;
    private PersonnelManager manager;

    public EmployeeController(PersonnelManager manager){
        employeesMapper = new HashMap<Integer,Employee>();
        this.manager =manager;
    }
    public void addNewEmployee(String employeeName, String bankAccount, List<PositionType> qualifedPositions, String joiningDay, int employeeId, String password){
        Employee newEmployee = new Employee(employeeName, bankAccount, qualifedPositions, joiningDay, employeeId, password);
        employeesMapper.putIfAbsent(employeeId, newEmployee);
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




    public HashMap<Integer, Employee> getEmployeesMapper(){
        return employeesMapper;
    }

    public Employee getEmployee(int id){
        return employeesMapper.get(id);
    }
    public boolean login (int empolyeeId, String password){
        Employee emp = employeesMapper.get(empolyeeId);
        if ( emp != null){
            if (emp.getPassword().equals(password))
                if (emp.isManager())
                    return true;
            else
                throw  new IllegalArgumentException("wron password");
        }
        else
            throw  new IllegalArgumentException("wron ID");
        return false;
    }

    public void deleteEmployee(int emploeeyId){
        if (! employeesMapper.containsKey(emploeeyId))
            throw new NoSuchElementException("employee Id not exist");
        else
            employeesMapper.remove(emploeeyId);
    }
}
