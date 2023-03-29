package ServiceLayer.HR;

import BusinessLayer.HR.EmployeeController;
import BusinessLayer.HR.ShiftController;

public class Initialization {
    public static void main(String[] args) {
        EmployeeController employeeController = new EmployeeController();
        ShiftController shiftController = new ShiftController();
        EmployeeService Emp = new EmployeeService(employeeController);
        MangerService mangerService = new MangerService(shiftController);
        Presentaition presentaition = new Presentaition(Emp, mangerService);
        presentaition.begin();
    }
}
