package DataLayer.HR_T_DAL.DalService;

import BusinessLayer.Transport.Delivery;
import BusinessLayer.Transport.Supplier;
import DataLayer.HR_T_DAL.DAOs.DeliveryDAO;
import DataLayer.HR_T_DAL.DTOs.*;
import DataLayer.Util.DAO;

import java.sql.Connection;
import java.sql.SQLException;

public class DalDeliveryService {

    private Connection connection;
    private DeliveryDAO deliveryDAO;

    private DAO dao;

    public DalDeliveryService(Connection connection) {
        this.connection = connection;
        this.deliveryDAO = new DeliveryDAO(connection);
        this.dao = new DAO(connection);
    }

    public void addDelivery(Delivery delivery) throws SQLException {
        DeliveryDTO dto = new DeliveryDTO(delivery.getId(),delivery.getDate().toString(),
                delivery.getDepartureTime().toString(),delivery.getTruckWeight(),
                delivery.getSource().getAddress(),delivery.getDriverID(),delivery.getTruckNumber(),
                delivery.getShippingArea());
       deliveryDAO.insert(dto);
    }

    public void addUnHandledSupplier(int deliveryId, String supplierAddress, String productName, int amount) throws SQLException {
        DeliveryUnHandledSuppliersDTO dto = new DeliveryUnHandledSuppliersDTO(deliveryId,supplierAddress,productName,amount);
        dao.insert(dto);
    }

    public void addHandledSupplier(int deliveryId, String supplierAddress, String productName, int amount) throws SQLException {
        DeliveryHandledSuppliersDTO dto = new DeliveryHandledSuppliersDTO(deliveryId,supplierAddress,productName,amount);
        dao.insert(dto);
    }

    public void addUnHandledBranch(int deliveryId, String supplierAddress, String productName, int amount) throws SQLException {
        DeliveryHandledBranchesDTO dto = new DeliveryHandledBranchesDTO(deliveryId,supplierAddress,productName,amount);
        dao.insert(dto);
    }

    public void addHandledBranch(int deliveryId, String supplierAddress, String productName, int amount) throws SQLException {
        DeliveryHandledBranchesDTO dto = new DeliveryHandledBranchesDTO(deliveryId,supplierAddress,productName,amount);
        dao.insert(dto);
    }

    public void addSupplier(Supplier supplier) throws SQLException {
        SupplierDTO dto = new SupplierDTO(supplier.getAddress(),supplier.getTelNumber(),
                supplier.getContactName(),supplier.getLocation().getX(),supplier.getLocation().getY());
        dao.insert(dto);
    }
}
