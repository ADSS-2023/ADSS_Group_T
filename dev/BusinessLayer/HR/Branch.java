package BusinessLayer.HR;

import java.util.HashMap;

public class Branch {
    private HashMap<Integer, EmployeeController> employeesController; // map of branche
    private HashMap<Integer, ShiftController> shiftController;

    public Branch() {
        employeesController = new HashMap<Integer, EmployeeController>();
        shiftController = new HashMap<Integer, ShiftController> () ;
        initBranches();



    }

    public void initBranches() {
        // Create EmployeeController and ShiftController objects
        EmployeeController empCtrl = new EmployeeController();
        ShiftController shiftCtrl = new ShiftController();

        // Add the objects to the respective HashMaps
        employeesController.put(1, empCtrl);
        shiftController.put(1, shiftCtrl);
    }

    public EmployeeController getEmployeesController(int key) {
        return employeesController.get(key);
    }

    public ShiftController getShiftControlle(int key) {
        return shiftController.get(key);
    }

    public void setEmployeesController(HashMap<Integer, EmployeeController> employeesController) {
        this.employeesController = employeesController;
    }



    public void setShiftControlle(HashMap<Integer, ShiftController> shiftControlle) {
        this.shiftController = shiftControlle;
    }
}
