package DataLayer.Util;


import java.lang.reflect.Field;
import java.sql.*;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAO {

    private Connection connection;

    public DAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * This function gets a dto type, and insert the dto into the suitable table.
     * @param dto dto to be inserted
     * @throws SQLException
     */
    public void insert(DTO dto) throws SQLException {
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
    public void update(DTO oldDto, DTO newDto) throws SQLException {
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
     * @param dto
     * @throws SQLException
     */
    public void delete(DTO dto) throws SQLException {
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

    public <T extends DTO> T find(Object pkVal, String pkName, String tableName, Class<T> dtoClass) throws SQLException {
        T result = null;
        String sql = "SELECT * FROM " + tableName + " WHERE " + pkName + " = ?";


        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, pkVal);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                result = dtoClass.getDeclaredConstructor().newInstance();
                result.setTableName(tableName);

                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();

                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object value = resultSet.getObject(i);
                    Field field = result.getClass().getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(result, value);
                }
            }
        } catch (ReflectiveOperationException e) {
            throw new SQLException("Error creating DTO instance", e);
        }

        return result;
    }

    public <T extends DTO> T find(Object pkVal1, String pkName1,Object pkVal2, String pkName2, String tableName, Class<T> dtoClass) throws SQLException {
        T result = null;
        String sql = "SELECT * FROM " + tableName + " WHERE " + pkName1 + " = ? and " + pkName2 + " = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, pkVal1);
            statement.setObject(2, pkVal2);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                result = dtoClass.getDeclaredConstructor().newInstance();
                result.setTableName(tableName);

                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();

                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object value = resultSet.getObject(i);
                    Field field = result.getClass().getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(result, value);
                }
            }
        } catch (ReflectiveOperationException e) {
            throw new SQLException("Error creating DTO instance", e);
        }

        return result;
    }


    public <T extends DTO> ArrayList<T> findAll(String tableName, Class<T> dtoClass) throws SQLException {
        ArrayList<T> results = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                T dto = dtoClass.getDeclaredConstructor().newInstance();
                dto.setTableName(tableName);

                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object value = resultSet.getObject(i);
                    Field field = dto.getClass().getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(dto, value);
                }

                results.add(dto);
            }
        } catch (ReflectiveOperationException e) {
            throw new SQLException("Error creating DTO instance", e);
        }

        return results;
    }
}

