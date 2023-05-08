package DataLayer.HR_T_DAL.DAOs;

import BusinessLayer.HR.Driver;
import DataLayer.HR_T_DAL.DTOs.DriverDTO;
import DataLayer.HR_T_DAL.DTOs.EmployeeDTO;
import DataLayer.Util.DAO;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DriverDAO extends DAO {

    public DriverDAO(Connection connection) {
        super(connection);
    }

    public DriverDTO getDriverById(int id) {
        // Retrieve a driver's details from the database by their ID
        return null;
    }

    public List<DriverDTO> getAllDrivers() {
        // Retrieve all drivers from the database
        return null;
    }

    public List<DriverDTO> getDriversByLicenseType(Driver.LicenseType licenseType) {
        // Retrieve all drivers from the database with a specific license type
        return null;
    }

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



}