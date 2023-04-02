package ServiceLayer.HR;

import BusinessLayer.HR.Constraint;
import BusinessLayer.HR.EmployeeController;

import java.util.Vector;

public class ShifService {
    private EmployeeController ec;
    public ShifService(EmployeeController ec){}
    public String checkProblems(String employeeName, String bankAccount, Vector<Position> qualifedPositions, Vector<Constraint> constraints, String joiningDay, int id , String password){
        Response res = new Response();
        try
        {
            return ("great success");
        }
        catch (Exception ex){
            return ("somthing went wrong");
        }
    }
}
