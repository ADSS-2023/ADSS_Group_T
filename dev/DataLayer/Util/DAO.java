package DataLayer.Util;


import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class DAO {
    /**
     * This function gets a dto type, and insert the dto into the suitable table.
     * @param connection sql connection
     * @param dto dto to be inserted
     * @throws SQLException
     */
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

    /**
     * This function gets an old dto and updates the suitable row with new dto.
     * @param connection sql connection
     * @param oldDto dto to be updated
     * @param newDto new value
     * @throws SQLException
     */
    public static void update(Connection connection, DTO oldDto, DTO newDto) throws SQLException {
        String tableName = newDto.getTableName();
        String sql = "UPDATE " + tableName + " SET ";
        Field[] fields = newDto.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (!field.getName().equals("id")) {
                sql += field.getName() + " = ?, ";
            }
        }
        sql = sql.substring(0, sql.length() - 2); // Remove trailing comma and space
        sql += " WHERE ";
        Field[] idFields = oldDto.getClass().getDeclaredFields();
        for (Field field : idFields) {
            field.setAccessible(true);
            Object value;
            try {
                value = field.get(oldDto);
            } catch (IllegalAccessException e) {
                throw new SQLException("Error accessing field " + field.getName() + " of DTO " + oldDto.getClass().getSimpleName(), e);
            }
            sql += field.getName() + " = ? AND ";
        }
        sql = sql.substring(0, sql.length() - 5); // Remove trailing " AND "
        PreparedStatement statement = connection.prepareStatement(sql);
        int index = 1;
        for (Field field : fields) {
            if (!field.getName().equals("id")) {
                field.setAccessible(true);
                Object value;
                try {
                    value = field.get(newDto);
                } catch (IllegalAccessException e) {
                    throw new SQLException("Error accessing field " + field.getName() + " of DTO " + newDto.getClass().getSimpleName(), e);
                }
                statement.setObject(index, value);
                index++;
            }
        }
        for (Field field : idFields) {
            field.setAccessible(true);
            Object value;
            try {
                value = field.get(oldDto);
            } catch (IllegalAccessException e) {
                throw new SQLException("Error accessing field " + field.getName() + " of DTO " + oldDto.getClass().getSimpleName(), e);
            }
            statement.setObject(index, value);
            index++;
        }
        statement.executeUpdate();
    }

    /**
     * This function gets a dto and delete it from the suitable table.
     * @param connection
     * @param dto
     * @throws SQLException
     */
    public static void delete(Connection connection, DTO dto) throws SQLException {
        String tableName = dto.getTableName();
        String sql = "DELETE FROM " + tableName + " WHERE ";
        Field[] idFields = dto.getClass().getDeclaredFields();
        for (Field field : idFields) {
            sql += field.getName() + "=? AND ";
        }
        sql = sql.substring(0, sql.length() - 5); // Remove trailing "AND "
        PreparedStatement statement = connection.prepareStatement(sql);
        int index = 1;
        for (Field field : idFields) {
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
}

