package ServiceLayer.HR;

import BusinessLayer.HR.Branch;
import BusinessLayer.HR.EmployeeController;
import BusinessLayer.HR.Shift;
import BusinessLayer.HR.ShiftController;
import UtilSuper.PositionType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Initialization {
    public static void main(String[] args) {
        init_data();
    }
    public static void init_data(){
        Branch branch = new Branch();
        EmployeeController employeeController = branch.getEmployeesController(1);
        ShiftController shiftController = branch.getShiftController(1);
        employeeController.initEmployeeConroller(shiftController);
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

        HashMap<String, ArrayList<Shift>> shifts = new HashMap<String, ArrayList<Shift>>();
        int shiftId = 0;

        // create shifts for 365 days with morning and evening shifts
        for (int month = 1; month <= 12; month++) {
            int numDaysInMonth = 0;
            if (month == 2) { // February
                numDaysInMonth = 28; // assuming a non-leap year
            } else if (month == 4 || month == 6 || month == 9 || month == 11) { // April, June, September, November
                numDaysInMonth = 30;
            } else { // January, March, May, July, August, October, December
                numDaysInMonth = 31;
            }

            for (int day = 1; day <= numDaysInMonth; day++) {
                String date = String.format("%02d.%02d.2023", day, month); // format date as "DD.MM.YYYY"
                Shift morningShift = new Shift(shiftId, date, true); // morning shift
                Shift eveningShift = new Shift(shiftId, date, false); // evening shift
                ArrayList<Shift> shiftList = new ArrayList<Shift>(2);
                shiftList.add(morningShift);
                shiftList.add(eveningShift);
                shifts.put(date, shiftList);
                shiftId+= 2;
            }
        }

        shiftController.init(shifts, employeeController.getEmployeesMapper());
        Presentaition presentaition = new Presentaition(Emp,shiftService);
        presentaition.begin();
    }

    public static void initShifts() {


    }
}
