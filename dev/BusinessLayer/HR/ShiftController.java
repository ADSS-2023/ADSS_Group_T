package BusinessLayer.HR;

import java.util.HashMap;
import java.util.Vector;

public class ShiftController {
    private HashMap<Integer,Shift> shifts;
    private int shiftId;

    public ShiftController(){
        shifts = new HashMap<Integer,Shift>();
        shiftId = 0;
    }
/*

    public void addSubmittedPosition(Employee emp){
        if (emp.isManager())
    }
*/

  /*  public void assignShiftnyManager{

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
