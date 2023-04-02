package ServiceLayer.HR;

import BusinessLayer.HR.EmployeeController;
import BusinessLayer.HR.Position;
import BusinessLayer.HR.ShiftController;
import UtilSuper.PositionType;


public class Initialization {
    public static void main(String[] args) {
        init_data().begin();
    }
    public static Presentaition init_data(){
        EmployeeController employeeController = new EmployeeController();
        ShiftController shiftController = new ShiftController();
        EmployeeService Emp = new EmployeeService(employeeController);
        MangerService mangerService = new MangerService(shiftController);





        employeeController.addNewEmployee("Israel","123456",{PositionType.general_worker},);




        Presentaition presentaition = new Presentaition(Emp, mangerService);
        return presentaition;
    }
}
