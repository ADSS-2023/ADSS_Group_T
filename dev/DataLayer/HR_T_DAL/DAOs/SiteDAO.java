package DataLayer.HR_T_DAL.DAOs;

import DataLayer.HR_T_DAL.DTOs.SiteDTO;
import DataLayer.Util.DAO;
import DataLayer.Util.DTO;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;

public class SiteDAO extends DAO {

    public SiteDAO(Connection connection) {
        super(connection);
    }

    public ArrayList<SiteDTO> findAllSite(String siteType) throws SQLException {
        ArrayList<SiteDTO> results = new ArrayList<>();
        String sql = "SELECT * FROM Site where type = " + siteType;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (resultSet.next()) {
                SiteDTO dto = (SiteDTO.class).getDeclaredConstructor().newInstance();
                dto.setTableName("Site");
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
