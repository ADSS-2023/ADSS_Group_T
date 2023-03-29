package ServiceLayer.HR;

import BusinessLayer.HR.Constraint;
import BusinessLayer.HR.Employee;
import BusinessLayer.HR.EmployeeController;
import BusinessLayer.HR.Position;

import java.util.Vector;

public class EmployeeService {
    private EmployeeController ec;

    public EmployeeService(EmployeeController ec){

    }
    public String addNewEmployee(String employeeName, String bankAccount, Vector<Position> qualifedPositions, Vector<Constraint> constraints, String joiningDay,int id , String password){
        Response res = new Response();
        try
        {
            ec.addNewEmployee(employeeName,bankAccount,qualifedPositions,joiningDay,id,password);
            return ("great success");
        }
        catch (Exception ex){
            return ("somthing went wrong");
        }
        return null;
    }
    public String addNewConstraint(int id ,String date ,boolean type, boolean temp){
        Response res = new Response();
        try
        {
            Employee employee = ec.getEmployee(id);
            employee.addConstraint(date,type,temp);
        }
        catch (Exception ex){
        }
        return null;
    }
    public String login(int id , String password){
        Response res = new Response();
        try
        {
            if(ec.login(id,password))
                return "m";
            else return "e";
        }
        catch (Exception ex){
        }
        return null;
    }
    public String getListOfAssignedShift(){
        Response res = new Response();
        try
        {
            Employee employee = ec.getEmployee(id);
            return employee.getListOfAssignedShift();
        }
        catch (Exception ex){
        }
        return null;
    }
    public String addRestrictionToEmployee(int id,String date,String type){
        Response res = new Response();
        try
        {
            Employee employee = ec.getEmployee(id);
            boolean bool = true;
            if(type.equals("e"))
                bool = false;
            return employee.addRestrictionToEmployee(id,date,bool);
        }
        catch (Exception ex){
        }
        return null;

    }



}
