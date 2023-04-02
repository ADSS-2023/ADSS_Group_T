package BusinessLayer.HR;

import UtilSuper.PositionType;

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


    public HashMap<Integer, Employee> getEmployeesMapper(){
        return employeesMapper;
    }

    public Employee getEmployee(int id){
        return employeesMapper.get(id);
    }
    public boolean login (int empolyeeId, String password){
        Employee emp = employeesMapper.get(empolyeeId);
        if ( emp == null)
            throw new NoSuchElementException("employee Id not exist");
        else
            emp.isManager();
    }

    public void deleteEmployee(int emploeeyId){
        if (! employeesMapper.containsKey(emploeeyId))
            throw new NoSuchElementException("employee Id not exist");
        else
            employeesMapper.remove(emploeeyId);

    }
}
