package DataLayer.HR_T_DAL.DAOs;

import BusinessLayer.HR.Driver;
import DataLayer.HR_T_DAL.DTOs.DriverDTO;
import DataLayer.HR_T_DAL.DTOs.DriverRequirementDTO;
import DataLayer.Util.DAO;
import UtilSuper.Pair;

import java.lang.reflect.Field;
import java.sql.*;
import java.time.LocalDate;

import java.util.LinkedHashMap;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.LinkedList;
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
    public Pair<DriverDTO, Boolean> getDriverAndIfIsAssigned(int id, String date) throws SQLException {
        Pair<DriverDTO, Boolean> result = new Pair<>(null,null);
        String sql = "SELECT d.driverId, d.licenseType, d.coolingLevel, dt.isAssigned FROM drivers d INNER JOIN DateToDriver dt ON d.driverId = dt.driverId WHERE d.driverId = ? AND dt.date = ? AND dt.isAssigned = 'true'";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.setString(2, date);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                DriverDTO driver = new DriverDTO(
                        resultSet.getInt("driverId"),
                        resultSet.getString("licenseType"),
                        resultSet.getString("coolingLevel")
                );
                driver.setTableName("Driver");
                result.setFirst(driver);
                result.setSecond(true);
            }
        }
        return result;
    }



//    public List<DriverRequirementDTO> findRequirementsByDate(LocalDate date) throws SQLException {
//        List<DriverRequirementDTO> results = new ArrayList<>();
//        String sql = "SELECT * FROM DriverRequirements WHERE date = ?";
//        try (PreparedStatement statement = connection.prepareStatement(sql)) {
//            statement.setObject(1, date);
//            ResultSet resultSet = statement.executeQuery();
//
//            while (resultSet.next()) {
//                DriverRequirementDTO result = new DriverRequirementDTO(
//                        resultSet.getString("date"),
//                        resultSet.getString("licenseType"),
//                        resultSet.getString("coolingLevel"),
//                        resultSet.getInt("coolingLevel")
//                );
//                result.setTableName("DriverRequirements");
//                ResultSetMetaData metaData = resultSet.getMetaData();
//                int columnCount = metaData.getColumnCount();
//                for (int i = 1; i <= columnCount; i++) {
//                    String columnName = metaData.getColumnName(i);
//                    Object value = resultSet.getObject(i);
//                    Field field = result.getClass().getDeclaredField(columnName);
//                    field.setAccessible(true);
//                    field.set(result, value);
//                }
//
//                results.add(result);
//            }
//        } catch (ReflectiveOperationException e) {
//            throw new SQLException("Error creating DTO instance", e);
//        }
//        return results;
//    }







    public LinkedList<DriverDTO> findAllSubmissionByDate(String date) throws SQLException {
        LinkedList<DriverDTO> result = new LinkedList<DriverDTO>();
        String sql = "SELECT d.driverId, d.licenseType, d.coolingLevel, dt.isAssigned FROM drivers d INNER JOIN DateToDriver dt ON d.driverId = dt.driverId WHERE dt.date = ? AND dt.isAssigned = 'true'";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(2, date);
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




















//    public List<DriverDTO> getDriversByDate(String date) throws SQLException {
//        List<DriverDTO> drivers = new ArrayList<>();
//        String sql = "SELECT d.driverId, d.licenseType, d.coolingLevel FROM drivers d INNER JOIN DateToDriver dt ON d.driverId = dt.driverId WHERE dt.date = ?";
//
//        try (PreparedStatement statement = connection.prepareStatement(sql)) {
//            statement.setString(1, date);
//            ResultSet resultSet = statement.executeQuery();
//
//            while (resultSet.next()) {
//                DriverDTO driver = new DriverDTO(
//                        resultSet.getInt("driverId"),
//                        resultSet.getString("licenseType"),
//                        resultSet.getString("coolingLevel")
//                );
//                driver.setTableName("Driver");
//                drivers.add(driver);
//            }
//        }
//        return drivers;
//    }



}