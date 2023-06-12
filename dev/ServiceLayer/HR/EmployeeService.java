package ServiceLayer.HR;

import BusinessLayer.HR.*;
import BusinessLayer.HR.User.PositionType;
import BusinessLayer.HR.User.UserType;
import UtilSuper.ResponseSerializer;
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
        Response response = new Response();
        try {
            employeeController.addNewEmployee(id, employeeName, bankAccount, description, salary, Time.stringToLocalDate(joiningDay), password, UserType.employee);
            response.setReturnValue("Employee added");
        } catch (Exception ex) {
            response.setErrorMessage(ex.getMessage());
        }
        return ResponseSerializer.serializeToJson(response);
    }

    public String submitShiftForEmployee(String branch, int id, LocalDate date, String shiftType) {
        Response response = new Response();
        try {
            boolean sht = shiftType.equals("m");
            response.setReturnValue(shiftController.submitShiftForEmployee(branch, id, date, sht));
        } catch (Exception ex) {
            response.setErrorMessage(ex.getMessage());
        }
        return ResponseSerializer.serializeToJson(response);
    }

    public String assignAll(String branch, LocalDate date, String shiftType) {
        Response response = new Response();
        try {
            boolean sht = shiftType.equals("m");
            response.setReturnValue(shiftController.assignAll(branch, date, sht));
        } catch (Exception ex) {
            response.setErrorMessage(ex.getMessage());
        }
        return ResponseSerializer.serializeToJson(response);
    }

    public String assignShiftForEmployee(String branch, int id, LocalDate date, String shiftType, String positionType) {
        Response response = new Response();
        try {
            boolean sht = shiftType.equals("m");
            response.setReturnValue(shiftController.assignEmployeeForShift(branch, id, date, sht, positionType));
        } catch (Exception ex) {
            response.setErrorMessage(ex.getMessage());
        }
        return ResponseSerializer.serializeToJson(response);
    }

    public String submitShiftForDriver(LocalDate date, int id) {
        Response response = new Response();
        try {
            response.setReturnValue(driverController.submitShift(date, id));
        } catch (Exception ex) {
            response.setErrorMessage(ex.getMessage());
        }
        return ResponseSerializer.serializeToJson(response);
    }

    public String assignDriverForShift(LocalDate date, int id) {
        Response response = new Response();
        try {
            driverController.assignDriver(date, id);
            response.setReturnValue("The driver was assigned successfully");
        } catch (Exception ex) {
            response.setErrorMessage(ex.getMessage());
        }
        return ResponseSerializer.serializeToJson(response);
    }


    public String addQualification(int id, String quali) {
        Response response = new Response();
        try {
            employeeController.addQualification(id, quali);
            response.setReturnValue("Qualification added");
        } catch (Exception ex) {
            response.setErrorMessage(ex.getMessage());
        }
        return ResponseSerializer.serializeToJson(response);
    }

    public String addNewDriver(int id, String employeeName, String bankAccount, String description, int salary, String joiningDay, String password, String licenseType, int coolingLevel) {
        Response response = new Response();
        try {
            driverController.addDriver(id, employeeName, bankAccount, description, salary, joiningDay, password, UserType.employee, licenseType, coolingLevel);
            response.setReturnValue("Driver added");
        } catch (Exception ex) {
            response.setErrorMessage(ex.getMessage());
        }
        return ResponseSerializer.serializeToJson(response);
    }

    public String getListOfSubmittion(int id) {
        Response response = new Response();
        try {
            response.setReturnValue(employeeController.getEmployeeById(id).showShiftsStatusByEmployee(LocalDate.now()));
        } catch (Exception ex) {
            response.setErrorMessage(ex.getMessage());
        }
        return ResponseSerializer.serializeToJson(response);
    }
}

