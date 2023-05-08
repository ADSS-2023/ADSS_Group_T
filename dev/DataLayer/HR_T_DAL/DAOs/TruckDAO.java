package DataLayer.HR_T_DAL.DAOs;

import BusinessLayer.Transport.Truck;
import DataLayer.HR_T_DAL.DTOs.TruckDTO;
import DataLayer.Util.DAO;
import DataLayer.Util.DTO;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.sql.*;
import java.util.*;

public class TruckDAO extends GenericDAO<Truck> {
    private static final String TABLE_NAME = "trucks";

    private static final String CREATE_TABLE_SQL =
            "CREATE TABLE IF NOT EXISTS trucks (\n" +
                    "    id INTEGER PRIMARY KEY,\n" +
                    "    license_number INTEGER NOT NULL,\n" +
                    "    model TEXT NOT NULL,\n" +
                    "    weight INTEGER NOT NULL,\n" +
                    "    max_weight INTEGER NOT NULL,\n" +
                    "    license_type TEXT NOT NULL,\n" +
                    "    cooling_level TEXT NOT NULL\n" +
                    ");";

    private static final String INSERT_SQL =
            "INSERT INTO trucks (license_number, model, weight, max_weight, license_type, cooling_level) " +
                    "VALUES (?, ?, ?, ?, ?, ?);";

    private static final String SELECT_ALL_SQL =
            "SELECT * FROM trucks;";

    private static final String SELECT_BY_ID_SQL =
            "SELECT * FROM trucks WHERE id = ?;";

    private static final String UPDATE_SQL =
            "UPDATE trucks SET license_number = ?, model = ?, weight = ?, max_weight = ?, " +
                    "license_type = ?, cooling_level = ? WHERE id = ?;";

    private static final String DELETE_SQL =
            "DELETE FROM trucks WHERE id = ?;";

    public TruckDAO() throws SQLException {
        super(TABLE_NAME, CREATE_TABLE_SQL, INSERT_SQL, SELECT_ALL_SQL, SELECT_BY_ID_SQL, UPDATE_SQL, DELETE_SQL);
    }

    @Override
    protected void insertParameters(PreparedStatement statement, Truck truck) throws SQLException {
        statement.setInt(1, truck.getLicenseNumber());
        statement.setString(2, truck.getModel());
        statement.setInt(3, truck.getWeight());
        statement.setInt(4, truck.getMaxWeight());
        statement.setString(5, truck.getLicenseType());
        statement.setString(6, truck.getCoolingLevel());
    }

    @Override
    protected void updateParameters(PreparedStatement statement, Truck truck) throws SQLException {
        insertParameters(statement, truck);
        statement.setInt(7, truck.getLicenseNumber());
    }

    @Override
    protected Truck mapResultSetToObject(ResultSet resultSet) throws SQLException {
        Truck truck = new Truck();

        truck.getLicenseNumber(resultSet.getInt("license_number"));
        truck.setModel(resultSet.getString("model"));
        truck.setWeight(resultSet.getInt("weight"));
        truck.setMaxWeight(resultSet.getInt("max_weight"));
        truck.setLicenseType(resultSet.getString("license_type"));
        truck.setCoolingLevel(resultSet.getString("cooling_level"));
        return truck;
    }
}

//
//
//public class TruckDAO extends DAO {
//
//
//
//    // Find a single record by ID
////    public static DTO find(Connection connection, String tableName, int id) throws SQLException {
////        TruckDTO dto = null;
////        String sql = "SELECT * FROM " + tableName + " WHERE id=?";
////        PreparedStatement statement = connection.prepareStatement(sql);
////        statement.setInt(1, id);
////        ResultSet rs = statement.executeQuery();
////        if (rs.next()) {
////            dto = new TruckDTO();
////            Field[] fields = dto.getClass().getDeclaredFields();
////            for (Field field : fields) {
////                field.setAccessible(true);
////                Object value;
////                try {
////                    value = rs.getObject(field.getName());
////                } catch (SQLException e) {
////                    throw new SQLException("Error getting value of field " + field.getName() + " from ResultSet", e);
////                }
////                try {
////                    field.set(dto, value);
////                } catch (IllegalAccessException e) {
////                    throw new SQLException("Error setting value " + value + " of field " + field.getName() + " to DTO " + dto.getClass().getSimpleName(), e);
////                }
////            }
////        }
////        return dto;
////    }
////
////    // Find all records in a table
////    public static List<DTO> find_all(Connection connection, String tableName) throws SQLException {
////        List<DTO> list = new ArrayList<>();
////        String sql = "SELECT * FROM " + tableName;
////        PreparedStatement statement = connection.prepareStatement(sql);
////        ResultSet rs = statement.executeQuery();
////        while (rs.next()) {
////            DTO dto = new DTO();
////            Field[] fields = dto.getClass().getDeclaredFields();
////            for (Field field : fields) {
////                field.setAccessible(true);
////                Object value;
////                try {
////                    value = rs.getObject(field.getName());
////                } catch (SQLException e) {
////                    throw new SQLException("Error getting value of field " + field.getName() + " from ResultSet", e);
////                }
////                try {
////                    field.set(dto, value);
////                } catch (IllegalAccessException e) {
////                    throw new SQLException("Error setting value " + value + " of field " + field.getName() + " to DTO " + dto.getClass().getSimpleName(), e);
////                }
////            }
////            list.add(dto);
////        }
////        return list;
////    }
//}
