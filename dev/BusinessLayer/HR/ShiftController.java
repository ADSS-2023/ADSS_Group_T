package BusinessLayer.HR;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class ShiftController {
    private HashMap<Integer,Shift> shifts;
    private int shiftId;

    public ShiftController(){
        shifts = new HashMap<Integer,Shift>();
        shiftId = 0;
    }


  /*  public List<Shift> getShiftsByDateAndShiftType(String date, String shiftType) {
        List<Shift> shifts = new ArrayList<Shift>();
        for (Employee employee : employees) {
            List<Shift> employeeShifts = employee.getAssignedShifts();
            for (Shift shift : employeeShifts) {
                if (shift.getDate().equals(date) && shift.getShiftType().equals(shiftType)) {
                    shifts.add(shift);
                }
            }
        }
        return shifts;
    }*/

    public void CreateShift(int shiftId, int managerID, String date,  String shiftType){
        Shift newShift = new Shift(shiftId, managerID, date, shiftType);
        shifts.put(shiftId,newShift);
        shiftId++;
    }
    public void DeleteShift(int shiftId){
        if(shifts.containsKey(shiftId)){
            shifts.remove(shiftId);
        }
    }

}
