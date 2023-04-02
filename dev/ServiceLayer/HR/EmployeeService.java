package ServiceLayer.HR;

import BusinessLayer.HR.Constraint;
import BusinessLayer.HR.Employee;
import BusinessLayer.HR.EmployeeController;
import UtilSuper.PositionType;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class EmployeeService {
    private EmployeeController ec;

    public EmployeeService(EmployeeController ec) {
        this.ec = ec;
    }

    public String addNewConstraint(int id, String date, boolean type, boolean temp) {
        Response res = new Response();
        try {
            Employee employee = ec.getEmployee(id);
            employee.addSubmittedShift(date, type, temp);
        } catch (Exception ex) {
        }
        return null;
    }

    public String login(int id, String password) {
        Response res = new Response();
        try {
            if (ec.login(id, password))
                return "m";
            else return "e";
        } catch (Exception ex) {
        }
        return null;
    }

    public String getListOfAssignedShift(int id) {
        Response res = new Response();
        try {
            Employee employee = ec.getEmployee(id);
            return employee.getListOfSubmittedConstraints();
        } catch (Exception ex) {
        }
        return null;
    }

    public String addRestrictionToEmployee(int id, String date, String type) {
        Response res = new Response();
        try {
            Employee employee = ec.getEmployee(id);
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
            ec.addNewEmployee(employeeName, bankAccount, null, joiningDay, employeeId, password, isManger);
        } catch (Exception ex) {
        }
        return null;

    }

    public String addQualification(int id, String quali) {
        Response res = new Response();
        try {
            Employee employee = ec.getEmployee(id);

        } catch (Exception ex) {
        }
        return null;

    }
}

