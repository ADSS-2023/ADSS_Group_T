package DataLayer.Util;


import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class DAO {
        // Assumes that each DTO has a field called "id" with type int
        public static void insert(Connection connection,DTO dto) throws SQLException {
            String tableName = dto.getTableName();
            String sql = "INSERT INTO " + tableName + " (";
            Field[] fields = dto.getClass().getDeclaredFields();
            for (Field field : fields) {
                sql += field.getName() + ",";
            }
            sql = sql.substring(0, sql.length() - 1); // Remove trailing comma
            sql += ") VALUES (";
            for (int i = 0; i < fields.length; i++) {
                sql += "?,";
            }
            sql = sql.substring(0, sql.length() - 1); // Remove trailing comma
            sql += ")";
            PreparedStatement statement = connection.prepareStatement(sql);
            int index = 1;
            for (Field field : fields) {
                field.setAccessible(true);
                Object value;
                try {
                    value = field.get(dto);
                } catch (IllegalAccessException e) {
                    throw new SQLException("Error accessing field " + field.getName() + " of DTO " + dto.getClass().getSimpleName(), e);
                }
                statement.setObject(index, value);
                index++;
            }
            statement.executeUpdate();
        }
        public static void update(Connection connection, DTO dto) throws SQLException {
            String tableName = dto.getTableName();
            String sql = "UPDATE " + tableName + " SET ";
            Field[] fields = dto.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (!field.getName().equals("id")) {
                    sql += field.getName() + " = ?, ";
                }
            }
            sql = sql.substring(0, sql.length() - 2); // Remove trailing comma and space
            sql += " WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            int index = 1;
            for (Field field : fields) {
                if (!field.getName().equals("id")) {
                    field.setAccessible(true);
                    Object value;
                    try {
                        value = field.get(dto);
                    } catch (IllegalAccessException e) {
                        throw new SQLException("Error accessing field " + field.getName() + " of DTO " + dto.getClass().getSimpleName(), e);
                    }
                    statement.setObject(index, value);
                    index++;
                }
            }
            // Set the value of the id field as the last parameter
            Field idField;
            try {
                idField = dto.getClass().getDeclaredField("id");
            } catch (NoSuchFieldException e) {
                throw new SQLException("DTO " + dto.getClass().getSimpleName() + " does not have an id field");
            }
            idField.setAccessible(true);
            Object idValue;
            try {
                idValue = idField.get(dto);
            } catch (IllegalAccessException e) {
                throw new SQLException("Error accessing id field of DTO " + dto.getClass().getSimpleName(), e);
            }
            statement.setObject(index, idValue);
            statement.executeUpdate();
        }

}

