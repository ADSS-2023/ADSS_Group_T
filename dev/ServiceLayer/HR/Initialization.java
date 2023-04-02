package ServiceLayer.HR;

import BusinessLayer.HR.EmployeeController;
import BusinessLayer.HR.ShiftController;
import UtilSuper.PositionType;

import java.util.ArrayList;
import java.util.List;


public class Initialization {
    public static void main(String[] args) {
        init_data();
    }
    public static void init_data(){
        EmployeeController employeeController = new EmployeeController();
        ShiftController shiftController = new ShiftController();
        EmployeeService Emp = new EmployeeService(employeeController);
        ShiftService shiftService = new ShiftService(shiftController);


        List<PositionType> pt1 = new ArrayList<PositionType>();
        pt1.add(PositionType.general_worker);pt1.add(PositionType.cashier);
        employeeController.addNewEmployee("Worker1","123456",pt1,"11.03.2023",1 , "1",true);

        List<PositionType> pt2 = new ArrayList<PositionType>();
        pt1.add(PositionType.general_worker);pt1.add(PositionType.shift_manager);
        employeeController.addNewEmployee("Worker2","123456",pt2,"11.01.2021",2 , "2",false);

        List<PositionType> pt3 = new ArrayList<PositionType>();
        pt1.add(PositionType.general_worker);pt1.add(PositionType.storekeeper);
        employeeController.addNewEmployee("Worker3","123456",pt3,"12.02.2022",3 , "3",false);



        Presentaition presentaition = new Presentaition(Emp,shiftService);
        presentaition.begin();
    }
}
