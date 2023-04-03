package ServiceLayer.HR;

import BusinessLayer.HR.Employee;
import BusinessLayer.HR.EmployeeController;
import BusinessLayer.HR.ShiftController;
import UtilSuper.PositionType;

import java.util.HashMap;

public class ShiftService {
    public ShiftController shiftController ;
    public ShiftService(ShiftController shiftController){
        this.shiftController = shiftController;
    }
    public String addShiftRequirements(HashMap<String,Integer> howMany , String date , String shiftType){
        Response res = new Response();
        try
        {
            boolean bool = true;
            if (shiftType.equals("e"))
                bool = false;
           shiftController.addRequirements(howMany,date,bool);
        }
        catch (Exception ex){
        }
        return null;
    }

    public String checkProblems(){
        Response res = new Response();
        try
        {
            //shiftController.addRequirements(String date , boolean shiftType ,);
        }
        catch (Exception ex){
        }
        return null;
    }

    public String shiftsHistory(){////////////////לשמור לסוף
        Response res = new Response();
        try
        {
            //shiftController.addRequirements(String date , boolean shiftType ,);
        }
        catch (Exception ex){
        }
        return null;
    }

}
