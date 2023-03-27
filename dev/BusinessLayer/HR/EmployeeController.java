package BusinessLayer.HR;

import java.util.HashMap;
import java.util.Vector;

public class EmployeeController {
    private HashMap<Integer,Employee> employeesMapper;
    private int empolyeeCounter;
    public EmployeeController(){
        employeesMapper = new HashMap<Integer,Employee>();
        empolyeeCounter = 0;
    }
    public void addNewEmployee(String employeeName,String bankAccount,Vector<Position> qualifedPositions,Vector<Constraint> constraints,String joiningDay){
        Employee newEmployee = new Employee(empolyeeCounter,employeeName,bankAccount,qualifedPositions,joiningDay);
        empolyeeCounter++;
    }
    public void deleteEmployee(int emploeeyId){
        if (employeesMapper.containsKey(emploeeyId)){
            employeesMapper.remove(emploeeyId);
        }
    }
}
