package BusinessLayer.HR;

import UtilSuper.PositionType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShiftController {
    private HashMap < Integer,  HashMap<LocalDate, ArrayList<Shift>> > shifts; // branch, shiftDate, shiftType

    private HashMap<Integer,Employee> employeesMapper;


    public ShiftController(){
        shifts = new HashMap<>();
    }

    public void init( HashMap<Integer,Employee> employeesMapper){
       this.employeesMapper = employeesMapper;
    }





    public String showShiftStatus(int branchId, LocalDate date, boolean shiftType){
        HashMap<LocalDate, ArrayList<Shift>> branchShifts = shifts.get(branchId);
        Shift shift = shiftType ? branchShifts.get(date).get(0) : branchShifts.get(date).get(1);
        return shift.showShiftStatus();
    }








    public void submitShift(int id, String date, boolean shiftType, boolean isTemp) {
        Employee employee =    employeesMapper.get(id);
        employee.addSubmittedShift(date, shiftType, isTemp);
        Shift shift = shiftType ?  shifts.get(date).get(0) : shifts.get(date).get(1);
        shift.addNewSubmittedPositionByEmpoyee( employeesMapper.get(id), isTemp, employee.getListOfQualifiedPositions());

    }

    public String assignEmployeeForShift(int id, String date, boolean shiftType, String positionType) {
        Shift shift = shiftType ?  shifts.get(date).get(0) : shifts.get(date).get(1);
        String st = shift.assignEmployeeForShift(positionType, employeesMapper.get(id));
        return st;
    }

    public String assignAll(int branch LocalDate date, boolean shiftType) {
        Shift shift = shiftType ?  shifts.get(date).get(0) : shifts.get(date).get(1);
        String st = shift.assignAll();
        return st;
    }



    public void addRequirements(int branch, LocalDate shiftDate, boolean shiftType, HashMap<String, Integer> requirements) throws IllegalArgumentException {
        if (shifts.containsKey(branch)) {
            HashMap<LocalDate, ArrayList<Shift>> branchShifts = shifts.get(branch);
            if (branchShifts.containsKey(shiftDate)) {
                ArrayList<Shift> shiftList = branchShifts.get(shiftDate);
                Shift shift = shiftList.get(shiftType ? 0 : 1);
                shift.addEmployeeRequirements(requirements);
            } else {
                throw new IllegalArgumentException("No shifts available for the given date");
            }
        } else {
            throw new IllegalArgumentException("Invalid branch ID");
        }
    }



}
