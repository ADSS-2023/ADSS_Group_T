package BusinessLayer.HR;

import UtilSuper.PositionType;
import UtilSuper.UserType;

import java.time.LocalDate;
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
    public void addNewEmployee(int id, String employeeName, String bankAccount, String description, int salary, LocalDate joiningDay, String password, UserType userType) throws Exception {
        // Check if the ID already exists
        if (employeesMapper.containsKey(id)) {
            throw new Exception("Employee ID already exists.");
        }

        // Create and set properties of the new employee object
        Employee newEmployee = new Employee(id, employeeName, bankAccount, description,  salary, joiningDay, password, userType);

        // Add the new employee to the employeesMapper map
        employeesMapper.put(id, newEmployee);
    }


    public void initEmployeeConroller (ShiftController shiftController){
        this.shiftController = shiftController;
}


    public String addRestriction(int id, int branch, LocalDate date, boolean isMorning) {
        return employeesMapper.get(id).addRestriction(branch, date, isMorning);
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
     //   } else if (employee.isManager()) {
      //      return true;
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
