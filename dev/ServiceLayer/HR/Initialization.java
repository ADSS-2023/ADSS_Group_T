package ServiceLayer.HR;

import BusinessLayer.HR.EmployeeController;
import BusinessLayer.HR.PersonnelManager;
import BusinessLayer.HR.ShiftController;
import UtilSuper.PositionType;

import java.util.ArrayList;
import java.util.List;


public class Initialization {
    public static void main(String[] args) {
        init_data().begin();
    }
    public static Presentaition init_data(){

        PersonnelManager personnelManager = new PersonnelManager();
        EmployeeController employeeController = new EmployeeController(personnelManager);
        ShiftController shiftController = new ShiftController();
        EmployeeService Emp = new EmployeeService(employeeController);
        MangerService mangerService = new MangerService(shiftController);


        List<PositionType> pt1 = new ArrayList<PositionType>();
        pt1.add(PositionType.general_worker);pt1.add(PositionType.cashier);
        employeeController.addNewEmployee("Worker1","123456",pt1,"11.03.2023",318888443 , "123456");

        List<PositionType> pt2 = new ArrayList<PositionType>();
        pt1.add(PositionType.general_worker);pt1.add(PositionType.shift_manager);
        employeeController.addNewEmployee("Worker2","123456",pt1,"11.01.2021",318888443 , "123456");

        List<PositionType> pt3 = new ArrayList<PositionType>();
        pt1.add(PositionType.general_worker);pt1.add(PositionType.storekeeper);
        employeeController.addNewEmployee("Worker3","123456",pt1,"12.02.2022",318888443 , "123456");



        Presentaition presentaition = new Presentaition(Emp, mangerService);
        return presentaition;
    }
}
