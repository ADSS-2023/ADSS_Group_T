package DataLayer.HR_T_DAL.DAOs;

import BusinessLayer.HR.Employee;
import BusinessLayer.HR.User.PositionType;
import BusinessLayer.HR.User.UserType;
import DataLayer.HR_T_DAL.DTOs.ConstraintByEmployeeDTO;
import DataLayer.HR_T_DAL.DTOs.EmployeeDTO;
import DataLayer.Util.DAO;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class EmployeeDAO extends DAO {

    public EmployeeDAO(Connection connection) {
        super(connection);
    }

    public List<Employee> getEmployeesByPositionType(PositionType positionType) {
        // Retrieve employees from the database by their position type
        return null;
    }

    public List<ConstraintByEmployeeDTO> getSubmittedShiftsByEmployee(EmployeeDTO employee) {
        // Retrieve all submitted shifts for an employee from the database
        return null;
    }

    public List<ConstraintByEmployeeDTO> getSubmittedShiftsNotAssigneByEmployee(EmployeeDTO employee) {
        // Retrieve all submitted shifts for an employee from the database
        return null;
    }

    public List<ConstraintByEmployeeDTO> getAssignedShiftsByEmployee(EmployeeDTO employee) {
        // Retrieve all assigned shifts for an employee from the database
        return null;
    }

    public void addSubmittedShift(EmployeeDTO employee, String branch, LocalDate date, boolean shiftType) {
        // Add a submitted shift for an employee to the database
    }

    public void assignShift(EmployeeDTO employee, String branch, LocalDate date, boolean shiftType, PositionType positionType) {
        // Assign a shift for an employee in the database
    }

    public void addShiftsRestriction(EmployeeDTO employee, String branch, LocalDate date, boolean shiftType) {
        // Add a restriction on a shift for an employee in the database
    }

    public Map<LocalDate, List<Boolean>> getShiftsRestrictionByEmployeeAndBranch(EmployeeDTO employee, String branch) {
        // Retrieve all shift restrictions for an employee and a branch from the database
        return null;
    }

    public List<EmployeeDTO> getEmployeesByUserType(UserType userType) {
        // Retrieve employees from the database by their user type
        return null;
    }
}
