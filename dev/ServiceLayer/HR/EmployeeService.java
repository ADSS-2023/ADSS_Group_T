package ServiceLayer.HR;

import BusinessLayer.HR.Employee;
import BusinessLayer.HR.EmployeeController;

public class EmployeeService {
    private EmployeeController employeeController;

    public EmployeeService(EmployeeController ec) {
        this.employeeController = ec;
    }

    public String login(int id, String password) {
        Response res = new Response();
        try {
            if (employeeController.login(id, password))
                return "m";
            else return "e";
        } catch (Exception ex) {
        }
        return null;
    }

    public String addRestrictionToEmployee(int id, String date, String type) {
        Response res = new Response();
        try {
            Employee employee = employeeController.getEmployee(id);
            boolean bool = true;
            if (type.equals("e"))
                bool = false;
            employee.addRestriction(date, bool);
        } catch (Exception ex) {
        }
        return null;
    }

    public String addNewEmployee(String employeeName, String bankAccount, String joiningDay, int employeeId, String password, boolean isManger) {
        Response res = new Response();
        try {
            employeeController.addNewEmployee(employeeName, bankAccount, null, joiningDay, employeeId, password, isManger);
        } catch (Exception ex) {
        }
        return null;

    }

    public String addQualification(int id, String quali) {
        Response res = new Response();
        try {
            employeeController.addQualification(id,quali);
        }
        catch (Exception ex) {
        }
        return null;

    }


    public String getListOfSubmittedConstraints(int id) {
        Response res = new Response();
        try {
            return employeeController.getListOfSubmittedConstraints(id);
        } catch (Exception ex) {
        }
        return null;
    }

    public String getListOfAssignedShifts(int id) {
        Response res = new Response();
        try {
            return employeeController.getListOfAssignedShifts(id);
        } catch (Exception ex) {
        }
        return null;
    }

}

