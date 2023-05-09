package DataLayer.HR_T_DAL.DAOs;

import BusinessLayer.Transport.Delivery;
import DataLayer.HR_T_DAL.DTOs.*;
import DataLayer.Util.DAO;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeliveryDAO extends DAO {

    public DeliveryDAO(Connection connection) {
        super(connection);
    }

    public DeliveryDTO getDeliveryById(int id) {
        // Retrieve a delivery's details from the database by its ID
        return null;
    }

    public List<DeliveryDTO> getAllDeliveries() {
        // Retrieve all deliveries from the database
        return null;
    }

    public List<DateToDeliveryDTO> findAllDeliveriesByDate(String date) throws SQLException {
        ArrayList<DateToDeliveryDTO> results = new ArrayList<>();
        String sql = "SELECT * FROM DateToDelivery where shiftDate = " + date;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (resultSet.next()) {
                DateToDeliveryDTO dto = (DateToDeliveryDTO.class).getDeclaredConstructor().newInstance();
                dto.setTableName("DateToDelivery");
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

    public ArrayList<DateToTruckDTO> findAllTrucksByDate(String date) throws SQLException {
        ArrayList<DateToTruckDTO> results = new ArrayList<>();
        String sql = "SELECT * FROM DateToTruck where shiftDate = " + date;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (resultSet.next()) {
                DateToTruckDTO dto = (DateToTruckDTO.class).getDeclaredConstructor().newInstance();
                dto.setTableName("DateToTruck");
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

    public ArrayList<DeliveryUnHandledSitesDTO> findAllUnHandledSuppliersForDelivery(int deliveryId) throws SQLException {
        ArrayList<DeliveryUnHandledSitesDTO> results = new ArrayList<>();
        String sql = "SELECT d.deliveryId, d.siteAddress, d.productName, d.amount\n" +
                "FROM DeliveryUnHandledSites d\n" +
                "INNER JOIN Site s ON d.siteAddress = s.siteAddress\n" +
                "WHERE d.deliveryId = " + deliveryId + " AND s.type = 'Supplier';\n";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (resultSet.next()) {
                DeliveryUnHandledSitesDTO dto = (DeliveryUnHandledSitesDTO.class).getDeclaredConstructor().newInstance();
                dto.setTableName("DeliveryUnHandledSites");
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
