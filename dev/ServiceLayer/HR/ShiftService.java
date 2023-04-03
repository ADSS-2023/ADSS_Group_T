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
            return ex.getMessage();
        }
        return "succeed";
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

    public String approveShift(String date , String shiftType){
        Response res = new Response();
        try
        {
            boolean bool = true;
            if (shiftType.equals("e"))
                bool = false;
            return shiftController.approveShift(date,bool);
        }
        catch (Exception ex){
        }
        return null;
    }
    public String submittedConstraints(int id){
        Response res = new Response();
        try
        {

        }
        catch (Exception ex){
        }
        return null;
    }

    public String addShift(int id, String date, String type, String temp,String position) {
        Response res = new Response();
        try {
            boolean boolType = true;
            if (type.equals("e"))
                boolType = false;
            boolean boolTemp = true;
            if (temp.equals("p"))
                boolTemp = false;
            shiftController.submitShift(id,date,boolType,boolTemp,position);
        } catch (Exception ex) {
        }
        return null;
    }


}
