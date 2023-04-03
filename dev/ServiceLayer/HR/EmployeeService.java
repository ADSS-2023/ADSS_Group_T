package ServiceLayer.HR;

import BusinessLayer.HR.Employee;
import BusinessLayer.HR.EmployeeController;

public class EmployeeService {
    private EmployeeController employeeController;

    public EmployeeService(EmployeeController ec) {
        this.employeeController = ec;
    }

    public String addNewConstraint(int id, String date, String type, String temp) {
        Response res = new Response();
        try {
            boolean boolType = true;
            if (type.equals("e"))
                boolType = false;
            boolean boolTemp = true;
            if (type.equals("p"))
                boolTemp = false;
            Employee employee = employeeController.getEmployee(id);
            employee.addSubmittedShift(date, boolType , boolTemp);
        } catch (Exception ex) {
        }
        return null;
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

    public String getAssignedShifts(int id) {
        Response res = new Response();
        try {
            Employee employee = employeeController.getEmployee(id);
            return employee.getAssignedShifts().toString();
        } catch (Exception ex) {
        }
        return null;
    }

}

