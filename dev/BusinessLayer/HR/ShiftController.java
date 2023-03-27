package BusinessLayer.HR;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class ShiftController {
    private HashMap<Integer,Shift> shifts;
    private int shiftsCounter;

    public ShiftController(){
        shifts = new HashMap<Integer,Shift>();
        shiftsCounter = 0;
    }

    public void CreateShift(String date, String shiftType, Vector<Employee> employeesList ,Vector<Position> positionlist,int managerId){
        Shift newShift = new Shift(shiftsCounter,date,managerId,shiftType,employeesList,positionlist);
        shifts.put(shiftsCounter,newShift);
        shiftsCounter++;
    }
    public void DeleteShift(int shiftId){
        if(shifts.containsKey(shiftId)){
            shifts.remove(shiftId);
        }
    }

}
