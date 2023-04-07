package BusinessLayer.HR;

import java.util.HashMap;

public class Branch {
    private HashMap<Integer, EmployeeController> employeesController; // map of branche
    private HashMap<Integer, ShiftController> shiftController;

    public Branch() {
        employeesController = new HashMap<Integer, EmployeeController>();
        shiftController = new HashMap<Integer, ShiftController> () ;
        // Create EmployeeController and ShiftController for branch number one
        EmployeeController empCtrl = new EmployeeController();
        ShiftController shiftCtrl = new ShiftController();

        // Add the objects to the respective HashMaps
        employeesController.put(1, empCtrl);
        shiftController.put(1, shiftCtrl);
    }

    public EmployeeController getEmployeeController(int key) {
        return employeesController.get(key);
    }

    public ShiftController getShiftController(int key) {
        return shiftController.get(key);
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
