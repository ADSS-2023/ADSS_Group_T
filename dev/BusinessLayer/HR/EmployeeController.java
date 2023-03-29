package BusinessLayer.HR;

import java.util.HashMap;

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
    private PersonnelManager manager;

    public EmployeeController(PersonnelManager manager){
        employeesMapper = new HashMap<Integer,Employee>();
        this.manager =manager;
    }
    public void addNewEmployee(String employeeName,String bankAccount,Vector<Position> qualifedPositions, String joiningDay, int employeeId, String password){
        Employee newEmployee = new Employee(employeeName, bankAccount, qualifedPositions, joiningDay, employeeId, password);
    }


    public HashMap<Integer, Employee> getEmployeesMapper(){
        return employeesMapper;
    }

    public Employee getEmployee(int id){
        return employeesMapper.get(id);
    }
    public void login (int empolyeeId, String password){
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
