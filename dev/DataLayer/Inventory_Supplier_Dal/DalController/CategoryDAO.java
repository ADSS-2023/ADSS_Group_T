package DataLayer.Inventory_Supplier_Dal.DalController;

import DataLayer.Util.DAO;
import DataLayer.Util.DTO;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;

public class CategoryDAO extends DAO {
    public CategoryDAO(){};

    public <T extends DTO> ArrayList<T> findAll(String tableName, Class<T> dtoClass, Connection connection) throws SQLException {
        ArrayList<T> results = new ArrayList<>();
        String sql = "SELECT * FROM "+tableName+" ORDER BY index_item ASC";

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
