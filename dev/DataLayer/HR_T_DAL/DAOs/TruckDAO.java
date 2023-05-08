package DataLayer.HR_T_DAL.DAOs;

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
public class TruckDAO extends DAO {
    public TruckDAO(Connection connection) {
        super(connection);
    }
// Find a single record by ID
//    public static DTO find(Connection connection, String tableName, int id) throws SQLException {
//        TruckDTO dto = null;
//        String sql = "SELECT * FROM " + tableName + " WHERE id=?";
//        PreparedStatement statement = connection.prepareStatement(sql);
//        statement.setInt(1, id);
//        ResultSet rs = statement.executeQuery();
//        if (rs.next()) {
//            dto = new TruckDTO();
//            Field[] fields = dto.getClass().getDeclaredFields();
//            for (Field field : fields) {
//                field.setAccessible(true);
//                Object value;
//                try {
//                    value = rs.getObject(field.getName());
//                } catch (SQLException e) {
//                    throw new SQLException("Error getting value of field " + field.getName() + " from ResultSet", e);
//                }
//                try {
//                    field.set(dto, value);
//                } catch (IllegalAccessException e) {
//                    throw new SQLException("Error setting value " + value + " of field " + field.getName() + " to DTO " + dto.getClass().getSimpleName(), e);
//                }
//            }
//        }
//        return dto;
//    }
//
//    // Find all records in a table
//    public static List<DTO> find_all(Connection connection, String tableName) throws SQLException {
//        List<DTO> list = new ArrayList<>();
//        String sql = "SELECT * FROM " + tableName;
//        PreparedStatement statement = connection.prepareStatement(sql);
//        ResultSet rs = statement.executeQuery();
//        while (rs.next()) {
//            DTO dto = new DTO();
//            Field[] fields = dto.getClass().getDeclaredFields();
//            for (Field field : fields) {
//                field.setAccessible(true);
//                Object value;
//                try {
//                    value = rs.getObject(field.getName());
//                } catch (SQLException e) {
//                    throw new SQLException("Error getting value of field " + field.getName() + " from ResultSet", e);
//                }
//                try {
//                    field.set(dto, value);
//                } catch (IllegalAccessException e) {
//                    throw new SQLException("Error setting value " + value + " of field " + field.getName() + " to DTO " + dto.getClass().getSimpleName(), e);
//                }
//            }
//            list.add(dto);
//        }
//        return list;
//    }
}
