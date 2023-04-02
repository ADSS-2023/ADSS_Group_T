package ServiceLayer.HR;

import BusinessLayer.HR.Employee;
import BusinessLayer.HR.EmployeeController;
import BusinessLayer.HR.ShiftController;

public class ShiftService {
    public ShiftController shiftController ;
    public ShiftService(ShiftController shiftController){
        this.shiftController = shiftController;
    }
    public String addRequirements(){
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
