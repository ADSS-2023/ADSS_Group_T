package ServiceLayer.HR;

import BusinessLayer.HR.Driver;
import BusinessLayer.HR.DriverController;
import BusinessLayer.HR.Employee;
import BusinessLayer.HR.EmployeeController;
import BusinessLayer.HR.User.UserType;
import UtilSuper.Time;

import java.time.LocalDate;
import UtilSuper.Response;

public class EmployeeService {
    private EmployeeController employeeController;
    private DriverController driverController;

    public EmployeeService(EmployeeController ec , DriverController driverController) {
        this.driverController = driverController;
        this.employeeController = ec;
    }

    public String addNewEmployee(int id, String employeeName, String bankAccount, String description, int salary, String joiningDay, String password) {
      Response res = new Response();
       try {
           employeeController.addNewEmployee(id, employeeName, bankAccount, description, salary, Time.stringToLocalDate(joiningDay), password, UserType.employee );
           return "employee added";
       }
       catch (Exception ex) {
           return ex.getMessage();
      }
   }

    public String addQualification(int id, String quali) {
        Response res = new Response();
        try {
            employeeController.addQualification(id,quali);
            return "Qualification added";
        }
        catch (Exception ex) {
            return ex.getMessage();
        }
    }

    public String addNewDriver(int id, String employeeName, String bankAccount, String description, int salary, String joiningDay, String password, String  licenseType, int coolingLevel) {
        Response res = new Response();
        try {

            driverController.addDriver(id, employeeName, bankAccount, description, salary, joiningDay, password, UserType.employee, licenseType, coolingLevel);
            return "driver added";
        }
        catch (Exception ex) {
                return ex.getMessage();
        }
    }

//    public String getListOfSubmittion(int id) {
//        Response res = new Response();
//        try {
//            return employeeController.getListOfSubmitttions(id);
//        } catch (Exception ex) {
//        }
//        return null;
//    }



}

