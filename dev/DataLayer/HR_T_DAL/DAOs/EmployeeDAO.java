package DataLayer.HR_T_DAL.DAOs;

import BusinessLayer.HR.Employee;
import BusinessLayer.HR.User.PositionType;
import BusinessLayer.HR.User.User;
import BusinessLayer.HR.User.UserType;
import DataLayer.HR_T_DAL.DTOs.ConstraintDTO;
import DataLayer.HR_T_DAL.DTOs.EmployeeDTO;
import DataLayer.HR_T_DAL.DTOs.UserDTO;
import DataLayer.Util.DAO;
import DataLayer.Util.DTO;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EmployeeDAO extends DAO {

    public EmployeeDAO(Connection connection) {
        super(connection);
    }

    public EmployeeDTO getEmployeeById(int id) {
        // Retrieve an employee's details from the database by their ID
        return null;
    }

    public List<EmployeeDTO> getAllEmployees() {
        // Retrieve all employees from the database
        return null;
    }

    public List<Employee> getEmployeesByPositionType(PositionType positionType) {
        // Retrieve employees from the database by their position type
        return null;
    }



    public List<ConstraintDTO> getSubmittedShiftsByEmployee(EmployeeDTO employee) {
        // Retrieve all submitted shifts for an employee from the database
        return null;
    }

    public List<ConstraintDTO> getSubmittedShiftsNotAssigneByEmployee(EmployeeDTO employee) {
        // Retrieve all submitted shifts for an employee from the database
        return null;
    }

    public List<ConstraintDTO> getAssignedShiftsByEmployee(EmployeeDTO employee) {
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

    // Find a single record by ID
    public static EmployeeDTO find(Connection connection, String tableName, int id) throws SQLException {
        EmployeeDTO dto = null;
        String sql = "SELECT * FROM " + tableName + " WHERE id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            dto = new EmployeeDTO();
            Field[] fields = dto.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Object value;
                try {
                    value = rs.getObject(field.getName());
                } catch (SQLException e) {
                    throw new SQLException("Error getting value of field " + field.getName() + " from ResultSet", e);
                }
                try {
                    field.set(dto, value);
                } catch (IllegalAccessException e) {
                    throw new SQLException("Error setting value " + value + " of field " + field.getName() + " to DTO " + dto.getClass().getSimpleName(), e);
                }
            }
        }
        return dto;
    }

    // Find all records in a table
    public static List<EmployeeDTO> find_all(Connection connection, String tableName) throws SQLException {
        List<EmployeeDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName;
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            EmployeeDTO dto = new EmployeeDTO();
            Field[] fields = dto.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Object value;
                try {
                    value = rs.getObject(field.getName());
                } catch (SQLException e) {
                    throw new SQLException("Error getting value of field " + field.getName() + " from ResultSet", e);
                }
                try {
                    field.set(dto, value);
                } catch (IllegalAccessException e) {
                    throw new SQLException("Error setting value " + value + " of field " + field.getName() + " to DTO " + dto.getClass().getSimpleName(), e);
                }
            }
            list.add(dto);
        }
        return list;
    }








}
