package BusinessLayer.HR;

import BusinessLayer.HR.User.UserType;
import DataLayer.HR_T_DAL.DalService.DalEmployeeService;
import DataLayer.HR_T_DAL.DalService.DalUserService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;


public class EmployeeController {
    private LinkedHashMap<Integer,Employee> employeesMapper;
    public ShiftController shiftController;
    public DalEmployeeService dalEmployeeService;
    public DalUserService dalUserService;



    public EmployeeController(DalEmployeeService dalEmployeeService , DalUserService dalUserService){
        employeesMapper = new LinkedHashMap<Integer,Employee>();
        this.dalEmployeeService = dalEmployeeService;
        this.dalUserService = dalUserService;
    }
    public void addNewEmployee(int id, String employeeName, String bankAccount, String description, int salary, LocalDate joiningDay, String password, UserType userType) throws Exception {
        // Check if the ID already exists
        if (!employeesMapper.containsKey(id)) {
            Employee employee = dalEmployeeService.findEmployeeById(id);
            if (employee != null){
                employeesMapper.put(id, employee);
                throw new Exception("Employee ID already exists.");
            }
        }

        // Create and set properties of the new employee object
        Employee newEmployee = new Employee(id, employeeName, bankAccount, description,  salary, joiningDay, password, userType);
        dalUserService.addNewEmployee(newEmployee);

        // Add the new employee to the employeesMapper map
        employeesMapper.put(id, newEmployee);
    }

    public void initEmployeeConroller (ShiftController shiftController, DalEmployeeService dalEmployeeService, DalUserService dalUserService){
        this.dalUserService = dalUserService;
        this.dalEmployeeService = dalEmployeeService;
        this.shiftController = shiftController;
}


    public void addQualification(int id,  String position) throws SQLException {
        Employee employee = employeesMapper.get(id);
        Employee employee2 =  null;
        if (employee == null){
            employee2 = dalEmployeeService.findEmployeeById(id);
            if(employee2 != null){
                employeesMapper.put(id, employee2);
                dalEmployeeService.addQualification(id,position);
            }
            else throw new IllegalArgumentException("there is no such employee with this id");
        }
    }

    public LinkedHashMap<Integer, Employee> getEmployeesMapper(){
        return employeesMapper;
    }

    public Employee getEmployeeById(int id) throws SQLException {
        Employee employee = employeesMapper.get(id);
        if(employee == null){
            employee = dalEmployeeService.findEmployeeById(id);
            if (employee == null) throw new SQLException("no employee with that id found");
        }
        return employee;
    }
}
