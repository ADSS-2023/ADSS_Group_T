package ServiceLayer.HR;

import BusinessLayer.HR.*;
import BusinessLayer.HR.User.PositionType;
import BusinessLayer.HR.User.UserType;
import UtilSuper.Time;

import java.time.LocalDate;
import java.util.NoSuchElementException;

import UtilSuper.Response;

public class EmployeeService {
    private EmployeeController employeeController;
    private DriverController driverController;

    private ShiftController shiftController;

    public EmployeeService(EmployeeController ec , DriverController driverController, ShiftController shiftController) {
        this.driverController = driverController;
        this.employeeController = ec;
        this.shiftController = shiftController;
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

    public String submitShiftForEmployee(String branch, int id, LocalDate date, String shifType ) {
        Response res = new Response();
        try {
            boolean sht = true;
            if (shifType.equals("m"))
                sht = true;
            else if (shifType.equals("e")){
                sht = false;
            }
            return shiftController.submitShiftForEmployee( branch,  id , date, sht);
        }
        catch (Exception ex) {
            return ex.getMessage();
        }
    }

    public String assignAll(String branch, LocalDate date, String shiftType ) {
        Response res = new Response();
        try {
            boolean sht = true;
            if (shiftType.equals("m"))
                sht = true;
            else if (shiftType.equals("e")){
                sht = false;
            }
            return shiftController.assignAll( branch, date, sht);

        }
        catch (Exception ex) {
            return ex.getMessage();
        }
    }

    public String assignShiftForEmployee(String branch, int id, LocalDate date, String shiftType, String positionType ) {
        Response res = new Response();
        try {
            boolean sht = true;
            if (shiftType.equals("m"))
                sht = true;
            else if (shiftType.equals("e")){
                sht = false;
            }
            return shiftController.assignEmployeeForShift( branch, id, date, sht, positionType);
        }
        catch (Exception ex) {
            return ex.getMessage();
        }
    }

    public String submitShiftForDriver(LocalDate date, int id) {
        Response res = new Response();
        try {
            return driverController.submitShift( date, id );
        }
        catch (Exception ex) {
            return ex.getMessage();
        }
    }

    public String assignDriverForShift(LocalDate date, int id, int numRequirements ) {
        Response res = new Response();
        try {
           driverController.assignDriver(date, id , numRequirements);
           return "The driver assign successfully";
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

