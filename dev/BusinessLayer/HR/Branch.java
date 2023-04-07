package BusinessLayer.HR;

import java.util.HashMap;

public class Branch {
    private HashMap<Integer, EmployeeController> employeesController; // map of branche
    private HashMap<Integer, ShiftController> shiftController;

    public Branch() {
        employeesController = new HashMap<Integer, EmployeeController>();
        shiftController = new HashMap<Integer, ShiftController> () ;
    }

    public HashMap<Integer, EmployeeController> getEmployeesController() {
        return employeesController;
    }

    public void setEmployeesController(HashMap<Integer, EmployeeController> employeesController) {
        this.employeesController = employeesController;
    }

    public HashMap<Integer, ShiftController> getShiftControlle() {
        return shiftController;
    }

    public void setShiftControlle(HashMap<Integer, ShiftController> shiftControlle) {
        this.shiftController = shiftControlle;
    }
}
