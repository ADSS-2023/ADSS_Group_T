package BusinessLayer.HR;

import java.util.HashMap;
import java.util.Vector;

public class ConstraintController {
    private HashMap<Integer,  Vector<Constraint>> constraintMapper;
    private Calendar cal;
    private EmployeeController employees;
    public ConstraintController(Calendar cal, EmployeeController employees){
        constraintMapper = new HashMap<Integer,  Vector<Constraint>> ();
        this.cal = cal;
        this.employees = employees;
    }

    public void submitShift(int employeeId, int shiftType, String date, String pos) {
        cal.getschedule().get(date).get(shiftType).submittedPosition(pos, employees.getEmployeesMapper().get(employeeId));
    }




}
