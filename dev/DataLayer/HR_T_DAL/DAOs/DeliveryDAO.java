package DataLayer.HR_T_DAL.DAOs;

import BusinessLayer.Transport.Delivery;
import DataLayer.HR_T_DAL.DTOs.DeliveryDTO;
import DataLayer.Util.DAO;

import java.sql.Connection;
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










}
