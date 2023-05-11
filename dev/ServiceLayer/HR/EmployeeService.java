package ServiceLayer.HR;

import BusinessLayer.HR.EmployeeController;
import UtilSuper.Response;

public class EmployeeService {
    private EmployeeController employeeController;

    public EmployeeService(EmployeeController ec) {
        this.employeeController = ec;
    }


//    public String addRestrictionToEmployee( int id, String branchId, String date, String type) {
//        Response res = new Response();
//        try {
//            Employee employee = employeeController.getEmployee(id);
//            boolean bool = true;
//            if (type.equals("e"))
//                bool = false;
//            employeeController.addRestriction(id, branchId,  date, bool);
//        } catch (Exception ex) {
//        }
//        return null;
//    }

//    public String addNewEmployee(int id, String employeeName, String bankAccount, String description, int salary, String joiningDay, String password) {
//        Response res = new Response();
//        try {
//            employeeController.addNewEmployee(id, employeeName, bankAccount, description, salary, joiningDay, password, UserType.employee );
//        } catch (Exception ex) {
//        }
//        return null;
//    }
   ;

//    public String addNewDriver(int id, String employeeName, String bankAccount, String description, int salary, String joiningDay, String password, String  licenseType, int coolingLevel) {
//        Response res = new Response();
//        try {
//            employeeController.addNewEmployee(id, employeeName, bankAccount, description, salary, joiningDay, password, UserType.employee, licenseType, coolingLevel );
//        } catch (Exception ex) {
//        }
//        return null;
//    }




//    public String updateEmployeeDetails(int id, String employeeName, String bankAccount, String description, int salary, String joiningDay, String password) {
//        Response res = new Response();
//        try {
//            employeeController.addNewEmployee(id, employeeName, bankAccount, description, salary, joiningDay, password, UserType.employee, licenseType, coolingLevel );
//        } catch (Exception ex) {
//        }
//        return null;
//    }

//    public String updateDriverDetails(String bankAccount, String description, int salary,  String password, String  licenseType, int coolingLevel) {
//        Response res = new Response();
//        try {
//            employeeController.addNewEmployee(id, employeeName, bankAccount, description, salary, joiningDay, password, UserType.employee, licenseType, coolingLevel );
//        } catch (Exception ex) {
//        }
//        return null;
//    }



    public String addQualification(int id, String quali) {
        Response res = new Response();
        try {
            employeeController.addQualification(id,quali);
        }
        catch (Exception ex) {
        }
        return null;

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

