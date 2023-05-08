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
import java.util.ArrayList;
import java.util.List;

public class DriverDAO extends DAO {

    public DriverDAO(Connection connection) {
        super(connection);
    }

    public List<DriverDTO> getDriversByLicenseType(Driver.LicenseType licenseType) throws SQLException {
        List<DriverDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM drivers WHERE licenseType = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, licenseType.toString());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                DriverDTO driver = new DriverDTO(
                        resultSet.getInt("driverId"),
                        resultSet.getString("licenseType"),
                        resultSet.getString("coolingLevel")
                );
                driver.setTableName("Driver");
                result.add(driver);
            }
        }
        return result;
    }
}