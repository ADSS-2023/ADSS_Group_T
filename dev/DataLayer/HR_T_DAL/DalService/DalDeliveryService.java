package DataLayer.HR_T_DAL.DalService;

import BusinessLayer.Transport.Delivery;
import BusinessLayer.Transport.Product;
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

    public void insertDelivery(Delivery delivery) throws SQLException {
        DeliveryDTO dto = new DeliveryDTO(delivery.getId(),delivery.getDate().toString(),
                delivery.getDepartureTime().toString(),delivery.getTruckWeight(),
                delivery.getSource().getAddress(),delivery.getDriverID(),delivery.getTruckNumber(),
                delivery.getShippingArea());
       dao.insert(dto);
    }

    public void insertUnHandledSupplier(int deliveryId, String supplierAddress, String productName, int amount) throws SQLException {
        DeliveryUnHandledSuppliersDTO dto = new DeliveryUnHandledSuppliersDTO(deliveryId,supplierAddress,productName,amount);
        dao.insert(dto);
    }

    public void insertHandledSupplier(int deliveryId, String supplierAddress, String productName, int amount) throws SQLException {
        DeliveryHandledSuppliersDTO dto = new DeliveryHandledSuppliersDTO(deliveryId,supplierAddress,productName,amount);
        dao.insert(dto);
    }

    public void insertUnHandledBranch(int deliveryId, String supplierAddress, String productName, int amount) throws SQLException {
        DeliveryHandledBranchesDTO dto = new DeliveryHandledBranchesDTO(deliveryId,supplierAddress,productName,amount);
        dao.insert(dto);
    }

    public void insertHandledBranch(int deliveryId, String supplierAddress, String productName, int amount) throws SQLException {
        DeliveryHandledBranchesDTO dto = new DeliveryHandledBranchesDTO(deliveryId,supplierAddress,productName,amount);
        dao.insert(dto);
    }

    public void insertSupplier(Supplier supplier) throws SQLException {
        SupplierDTO dto = new SupplierDTO(supplier.getAddress(),supplier.getTelNumber(),
                supplier.getContactName(),supplier.getLocation().getX(),supplier.getLocation().getY(),supplier.getShippingArea());
        dao.insert(dto);
    }

    public void insertBranch(Supplier supplier) throws SQLException {
        BranchDTO dto = new BranchDTO(supplier.getAddress(),supplier.getTelNumber(),
                supplier.getContactName(),supplier.getLocation().getX(),supplier.getLocation().getY(),supplier.getShippingArea());
        dao.insert(dto);
    }

    public void insertProduct(Product product) throws SQLException{
        ProductDTO dto = new ProductDTO(product.getName(),product.getCoolingLevel().toString());
        dao.insert(dto);
    }
    public void insertDateToDelivery(String shiftDate, int deliveryId) throws SQLException {
        DateToDeliveryDTO dto = new DateToDeliveryDTO(shiftDate, deliveryId);
        dao.insert(dto);
    }

    public void insertDateToTruck(String shiftDate, int truckId) throws SQLException {
        DateToTruckDTO dto = new DateToTruckDTO(shiftDate, truckId);
        dao.insert(dto);
    }

    public void deleteUnHandledSupplier(int deliveryId, String supplierAddress, String productName, int amount) throws SQLException {
        DeliveryUnHandledSuppliersDTO dto = new DeliveryUnHandledSuppliersDTO(deliveryId,supplierAddress,productName,amount);
        dao.delete(dto);
    }

    public void deleteHandledSupplier(int deliveryId, String supplierAddress, String productName, int amount) throws SQLException {
        DeliveryHandledSuppliersDTO dto = new DeliveryHandledSuppliersDTO(deliveryId,supplierAddress,productName,amount);
        dao.delete(dto);
    }

    public void deleteUnHandledBranch(int deliveryId, String supplierAddress, String productName, int amount) throws SQLException {
        DeliveryHandledBranchesDTO dto = new DeliveryHandledBranchesDTO(deliveryId,supplierAddress,productName,amount);
        dao.delete(dto);
    }

    public void deleteHandledBranch(int deliveryId, String supplierAddress, String productName, int amount) throws SQLException {
        DeliveryHandledBranchesDTO dto = new DeliveryHandledBranchesDTO(deliveryId,supplierAddress,productName,amount);
        dao.delete(dto);
    }

    public void deleteDateToDelivery(String shiftDate, int deliveryId) throws SQLException {
        DateToDeliveryDTO dto = new DateToDeliveryDTO(shiftDate, deliveryId);
        dao.delete(dto);
    }

    public void deleteDateToTruck(String shiftDate, int truckId) throws SQLException {
        DateToTruckDTO dto = new DateToTruckDTO(shiftDate, truckId);
        dao.delete(dto);
    }
}
